
  (ns opencv4.rotating
    (:require
  [opencv4.core :refer :all]
  [opencv4.utils :as u]
  [opencv4.colors.rgb :as color]))

;
; generate image
;
(def rotation-angle (rand 180))

(defn generative-art []
  (let [
    height 1000
    width 1000
    img (new-mat width height CV_8UC3)
    ]
    (set-to img color/white)
    (dotimes [ i (inc (rand 5)) ]
     (line img (new-point  (rand width) (rand height)) (new-point (rand width) (rand height)) color/cyan-3 (+ 100 (rand 50)))  )
    (dotimes [ i (inc (rand 5)) ]
     (circle img (new-point  (rand width) (rand height)) (+ 50 (rand 50)) color/greenyellow FILLED))
     img))

(def img (generative-art))

(def img-2 (u/mat-from img))
(def M2
  (get-rotation-matrix-2-d
    (new-point (/ (.width img) 2) (/ (.height img) 2))  rotation-angle 1))
(warp-affine img img-2 M2 (.size img))

(def mask (new-mat))
(in-range img-2 (new-scalar 0 0 0) (new-scalar 0 255 255) mask)
(dilate! mask (get-structuring-element MORPH_RECT (new-size 5 5)))

(def img-3 (u/mat-from img-2))
(set-to img-3 color/white)

(copy-to img-3 img-2 mask)

(def output (new-mat))
(hconcat [img (-> mask clone (cvt-color! COLOR_GRAY2RGB))  img-2 ] output)
(u/show
  (-> output clone (u/resize-by 0.2))
  {:frame {:width 1024 :heighy 200 :title "heavy rotation"}}  )
