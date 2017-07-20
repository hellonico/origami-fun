(ns opencv3.video.writing
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u])
  (:import
    [org.opencv.videoio Videoio VideoCapture VideoWriter]
    [org.opencv.video Video]))

(def capture (VideoCapture.))
(.set capture Videoio/CAP_PROP_FRAME_WIDTH 400)
(.set capture Videoio/CAP_PROP_FRAME_HEIGHT 300)

(def outputVideo (VideoWriter.))

; (VideoWriter/fourcc \M \P \E \G)
; (VideoWriter/fourcc \X \2 \6 \4)

(.open
  outputVideo
  "hello.avi"
  (VideoWriter/fourcc \M \J \P \G)
  30
  (new-size 400 300))

(def buffer (new-mat 400 300 CV_8UC3))

(.open capture 0)
(.release capture)
(dotimes [i 150]
  (.read capture buffer)
  ; (cvt-color! buffer COLOR_RGB2GRAY)
  (rotate! buffer ROTATE_90_CLOCKWISE)
  ; (put-text buffer "Funny text inside the box"
  ; (new-point (/ (.rows buffer) 2) (/ (.cols buffer) 2))
  ;           FONT_ITALIC 1.0 (new-scalar 255))
  ; (flip! buffer -1)
  (resize! buffer (new-size 400 300))
  (.write outputVideo buffer))

(.release outputVideo)
