(ns opencv3.combine
 (:require [opencv3.core :refer :all]))

;
; Combining multiple pictures into one
; 

; simple vertical
(def left (imread "resources/images/cat.jpg"))
(def middle (imread "resources/images/cat.jpg"))
(def right (imread "resources/images/cat.jpg"))

(def target (new-mat))
(hconcat [left middle right] target)
(imwrite target "output/combined.png")

; simple horizontal
(def target2 (new-mat))
(vconcat [target target] target2)
(imwrite target2 "output/combined.png")

; why it is interesting to use clojure here
(defn dilate1[src dilation-size]
  (-> src
  (clone)
  (dilate!
    (get-structuring-element
      MORPH_RECT
      (new-size (inc (* 2 dilation-size)) (inc (* 2 dilation-size)))))))

(def cat (imread "resources/images/cat2.png"))
(def target (new-mat))
(vconcat
  (map
   (partial dilate1 cat)
   (map (partial * 10 ) (range 5)))
  target)

(imwrite target "output/cat.png")
