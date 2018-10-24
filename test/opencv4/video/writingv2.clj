(ns opencv4.video.writingv2
  (:require
    [opencv4.core :refer :all]
    [opencv4.utils :as u]
    [opencv4.video :refer :all]))

(defn -main[& args]
  (let[ outputVideo
    (new-videowriter)]
  (.open
    outputVideo
    (first args)
    ;(VideoWriter/fourcc \M \J \P \G)
    1196444237
    30
    (new-size 240 320) ;
    )

  (u/simple-cam-window
    (fn [buffer]
     (let [ r (u/resize-by buffer 0.5) o (clone r)]
     (.write outputVideo (rotate! r ROTATE_90_CLOCKWISE))
     o)))
  ;  (.release outputVideo)
))

; (-main)
