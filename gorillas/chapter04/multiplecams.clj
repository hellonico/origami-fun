;; gorilla-repl.fileformat = 1

;; **
;;; # Multiple Cams
;;; 
;; **

;; @@
(ns talented-silence
  (:require
    [opencv4.core :refer :all]
    [opencv4.colors.rgb :as rgb]
    [opencv4.utils :as u]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### one cam setup
;; **

;; @@
(def detector
  (new-cascadeclassifier "resources/lbpcascade_frontalface.xml"))

(defn draw-rect [mat rect _color]
  (rectangle
    mat
    (new-point (.-x rect) (.-y rect))
    (new-point (+ (.-width rect) (.-x rect)) (+ (.-height rect) (.-y rect)))
    _color
    5))

(defn my-fn [ mat ]
  (let [rects (new-matofrect)]
  (.detectMultiScale detector mat rects)
    (doseq [rect (.toArray rects)]
      (draw-rect mat rect opencv4.colors.rgb/pink))
    mat))

(u/cams-window
 {:devices [{:device 0 :width 200 :height 150 :fn my-fn}]
  :video { :fn identity }
  :frame {:width 500 :height 500 :title "OneOfTheSame"}})
;; @@
;; ->
;;; {:frame {:color 00, :title OneOfTheSame, :width 500, :height 500}, :devices [{:device 0, :width 200, :height 150, :fn #function[talented-silence/my-fn]}], :video {:fn #function[clojure.core/identity]}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
