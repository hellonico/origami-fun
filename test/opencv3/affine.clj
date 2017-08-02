(ns opencv3.affine
  (:require [opencv3.core :refer :all]
        [opencv3.utils :as u]))

; http://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/warp_affine/warp_affine.html

;
; ROTATION
;
(->
"resources/images/cat.jpg"
 (imread)
 (u/resize-by 0.25)
 (warp-affine!
   (get-rotation-matrix-2-d (new-point 200 200) 90 1) (new-size 600 400) INTER_NEAREST)
 (u/show {:frame {:width 600 :height 500}}))

; http://docs.opencv.org/trunk/d2/dbd/tutorial_distance_transform.html
; https://github.com/opencv/opencv/blob/master/modules/imgproc/misc/java/test/ImgprocTest.java

(def rose
  (imread "resources/matching/rose_flower.jpg"))
(def src
  (u/matrix-to-matofpoint2f [[0 0] [3 1] [10 40]]))
(def dst
  (u/matrix-to-matofpoint2f [[3 3] [7 4] [5 6]]))
(def transform-mat (get-affine-transform dst src))
(-> rose clone (u/resize-by 0.3) (warp-affine! transform-mat (.size rose)) u/show)
