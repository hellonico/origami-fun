(ns opencv4.rotasquare
  (:require
    [opencv4.utils :as u]
    [opencv4.core :refer :all]))

(defn center-crop[img dest]
  (let[
    w (.cols img)
    h (.rows img)
    s (clojure.core/min w h)
    rect (new-rect
      (- (/ w 2) (/ s 2))
      (- (/ h 2) (/ s 2))
      s
      s)]
      (.copyTo (.submat img rect) dest)))

(defn center-crop![src]
  (center-crop src src)
  src)

(defn rotation-collage[img0]
  (let[
    img1 (-> img0 clone center-crop!)
    img2 (-> img0 clone (rotate! ROTATE_90_CLOCKWISE) center-crop!)
    img3 (-> img0 clone (rotate! ROTATE_180) center-crop!)
    img4 (-> img0 clone (rotate! ROTATE_90_COUNTERCLOCKWISE) center-crop!)
    up   (new-mat)
    down (new-mat)
    target (new-mat)
    ]
    (hconcat [img4 img2] up)
    (hconcat [img3 img1] down)
    (vconcat [up down] target)
    target))

(defn -main[ & args]
  (imwrite
    (rotation-collage
    (imread (first args)))
    (second args)))
