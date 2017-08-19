(ns opencv3.memories
 (:require
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))

;
; basic mat performance test.
;

; COUNT | TIME          |  machine  | use clone
; 500   | 24410.345098  |  macbook  | no
; 500   | 32727.560441  |  macbook  | yes
; 100   | 1313.304997   |  macbook  | no
; 100   | 1466.568298   |  macbook  | yes

(time
(do
(def value 100)
; (double (/ 1 value))
(def one
  (->
  "resources/images/cat.jpg"
  imread
  (u/resize-by (double (/ 1 100)))))
  ; (u/show one)

(def target
  (new-mat (* 12 value) (* 16 value) CV_8UC3))

(dotimes [ j value ]
 (dotimes [ i value]
  (let [ s (.submat target (new-rect (* 16 i) (* 12 j) 16 12)) ]
    (.copyTo (-> one clone) s))
    ; (.copyTo one s))
    ))

(u/show
  (-> target (u/resize-by 1))
  {:frame {:width 1600 :height 1200 }}
  )

  ))
