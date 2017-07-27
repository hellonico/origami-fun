(ns opencv3.video.writingv2
  ; (:import
  ;   [org.opencv.videoio VideoWriter])
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]
    [opencv3.video :refer :all]))

(defn -main[& args]
  (let[ outputVideo
    (new-videowriter)]
  (.open
    outputVideo
    (first args)
    ;(VideoWriter/fourcc \M \J \P \G)
    1196444237
    30
    (new-size 240 320))

  (u/simple-cam-window
    (fn [buffer]
     (let [ r (u/resize-by buffer 0.5) o (clone r)]
     (.write outputVideo (rotate! r ROTATE_90_CLOCKWISE))
     o)))
  ;  (.release outputVideo)
))
