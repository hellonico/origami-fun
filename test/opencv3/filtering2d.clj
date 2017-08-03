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
    target (u/mat-from img)
    kernel-size (+ 3 (* 2 (mod ind 10)))
    kernel (new-mat kernel-size kernel-size CV_32F)]
  (set-to kernel (new-scalar (/ 1.0 (* kernel-size kernel-size))))
  (filter-2-d img target ddepth kernel anchor delta BORDER_DEFAULT)
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
