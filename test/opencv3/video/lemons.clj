(ns opencv3.video.lemons
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :refer :all]
    [opencv3.colors.rgb :as rgb]
    [opencv3.changesomecolors :refer [low-high!]]
    [opencv3.utils :as u]))

(comment

  (def capture (new-videocapture))
  (.open capture 0)

  (.set capture CAP_PROP_FRAME_WIDTH 400)
  (.set capture CAP_PROP_FRAME_HEIGHT 300)

  (def window
    (u/show (new-mat 400 400 CV_8UC3 (new-scalar 255 255 255))))
  (def buffer (new-mat))

  (dotimes [i 200]
    (.read capture buffer)
    (u/re-show window
      (-> buffer (low-high! 150 rgb/violetred 105 rgb/greenyellow))))

  (.release capture)

  )
