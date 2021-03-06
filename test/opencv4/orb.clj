(ns opencv4.orb
  (:require
    [opencv4.core :refer :all]
    [opencv4.utils :as u])
  (:import
    [org.opencv.calib3d Calib3d]
    [org.opencv.features2d Features2d DescriptorExtractor DescriptorMatcher FeatureDetector]))
;;;
; DETECTION WITH ORB
;;;

; ORB detector
; http://docs.opencv.org/3.1.0/dc/dc3/tutorial_py_matcher.html#gsc.tab=0
; http://stackoverflow.com/questions/14940111/opencv-featuredetector
; http://www.answers.opencv.org/question/3167/java-how-to-set-parameters-to-orb-featuredetector/
; http://docs.opencv.org/java/3.1.0/org/opencv/features2d/FeatureDetector.html
; http://d.hatena.ne.jp/ramencozo/20130626/1372255948

(def detector (FeatureDetector/create FeatureDetector/ORB))
(def extractor  (DescriptorExtractor/create DescriptorExtractor/ORB))

(def original (imread "resources/images/cat.jpg"))
(def mat1 (clone original))
(def points1 (new-matofkeypoint))
(.detect detector mat1 points1)

(def mat2 (imread "resources/images/cat_face.jpg"))
(def points2 (new-matofkeypoint))
(.detect detector mat2 points2)

(def desc1 (new-mat))
(def desc2 (new-mat))
(.compute extractor mat1 points1 desc1)
(.compute extractor mat2 points2 desc2)

(def matcher (DescriptorMatcher/create DescriptorMatcher/BRUTEFORCE_HAMMING))
(def matches (new-matofdmatch))
(.match matcher desc1 desc2 matches)

(defn best-n-dmatches[n dmatches]
  (new-matofdmatch
    (into-array org.opencv.core.DMatch
      (take n (sort-by #(.-distance %) (.toArray dmatches))))))

(defn draw-matches [_mat1 _points1 _mat2 _points2 _matches n draw-method]
  (let[ _mat (new-mat (* 2 (.rows _mat1)) (* 2 (.cols _mat1)) (.type _mat1))
        _sorted-matches (best-n-dmatches n _matches)]

    (Features2d/drawMatches
              _mat1
              _points1
              _mat2
              _points2
              _sorted-matches
              _mat
              (new-scalar 255 0 0)
              (new-scalar 0 0 255)
              (new-matofbyte)
              draw-method)
      _mat))

(imwrite
  (draw-matches mat1 points1 mat2 points2 matches 10 Features2d/NOT_DRAW_SINGLE_POINTS)
  "output/detection.png")

(imwrite
  (draw-matches mat1 points1 mat2 points2 matches 20 Features2d/DRAW_RICH_KEYPOINTS)
  "output/detection.png")

(imwrite
  (draw-matches mat1 points1 mat2 points2 matches 20 Features2d/NOT_DRAW_SINGLE_POINTS)
  "output/detection.png")



;;;
; HOMOGRAPHY
;;;

(def re-matches
  (best-n-dmatches 30 matches))

(def good-matches-list
  (.toList re-matches) )

(def points1-list (.toList points1))
(def points2-list (.toList points2))

(def obj-list (new-arraylist))
(def scene-list (new-arraylist))

(doseq [match good-matches-list]
  (.add obj-list (.-pt (.get points2-list (.-queryIdx match))))
  (.add scene-list (.-pt (.get points1-list (.-trainIdx match)))))

(def obj
  (doto (new-matofpoint2f)
    (.fromList obj-list)))

(def scene (doto (new-matofpoint2f)
  (.fromList scene-list)))

(def H
  (find-homography obj scene RHO 5))

(def warpimg (clone mat2))
(def ims (new-size (.cols mat2) (.rows mat2)))
(warp-perspective mat2 warpimg H ims)

(def obj-corners (new-mat 4 1 CV_32FC2))
(.put obj-corners 0 0 (double-array [0 0]))
(.put obj-corners 1 0 (double-array [(.cols mat2)  0]))
(.put obj-corners 2 0 (double-array [(.cols mat2)  (.rows mat2)]))
(.put obj-corners 3 0 (double-array [0 (.rows mat2)]))

(def scene-corners (new-mat 4 1 CV_32FC2))
(perspective-transform obj-corners scene-corners H)

(def detect-mat (clone mat1))
(line detect-mat (new-point (.get scene-corners 0 0)) (new-point (.get scene-corners 1 0)) (new-scalar 0 255 0) 4)
(line detect-mat (new-point (.get scene-corners 1 0)) (new-point (.get scene-corners 2 0)) (new-scalar 255 0) 4)
(line detect-mat (new-point (.get scene-corners 2 0)) (new-point (.get scene-corners 3 0)) (new-scalar 0 255 0) 4)
(line detect-mat (new-point (.get scene-corners 3 0)) (new-point (.get scene-corners 0 0)) (new-scalar 0 255 0) 4)

(imwrite detect-mat "output/detection.png")
