(ns opencv4.roi
(:require
[opencv4.core :refer :all]
[opencv4.utils :as u]))

(def original
  (imread "resources/images/bizcard.png"))

(def work
  (-> original
   clone
   (cvt-color! COLOR_RGB2GRAY)
   (gaussian-blur! (new-size 15 15) 50)
   (threshold!  -1 200 (+ THRESH_BINARY_INV THRESH_OTSU))
   (dilate! (get-structuring-element MORPH_RECT (new-size 2 2)))))

(def contours (new-arraylist))
(find-contours work contours (new-mat) RETR_LIST CHAIN_APPROX_SIMPLE)

(def result
  (clone original))

(doseq [c contours]
  (if (> (contour-area c) 50)
  (let [ rect (bounding-rect c)]
     (if (> (.height rect) 25)
     (do
      (rectangle result
       (new-point (.x rect) (.y rect ))
       (new-point (+ (.width rect) (.x rect)) (+ (.y rect) (.height rect)))
       (new-scalar 0 0 0)
       5
       ))))))

(u/show
  (-> result (u/resize-by 0.3)))
