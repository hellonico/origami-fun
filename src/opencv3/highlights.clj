(ns opencv3.highlights
  (:require
    [opencv3.core :refer :all]
    [opencv3.colors.rgb :as rgb]
    [opencv3.utils :as u]))

;
; blur part of the picture
;

(def rose
  (-> "resources/matching/rose_flower.jpg" imread (u/resize-by 0.5) ))

(def sub (.submat rose (new-rect 100 50 300 300)))
(median-blur! sub 9 )

(u/show rose)

(def sub2 (.submat rose (new-rect 500 50 100 100)))

(u/show rose)



;
; turn to grey part of the picture
;
(def big-rose
  (-> "resources/matching/rose_flower.jpg" imread))
(def rects [
  (new-rect 50 50 200 300)
  (new-rect 400 100 200 380)])
(doseq [ r rects ]
  (let [ s (.submat big-rose r ) mask (new-mat)]
    (-> s (cvt-color!  COLOR_BGR2GRAY) (cvt-color! COLOR_GRAY2BGR))
    (.copyTo s (.submat big-rose r ) mask)))
(u/show big-rose)


;
; turn picture to grey except part
;
(def rose
  (-> "resources/matching/rose_flower.jpg" imread (u/resize-by 0.5) ))
(def copy
   (-> rose clone (cvt-color! COLOR_BGR2GRAY) (cvt-color! COLOR_GRAY2BGR)))

(def rects [
  (new-rect 50 50 200 300)
  (new-rect 300 100 300 190)])

(doseq [ r rects ]
  (let [ s (.submat rose r ) mask (new-mat)]
    (.copyTo s (.submat copy r) mask)))

(u/show copy)
