(ns opencv4.video.scaling
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :refer :all]
    [opencv4.colors.rgb :as rgb]
    [opencv4.utils :as u]))

(comment
(u/simple-cam-window (fn [ buffer ]
  (-> buffer
  (cvt-color! COLOR_RGB2HLS)
  (multiply! (u/matrix-to-mat-of-double [ [1.0 1.3 1.3]] ))
  (cvt-color! COLOR_HLS2RGB))
  ))

(u/simple-cam-window (fn [ buffer ]
  (-> buffer
  (multiply! (u/matrix-to-mat-of-double [ [1.0 0.0 0.0]] ))
  (cvt-color! COLOR_RGB2HLS)
  (multiply! (u/matrix-to-mat-of-double [ [1.0 1.5 1.5]] ))
  (cvt-color! COLOR_HLS2RGB))
  ))

)
