(import '[org.opencv.core Core Point Rect Mat CvType Size Scalar]
         org.opencv.imgcodecs.Imgcodecs
         org.opencv.imgproc.Imgproc)

; http://d.hatena.ne.jp/Kazzz/20121213/p1
; https://github.com/tanaka0079/java/blob/master/opencv/image/Gamma.java

(defn apply-gamma[img gamma]
	(let [
		_img (Imgcodecs/imread img)
		_gamma (/ 1.0 gamma)
		_lut (Mat. 1 256 CvType/CV_8UC1)
		]
		(.setTo _lut (Scalar. (double 0)))
		(doseq [i (range 0 256)]
 		(let [v (double-array [ (* 255 (Math/pow (/ i 255) _gamma))] ) ]
 			(.put _lut 0 i v )))
		(Core/LUT _img _lut _img)
		_img))

(def result (apply-gamma  "resources/nico.jpg" 0.2))
(Imgcodecs/imwrite "target/img1.png" result)
