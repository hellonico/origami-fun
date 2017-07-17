(ns opencvfun.20-detect
  (:require [opencvfun.utils :as u])
  (:import
    [org.opencv.core MatOfKeyPoint MatOfRect Point Rect Mat Size Scalar Core CvType Mat MatOfByte]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.imgproc Imgproc]))

(def bgr-image (Imgcodecs/imread "resources/detect/circles.jpg"))
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
(u/quick-save orig-image)
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
    ;
    ; (Core/inRange hsv-image (Scalar. 0.0 100.0 100.0) (Scalar. 10.0 255.0 255.0) lower-red)
    ; (Core/inRange hsv-image (Scalar. 160.0 100.0 100.0) (Scalar. 179.0 255.0 255.0) upper-red)
    ;
    ; (Core/addWeighted lower-red 1.0 upper-red 1.0 0.0 red-hue-image)


    ; blue
    (Core/inRange hsv-image (Scalar. 110.0 70.0 70.0) (Scalar. 130.0 255.0 255.0) red-hue-image)
    ; yellow
    ; cvScalar(20, 100, 100), cvScalar(30, 255, 255)
    ;(Core/inRange hsv-image (Scalar. 20.0 100.0 100.0) (Scalar. 30.0 255.0 255.0) red-hue-image)

    (Imgproc/GaussianBlur red-hue-image red-hue-image (Size. 9 9) 2 2)

    (Imgproc/HoughCircles red-hue-image circles Imgproc/CV_HOUGH_GRADIENT 1 (/ (.rows red-hue-image) 8) 100 20 0 0)

    (doseq [i (range 0 (.cols circles))]
      (let [ circle (.get circles 0 i) x (nth circle 0) y (nth circle 1) r (nth circle 2)  p (Point. x y)]
      (Imgproc/circle orig-image p r (Scalar. 0 255 0) 5)))
      orig-image
    ))


(defn circle-detection-2[bgr-image s1 s2]
  (let [
    orig-image (.clone bgr-image)
    hsv-image (Mat.)
    red-hue-image (Mat.)
    circles (Mat.)
    ]
    (Imgproc/medianBlur bgr-image bgr-image 3)
    (Imgproc/cvtColor bgr-image hsv-image Imgproc/COLOR_BGR2HSV)
    ; blue
    (Core/inRange bgr-image s1 s2 red-hue-image)
    (Imgproc/GaussianBlur red-hue-image red-hue-image (Size. 9 9) 2 2)
    (Imgproc/HoughCircles red-hue-image circles Imgproc/CV_HOUGH_GRADIENT 1 (/ (.rows red-hue-image) 8) 100 20 0 0)
    (doseq [i (range 0 (.cols circles))]
      (let [ circle (.get circles 0 i) x (nth circle 0) y (nth circle 1) r (nth circle 2)  p (Point. x y)]
      (Imgproc/circle orig-image p r (Scalar. 0 255 0) 5)))
      orig-image))

(def bgr-image (u/load-img "resources/detect/circles.jpg"))
(u/swing-show-image (circle-detection bgr-image))

(u/quick-save
  (circle-detection bgr-image))

(u/swing-show-image
  (circle-detection
    (u/load-img "resources/detect/circles_rectangles_noise.jpg")))

(u/quick-save
  (circle-detection
    (u/load-img "resources/detect/circles_rectangles_noise.jpg")))

(def blue-ball (u/load-mat-from-url "https://i.ytimg.com/vi/8HZPb7ulu08/maxresdefault.jpg"))
(u/quick-save
  (circle-detection-2 blue-ball
    (Scalar. 120.0 110.0 110.0)
    (Scalar. 125.0 125.0 125.0)))

(def mat-1 (Mat. 100 100 CvType/CV_8UC3 (Scalar. 0 0 0)))
(def blue (Scalar. 255 0 0))
(Imgproc/rectangle mat-1 (Point. 20 20) (Point. 50 50) blue Core/FILLED)

(u/quick-save mat-1)

(def mat-2 (Mat.))
(Core/inRange mat-1 blue blue mat-2)
(u/quick-save mat-2)


;Imgproc.drawContours(drawOn, handContours, idx, new Scalar(180, 10, 100, 255), Core.BORDER_DEFAULT, Core.LINE_8, new Mat(), 25, new Point(0, 0));
    ;
    ; cv::Mat img = cv::Mat::zeros(100,100,CV_8UC3);
    ;     cv::Scalar blue(255,0,0);
    ;
    ;     img(cv::Rect(20,20,50,50)) = blue;
    ;     cv::imshow("Original Image", img);
    ;
    ;
    ;     // Detect this blue square
    ;     cv::Mat img2;
    ;     cv::inRange(img, blue, blue, img2);
