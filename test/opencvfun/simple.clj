(ns opencvfun.simple
  (:require opencvfun.utils)
  (:import
    [org.opencv.core MatOfKeyPoint MatOfRect Point Rect Mat Size Scalar Core CvType Mat MatOfByte]
    [org.opencv.photo Photo]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.imgproc Imgproc]))

; view the bitmap of the eye mat
(def mat (Mat/eye 3 3 CvType/CV_8UC1))
(.dump mat)

; view the bitmap of an all ones mat
(def mat2 (Mat/ones 3 3 CvType/CV_8UC1))
(.dump mat2)

; simple mat to file
(def mat3 (Mat. 100 200 CvType/CV_8UC3 (Scalar. 127 127 255)))
(Imgcodecs/imwrite "target/1.jpg" mat3)

; flip
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(. Core flip mat mat 1)
(Core/flip mat mat 1)
(Imgcodecs/imwrite "output/1.jpg" mat)
; 0 : flip x
; 1 ; flip y
; -1 ; flip x and y

; change color map
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(def mat2 (Mat.))
;(Imgproc/cvtColor mat mat Imgproc/COLOR_RGB2GRAY)
(Imgproc/cvtColor mat mat2 Imgproc/COLORMAP_JET)
; circle moved from Core to Imgproc

; write circle
; just line
(Imgproc/circle mat2 (Point. 800 400) 200 (Scalar. 0 0 0))
; colored
(Imgproc/circle mat2 (Point. 800 400) 200 (Scalar. 0 0 0) -1)
(Imgcodecs/imwrite "target/1.jpg" mat2)

; resizing a picture
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(Imgproc/resize mat mat (Size. (.width mat) (.height mat)))
(Imgcodecs/imwrite "target/1.jpg" mat)

; rotation a picture
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(def rotation-point (Point. 400 400))
(def rotation (Imgproc/getRotationMatrix2D rotation-point 33.0 1.0))
(Imgproc/warpAffine mat mat rotation (.size mat) Imgproc/INTER_NEAREST)
(Imgcodecs/imwrite "output/1.jpg" mat)

;;;
; THRESHOLD
;;;

; binary
(def mat (Imgcodecs/imread "resources/images/cat.jpg" CvType/CV_8UC1))
(Imgproc/threshold mat mat 100.0 200.0 Imgproc/THRESH_BINARY)
(Imgcodecs/imwrite "target/1.jpg" mat)

; binary inverse
(def mat (Imgcodecs/imread "resources/images/cat.jpg" CvType/CV_8UC1))
(Imgproc/threshold mat mat 100.0 200.0 Imgproc/THRESH_BINARY_INV)
(Imgcodecs/imwrite "target/1.jpg" mat)

; binary trunc
(def mat (Imgcodecs/imread "resources/images/cat.jpg" CvType/CV_8UC1))
(Imgproc/threshold mat mat 100.0 200.0 Imgproc/THRESH_TRUNC)
(Imgcodecs/imwrite "target/1.jpg" mat)

(def mat (Imgcodecs/imread "resources/images/cat.jpg" CvType/CV_8UC1))
(Imgproc/threshold mat mat 100.0 200.0 Imgproc/THRESH_TOZERO)
(Imgcodecs/imwrite "target/1.jpg" mat)

(def mat (Imgcodecs/imread "resources/images/cat.jpg" CvType/CV_8UC1))
(Imgproc/threshold mat mat 100.0 200.0 Imgproc/THRESH_TOZERO_INV)
(Imgcodecs/imwrite "target/1.jpg" mat)

; adaptive with binary and binary inv
(def mat (Imgcodecs/imread "resources/images/cat.jpg" CvType/CV_8UC1))
(Imgproc/adaptiveThreshold mat mat 200.0 Imgproc/ADAPTIVE_THRESH_MEAN_C Imgproc/THRESH_BINARY 3 8)
(Imgcodecs/imwrite "target/1.jpg" mat)

;;;
; histogram
;;;
; need to be loaded in black and white first
(def mat (Imgcodecs/imread "resources/images/cat.jpg" CvType/CV_8UC1))
(Imgproc/equalizeHist mat mat)
(Imgcodecs/imwrite "target/1.jpg" mat)

;;;
; Split colors
;;;
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
; (def dst (make-array Mat 3))
(def dst (java.util.ArrayList. 3))
(Core/split mat dst)
(Imgcodecs/imwrite "output/0.jpg" (.get dst 0))

;;;
; filters
;;;
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(Core/bitwise_not mat mat)
(Imgcodecs/imwrite "target/1.jpg" mat)

;;;
; Blurs
;;;
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(Imgproc/blur mat mat (Size. 30 30))
(Imgcodecs/imwrite "target/1.jpg" mat)

(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(Imgproc/medianBlur mat mat 05)
(Imgcodecs/imwrite "target/1.jpg" mat)

; speed
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(Imgproc/GaussianBlur mat mat (Size. 31 5) 80 3)
(Imgcodecs/imwrite "target/1.jpg" mat)

; laplacian
(def mat (Imgcodecs/imread "resources/nico.jpg"))
(Imgproc/Laplacian mat mat -1)
(Imgcodecs/imwrite "target/1.jpg" mat)

; Sobel
(def mat (Imgcodecs/imread "resources/nico.jpg"))
(Imgproc/Sobel mat mat -1 0 1)
(Imgcodecs/imwrite "target/1.jpg" mat)

; Canny
(def mat (Imgcodecs/imread "resources/nico.jpg"))
(Imgproc/Canny mat mat 150.0 200.0)
(Imgcodecs/imwrite "target/1.jpg" mat)

; dilate
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(Imgproc/dilate mat mat (Mat.))
(Imgcodecs/imwrite "target/1.jpg" mat)

; erode
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(Imgproc/erode mat mat (Mat.))
(Imgcodecs/imwrite "target/1.jpg" mat)

; box filter
(def mat (Imgcodecs/imread "resources/images/cat.jpg"))
(Imgproc/boxFilter mat mat (.depth mat) (Size. 100 100)) ; increase the size to blur
(Imgcodecs/imwrite "target/1.jpg" mat)

;;;
; detection again
;;;

(import '[org.opencv.objdetect CascadeClassifier])

; face detection
(defn draw-rect [mat rect]
  (Imgproc/rectangle
    mat
    (Point. (.-x rect) (.-y rect))
    (Point. (+ (.-width rect) (.-x rect)) (+ (.-height rect) (.-y rect)))
    (Scalar. 0 0 255)))

(defn recognize [xml-file image-file]
  (let[ rects (MatOfRect.)
        detector (CascadeClassifier. xml-file)
        mat (Imgcodecs/imread image-file)]
    (.detectMultiScale detector mat rects)
    (doseq [rect (.toArray rects)]
      (draw-rect mat rect))
    (Imgcodecs/imwrite "target/1.jpg" mat)
      ))

; nico's detection
(recognize
  "resources/lbpcascade_frontalface.xml"
  "resources/nico.jpg")

; eyes detection
(recognize
  "resources/data/haarcascades_cuda/haarcascade_eye.xml"
  "resources/nico.jpg")
