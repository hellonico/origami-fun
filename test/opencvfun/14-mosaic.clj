; mosaic
(def im (Imgcodecs/imread "resources/images/cat.jpg"))
(Imgproc/resize im im (Size.) 0.1 0.1 Imgproc/INTER_NEAREST)
(Imgproc/resize im im (Size.) 10.0 10.0 Imgproc/INTER_NEAREST)
(Imgcodecs/imwrite "target/1.jpg" im)

(require '[opencv3.core :refer :all])
(import '[org.opencv.core Size])
(import '[org.opencv.imgproc Imgproc])
