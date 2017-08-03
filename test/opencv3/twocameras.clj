(ns opencv3.video.twocameras
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :refer :all]
    [opencv3.utils :as u]))

(defn render-two [ left right]
  (let [ output (new-mat (rows left) (* 2 (cols left))  CV_8UC3)
  ol (.submat output (new-rect 0 0 (cols left) (rows left)))
  or (.submat output (new-rect (cols left) 0 (cols left) (rows left)))
  ]
  (set-to output (new-scalar 255 255 255))
  (copy-to left ol)
  (resize! right (size or))
  (copy-to right or)
  output))

(defn to-gray[buffer ]
  (-> buffer (cvt-color! COLOR_RGB2GRAY) (cvt-color! COLOR_GRAY2RGB)))

(comment
(u/two-cams-window
  {:devices [
    {:device 0
     :width 250
     :height 150
     :fn  to-gray}
    {:device 1
     :width 250
     :height 150
     :fn to-gray}
   ]
   :video {
     :fn render-two
   }
   :frame
    {:width 650
     :height 300
     :title "TwoCellos"}})

(u/two-cams-window
 {:devices [
   {:device 0
    :width 100
    :height 80
    :fn  to-gray}
   {:device 0
    :width 100
    :height 80
    :fn (comp to-gray #(flip! % 1))}
  ]
  :video {
    :fn render-two
  }
  :frame
   {:width 650
    :height 300
    :title "TwoOfTheSame"}})

)
