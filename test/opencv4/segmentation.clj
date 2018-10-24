(ns opencv4.segmentation
   (:require
     [opencv4.utils :as u]
     [opencv4.core :refer :all]))

; http://www.codepasta.com/site/vision/segmentation/

; Step 0: First begin with preprocessing the image with a slight Gaussian blur to reduce noise from the original image before doing an edge detection.

(def shoe
  (imread "resources/images/shoes/brownshoe.jpg"))

(def blurred (new-mat))
(gaussian-blur shoe blurred (new-size 5 5)  0)


; Step 1: Next we do the edge detection. The most commonly use method to do edge detection is to use the Sobel operator.
; The Sobel operator calculates the difference between intensities (aka "gradients") of pixels in a specific direction (x-axis or y-axis).
; We have to compute the Sobel edge on both axis and then get the magnitude of gradients combined; which is done by finding their euclidian distance (sqrt(x^2 + y^2)) on each corresponding pair of values.
; Doing that with loops in Python would be slow. Fortunately numpy gives np.hypot() method that can do that for us quickly.

(defn edge-detect! [ src channel ]
  (let [
    x1 (new-mat)
    y1 (new-mat)
    delta (new-mat (.cols src) (.rows src) CV_8UC1)
    ]
    (sobel src x1 CV_16S 1 0)
    (sobel src y1 CV_16S 0 1)
    (absdiff x1 y1 delta)
    delta))

(def work    (->
      shoe
      clone
      (u/resize-by 0.5)
      (cvt-color! COLOR_RGB2GRAY)
      (convert-to! CV_8UC1)
      (gaussian-blur! (new-size 5 5) 0 0)
      (adaptive-threshold! 200.0 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY_INV 3 7)
      ; (threshold! 80 255 THRESH_BINARY)
      ))
(u/show work {:frame {:height 800}} )

(def contours (new-arraylist))
(find-contours work contours (new-mat) RETR_TREE CHAIN_APPROX_SIMPLE)

(def c0 (-> shoe clone (u/resize-by 0.5)))
(println (count contours))
(dotimes [ci (.size contours)]
    (if (> (contour-area (nth contours ci)) 10)
      (draw-contours c0 contours ci (new-scalar 255 0 0) FILLED)))

(u/show c0 {:frame {:height 600}})
