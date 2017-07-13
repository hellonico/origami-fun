; draw the countour of a picture, after turning it to black and white.

(import '[org.opencv.core Core Point Rect Mat CvType Size Scalar]
         org.opencv.imgcodecs.Imgcodecs
         org.opencv.imgproc.Imgproc)

(def im (Imgcodecs/imread "resources/nico.jpg"))
(def gray (Mat.))
(Imgproc/cvtColor im gray Imgproc/COLOR_RGB2GRAY)
(Imgcodecs/imwrite "target/bw.jpg" gray)

(def result (Mat.))

(do
(Imgproc/Canny gray result 400 500 3 true)
(Imgcodecs/imwrite "target/bw.jpg" result))
