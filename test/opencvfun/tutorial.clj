(import '[org.opencv.core Core CvType Mat MatOfByte Size])
(import '[org.opencv.photo Photo])
(import '[org.opencv.core Point Rect Mat CvType Size Scalar]
         org.opencv.imgcodecs.Imgcodecs
         org.opencv.imgproc.Imgproc)


(def image (Mat. (new Size 640 480) CvType/CV_8UC3))

(doseq [row (range 0 (.rows image))]
  (doseq [col (range 0 (.cols image))]
    (.put image row col (byte-array [50 50 50]))))

(Imgcodecs/imwrite "target/img1.png" image)

; mosaic
(def im (Imgcodecs/imread "resources/images/lena.png"))
(Imgproc/resize im im (Size.) 0.1 0.1 Imgproc/INTER_NEAREST)
(Imgproc/resize im im (Size.) 10.0 10.0 Imgproc/INTER_NEAREST)
(Imgcodecs/imwrite "target/img1.png" im)

; denoising
(def im (Imgcodecs/imread "resources/summer.png"))
(Photo/fastNlMeansDenoising im im)
(Imgcodecs/imwrite "target/img1.png" im)

; blurring
(def im (Imgcodecs/imread "resources/nico.jpg"))
(def dst (Mat.))
(Imgproc/blur im dst (Size. 5 5))
(Imgcodecs/imwrite "target/img1.png" dst)

; grey scale
(def im (Imgcodecs/imread "resources/nico.jpg"))
(Imgproc/cvtColor im im (Imgproc/COLOR_RGB2GRAY))
(Imgcodecs/imwrite "target/img1.png" im)

; laplacian
(def gray (Imgcodecs/imread "resources/nico.jpg" 0))
(Imgproc/Laplacian gray gray (.depth gray))
(Imgcodecs/imwrite "target/img1.jpg" gray)

; levels
(def im (Imgcodecs/imread "resources/nico.jpg"))
(def n 100)
(def sz (.size im))

(doseq [i (range 0 (.-height sz ))]
  (doseq [j (range 0 (.-width sz ))]
  (let [pixel (.get im i j)]
    (aset pixel 0 (+ (aget pixel 0) (/ n 2)))
    (aset pixel 1 (+ (aget pixel 1) (/ n 2)))
    (aset pixel 2 (+ (aget pixel 2) (/ n 2)))
    (.put im i j pixel))))

(Imgcodecs/imwrite "target/img1.png" im)

; ?? missing something
(def im (Imgcodecs/imread "resources/images/cat2.jpg"))

(def gp1 (Mat.))
; downsample the image
(Imgproc/pyrDown im gp1)
; upsample the image
(Imgproc/pyrUp gp1 gp1)
; subtract original
(Core/subtract im gp1 gp1)
(Imgcodecs/imwrite "target/img1.png" gp1)

; thresholding

(defn quick-apply-threshold[ img thresh maxval type]
  (let[ im (Imgcodecs/imread img)]
  (Imgproc/threshold im im thresh maxval type)
  (Imgcodecs/imwrite "target/img1.png" im)
  im
  ))

(quick-apply-threshold
  "resources/images/cat2.jpg"
  140 255
  Imgproc/THRESH_BINARY)

(quick-apply-threshold
  "resources/images/cat2.jpg"
  100 240
  Imgproc/THRESH_BINARY_INV)

; http://qiita.com/gutugutu3030/items/3907530ee49433420b37

; original gaussian blur on image
(def im (Imgcodecs/imread "resources/images/cat2.jpg"))
(def hsv (Mat.))
(Imgproc/cvtColor im hsv Imgproc/COLOR_BGR2HSV)
(Imgproc/medianBlur hsv hsv 3)
(Imgcodecs/imwrite "target/img1.png" hsv)

(def im (Imgcodecs/imread "resources/images/cat2.jpg"))
(def hsv (Mat.))
(Imgproc/cvtColor im hsv Imgproc/COLOR_BGR2RGBA)
(Imgproc/medianBlur hsv hsv 3)

; nice threshold on above gaussian
(Imgproc/threshold
  hsv hsv
  100 240
  Imgproc/THRESH_BINARY)
(def base (Imgcodecs/imwrite "target/img1.png" hsv))

(defn dist-transform[mat]
  (let[
    mat1 (Mat. (.cols mat) (.rows mat) CvType/CV_8UC1)
    mat2 (Mat.)
    ]
  (Imgproc/distanceTransform mat1 mat2 Imgproc/CV_DIST_L2 3)
  (Core/convertScaleAbs mat2 mat1)
  (Core/normalize mat1 mat1 0.0 255.0 Core/NORM_MINMAX)
  mat1))

; だめだ
(def neko (load-neko))
(def neko-2 (dist-transform neko))
(quick-save neko-2)

(def neko (load-neko))
(def neko-2 (Mat.))
(Imgproc/cvtColor neko neko-2 Imgproc/COLOR_BGR2HSV)
(Imgproc/distanceTransform neko-2 neko-2 Imgproc/CV_DIST_L2 3)
