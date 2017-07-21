(ns opencv3.video.short-stream
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u])
  (:import
    [org.opencv.core Mat Core CvType]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video]))
;
; opencv video test
;

; https://github.com/opencv-java/video-basics/tree/master/src/it/polito/teaching/cv
; https://github.com/huiyingShen/Java-Background-Subtraction/blob/master/src/org/ski/webcam/WebcamFrame.java

(comment


(def capture (VideoCapture.))
 (.open capture 0)

(def window
 (u/show (new-mat height width CV_8UC3 (new-scalar 255 255 255))))

(def buffer (new-mat))
 (dotimes [i 200]
  (.read capture buffer)
  (u/re-show
   window
   buffer))

   (.release capture)

  )
