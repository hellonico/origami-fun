(ns opencv3.video.find-red
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :refer :all]
    [opencv3.utils :as u]))

(defn find-red[ buffer ]
  (let [
    work (new-mat)
    mat1 (new-mat)
    output (new-mat)
    ]
  (cvt-color buffer work COLOR_BGR2HSV)

  ; (gaussian-blur! work (new-size 5 5) 0 0)
  ; (in-range work (new-scalar 0 10 0) (new-scalar 50 255 255) mat1)

  ; only GREEN
  ; (in-range work (new-scalar 40 100 100) (new-scalar 120 255 255) mat1)

  ; only RED
  (in-range work (new-scalar 0 70 70) (new-scalar 10 255 255) mat1)
  ; (in-range work (new-scalar 40 100 100) (new-scalar 120 255 255) mat1)
  ; inRange(hsv, Scalar(0, 70, 50), Scalar(10, 255, 255), mask1);
  ;  inRange(hsv, Scalar(170, 70, 50), Scalar(180, 255, 255), mask2);

  ; (bitwise-not! mat1)
  ; (.copyTo buffer output mat1)

  ; (.copyTo mat1 output (new-mat))
  (.copyTo buffer output mat1)
  ; (in-range work (new-scalar 170 70 50) (new-scalar 180 255 255) mat2)
  ; (bitwise-or! mat1 mat2)

  ; (.setTo buffer (new-scalar 255 255 0) mask)

  ; (gaussian-blur! buffer (new-size 5 5) 0 0)
  ; (adaptive-threshold! buffer 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 7 5)
  ; (threshold! buffer 10 255 (+ THRESH_BINARY_INV THRESH_OTSU))
  output
  ))

(defn -main[& args]
  (u/simple-cam-window find-red))

(comment
  ; SHORT VERSION
  (-main)

  ; LONG VERSION
  (def capture (new-videocapture))
  (.open capture 0)

  (.set capture CAP_PROP_FRAME_WIDTH 400)
  (.set capture CAP_PROP_FRAME_HEIGHT 300)

  (def window
    (u/show (new-mat 400 300 CV_8UC3
      (new-scalar 255 255 255))))
  (def buffer (new-mat))

  ; https://stackoverflow.com/questions/32522989/opencv-better-detection-of-red-color

  (dotimes [i 100]
    (.read capture buffer)
    (let [ output (find-red buffer)]
    (u/re-show window output)))

  (.release capture)

  )
