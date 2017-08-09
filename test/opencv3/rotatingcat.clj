(ns opencv3.rotatingcat
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

(defn rotate-by! [src angle]
  (let [ factor 1]
  (warp-affine! src
   (get-rotation-matrix-2-d (new-point (* factor (/ (.width src) 2)) (* factor (/ (.height src) 2))) angle 1)
   (new-size (* (.width src) factor ) (* (.height src) factor ) ) INTER_NEAREST)))

(defn -main [ & args]
  (let [ neko-path (first args) neko (imread neko-path) w (u/show neko) ]
  (dotimes [ i 360 ]
    (Thread/sleep 10)
    (u/re-show w
      (-> neko clone (rotate-by! i))
      ))))

; (-main "resources/images/cat_face.jpg")

(comment
(u/show
  (-> "resources/images/cat_face.jpg"
  imread
  (rotate-by! 60)
  (imwrite "resources/images/rotate/rotatedcat60.png")))
)
