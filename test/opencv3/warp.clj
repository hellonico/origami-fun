(ns opencv3.warp
  (:import
    [org.opencv.calib3d Calib3d])
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

(def image2
  (imread "resources/images/warp/lena.png"))

(def image
  (-> "resources/images/warp/iknow.jpg"
  imread
  (resize! (.size image2))))

(def prev
  (u/matrix-to-matofpoint2f [
    [-30 -60]
    [(+ (cols image) 50) -50]
    [(+ (cols image) 100) (+ (rows image) 50)]
    [-50 (+ 50 (rows image))]
    ]))

(def post
  (u/matrix-to-matofpoint2f [
    [0 0]
    [ (dec (cols image)) 0]
    [(dec (cols image)) (dec (rows image))]
    [ 0 (dec (rows image))]
    ]))

(def homography (Calib3d/findHomography prev post))
(def warped (new-mat))
(warp-perspective image warped homography (size image))

(def contours (new-arraylist))
(-> warped
  clone
  (cvt-color! COLOR_BGR2GRAY)
  (find-contours contours (new-mat) RETR_EXTERNAL CHAIN_APPROX_NONE))

(def mask (new-mat (.size image) CV_8U))
(set-to mask (new-scalar 0.0))
(draw-contours mask contours 0 (new-scalar 255.0) -1)

(erode mask mask (new-mat))
(copy-to warped image2 mask)

(u/show image2 {:frame {:width 600 :height 600}})
