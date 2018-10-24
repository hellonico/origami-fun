(ns opencv4.gabor
  (:require [opencv4.core :refer :all] [opencv4.utils :as u])
  )

; http://rondelion.blogspot.jp/2014/04/opencv-java-api-gabor-filter.html

(def height 10)
(def width 10)
(def sigma 1.4)
(def lambda 4)
(def gamma 1.0)
(def psiDeg 0)
(def psi (* psiDeg (/ Math/PI 180)))
(def img (-> "resources/images/cat.jpg" imread (u/resize-by 0.2) (cvt-color! COLOR_RGB2GRAY)))

(defn kernel-by-theta [ thetaDeg ]
  (get-gabor-kernel (new-size width height ) sigma  (* thetaDeg (/ Math/PI 180)) lambda gamma))

(defn apply-gabor [ img angle]
  (->
    img
    clone
    (filter-2-d! (.type img)  (kernel-by-theta angle))))
(def output (new-mat))
(hconcat
  (into []
  (map #(apply-gabor img %) [0 45 90 135])  ) output)
(u/show output {:frame {:width 1600}})

(defn apply-gabor-inv [ img angle]
  (-> img
    (apply-gabor angle)
    bitwise-not!))
(def output2 (new-mat))
(hconcat
  (into []
  (map #(apply-gabor-inv img %) [0 45 90 135])  ) output2)
(u/show output2 {:frame {:width 1600}})
