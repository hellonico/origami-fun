(ns opencv-fun.0-simplecam2)っっっっqっっっっq

(import '[org.opencv.core Size Mat MatOfByte]
        '[org.opencv.imgcodecs Imgcodecs]
        '[org.opencv.videoio Videoio VideoCapture]
        '[org.opencv.imgproc Imgproc])
;(require '[opencvfun.utils :refer :all])

(def capture (VideoCapture.))
(.set capture Videoio/CAP_PROP_FRAME_WIDTH 1600)
(.set capture Videoio/CAP_PROP_FRAME_HEIGHT 1200)

;(.open capture 0)

(defn capture-one []
  (let [_frame (Mat.)]
    (.open capture 0)
    ; need some time or polling before the camera is ready
    (Thread/sleep 1500)
    ; (.isOpened capture)
    (.read capture _frame)
    (.release capture)
  _frame))

(def frame (capture-one))

; (swing-show-image frame)
(Imgproc/cvtColor frame frame Imgproc/COLOR_BGR2GRAY)
;(Imgproc/cvtColor frame frame Imgproc/COLORMAP_RAINBOW)

(Imgcodecs/imwrite "output/simplecam.png" frame)

; video and blur
(Imgproc/cvtColor frame frame Imgproc/COLOR_BGR2GRAY)

(def blur (Imgproc/GaussianBlur frame frame (Size. 3 3) 0))
(Imgcodecs/imwrite "target/img1.png" frame)
