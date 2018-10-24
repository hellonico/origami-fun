(ns opencv4.video.face-recognition
  (:require [opencv4.core :refer :all])
  (:require [opencv4.video :refer :all])
  (:require [opencv4.utils :as u]))


(defn draw-rects! [mat rects]
  (doseq [rect (.toArray rects)]
   (rectangle
    mat
    (new-point (.-x rect) (.-y rect))
    (new-point (+ (.-width rect) (.-x rect)) (+ (.-height rect) (.-y rect)))
    (new-scalar 236.09 68.0 60.0)
    5)
    mat
    ))

(def detector
  (new-cascadeclassifier "resources/lbpcascade_frontalface.xml"))

(defn -main[& args]
    (u/simple-cam-window
      (fn [buffer]
       (let [rects (new-matofrect)]
       (.detectMultiScale detector buffer rects)
       (draw-rects! buffer rects)
       buffer))))

(comment
  ; SHORT VERSION
  (-main)
  ; LONG VERSION
  (def capture (new-videocapture))
  (.open capture 0)

  (.set capture CAP_PROP_FRAME_WIDTH 400)
  (.set capture CAP_PROP_FRAME_HEIGHT 300)
  (.set capture CV_CAP_PROP_FPS 30)

  (def window
    (u/show (new-mat 400 400 CV_8UC3
      (new-scalar 255 255 255))))
  (def buffer (new-mat))
  (def rects (new-matofrect))

  (dotimes [i 200]
    (.read capture buffer)
    (.detectMultiScale detector buffer rects)
    (draw-rects! buffer rects)
    (u/re-show window buffer))

  (.release capture)

  )
