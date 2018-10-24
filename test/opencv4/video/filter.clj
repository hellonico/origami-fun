(ns opencv4.video.filter
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :refer :all]
    [opencv4.utils :as u]))

(def lower-red
  (new-scalar 0 70 70))
(def upper-red
  (new-scalar 10 255 255))

(defn filter-stream[ lower upper buffer ]
   (u/resize-by buffer 0.5)
   (let [
     hsv (-> buffer clone (cvt-color! COLOR_BGR2HSV))
     mask (new-mat)
     res (new-mat)
     output (new-mat)
     ]
     (in-range hsv lower-red upper-red mask)
     (bitwise-and! buffer res mask)
     (hconcat [ buffer hsv (cvt-color! mask COLOR_GRAY2RGB) res] output)
     output))

(defn -main[& args]
   (u/simple-cam-window
     {:frame {:width 700 :height 200 :title "Red Red Red"}}
     (partial filter-stream lower-red upper-red )))

(comment
  (-main)

  (u/simple-cam-window
     {:frame {:width 700 :height 200 :title "Red Red Red"}}
     (partial filter-stream
        (new-scalar 100 70 70)
        (new-scalar 110 255 255)))
  )
