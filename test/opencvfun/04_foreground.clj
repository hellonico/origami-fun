(ns opencv-fun.04-foreground)
; BROKEN
(import '[org.opencv.core CvType Size Core Mat MatOfFloat MatOfInt MatOfRect Point Rect Scalar]
        '[org.opencv.objdetect CascadeClassifier]
        '[org.opencv.imgcodecs Imgcodecs]
        '[org.opencv.imgproc Imgproc])

(defn getHistAverage[^Mat hsvImg ^Mat hueValues]
	(let[
      average (atom 0.0)
      hist_hue (Mat.)
      histSize (MatOfInt. (int-array [100]))
      hue (java.util.ArrayList.)
      ]
   (.add hue hueValues)
		
		;compute the histogram
		(Imgproc/calcHist hue (MatOfInt. (int-array [0])) (Mat.) hist_hue histSize (MatOfFloat. (float-array [0 179])))
		
;  (println (.get hist_hue 0 0))
(println 
  (map #(* % (first (.get hist_hue % 0))) (range 0 180)))

  (reset! average 
   (apply + (map #(* % (first (.get hist_hue % 0))) (range 0 180))))
  
  (println @average)
  (/ @average (.-height (.size hsvImg)) (.-width (.size hsvImg)))))

(defn do-background-removal[frame]
  (let[
       hsvImg (Mat.)
       hsvPlanes (java.util.ArrayList.)
       thresholdImg (Mat.)
       thresh_type (Imgproc/THRESH_BINARY_INV)
;       thresh_type (Imgproc/THRESH_BINARY)
       ]
    (.create hsvImg (.size frame) CvType/CV_8U)
    (Imgproc/cvtColor frame hsvImg (Imgproc/COLOR_BGR2HSV))
    (Core/split hsvImg hsvPlanes)
    (let[threshValue (getHistAverage hsvImg (first hsvPlanes))]
    (Imgproc/threshold (first hsvPlanes) thresholdImg threshValue 179.0 thresh_type)
    (Imgproc/blur thresholdImg thresholdImg (Size. 5 5))
    (Imgproc/dilate thresholdImg thresholdImg (Mat.) (Point. -1 -1) 1)
		(Imgproc/erode thresholdImg thresholdImg (Mat.) (Point. -1 -1) 3)
		(Imgproc/threshold thresholdImg thresholdImg threshValue 179.0 (Imgproc/THRESH_BINARY))
		
    (let [^Mat foreground (Mat. (.size frame) (CvType/CV_8UC3) (Scalar. 255 255 255))]
		(.copyTo frame foreground thresholdImg)
  foreground))))

(import '[org.opencv.core Mat MatOfByte]
        '[org.opencv.imgcodecs Imgcodecs]
        '[org.opencv.videoio VideoCapture]
        '[org.opencv.imgproc Imgproc])

(def capture (VideoCapture.))
;(.open capture 0)

(defn capture-one []
  (let [_frame (Mat.)]
    (.open capture 0)
    ; need some time or polling before the camera is ready
    (Thread/sleep 500)
    ; (.isOpened capture)
    (.read capture _frame)
    (.release capture)
  _frame))


(def frame (capture-one))
(def backgroud (do-background-removal frame))
		