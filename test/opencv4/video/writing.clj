(ns opencv4.video.writing
  (:require [opencv4.core :refer :all])
  (:require [opencv4.utils :as u])
  (:import
    [org.opencv.videoio Videoio VideoCapture VideoWriter]
    [org.opencv.video Video]))

(def capture (VideoCapture.))

(def outputVideo (VideoWriter.))

; (VideoWriter/fourcc \M \P \E \G)
; (VideoWriter/fourcc \X \2 \6 \4)

(.open
  outputVideo
  "hello.mp4"
  (VideoWriter/fourcc \M \J \P \G)
  30
  (new-size 400 300))

(def buffer
  (new-mat 400 300 CV_8UC3))

(.open capture 0)

(.set capture Videoio/CAP_PROP_FRAME_WIDTH 400)
(.set capture Videoio/CAP_PROP_FRAME_HEIGHT 300)

(dotimes [i 150]
  (.read capture buffer)
  ; (cvt-color! buffer COLOR_RGB2GRAY)
  ; (rotate! buffer ROTATE_90_CLOCKWISE)
  ; (put-text buffer "Funny text inside the box"
  ; (new-point (/ (.rows buffer) 2) (/ (.cols buffer) 2))
  ;           FONT_ITALIC 1.0 (new-scalar 255))
  ; (flip! buffer -1)
  (resize! buffer (new-size 400 300))
  ; (println (.size buffer))
  (.write outputVideo buffer))

(.release capture)
; (.release outputVideo)
