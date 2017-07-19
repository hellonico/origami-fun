(ns opencv3.orb
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

(def detector (FeatureDetector/create FeatureDetector/ORB))
(def extractor  (DescriptorExtractor/create DescriptorExtractor/ORB))

(def mat1 (imread "resources/images/cat.jpg"))
(cvt-color! mat1 BGR )
;(def mat1 (turn-to-gray _mat1))
(def points1 (MatOfKeyPoint.))
(.detect detector mat1 points1)

(def mat2 (imread "resources/images/cat_face.jpg"))
;(def mat2 (turn-to-gray _mat2))
(def points2 (MatOfKeyPoint.))
(.detect detector mat2 points2)

(def desc1 (u/mat-from mat1))
(def desc2 (u/mat-from mat2))
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
(def mat3 (new-mat (.rows mat1) (* 2 (.cols mat1)) (.type mat1)))
; (Features2d/drawMatches mat1 points1 mat2 points2 sorted-matches mat3)
(Features2d/drawMatches
          mat1
          points1
          mat2
          points2
          sorted-matches
          mat3
          (new-scalar 255 0 0)
          (new-scalar 0 0 255)
          (MatOfByte.)
          Features2d/DRAW_RICH_KEYPOINTS)
(imwrite mat3 "output/detection.png")
