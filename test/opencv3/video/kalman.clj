(ns opencv3.video.kalman
  (:require [opencv3.core :refer :all])
  (:require [opencv3.utils :as u])
  (:import
    [org.opencv.core Mat Core CvType]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.video Video KalmanFilter]))
; http://www.bzarg.com/p/how-a-kalman-filter-works-in-pictures/

; https://stackoverflow.com/questions/35407605/implementing-kalman-filter-in-opencv-java

; init kalman
(def kalman (KalmanFilter. 4 2 0 CV_32F))

(def transition-matrix (new-mat 4 4 CV_32F))
(.put transition-matrix 0 0
(float-array [1 0 1 0
              0 1 0 1
              0 0 1 0
              0 0 0 1]))
(.set_transitionMatrix kalman transition-matrix)

(def measure-matrix (new-mat 2 1 CV_32F))
(.setTo measure-matrix (new-scalar 0.0))
(.set_measurementMatrix kalman measure-matrix)

(def statePre (new-mat 4 1 CV_32F))
(.put statePre 0 0 (float-array [300 200 0 0]))
(.set_statePre kalman statePre)

(def process-noise-cov (new-mat 4 4 CV_32F))
(.setTo process-noise-cov (new-scalar  1.0))
(.mul process-noise-cov process-noise-cov 1e-1)
(.set_processNoiseCov kalman process-noise-cov)

(def measure-noise-cov (new-mat 4 4 CV_32F))
(.setTo measure-noise-cov (new-scalar  1.0))
(.mul measure-noise-cov measure-noise-cov 1e-1)
(.set_measurementNoiseCov kalman measure-noise-cov)

(def error-cov (new-mat 4 4 CV_32F))
(.setTo error-cov (new-scalar  1.0))
(.mul error-cov error-cov 0.1)
(.set_errorCovPost kalman error-cov)


; https://github.com/Franciscodesign/Moving-Target-Tracking-with-OpenCV/blob/e1ceb8b716984d105de7d375a70ba7a937f27a82/src/sonkd/Kalman.java

; EASIER TO UNDERSTAND
; http://opencvexamples.blogspot.com/2014/01/kalman-filter-implementation-tracking.html
