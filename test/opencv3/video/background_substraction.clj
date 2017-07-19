(ns opencv3.video.background-sub
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u])
  (:import
    [org.opencv.core Mat Core CvType]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video]))

(def width 200)
(def height 150)
(def capture (VideoCapture.))
(.set capture Videoio/CAP_PROP_FRAME_WIDTH width)
(.set capture Videoio/CAP_PROP_FRAME_HEIGHT height)

(.open capture 0)
; (.release capture)

(def window
  (u/show (new-mat height width CV_8UC3 (new-scalar 255 255 255))))

;
; opencv based background substraction
;

; http://docs.opencv.org/3.1.0/db/d5c/tutorial_py_bg_subtraction.html
; https://github.com/ahanin/opencv-demo/blob/master/src/main/java/tk/year/opencv/demo/filters/MorphologyEx.java

; background filters

(def mog2
  (Video/createBackgroundSubtractorMOG2))
(def knn
  (Video/createBackgroundSubtractorKNN))
(def kernel
  (get-structuring-element MORPH_ELLIPSE (new-size 3 3)))
; those seem to be gone
; (Video/createBackgroundSubtractorMOG)
; (Video/createBackgroundSubtractorGMG)

(defn bg-substractor[mat]
  (let[mask (new-mat)]
	(.apply mog2 mat mask)
  mask))

(defn bg-substractor-2[mat]
  (let[mask (new-mat)]
	(.apply knn mat mask)
  mask))

(defn bg-substractor-3[mat]
  (let[mask (new-mat)]
	(.apply mog2 mat mask)
  (morphology-ex mask mask MORPH_BLACKHAT kernel)
  mask))


(comment
  (def buffer (new-mat))
  (dotimes [_ 100]
    (.read capture buffer)
    (u/re-show
      window
      (bg-substractor-3 buffer))
  )
  )

(defn -main[ & args]
  (while true
    (do
    (.read capture buffer)
    (u/re-show
      window
      (bg-substractor-3 buffer))))
    )
