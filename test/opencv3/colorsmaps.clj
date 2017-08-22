(ns opencv3.colormaps
 (:require
  [opencv3.core :refer :all]
  [opencv3.utils :as u]))

;
; Taken from the following tutorial
; https://www.learnopencv.com/applycolormap-for-pseudocoloring-in-opencv-c-python/#download
;

;
; Have fun with one color maps
;
(->
  "resources/images/planets/kepler.jpg"
  imread
  (apply-color-map!  COLORMAP_HOT)
  (imwrite "output/planet.jpg"))

;
; Apply all color maps to a single source
; And display then all in one mat
;
(def colors-maps
  [
"COLORMAP_HOT"
"COLORMAP_HSV"
"COLORMAP_JET"
"COLORMAP_BONE"
"COLORMAP_COOL"
"COLORMAP_PINK"
"COLORMAP_RAINBOW"
"COLORMAP_OCEAN"
"COLORMAP_WINTER"
"COLORMAP_SUMMER"
"COLORMAP_AUTUMN"
"COLORMAP_SPRING"])

(defn change-color [ source color-map-string ]
  (->
  source
  clone
  (apply-color-map! (eval (read-string color-map-string)))
  (put-text!
    color-map-string
    (new-point 70 70) FONT_HERSHEY_PLAIN 5 (new-scalar 255 255 255) 3)))

(def source
  (-> "resources/images/planets/kepler.jpg" imread))

(def targets
  (cons
    (-> source clone   (put-text!
      "ORIGINAL"
      (new-point 70 70) FONT_HERSHEY_PLAIN 5 (new-scalar 255 255 255) 3))
   (map (partial change-color source) colors-maps)))

(imwrite (vconcat! targets) "output/colormaps.png")
