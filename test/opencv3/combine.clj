(ns opencv3.combine
 (:require [opencv3.core :refer :all]))

; Combining multiple pictures into one

(def left (imread "resources/images/cat.jpg"))
(def middle (imread "resources/images/cat.jpg"))
(def right (imread "resources/images/cat.jpg"))

(def target (new-mat))
(hconcat [left middle right] target)
(imwrite target "output/combined.png")

(def target2 (new-mat))
(vconcat [target target] target2)
(imwrite target2 "output/combined.png")
