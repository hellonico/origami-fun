(ns opencv3.zoom
  (:require
    [opencv3.utils :as u]
    [opencv3.core :refer :all]))

(->
  "resources/images/cat2.png"
  (imread)
  (resize! (new-size 400 300) 0.5 0.5 INTER_NEAREST)
  (imwrite "output/cat3.png")
  )
