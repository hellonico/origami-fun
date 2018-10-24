(ns opencv4.video.short-stream2
  (:require
    [opencv4.core :refer :all]
    [opencv4.utils :as u]))
;
; opencv video test
;

; https://github.com/opencv-java/video-basics/tree/master/src/it/polito/teaching/cv
; https://github.com/huiyingShen/Java-Background-Subtraction/blob/master/src/org/ski/webcam/WebcamFrame.java

(defn -main[ & args ]
  (u/simple-cam-window identity))

(comment

; short version
(-main)

; long version
(def capture (new-videocapture))
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
