(ns opencv3.affine
  (:require
     [opencv3.core :refer :all]
     [opencv3.utils :as u]))


;
; laplacian variation
;
; https://stackoverflow.com/questions/36413394/opencv-variation-of-the-laplacian-java
; http://www.pyimagesearch.com/2015/09/07/blur-detection-with-opencv/
;
; used to detect blur in an image

(def img
  (-> "resources/images/cat.jpg" imread))

(def kernel
  (u/matrix-to-mat
  [ [0 -1 0]
    [-1 4 -1]
    [0 -1 0]]))

(filter-2-d! img -1 kernel)
(def std (new-matofdouble))
(def median (new-matofdouble))
(mean-std-dev img median std)

(Math/pow (first (.get std 0 0)) 2)

;
; implementation using a function
;

(def laplacian-kernel (u/matrix-to-mat
[ [0 -1 0]
  [-1 4 -1]
  [0 -1 0]]))

(defn std-laplacian [img]
  (let [ std (new-matofdouble)]
    (filter-2-d! img -1 laplacian-kernel)
    (mean-std-dev img (new-matofdouble) std)
    (Math/pow (first (.get std 0 0)) 2)))

(defn is-image-blurred?[img]
  (< (std-laplacian img) 100))

;
; Samples
;

(comment
 (-> "resources/images/tiger-blur.gif" imread is-image-blurred?)
 (-> "resources/blurred/blurred_cat.jpg" imread is-image-blurred?)

 (-> "resources/images/laptop-cat.jpg" imread is-image-blurred?)

)
