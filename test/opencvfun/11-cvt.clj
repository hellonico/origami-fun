
(import '[org.opencv.core Core Point Rect Mat CvType Size Scalar]
         org.opencv.imgcodecs.Imgcodecs
         org.opencv.imgproc.Imgproc)

(def im  (Imgcodecs/imread "resources/images/cat.jpg"))
(def hsv (Mat.))
(def mask (Mat.))
(def im2 (Mat.))
(Imgproc/cvtColor im hsv Imgproc/COLOR_BGR2HSV)
(Core/inRange
  hsv
  (Scalar. 40.0 10.0 0.0)
  (Scalar. 70.0 255.0 255.0)
  mask)
(.copyTo im im2 mask)

; generated
(Imgcodecs/imwrite "output/cat1.png" im2)

; original
(Imgcodecs/imwrite "output/cat2.png" im)
