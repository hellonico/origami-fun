(ns opencv3.siemensstar
  (:require [opencv3.core :refer :all] [opencv3.utils :as u] [opencv3.colors.rgb :as color]))

; https://stackoverflow.com/questions/23326724/draw-a-siemens-star-in-opencv-and-c

(defn siemens-star
  ([ radius nb-pieces] (siemens-star radius nb-pieces [color/cyan-4-]))
  ([ radius nb-pieces colors]
  (let [
    star (new-mat (* 2 radius) (* 2 radius) CV_8UC3 color/white-smoke-)
    center (new-point radius radius)
    angle (/ 360 (* 2 nb-pieces))
    ]
    (doseq [ i (range 0 360 (* 2 angle) ) ]
     (ellipse star center (new-size radius radius) 0 i (+ angle i) (rand-nth colors) FILLED))
    star)))

(u/show
  (siemens-star 256 20 [color/blueviolet color/greenyellow color/magenta-3]))
