(ns opencv3.rotation
  (:require
    [opencv3.utils :as u]
    [opencv3.core :refer :all]))

(def img1 (imread "resources/images/cat.jpg"))
(defn center-crop[img dest]
  (let[
    w (.cols img)
    h (.rows img)
    s (clojure.core/min w h)
    rect (new-rect
      (- (/ w 2) (/ s 2))
      (- (/ h 2) (/ s 2))
      s
      s)
    ]
    (.copyTo (.submat img rect) dest)))
(defn center-crop![src]
  (center-crop src src)
  src
  )

(def img0 (-> img1 clone center-crop!))
(def img2 (-> img1 clone (rotate! ROTATE_90_CLOCKWISE) center-crop!))
(def img3 (-> img1 clone (rotate! ROTATE_180) center-crop!))
(def img4 (-> img1 clone (rotate! ROTATE_90_COUNTERCLOCKWISE) center-crop!))

(def up   (new-mat))
(def down (new-mat))

(hconcat [img4 img2] up)
(hconcat [img3 img0] down)

(def target (new-mat))
(vconcat [up down] target)

(imwrite target "output/rotate.png")
