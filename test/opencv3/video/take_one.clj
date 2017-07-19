(ns opencv3.video.take-one
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u])
  (:import
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video]))

(def capture (VideoCapture.))
(.set capture Videoio/CAP_PROP_FRAME_WIDTH 400)
(.set capture Videoio/CAP_PROP_FRAME_HEIGHT 300)
;(.open capture 0)

(defn capture-one
  ([] (capture-one 1000 true))
  ([wait-ms open-close]
  (let [_frame (new-mat)]
    (if open-close
     (.open capture 0))
    ; need some time or polling before the camera is ready
    (Thread/sleep wait-ms)
    ; (.isOpened capture)
    (.read capture _frame)
    (if open-close
    (.release capture))
  _frame)))

(.open capture 0)
(defn capture-many
  ([count] (capture-many count 1000))
  ([count wait]
    (into []
      (map (fn[_] (capture-one wait false)) (range 0 count)
      ))))
(.release capture)

(def frames (capture-many 20 300))
; (def frames *1)
(def target (new-mat))
(vconcat frames target)
(imwrite target "output/gamma.png")
