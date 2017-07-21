(ns opencv3.video.face-recognition
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u])
  (:import
    [org.opencv.core Mat Core CvType]
    [org.opencv.objdetect CascadeClassifier]
    [org.opencv.core MatOfRect]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video]))


(defn draw-rects! [mat rect]
  (doseq [rect (.toArray rects)]
   (rectangle
    mat
    (new-point (.-x rect) (.-y rect))
    (new-point (+ (.-width rect) (.-x rect)) (+ (.-height rect) (.-y rect)))
    (new-scalar 236.09 68.0 60.0)
    5)
    mat
    ))

(def detector
  (CascadeClassifier. "resources/lbpcascade_frontalface.xml"))

(comment

  (def capture (VideoCapture.))
  (.open capture 0)

  (.set capture Videoio/CAP_PROP_FRAME_WIDTH 400)
  (.set capture Videoio/CAP_PROP_FRAME_HEIGHT 300)

  (def window
    (u/show (new-mat 400 400 CV_8UC3
      (new-scalar 255 255 255))))
  (def buffer (new-mat))
  (def rects (MatOfRect.))

  (dotimes [i 200]
    (.read capture buffer)
    (.detectMultiScale detector buffer rects)
    (draw-rects! buffer rects)
    (u/re-show window buffer))

  (.release capture)

  )
