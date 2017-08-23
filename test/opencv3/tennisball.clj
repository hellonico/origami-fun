(ns opencv3.tennisball
  (:require
    [opencv3.utils :as u]
    [opencv3.colors.rgb :as color]
    [opencv3.core :refer :all]))

; https://stackoverflow.com/questions/31504366/opencv-for-java-houghcircles-finding-all-the-wrong-circles

;
; Method 1 : Use Hough Circles
;
(def img (-> "http://i.imgur.com/uoNRu60.jpg" (u/mat-from-url)))
(def hsv (-> img clone (cvt-color! COLOR_RGB2HSV)))
(def thresh-image (new-mat))
(in-range hsv (new-scalar 50 100 0) (new-scalar 95 255 255) thresh-image)

; the ball is here
(u/show thresh-image)

(def circles (new-mat))
(def minRadius 50)
(def maxRadius 80)
(hough-circles thresh-image circles CV_HOUGH_GRADIENT 1 minRadius 120 15 minRadius maxRadius)

(def output (clone img))
(dotimes [i (.cols circles)]
  (let [ circle (.get circles 0 i) x (nth circle 0) y (nth circle 1) r (nth circle 2)  p (new-point x y)]
  (opencv3.core/circle output p (int r) color/red-2 1)))

; the ball is detected
(u/show output)

;
; Method 2: Use find Contours
;
(def img (-> "http://i.imgur.com/uoNRu60.jpg" (u/mat-from-url)))
(def hsv (-> img clone (cvt-color! COLOR_RGB2HSV)))
(def thresh-image (new-mat))
(in-range hsv (new-scalar 50 100 0) (new-scalar 95 255 255) thresh-image)

(def contours (new-arraylist))
(def mask (new-mat))
(def output (clone img))
(find-contours thresh-image contours mask RETR_LIST CHAIN_APPROX_SIMPLE)
(dotimes [ci (.size contours)]
 (if (> (contour-area (.get contours ci)) 100 )
  (draw-contours output contours ci color/plum 2)))

(u/show output)
