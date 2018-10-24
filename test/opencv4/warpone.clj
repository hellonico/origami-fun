(ns opencv4.warpone
  (:import
    [org.opencv.calib3d Calib3d])
  (:require
    [opencv4.core :refer :all]
    [opencv4.utils :as u]))


; use in akazing.clj

(defn rotate-by! [src angle]
  (let [ factor 1]
  (warp-affine! src
   (get-rotation-matrix-2-d (new-point (* factor (/ (.width src) 2)) (* factor (/ (.height src) 2))) angle 1)
   (new-size (* (.width src) factor ) (* (.height src) factor ) ) INTER_NEAREST)
   src
   ))

(def image
  (-> "resources/images/cat_face.jpg"
  imread))

(def prev
  (u/matrix-to-matofpoint2f [[-30 -60] [(+ (cols image) 50) -250] [(+ (cols image) 100) (+ (rows image) 50)] [-50 (+ 50 (rows image))]]))

(def post
  (u/matrix-to-matofpoint2f [
    [0 0] [ (dec (cols image)) 0] [(dec (cols image)) (dec (rows image))] [ 0 (dec (rows image))]]))

(def homography (Calib3d/findHomography prev post))
(def warped (new-mat))
(warp-perspective image warped homography (size image))
(-> warped
  (rotate-by! 30)
  (imwrite "resources/images/rotate/warped.png")
  u/show
  )
