(ns opencv3.hull
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u]))

; http://docs.opencv.org/2.4/doc/tutorials/imgproc/shapedescriptors/hull/hull.html

; find the convex hull object for each object

(def kikyu
  (->
    "http://www.v3wall.com/wallpaper/1366_768/0912/1366_768_20091223010850201138.jpg"
   u/mat-from-url))

(def work
  (-> kikyu
    clone
   (cvt-color! COLOR_BGR2GRAY)
   (blur! (new-size 3 3))
   (threshold! 110 255 THRESH_BINARY)
   (imwrite "output/kikyu_work.png")))

(def contours (new-list))
(find-contours work contours (new-mat) RETR_LIST CHAIN_APPROX_SIMPLE (new-point 0 0))

(defn draw-contours! [img contours]
 (dotimes [i (.size contours)]
  (let [c (.get contours i)
     m2f (new-matofpoint2f (.toArray c))
     len (arc-length m2f true)
     ret (new-matofpoint2f)
     approx (approx-poly-dp m2f ret (* 0.01 len) true) ; ?
     nb-sides (.size (.toList ret))]
     (draw-contours img contours i (new-scalar 70 0 0) 10))) img)

; find the important indexes using hull
(defn convert-indexes-to-point [ contour indexes ]
 (let [
    arr-contour (.toArray contour)
    arr-points (into-array org.opencv.core.Point (map #(nth arr-contour %) (.toArray indexes)))
    hull (new-matofpoint)
    ]
    (.fromArray hull arr-points)
    hull))

(defn contour-to-hull [ contours ]
  (map
    (fn[c]
      (let[ matofint (new-matofint) ]
        (convex-hull c matofint false)
        (convert-indexes-to-point c matofint)))
     contours))

(->
  kikyu
  clone
  (draw-contours! (contour-to-hull contours))
  (imwrite "output/kikyu.png"))
