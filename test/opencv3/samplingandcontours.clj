(ns opencv3.samplingandcontours
(:require
  [opencv3.utils :as u]
  [opencv3.core :refer :all]))

; prep work
(def img
  (imread "resources/morph/u9phq.png"))
(def gray
  (->
    img
    (clone)
    (cvt-color! COLOR_BGR2GRAY)))

; erode and dilate
(def k 8)
(dotimes[ i k]
  (-> gray
    (median-blur! 17)
    (erode! (new-mat))
    (median-blur! 17)
    (dilate! (new-mat))))

(def contours (new-arraylist))
(find-contours gray contours (new-mat) RETR_CCOMP CHAIN_APPROX_SIMPLE)

; simply draw contours
(def conv (u/mat-from img))
(draw-contours conv contours 0 (new-scalar 255 0 0) 3)

; draw an ellipse in the bouded box
(def boxed
  (clone img))
(def b-box
  (bounding-rect (first contours)))
(def center
  (u/middle-of-two-points (.br b-box) (.tl b-box)))
(def r-rect
  (new-rotatedrect center (.size b-box) 1 ))
(ellipse boxed r-rect (new-scalar 255 0 0) 3)

; display results
(def results (new-mat))
(defn annotate[mat text]
 (put-text mat
  text (new-point 100 100) FONT_HERSHEY_PLAIN 1 (new-scalar 255 0 0) 1))
(annotate img "original")
(annotate conv (str "blurred (" k")"))
(annotate boxed "ellipse")
(vconcat [img conv boxed] results)
; (u/show results)
(imwrite results "output/boxing.png")
