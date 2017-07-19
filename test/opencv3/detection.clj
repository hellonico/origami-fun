(ns opencv3.detection
  (:require [opencv3.core :refer :all])
  (:import
    [org.opencv.objdetect CascadeClassifier]
    [org.opencv.core MatOfRect]))

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

  (defn recognize [xml-file image-file]
    (let[ rects (MatOfRect.)
          detector (CascadeClassifier. xml-file)
          mat (imread image-file)]
      (.detectMultiScale detector mat rects)
      (doseq [rect (.toArray rects)]
        (draw-rect mat rect))
      (imwrite mat "output/detection.png")))

; face detection
(recognize
  "resources/lbpcascade_frontalface.xml"
  "resources/nico.jpg")

; eyes detection
(recognize
  "resources/data/haarcascades_cuda/haarcascade_eye.xml"
  "resources/nico.jpg")
