(ns opencv3.bounding-boxes
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u]))
; http://docs.opencv.org/2.4/doc/tutorials/imgproc/shapedescriptors/bounding_rects_circles/bounding_rects_circles.html

(def kikyuq
  (-> "http://www.v3wall.com/wallpaper/1366_768/0912/1366_768_20091223010850201138.jpg"
   u/mat-from-url))

(def work
  (-> kikyu
    clone
   (cvt-color! COLOR_BGR2GRAY)
   (blur! (new-size 3 3))
   (threshold! 110 255 THRESH_BINARY)
   (imwrite "output/kikyu_work.png")))

(def contours (new-arraylist))
(find-contours work contours (new-mat) RETR_LIST CHAIN_APPROX_SIMPLE (new-point 0 0))

; draw bounding box using bounding-rect
(def output (clone kikyu))
(doseq [c contours]
  (if (> (contour-area c) 100 )
    (let [ rect (bounding-rect c)]
      (if (and  (> (.height rect) 28)  (> (.width rect) 50))
      (rectangle
        output
        (new-point (.x rect) (.y rect))
        (new-point (+ (.width rect) (.x rect)) (+ (.y rect) (.height rect)))
        (new-scalar 70 0 0)
        5)))))
(imwrite output "output/kikyu.png")

; draw contours
(defn draw-contours! [img contours]
 (dotimes [i (.size contours)]
  (let [c (.get contours i)
     m2f (new-matofpoint2f (.toArray c))
     len (arc-length m2f true)
     ret (new-matofpoint2f)
     approx (approx-poly-dp m2f ret (* 0.01 len) true) ; ?
     nb-sides (.size (.toList ret))]
     (draw-contours img contours i (new-scalar 70 0 0) 10))) img)

(->
  kikyu
  clone
  (draw-contours! contours)
  (imwrite "output/kikyu.png"))
