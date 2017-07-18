(ns opencv3.testing
  (:require [opencv3.core :refer :all])
  (:require [opencvfun.utils :as u])
  (:import
    [org.opencv.core Mat Core CvType]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video]
    [org.opencv.imgproc Imgproc]))

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
(.release capture)

(def img
  (u/show (Mat. height width CvType/CV_8UC3 (new-scalar 255 255 255))))

(def buffer (new-mat))
(dotimes [i 100]
  (.read capture buffer)
  (cvt-color! buffer Imgproc/COLOR_RGB2GRAY)
  (u/re-show
    img
    buffer))

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
  (Imgproc/getStructuringElement Imgproc/MORPH_ELLIPSE (new-size 3 3)))
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
  (Imgproc/morphologyEx mask mask Imgproc/MORPH_BLACKHAT kernel)
  mask))

(def buffer (new-mat))
(dotimes [_ 100]
  (.read capture buffer)
  (u/re-show
    img
    (bg-substractor-3 buffer)))
