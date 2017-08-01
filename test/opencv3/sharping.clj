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

 (def cat (-> "resources/images/cat.jpg" imread))

(def p (atom 5))
(u/show original
 {:handlers {
 38  #(erode! % (get-structuring-element MORPH_RECT (new-size @p @p)))
 37  #(do (swap! p inc) %)
 39  #(do (swap! p inc) %)
 40  #(dilate! % (get-structuring-element MORPH_RECT (new-size @p @p)))
 49 (fn[_] original)
 }})

 (def p (atom 2))
 (u/show original
  {:handlers {
  37  #(do (swap! p dec) %)
  39  #(do (swap! p inc) %)
  38  #(u/resize-by % @p)
  40  #(u/resize-by % (/ 1 @p))
  49 (fn[_] original)}})

  (def neko (atom (-> "resources/images/cat.jpg" imread)))
  (u/show neko)
  (reset! neko
    ; (sharpen2! @neko)
    ; (-> "resources/images/cat.jpg" imread)
    (u/resize-by @neko 0.5)
    ; (dilate! @neko (get-structuring-element MORPH_RECT (new-size 5 5)))

    )

(->
  "resources/images/cat.jpg"
  imread
  (u/resize-by 0.5)
  sharpen2!
  u/show)

)
