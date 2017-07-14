(ns opencvfun.20-detect
  (:require [opencvfun.utils :as u])
  (:import
    [org.opencv.core MatOfKeyPoint MatOfRect Point Rect Mat Size Scalar Core CvType Mat MatOfByte]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.imgproc Imgproc]))

(def bgr-image (u/load-img "resources/detect/circles.jpg"))
(def orig-image (.clone bgr-image))

(Imgproc/medianBlur bgr-image bgr-image 3)

; Convert input image to HSV

(def hsv-image (Mat.))
(Imgproc/cvtColor bgr-image hsv-image Imgproc/COLOR_BGR2HSV)

(def lower-red (Mat.))
(def upper-red (Mat.))

(Core/inRange hsv-image (Scalar. 0.0 100.0 100.0) (Scalar. 10.0 255.0 255.0) lower-red)
(Core/inRange hsv-image (Scalar. 160.0 100.0 100.0) (Scalar. 179.0 255.0 255.0) upper-red)

(def red-hue-image (Mat.))
(Core/addWeighted lower-red 1.0 upper-red 1.0 0.0 red-hue-image)
(Imgproc/GaussianBlur red-hue-image red-hue-image (Size. 9 9) 2 2)

(u/swing-show-image red-hue-image)

(def circles (Mat.))
(Imgproc/HoughCircles red-hue-image circles Imgproc/CV_HOUGH_GRADIENT 1 (/ (.rows red-hue-image) 8) 100 20 0 0)

(doseq [i (range 0 (.cols circles))]
  (let [ circle (.get circles 0 i) x (nth circle 0) y (nth circle 1) r (nth circle 2)  p (Point. x y)]
  (Imgproc/circle orig-image p r (Scalar. 0 255 0) 5)))

(u/swing-show-image orig-image)


(defn circle-detection[bgr-image]
  (let [
    orig-image (.clone bgr-image)
    hsv-image (Mat.)
    lower-red (Mat.)
    upper-red (Mat.)
    red-hue-image (Mat.)
    circles (Mat.)
    ]
    ; median blurring used as a work around the noise in the picture.
    (Imgproc/medianBlur bgr-image bgr-image 3)

    (Imgproc/cvtColor bgr-image hsv-image Imgproc/COLOR_BGR2HSV)

    (Core/inRange hsv-image (Scalar. 0.0 100.0 100.0) (Scalar. 10.0 255.0 255.0) lower-red)

    (Core/inRange hsv-image (Scalar. 160.0 100.0 100.0) (Scalar. 179.0 255.0 255.0) upper-red)

    (Core/addWeighted lower-red 1.0 upper-red 1.0 0.0 red-hue-image)

    (Imgproc/GaussianBlur red-hue-image red-hue-image (Size. 9 9) 2 2)

    (Imgproc/HoughCircles red-hue-image circles Imgproc/CV_HOUGH_GRADIENT 1 (/ (.rows red-hue-image) 8) 100 20 0 0)

    (doseq [i (range 0 (.cols circles))]
      (let [ circle (.get circles 0 i) x (nth circle 0) y (nth circle 1) r (nth circle 2)  p (Point. x y)]
      (Imgproc/circle orig-image p r (Scalar. 0 255 0) 5)))
      orig-image
    ))

(def bgr-image (u/load-img "resources/detect/circles.jpg"))
(u/swing-show-image (circle-detection bgr-image))

(u/swing-show-image
  (circle-detection
    (u/load-img "resources/detect/circles_rectangles_noise.jpg")))
