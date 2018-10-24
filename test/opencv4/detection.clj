(ns opencv4.detection
  (:require
    [opencv4.core :refer :all]
    [opencv4.colors.rgb :as color]))

;;;
; face detection
;;;

(defn draw-rect
  [mat rect _color]
  (rectangle
    mat
    (new-point (.-x rect) (.-y rect))
    (new-point (+ (.-width rect) (.-x rect)) (+ (.-height rect) (.-y rect)))
    _color
    5))

(defn recognize
  ([xml-file mat] (recognize xml-file color/red-2 mat))
  ([xml-file _color mat ]
  (let [ rects (new-matofrect)
        detector (new-cascadeclassifier xml-file)]
    (.detectMultiScale detector mat rects)
    (doseq [rect (.toArray rects)]
      (draw-rect mat rect _color))
      mat)))

(comment

; face detection
(-> "resources/nico.jpg"
 imread
 ((partial recognize "resources/lbpcascade_frontalface.xml"))
 (imwrite "output/detection.png"))

; eyes detection
(-> "resources/nico.jpg"
 imread
 ((partial recognize "resources/data/haarcascades_cuda/haarcascade_eye.xml"))
 (imwrite "output/detection.png"))

; palm detection
(-> "resources/images/threehands.jpg"
 imread
 ((partial recognize "resources/XML/palm.xml"))
 (imwrite "output/detection.png"))

; fist (none found)
 (-> "resources/images/threehands.jpg"
  imread
  ((partial recognize "resources/XML2/fist.xml"))
  (imwrite "output/detection.png"))

(-> "resources/images/threefists.jpg"
 imread
 ((partial recognize "resources/XML2/fist.xml"))
 (imwrite "output/detection.png"))

; fist and palm both found
(-> "resources/images/hands/fist-palm.jpg"
 imread
 ((partial recognize "resources/XML2/palm.xml" color/orange-2))
 ((partial recognize "resources/XML2/fist.xml" color/green-2))
 (imwrite "output/detection.png"))

)
