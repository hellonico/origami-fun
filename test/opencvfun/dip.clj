(ns opencvfun.dip
  (:use opencv3.utils)
  (:import
    [org.opencv.core Point Rect Mat Size Scalar Core CvType Mat MatOfByte]
    [org.opencv.photo Photo]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.imgproc Imgproc]))

; Prewitt operator is used for edge detection in an image.
; It detects two types of edges: vertical edges and horizontal edges.
(def source (Imgcodecs/imread "resources/images/cat.jpg" CvType/CV_32F))
(def destination (mat-from source))
(def kernel-size 9)
(def kernel (Mat. kernel-size kernel-size CvType/CV_32F))

(def vertical-matrix
  [ [-1 0 1]
    [-2 0 2]
    [-1 0 1]])

(def horizontal-matrix
  [ [-1 -1 -1]
    [0 0 0]
    [1 1 1]])

(matrix-to-mat vertical-matrix kernel float-array)

(Imgproc/filter2D source destination -1 kernel)
(quick-save destination)

(defn to-bw [source]
  (let[ gray (Mat.)]
  (Imgproc/cvtColor source gray Imgproc/COLOR_RGB2GRAY)
  gray))

(defn apply-filter2d [__source _matrix]
  (let[
     _source (to-bw __source)
     _destination (mat-from _source)
     kernel-size 9
     _kernel (Mat. kernel-size kernel-size CvType/CV_32F)
     ]
     (println (matrix-to-mat _matrix _kernel float-array))
     (Imgproc/filter2D _source _destination -1 _kernel)
     (quick-save _destination)))

(defn apply-prewitt-horizontal [source]
  (apply-filter2d source
      [ [-1 -1 -1]
        [0 0 0]
        [1 1 1]]))
(defn apply-prewitt-vertical [source]
  (apply-filter2d source
      [ [-1 0 1]
        [-1 0 1]
        [-1 0 1]]))

(defn apply-sobel-vertical [source]
  (apply-filter2d source
    [ [-1 0 1]
      [-2 0 2]
      [-1 0 1]]))
(defn apply-sobel-horizontal [source]
  (apply-filter2d source
    [ [-1 -2 -1]
      [0 0 0]
      [1 2 1]]))

(defn apply-kirsch-east [source]
  (apply-filter2d source
    [ [-3 -3 -3]
      [-3 0 -3]
      [5 5 5]]))

(defn apply-kirsch-southwest [source]
  (apply-filter2d source
    [ [5 5 -3]
      [5 0 -3]
      [-3 -3 -3]]))

(defn apply-robinson-north [source]
  (apply-filter2d source
    [ [-1 0 1]
      [-2 0 2]
      [-1 0 1]]))
(defn apply-robinson-east [source]
  (apply-filter2d source
    [ [-1 -2 -1]
      [0 0 0]
      [1 2 1]]))

(defn apply-laplacian-negative [source]
  (apply-filter2d source
    [ [0 -1 0]
      [-1 4 -1]
      [0 -1 0]]))
(defn apply-laplacian-positive [source]
  (apply-filter2d source
    [ [0 1 0]
      [1 4 1]
      [0 1 0]]))

; something is cashing, so just in case, run twice :)
(apply-prewitt-horizontal source)
(apply-prewitt-vertical source)
(apply-sobel-vertical source)
(apply-sobel-horizontal source)
(apply-robinson-north source)
(apply-robinson-east source)
(apply-kirsch-east source)
(apply-kirsch-southwest source)
(apply-laplacian-negative source)
(apply-laplacian-positive source)

(apply-filter2d source
  [ [-1 -5 -1]
    [0 0 0]
    [1 5 1]])

(apply-filter2d source
  [ [-1 -1 -1]
    [-1 10 1]
    [1 1 1]])

; http://stackoverflow.com/questions/8089074/idiomatically-iterating-over-a-2-or-higher-dimensional-sequence-in-clojure

; https://www.tutorialspoint.com/java_dip/create_zooming_effect.htm
; zooming
(def source (Imgcodecs/imread "resources/nico2.png"))
(def zooming-factor 2)
(def target (Mat.
  (* zooming-factor (.rows source))
  (* zooming-factor (.cols source))
  (.type source)
 ))
(Imgproc/resize source target (.size target) zooming-factor zooming-factor Imgproc/INTER_NEAREST)
(quick-save target)

; shortcut
(defn quick-zoom [_source _factor _inter ]
  (let [_target (Mat.
    (.intValue (* _factor (.rows _source)))
    (.intValue (* _factor (.cols _source)))
    (.type _source)
    )]
    (Imgproc/resize _source _target (.size _target) _factor _factor _inter)
    (quick-save _target)))

(quick-zoom source 2 Imgproc/INTER_LINEAR)


; back to the original
(defn distTransform[mat]
       (let[
         mat1 (Mat. (.cols mat) (.rows mat) CvType/CV_8UC1)
         mat2 (Mat.)
         ]
         (Imgproc/distanceTransform mat mat2 Imgproc/CV_DIST_L2 3)
         (Core/convertScaleAbs mat2 mat1)
         (Core/normalize mat1 mat1 0.0 255.0 Core/NORM_MINMAX)
         mat1))

(def source (Imgcodecs/imread "resources/images/cat2.png" CvType/CV_8UC1))
(def t (distTransform source))
(def hsv (mat-from source))
(.copyTo source hsv)
(Imgproc/medianBlur hsv hsv 3)

(Imgproc/distanceTransform source hsv Imgproc/CV_DIST_L2 3)
(quick-save hsv)
(quick-save source)
(quick-save t)

(swing-show-image source)
