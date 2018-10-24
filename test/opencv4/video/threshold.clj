(ns opencv4.video.threshold
  (:require [opencv4.core :refer :all])
  (:require [opencv4.utils :as u])
  (:import
    [org.opencv.core Mat Core CvType]
    [org.opencv.objdetect CascadeClassifier]
    [org.opencv.core MatOfRect]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video]))

(comment

  (def capture (VideoCapture.))
  (.open capture 0)

  (.set capture Videoio/CAP_PROP_FRAME_WIDTH 400)
  (.set capture Videoio/CAP_PROP_FRAME_HEIGHT 300)

  (def window
    (u/show (new-mat 400 400 CV_8UC3
      (new-scalar 255 255 255))))
  (def buffer (new-mat))

  (dotimes [i 200]
    (.read capture buffer)
    (cvt-color! buffer COLOR_BGR2GRAY)
    (threshold! buffer 0 255 (+ THRESH_BINARY_INV THRESH_OTSU))
    (u/re-show window buffer))

  (.release capture)

  )
