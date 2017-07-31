(ns opencv3.video.cartoon
 (:require
  [opencv3.core :refer :all]
  [opencv3.video :as v]
  [opencv3.utils :as u]))

(defn cartoon
  [buffer]
  (->
    (cvt-color! buffer COLOR_RGB2GRAY))
    (let [ c (clone buffer)]
    ; (bilateral-filter buffer c 9 9 7)
    (bilateral-filter buffer c 10 250 50)
    (-> c
     (median-blur! 7)
     (adaptive-threshold! 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 9 2)
     (cvt-color! COLOR_GRAY2RGB))))

(defn cartoon-2
   [buffer]
   (->
     (cvt-color! buffer COLOR_RGB2GRAY))
     (let [ c (clone buffer)]
     (bilateral-filter buffer c 9 9 7)
    ;  (bilateral-filter buffer c 10 250 50)
     (-> c
      (median-blur! 7)
      (adaptive-threshold! 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 9 2)
      (cvt-color! COLOR_GRAY2RGB))))

(defn cartoon-3
 [buffer]
 (->
   (cvt-color! buffer COLOR_RGB2GRAY))
   (let [ c (clone buffer)]
   (bilateral-filter buffer c 9 200 1)
  ;  (bilateral-filter buffer c 10 250 50)
   (-> c
    (median-blur! 9)
    (adaptive-threshold! 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 9 2)
    (cvt-color! COLOR_GRAY2RGB))))

(u/simple-cam-window
  (fn[buffer]
    (-> buffer
      (cartoon-3))))
