(ns opencvfun.homography
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u])
  (:import
    [java.util LinkedList]
    [org.opencv.calib3d Calib3d]
    [org.opencv.features2d Features2d DescriptorExtractor DescriptorMatcher FeatureDetector]
    [org.opencv.core Point Size MatOfByte MatOfPoint2f Scalar DMatch MatOfDMatch MatOfKeyPoint Mat Core CvType Mat]))

;;;
; DETECTION WITH ORB
;;;

(def detector
  (FeatureDetector/create FeatureDetector/ORB))
(def extractor
  (DescriptorExtractor/create DescriptorExtractor/ORB))

(def mat1 (imread "resources/box_in_scene.png"))
(def points1 (MatOfKeyPoint.))
(.detect detector mat1 points1)

(def mat2 (imread "resources/box.png"))
(def points2 (MatOfKeyPoint.))
(.detect detector mat2 points2)

(def desc1 (u/mat-from mat1))
(def desc2 (u/mat-from mat2))
(.compute extractor mat1 points1 desc1)
(.compute extractor mat2 points2 desc2)

(def matcher (DescriptorMatcher/create DescriptorMatcher/BRUTEFORCE))
(def matches (MatOfDMatch.))
(.match matcher desc1 desc2 matches)
(println matches)

(defn best-n-dmatches[n dmatches]
  (MatOfDMatch.
    (into-array DMatch (take n (sort-by #(.-distance %) (.toArray dmatches))))))

(def re-matches
  (best-n-dmatches 30 matches))

(def good-matches-list
  (.toList re-matches) )

(def points1-list (.toList points1))
(def points2-list (.toList points2))

(def obj-list (LinkedList.))
(def scene-list (LinkedList.))

(doseq [match good-matches-list]
  (.addLast obj-list (.-pt (.get points2-list (.-queryIdx match))))
  (.addLast scene-list (.-pt (.get points1-list (.-trainIdx match)))))

(def obj (doto (MatOfPoint2f.)
  (.fromList obj-list)))

(def scene (doto (MatOfPoint2f.)
  (.fromList scene-list)))

(def H
  (Calib3d/findHomography obj scene Calib3d/RHO 5))

(def warpimg (.clone mat2))
(def ims (new-size (.cols mat2) (.rows mat2)))
(warp-perspective mat2 warpimg H ims)

(def obj-corners (new-mat 4 1 CV_32FC2))
(.put obj-corners 0 0 (double-array [0 0]))
(.put obj-corners 1 0 (double-array [(.cols mat2)  0]))
(.put obj-corners 2 0 (double-array [(.cols mat2)  (.rows mat2)]))
(.put obj-corners 3 0 (double-array [0 (.rows mat2)]))

(def scene-corners (new-mat 4 1 CV_32FC2))
(perspective-transform obj-corners scene-corners H)

; clone the original scene
(def detect-mat (.clone mat1))

(line detect-mat (Point. (.get scene-corners 0 0)) (Point. (.get scene-corners 1 0)) (Scalar. 0 255 0) 4)
(line detect-mat (Point. (.get scene-corners 1 0)) (Point. (.get scene-corners 2 0)) (Scalar. 0 255 0) 4)
(line detect-mat (Point. (.get scene-corners 2 0)) (Point. (.get scene-corners 3 0)) (Scalar. 0 255 0) 4)
(line detect-mat (Point. (.get scene-corners 3 0)) (Point. (.get scene-corners 0 0)) (Scalar. 0 255 0) 4)

(imwrite detect-mat "output/detection.png")
