;; gorilla-repl.fileformat = 1

;; **
;;; # Multiple Cams
;;; 
;; **

;; @@
(ns talented-silence
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :as v]
    [opencv3.colors.rgb :as rgb]
    [opencv3.utils :as u]))
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
      (draw-rect mat rect opencv3.colors.rgb/pink))
    mat))

(u/cams-window
 {:devices [
   {:device 0 :width 200 :height 150 :fn my-fn}
  ]
  :video { :fn identity }
  :frame
  {:width 350 :height 300 :title "OneOfTheSame"}})
;; @@
;; ->
;;; {:frame {:color 00, :title OneOfTheSame, :width 350, :height 300}, :devices [{:device 0, :width 200, :height 150, :fn #function[talented-silence/my-fn]}], :video {:fn #function[clojure.core/identity]}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=
