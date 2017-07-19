(ns opencv3.gamma
 (:require
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))


; http://d.hatena.ne.jp/Kazzz/20121213/p1
; https://github.com/tanaka0079/java/blob/master/opencv/image/Gamma.java

(defn gamma![_img gamma]
	(let [
		; _img (Imgcodecs/imread img)
		_gamma (/ 1.0 gamma)
		_lut (new-mat 1 256 CV_8UC1)
		]
		(.setTo _lut (new-scalar (double 0)))
		(doseq [i (range 0 256)]
 		(let [v (double-array [ (* 255 (Math/pow (/ i 255) _gamma))] ) ]
 			(.put _lut 0 i v )))
		(lut _img _lut _img)
		_img))

(->
  "resources/nico.jpg"
  (imread)
  (gamma! 0.2)
  (imwrite "output/gamma.png"))


(def source (imread "resources/nico.jpg"))
(def target (u/mat-from source))
(def gamma-d
  (into []
   (map #(gamma! (clone source) %) (reverse (range 2 10)))))
(vconcat gamma-d target)
(imwrite target "output/gamma.png")
