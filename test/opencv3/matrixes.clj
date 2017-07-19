(ns opencv3.matrixes
  (:require
    [opencv3.utils :as u]
    [opencv3.core :refer :all]))

; Prewitt operator is used for edge detection in an image.
; It detects two types of edges: vertical edges and horizontal edges.

(def vertical-matrix
  [ [-1 0 1]
    [-2 0 2]
    [-1 0 1]])

(def horizontal-matrix
  [ [-1 -1 -1]
    [0 0 0]
    [1 1 1]])

(->
  (imread "resources/images/cat.jpg")
  (filter-2-d! -1 (u/matrix-to-mat vertical-matrix))
  (imwrite "output/cat3.png"))

(->
  (imread "resources/images/cat.jpg")
  (filter-2-d! -1 (u/matrix-to-mat horizontal-matrix))
  (imwrite "output/cat3.png"))

; prewitt-horizontal
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [-1 -1 -1]
       [0 0 0]
       [1 1 1]]))
  (imwrite "output/cat3.png"))

; prewitt-vertical
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [-1 0 1]
      [-1 0 1]
      [-1 0 1]]))
  (imwrite "output/cat3.png"))

; sobel vertical-matrix
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [-1 0 1]
      [-2 0 2]
      [-1 0 1]]))
  (imwrite "output/cat3.png"))

; sobel horizontal
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [-1 -2 -1]
      [0 0 0]
      [1 2 1]]))
  (imwrite "output/cat3.png"))

; kirsch east
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [-3 -3 -3]
      [-3 0 -3]
      [5 5 5]]))
  (imwrite "output/cat3.png"))

; kirch southwest
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [5 5 -3]
      [5 0 -3]
      [-3 -3 -3]]))
  (imwrite "output/cat3.png"))

; robinson north
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [-1 0 1]
      [-2 0 2]
      [-1 0 1]]))
  (imwrite "output/cat3.png"))

; robinson east
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [-1 -2 -1]
      [0 0 0]
      [1 2 1]]))
  (imwrite "output/cat3.png"))

; laplacian negative
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [0 -1 0]
      [-1 4 -1]
      [0 -1 0]]))
  (imwrite "output/cat3.png"))

; laplacian positive
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_RGB2GRAY)
  (filter-2-d! -1 (u/matrix-to-mat
    [ [0 1 0]
      [1 4 1]
      [0 1 0]]))
  (imwrite "output/cat3.png"))
