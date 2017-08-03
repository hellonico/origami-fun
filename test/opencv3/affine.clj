(ns opencv3.affine
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

; ROTATION
;
(->
"resources/images/cat.jpg"
 imread
 (u/resize-by 0.25)
 (warp-affine!
   (get-rotation-matrix-2-d (new-point 200 200) 90 1) (new-size 600 400) INTER_NEAREST)
 (u/show {:frame {:width 600 :height 500}}))

; http://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/warp_affine/warp_affine.html
; http://docs.opencv.org/trunk/d2/dbd/tutorial_distance_transform.html
; https://github.com/opencv/opencv/blob/master/modules/imgproc/misc/java/test/ImgprocTest.java

(def rose (->
  "resources/matching/rose_flower.jpg"
  imread
  (u/resize-by 0.3)))

(def src
  (u/matrix-to-matofpoint2f [[1 0] [3 1] [5 6]]))
(def dst
  (u/matrix-to-matofpoint2f [[1 0] [3 1] [3 6]]))
(def transform-mat (get-affine-transform dst src))
(-> rose clone (warp-affine! transform-mat (.size rose)) u/show)
