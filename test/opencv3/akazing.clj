(ns opencv3.akazing
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u])
  (:import
    [org.opencv.features2d Features2d DescriptorExtractor DescriptorMatcher FeatureDetector]))
;;;
; DETECTION WITH AKAZE
;;;

(def detector (FeatureDetector/create FeatureDetector/AKAZE))
(def extractor  (DescriptorExtractor/create DescriptorExtractor/AKAZE))

(def original (imread "resources/images/cat.jpg"))
(def mat1 (clone original))
(def points1 (new-matofkeypoint))
(.detect detector mat1 points1)

; (def mat2 (imread "resources/images/cat_face.jpg" ))
; (def mat2 (imread "resources/images/lena.png" ))
; (def mat2 (->
  ; (imread "resources/images/rotate/rotatedcat30.png" )
  ; (blur! (new-size 51 51))
  ; (gaussian-blur! (new-size 9 9) 20 20)
  ; (dilate! (get-structuring-element MORPH_RECT (new-size 3 3)))
  ; ))
; (def mat2 (imread "resources/nico.jpg"))
; (def mat2 (->
;   (imread "resources/images/rotate/rotatedcat60.png" )))
(def mat2 (-> "resources/images/rotate/warped.png" imread))

(def points2 (new-matofkeypoint))
(.detect detector mat2 points2)

(def show-keypoints1 (new-mat))
(Features2d/drawKeypoints mat1 points1 show-keypoints1 (new-scalar 255 0 0) 0)
(u/show show-keypoints1)

(def show-keypoints2 (new-mat))
(Features2d/drawKeypoints mat2 points2 show-keypoints2 (new-scalar 255 0 0) 0)
(u/show show-keypoints2)


(def desc1 (new-mat))
(def desc2 (new-mat))
(.compute extractor mat1 points1 desc1)
(.compute extractor mat2 points2 desc2)

(def matcher (DescriptorMatcher/create DescriptorMatcher/BRUTEFORCE_HAMMINGLUT))
(def matches (new-matofdmatch))
(.match matcher desc1 desc2 matches)

(defn best-n-dmatches2[dmatches]
  (new-matofdmatch
    (into-array org.opencv.core.DMatch
      (filter #(< (.-distance %) 10) (.toArray dmatches)))))

(defn draw-matches [_mat1 _points1 _mat2 _points2 _matches draw-method]
  (let[ _mat (new-mat (* 2 (.rows _mat1)) (* 2 (.cols _mat1)) (.type _mat1))
        _sorted-matches (best-n-dmatches2 _matches)]

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
(defn is-a-match [dmatches]
  (> (count (filter #(< (.-distance %) 10) (.toArray dmatches))) 0))

(is-a-match matches)

(imwrite
  (draw-matches mat1 points1 mat2 points2 matches Features2d/NOT_DRAW_SINGLE_POINTS)
  "output/detection.png")

