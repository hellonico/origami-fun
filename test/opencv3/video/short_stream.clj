(ns opencv3.video.short-stream
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u])
  (:import
    [org.opencv.core Mat Core CvType]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video]))

; frame test show
(def img
  (u/show (imread "resources/detect/circles.jpg")))
(u/re-show
  img
  (imread "resources/detect/circles_rectangles.jpg"))

;
; opencv video test
;

; https://github.com/opencv-java/video-basics/tree/master/src/it/polito/teaching/cv
; https://github.com/huiyingShen/Java-Background-Subtraction/blob/master/src/org/ski/webcam/WebcamFrame.java

(def width 200)
(def height 150)
(def buffer (new-mat))
(def capture (VideoCapture.))
(.set capture Videoio/CAP_PROP_FRAME_WIDTH width)
(.set capture Videoio/CAP_PROP_FRAME_HEIGHT height)

(.open capture 0)
; (.release capture)

(def window
  (u/show (new-mat height width CV_8UC3 (new-scalar 255 255 255))))
(u/re-show window
  (new-mat height width CV_8UC3 (new-scalar 255 100 255)))

(dotimes [i 500]
  (.read capture buffer)
  (cvt-color! buffer COLOR_RGB2GRAY)
  (put-text buffer (java.util.Date.) (new-point 100 300) FONT_HERSHEY_PLAIN 20 (new-scalar 255 255 0) 30)
  (u/re-show
    window
    buffer))
