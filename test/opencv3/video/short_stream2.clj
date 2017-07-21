(ns opencv3.video.short-stream2
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u])
  (:import
    [org.opencv.core Mat Core CvType]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video]))

(comment


  (def capture (VideoCapture.))
  (.open capture 0)
  ; size needs to be done after

  (.set capture Videoio/CAP_PROP_FRAME_WIDTH 400)
  (.set capture Videoio/CAP_PROP_FRAME_HEIGHT 300)

  (def window
    (u/show (new-mat height width CV_8UC3 (new-scalar 255 255 255))))

  (def buffer (new-mat))
  (def output (new-mat))

  (dotimes [i 200]
    (.read capture buffer)
    (cvt-color! buffer COLOR_RGB2GRAY)
    (let[ bottom (-> buffer clone (flip! -1))]
    (put-text buffer (str (java.util.Date.)) (new-point 10 50) FONT_HERSHEY_PLAIN 1 (new-scalar 255 255 0) 1)
    (vconcat [buffer bottom] output)
    (u/re-show window output)))

  (.release capture)

  )
