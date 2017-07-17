(ns opencv3.core
  (:import
    [org.opencv.core MatOfKeyPoint MatOfRect Point Rect Mat Size Scalar Core CvType Mat MatOfByte]
    [org.opencv.photo Photo]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.imgproc Imgproc]))

(defn imread[string]
  (Imgcodecs/imread string))

(defn imwrite[mat string]
  (Imgcodecs/imwrite string mat))

(defn new-size[x y]
  (Size. x y))
(defn new-mat[]
  (Mat.))
(defn new-point[x y]
  (Point. x y)
  )

(defn clone[mat]
  (.clone mat))

(defn split [src dst]
  (Core/split src dst))

(defn bitwise-not[src dst]
  (Core/bitwise_not src dst))
(defn bitwise-not![src]
  (bitwise-not src src)
  src)

(defn blur[src dst size]
  (Imgproc/blur src dst size))
(defn blur![src size]
  (blur src src size)
  src)

(defn gaussian-blur[src dst size a b]
    (Imgproc/GaussianBlur src dst size a b))
(defn gaussian-blur![src size a b]
  (gaussian-blur src src size a b)
  src)

(defn median-blur[src dst a]
  (Imgproc/medianBlur src dst a)
  )
(defn median-blur![src a]
  (median-blur src src a)
  src)

; 0 : flip x
; 1 ; flip y
; -1 ; flip x and y
(defn flip[src dst n]
  (Core/flip src dst n))
(defn flip![src n]
  (flip src src n)
  src)

(defn cvt-color[src dst cvt]
  (Imgproc/cvtColor src dst cvt))
(defn cvt-color![src cvt]
  (cvt-color src src cvt)
  src)


(defn erode[src dst]
  (Imgproc/erode src dst (Mat.)))
(defn erode![src]
  (erode src src)
  src)

(defn dilate[src dst]
  (Imgproc/dilate src dst (Mat.)))
(defn dilate![src]
  (dilate src src)
  src)

(defn canny[src dst a b]
  (Imgproc/Canny src dst a b)
  dst)
(defn canny![src a b]
  (canny src src a b)
  src)

(defn resize[src dst size]
  (Imgproc/resize src dst size))
(defn resize![src size]
  (resize! src src size)
  src)

(defn new-scalar[a b c]
  (Scalar. a b c))

(defn in-range[src range-start range-end dst]
  (Core/inRange src range-start range-end dst))
(defn in-range![src range-start range-end]
  (in-range src range-start range-end src)
  src)

(defn add-weighted [src1 a1 src2 a2 a3 dst]
  (Core/addWeighted src1 a1 src2 a2 a3 dst))
(defn add-weighted![src1 a1 src2 a2 a3]
  (Core/addWeighted src1 a1 src2 a2 src1 a3)
  src1)

(defn gaussian-blur[src dst size a1 a2]
  (Imgproc/GaussianBlur src dst size a1 a2)
  )
(defn gaussian-blur![src size a1 a2]
  (gaussian-blur src src size a1 a2)
  src)

(defn normalize[src dst a1 a2 a3]
  ;(Core/normalize grayed grayed 0 255 Core/NORM_MINMAX)
  (Core/normalize src dst a1 a2 a3)
  )

(defn normalize![src a1 a2 a3]
  (normalize src src a1 a2 a3)
  src)

(defn hough-circles[src dst a1 a2 a3 a4 a5 a6 a7]
  (Imgproc/HoughCircles
    src
    dst
    a1
    a2
    a3 a4 a5 a6 a7))

(defn circle![src p r color thick]
  (Imgproc/circle src p r color thick))

; (defn get-structuring-element[]
;   )
