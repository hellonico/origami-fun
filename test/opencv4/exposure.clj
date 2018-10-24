(ns opencv4.exposure
   (:require
     [opencv4.utils :as u]
     [opencv4.core :refer :all]))

(def org (imread "resources/images/cat.jpg"))
(def img1
  (->
   org
   clone
 (convert-to! -1 0.2 0)))
(def img2
 (->
  org
  clone
(convert-to! -1 2 0)))

(def img3 (new-mat))
(add-weighted img1 1.5 img2 0.5 0 img3)
(u/show
  (-> img3 (u/resize-by 0.4))
  {:frame {:width 800 :height 600}})
