(ns opencv3.threshold
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

; inspired by
; Edge detection using OpenCV (Canny)

(def neko
  (->
  "resources/images/cat.jpg"
  imread
  (u/resize-by 0.2)))


(def factor 6)
(def background (new-mat))
(def work (clone neko))

(dotimes [_ factor] (pyr-down! work))
(bilateral-filter work background 9 9 7)
(dotimes [_ factor] (pyr-up! background))
(resize! background (new-size (.cols neko) (.rows neko)))

(def
  c
  (-> neko
  clone
  (blur! (new-size 3 3))
  (cvt-color! COLOR_BGR2GRAY)
  (canny! 300.0 100.0 3 true)
  (bitwise-not!)
  (cvt-color! COLOR_GRAY2BGR)
  ))

(let [result (new-mat) ]
  (bitwise-and background c result)
  (u/show result))

(def t
(-> neko
  clone
  (cvt-color! COLOR_BGR2GRAY)
  (threshold! 150 255 THRESH_BINARY_INV)
  ; (bitwise-not!)
  (cvt-color! COLOR_GRAY2BGR)
  ))

(let [result (new-mat) ]
  (bitwise-and background t result)
  (u/show result))
