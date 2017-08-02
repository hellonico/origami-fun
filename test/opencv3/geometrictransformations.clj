(ns opencv3.geometrictransformations
  (:require [opencv3.core :refer :all]
        [opencv3.utils :as u]))

; http://docs.opencv.org/trunk/da/d6e/tutorial_py_geometric_transformations.html

; OpenCV provides two transformation functions, cv2.warpAffine and cv2.warpPerspective,
; with which you can have all kinds of transformations. cv2.warpAffine takes a 2x3 transformation matrix while cv2.warpPerspective takes a 3x3 transformation matrix as input.

; In affine transformation, all parallel lines in the original image will still be parallel in the output image.
;
; To find the transformation matrix, we need three points from input image and their corresponding locations in output image.
; 
; Then cv2.getAffineTransform will create a 2x3 matrix which is to be passed to cv2.warpAffine.

;
; Affine Transformation
;
(def pts1
  (u/matrix-to-matofpoint2f
    [[50 50]
     [200 50]
     [50 200]]))

(def pts2
  (u/matrix-to-matofpoint2f
    [[10 100]
     [200 50]
     [100 250]]))

(def M
  (get-affine-transform pts1 pts2))

(def img
  (-> "resources/images/cat.jpg"
  imread
  (u/resize-by 0.5)))

(-> img clone (warp-affine! M (.size img )) u/show)

;
; Perspective Transformation
;
(def points1
  (u/matrix-to-matofpoint2f
  [[0 0] [300 0] [0 300] [300 300]]))

(def points2
  (u/matrix-to-matofpoint2f
    [[56 65] [368 52] [28 387] [389 390]]))

(def T
  (get-perspective-transform points1 points2))

(-> img clone (u/resize-by 0.5) (warp-perspective! T (.size img )) u/show)
