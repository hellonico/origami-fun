(ns opencv-fun.01-blurred-lena)

(import '[org.opencv.core Point Rect Mat CvType Size Scalar]
         org.opencv.imgcodecs.Imgcodecs
         org.opencv.imgproc.Imgproc)


; open close image, do not do anything
(let [lena (Imgcodecs/imread "resources/images/frame3.png")]
  (Imgcodecs/imwrite "target/copy.png" lena))

; load, blur and save
(let [lena (Imgcodecs/imread "resources/images/lena.png")
      blurred (Mat. 512 512 CvType/CV_8UC3)]
  (Imgproc/GaussianBlur lena blurred (Size. 9 9) 10 10)
  (Imgcodecs/imwrite "output/blurred.png" blurred))
