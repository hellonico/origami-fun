(ns opencv3.filtering2d
 (:require
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))

; http://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/filter_2d/filter_2d.html

(defn apply-kernel-with [ src ind ]
  (let [
    ddepth -1 ; same as src
    delta 0
    anchor (new-point -1 -1)
    target (u/mat-from src)
    kernel-size (+ 3 (* 2 (mod ind 10)))
    kernel (new-mat kernel-size kernel-size CV_32F)]
  (set-to kernel (new-scalar (/ 1.0 (* kernel-size kernel-size))))
  (filter-2-d src target ddepth kernel anchor delta BORDER_DEFAULT)
  target))

(defn range-view [ img max ]
  (let[  output (new-mat)]
    (hconcat (into [] (map #(apply-kernel-with img %) (reverse (range 0 max)))) output)
    output))

(-> "resources/images/cat.jpg"
  imread
  (u/resize-by 0.07)
  (range-view 10)
  (u/show  {:frame {:width 1300 :height 200 :title "un-blurring cat "}}))

(defn apply-custom-kernel [ src target matrix ]
  (let [kernel (u/matrix-to-mat matrix)]
    (filter-2-d src target -1 kernel)))

(defn apply-custom-kernel! [ src matrix ]
  (let [target (u/mat-from src)
        kernel (u/matrix-to-mat matrix)]
    (filter-2-d src target -1 kernel)
    target))

(defn many-matrixes [ src matrixes ]
  (let [ output (new-mat) ]
   (hconcat (vec (map #(apply-custom-kernel! src %) matrixes)) output)
     output))

(def source
   (-> "resources/images/cat.jpg"
   imread
   (u/resize-by 0.15)))

(u/show
 (many-matrixes source [

 [[17.8824    -43.5161    4.11935]
 [ 3.45565    27.1554    -3.86714]
 [ 0.0299566   0.184309   1.46709]]

 [[17.8824    -43.5161     4.11935]
 [ -3.45565    27.1554    -3.86714]
 [ 0.0299566   0.184309    1.46709]]

 [[17.8824    -43.5161     4.11935]
 [ -3.45565    27.1554    -3.86714]
 [ 0.0299566   0.184309   -1.46709]]

 [[17.8824    -23.5161     4.11935]
 [ 3.45565     27.1554     3.86714]
 [ 0.0299566  -20.184309  -1.46709]]

 ])
 {:frame {:width 1200}})
