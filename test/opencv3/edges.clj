(ns opencv3.edges
(:require
  [opencv3.utils :as u]
  [opencv3.core :refer :all]))

; inspired by, but reimplemented, from
; https://www.packtpub.com/books/content/detecting-edges-lines-and-shapes

(-> "http://www.v3wall.com/wallpaper/1366_768/0912/1366_768_20091223010850201138.jpg"
 u/mat-from-url
 (median-blur! 3)
 (cvt-color! COLOR_BGR2GRAY)
 (laplacian! -1)
 (bitwise-not!)
 (imwrite "output/laplacian1.png"))

(-> "resources/images/cat.jpg"
  imread
  (median-blur! 7)
  (cvt-color! COLOR_BGR2GRAY)
  (laplacian! 0)
  (bitwise-not!)
  (imwrite "output/laplacian2.png"))

(-> "http://www.v3wall.com/wallpaper/1366_768/0912/1366_768_20091223010850201138.jpg"
 u/mat-from-url
 (median-blur! 3)
 (cvt-color! COLOR_BGR2GRAY)
 (canny! 300.0 100.0 3 true)
 (bitwise-not!)
 (u/resize-by 0.5)
 (u/mat-view))
