(ns opencvfun.featuredetect
  (:use [opencvfun.utils])
  (:require [opencv3.core :refer :all])
  (:import
    [java.util LinkedList]
    [org.opencv.calib3d Calib3d]
    [org.opencv.features2d Features2d DescriptorExtractor DescriptorMatcher FeatureDetector]
    [org.opencv.core Size MatOfByte MatOfPoint2f Scalar DMatch MatOfDMatch MatOfKeyPoint Mat Core CvType Mat]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.imgproc Imgproc]))

; ORB detector
; http://docs.opencv.org/3.1.0/dc/dc3/tutorial_py_matcher.html#gsc.tab=0
; http://stackoverflow.com/questions/14940111/opencv-featuredetector
; http://www.answers.opencv.org/question/3167/java-how-to-set-parameters-to-orb-featuredetector/
; http://docs.opencv.org/java/3.1.0/org/opencv/features2d/FeatureDetector.html
; http://d.hatena.ne.jp/ramencozo/20130626/1372255948

;;;
; DETECTION WITH ORB
;;;

(def detector (FeatureDetector/create FeatureDetector/ORB))
(def extractor  (DescriptorExtractor/create DescriptorExtractor/ORB))

(def mat1 (Imgcodecs/imread "resources/images/cat.jpg"))
;(def mat1 (turn-to-gray _mat1))
(def points1 (MatOfKeyPoint.))
(.detect detector mat1 points1)

(def mat2 (Imgcodecs/imread "resources/images/cat_face.jpg"))
;(def mat2 (turn-to-gray _mat2))
(def points2 (MatOfKeyPoint.))
(.detect detector mat2 points2)

(def desc1 (mat-from mat1))
(def desc2 (mat-from mat2))
(.compute extractor mat1 points1 desc1)
(.compute extractor mat2 points2 desc2)

(def matcher (DescriptorMatcher/create DescriptorMatcher/BRUTEFORCE_HAMMING))
(def matches (MatOfDMatch.))
(.match matcher desc1 desc2 matches)

(defn best-n-dmatches[n dmatches]
  (MatOfDMatch.
    (into-array DMatch (take n (sort-by #(.-distance %) (.toArray dmatches))))))

(def sorted-matches
  (best-n-dmatches 20 matches))

; show differences on a picture (not needed for after)
(def mat3 (Mat. (.rows mat1) (* 2 (.cols mat1)) (.type mat1)))
(Features2d/drawMatches mat1 points1 mat2 points2 sorted-matches mat3)
(Imgcodecs/imwrite "output/detection.png" mat3)

; http://stackoverflow.com/questions/26615649/opencv-fitting-an-object-into-a-scene-using-homography-and-perspective-transfor
; https://stackoverflow.com/questions/26771285/opencv-how-to-use-findhomography-correctly

;;;
; COMMON CODE
;;;
(defn draw-matches [_mat1 _points1 _mat2 _points2 _matches n]
  (let[ drawnMatches (MatOfByte.)
        _mat (Mat. (.rows _mat1) (* 2 (.cols _mat1)) (.type _mat1))
        _sorted-matches
         (best-n-dmatches n _matches)]

    ; (Features2d/drawMatches
    ;   _mat1 _points1
    ;   _mat2 _points2
    ;   _sorted-matches
    ;   _mat
    ;   (Scalar/all -1)
    ;   (Scalar/all -1)
    ;   drawnMatches
    ;   (Features2d/NOT_DRAW_SINGLE_POINTS))

    (Features2d/drawMatches
              _mat1
              _points1
              _mat2
              _points2
              _sorted-matches
              _mat
              (Scalar. 255 0 0)
              (Scalar. 0 0 255)
              (MatOfByte.)
              Features2d/NOT_DRAW_SINGLE_POINTS)

      (Imgcodecs/imwrite "target/1.jpg" _mat)))

(draw-matches mat2 points2 mat1 points1 matches 40)
(draw-matches mat1 points1 mat2 points2 matches 20) ; works too

;;;
; Homography
;;;

(def re-matches
  (best-n-dmatches 20 matches))

(def good-matches-list
  (.toList re-matches) )

(def points1-list (.toList points1))
(def points2-list (.toList points2))

(def obj-list (LinkedList.))
(def scene-list (LinkedList.))

(doseq [match good-matches-list]
  (.addLast obj-list (.-pt (.get points1-list (.-queryIdx match))))
  (.addLast scene-list (.-pt (.get points2-list (.-trainIdx match)))))

(def obj (MatOfPoint2f.))
(.fromList obj obj-list)
(def scene (MatOfPoint2f.))
(.fromList scene scene-list)

(def H
  (Calib3d/findHomography obj scene Calib3d/RANSAC 3))
(def warpimg (clone mat1))
(def ims (new-size (.cols mat1) (.rows mat1)))
(warp-perspective mat1 warpimg H ims)
(imwrite warpimg "output/detection.png")
