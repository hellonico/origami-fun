(ns opencv3.video.twocameras
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :refer :all]
    [opencv3.utils :as u]))

(u/two-cams-window
  {:devices [
    {:device 0
     :width 250
     :height 150
     :fn #(-> % clone (cvt-color! COLOR_RGB2GRAY) (cvt-color! COLOR_GRAY2RGB)) }
    {:device 1
     :width 250
     :height 150
     :fn #(-> % clone (flip! -1))}
   ]
   :frame
    {:width 700
     :height 400
     :title "Two Cellos"}})
