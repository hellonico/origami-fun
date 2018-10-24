(ns opencv4.movetocenter
(:require
  [opencv4.utils :as u]
  [opencv4.core :refer :all]))

; https://stackoverflow.com/questions/32590277/move-area-of-an-image-to-the-center-using-opencv

(def img (imread "resources/morph/cjy6M.jpg"))
(def hsv (new-mat))
(cvt-color img hsv COLOR_BGR2HSV)

; thresh hold on yellow in hsv space
(def mask-on-yellow (new-mat))
(in-range hsv (new-scalar 20 100 100) (new-scalar 40 255 255) mask-on-yellow)

; find contours, and select first 1, (actually only one)
(def contours (new-arraylist))
(find-contours mask-on-yellow contours (new-mat) RETR_EXTERNAL CHAIN_APPROX_SIMPLE)
(count contours)
(def background-color (new-scalar 0 0 0))

; mask type CV_8UC1 is important !!
(def mask (new-mat (.rows img) (.cols img) CV_8UC1 background-color))
(draw-contours mask contours 0 (new-scalar 255.0) FILLED)

(def box (bounding-rect (first contours)))
(def item (.submat img box))

(def segmented-item
  (new-mat (.rows item) (.cols item) CV_8UC3 background-color))
(.copyTo item segmented-item (.submat mask box) )

(def center
  (new-point (/ (.cols img ) 2 ) (/ (.rows img) 2)))

(def center-box
  (new-rect
    (- (.-x center ) (/ (.-width box) 2))
    (- (.-y center ) (/ (.-height box) 2))
    (.-width box)
    (.-height box)))

(def result  (u/mat-from img))
(def final (.submat result center-box))
(.copyTo segmented-item final (new-mat))

(u/show result)
