(ns opencv3.shapefinder
  (:import [org.opencv.core MatOfPoint MatOfPoint2f])
  (:require [opencv3.core :refer :all]))

;
; redo exercice from shapes2
;
(defn draw-contours! [img contours]
 (dotimes [i (.size contours)]
    (let [c (.get contours i)
       m2f (MatOfPoint2f. (.toArray c))
       len (arc-length m2f true)
       ret (MatOfPoint2f.)
       approx (approx-poly-dp m2f ret (* 0.01 len) true)
       nb-sides (.size (.toList ret))]
 ; (println ">" nb-sides)
 (draw-contours img contours i
  (condp = nb-sides
   3  (new-scalar 56.09 68.05 66.27)
   4  (new-scalar 356.09 51.57 43.73)
   7   (new-scalar 26.09 51.57 43.73)
   8 (new-scalar 146.09 51.57 43.73)
   9 (new-scalar 266.09 51.57 43.73)
   10  (new-scalar 236.09 51.57 43.73)
   11 (new-scalar 206.09 68.05 66.27)
   12 (new-scalar 0 0 255)
      (new-scalar 127 50 0))
   -1)))

   img)

(defn contour-finder[target-image output-file]
  (let [ img (imread target-image)
         thresh (-> target-image (imread 0) (threshold! 210 240 1))
         contours (new-list)]

    (find-contours thresh contours (new-mat) RETR_LIST CHAIN_APPROX_SIMPLE)
    (-> target-image
      imread
      (draw-contours! contours)
      (imwrite output-file))))

(defn -main[& args]
  (contour-finder (first args) (second args)))
