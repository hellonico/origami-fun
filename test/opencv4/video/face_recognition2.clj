(ns opencv4.video.face-recognition2
  (:require [opencv4.core :refer :all])
  (:require [opencv4.video :refer :all])
  (:require [opencv4.utils :as u]))


(defn draw-rects! [buffer rects]
  (let[ copy (-> buffer clone (cvt-color! COLOR_BGR2GRAY) (cvt-color! COLOR_GRAY2BGR)) ]
  (doseq [r (.toArray rects)]
  (let [ s (.submat buffer r ) mask (new-mat)]
    (.copyTo s (.submat copy r) mask)))
    copy))

(def detector
  (new-cascadeclassifier
    "resources/lbpcascade_frontalface.xml"))

(defn -main[& args]
  (u/simple-cam-window
    (fn [buffer]
     (let [rects (new-matofrect)]
     (.detectMultiScale detector buffer rects)
     (draw-rects! buffer rects)))))

(comment
(-main)
)
