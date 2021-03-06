(ns opencv4.hand
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :refer :all]
    [opencv4.utils :as u]))

(def detector
  (new-cascadeclassifier "resources/lbpcascade_frontalface.xml"))

(defn draw-rect
  [mat rect _color]
  (rectangle
    mat
    (new-point (.-x rect) (.-y rect))
    (new-point (+ (.-width rect) (.-x rect)) (+ (.-height rect) (.-y rect)))
    _color
    5))

(defn my-fn [ mat ]
  (let [rects (new-matofrect)]
  (.detectMultiScale detector mat rects)
  ; (doseq [rect (.toArray rects)]
    ; (draw-rect mat rect opencv4.colors.rgb/pink))
    (draw-rect
      mat
      (first (.toArray rects))
      opencv4.colors.rgb/pink)
    mat))

(u/cams-window
 {:devices [
   {:device 0 :width 200 :height 150 :fn my-fn}
  ]
  :video { :fn identity }
  :frame
  {:width 350 :height 300 :title "OneOfTheSame"}})
