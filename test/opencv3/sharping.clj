(ns opencv3.sharping
 (:require
  [opencv3.core :refer :all]
  [opencv3.utils :as u]))


(defn sharpen [src target]
  (let [
    image (u/mat-from src)]
    (gaussian-blur src image (new-size 0 0) 3)
    (add-weighted src 1.5 image -0.5 0 target)))

(defn sharpen! [src]
  (sharpen src src)
  src)

(defn sharpen2 [src target]
    (filter-2-d src target -1
      (u/matrix-to-mat [[-1 -1 -1
                         -1  9 -1
                         -1 -1 -1]])))
(defn sharpen2! [src]
  (sharpen2 src src)
  src)


(comment
  ; https://stackoverflow.com/questions/4993082/how-to-sharpen-an-image-in-opencv

(def original
  (->
  "http://www.petmd.com/sites/default/files/sleepy-cat-125522297.jpg"
  u/mat-from-url))

  (u/show original {:handlers
   {38 #(flip! % -1)
    39 #(flip! % 1)
    40 (fn[_] original)
    49 #(sharpen! %)
    50 #(sharpen2! %)}})

(->
  "resources/images/cat.jpg"
  imread
  (u/resize-by 0.5)
  sharpen2!
  u/show)


)
