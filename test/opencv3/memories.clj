(ns opencv3.memories
 (:require
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))

(time
(do
(def value 1000)
; (double (/ 1 value))
(def one
  (->
  "resources/images/cat.jpg"
  imread
  (u/resize-by (double (/ 1 100)))))
  ; (u/show one)

(def target
  (new-mat (* 12 value) (* 16 value) CV_8UC3))

(doseq [ j (range 0 (dec value))]
 (doseq [ i (range 0 (dec value))]
  (let [ s (.submat target (new-rect (* 16 i) (* 12 j) 16 12)) ]
    (.copyTo (-> one clone) s))))

(u/show
  (-> target (u/resize-by 0.1))
  {:frame {:width 1600 :height 1200 }}
  )))
