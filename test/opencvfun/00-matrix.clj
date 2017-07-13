(ns opencvfun.00-matrix)

(import '[org.opencv.core CvType Mat Size])


; The Mat.eye represents a identity matrix,
; we set the dimensions of it (3x3) and the type of its elements.
(def mat (Mat/eye 3 3 CvType/CV_8UC1))

; we create a 640 x 480 image and
(def image-2 (Mat. (new Size 640 480) CvType/CV_8UC3))

; fill all pixel with the same RGB color, here blue
(doseq [row (range 0 (.rows image-2))]
  (doseq [col (range 0 (.cols image-2))]
    (.put image-2 row col (byte-array [(rand 128) 128 50]))))

; write the image to file
(import org.opencv.imgcodecs.Imgcodecs)
(Imgcodecs/imwrite "target/1.png" image-2)
