(ns opencv4.alpha
    (:require
        [opencv4.core :refer :all]
        [opencv4.utils :as u]))

; add
(def a (imread "resources/red_cat.jpg"))
(def b (imread "resources/mountain.jpg"))
(add! a b)
(imwrite a "target/mountain_cat.jpg")

; add weighted
(def a (imread "resources/red_cat.jpg"))
(def b (imread "resources/mountain.jpg"))
(def out (new-mat))
(add-weighted a 0.7 b 0.3 1 out)
(imwrite out "target/mountain_cat.jpg")

; substract
(def a (imread "resources/red_cat.jpg"))
(def b (imread "resources/mountain.jpg"))
(def out (new-mat))
(subtract a b out (new-mat) 0)
(imwrite out "target/mountain_cat.jpg")
