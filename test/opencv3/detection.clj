(ns opencv3.detection
  (:require [opencv3.core :refer :all]))

;;;
; face detection
;;;

(defn draw-rect [mat rect]
  (rectangle
    mat
    (new-point (.-x rect) (.-y rect))
    (new-point (+ (.-width rect) (.-x rect)) (+ (.-height rect) (.-y rect)))
    (new-scalar 0 0 255)
    5))

(defn recognize [xml-file mat]
  (let[ rects (new-matofrect)
        detector (new-cascadeclassifier xml-file)]
    (.detectMultiScale detector mat rects)
    (doseq [rect (.toArray rects)]
      (draw-rect mat rect))
      mat))

(comment

; face detection
(-> "resources/nico.jpg"
 imread
 (partial recognized "resources/lbpcascade_frontalface.xml")
 (imwrite mat "output/detection.png"))

; eyes detection
(-> "resources/nico.jpg"
 imread
 (partial recognized "resources/data/haarcascades_cuda/haarcascade_eye.xml")
 (imwrite mat "output/detection.png"))

)
