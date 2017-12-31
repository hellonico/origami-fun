;; gorilla-repl.fileformat = 1

;; **
;;; # Two Cams
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
;;; #### two frames
;; **

;; @@

(defn to-gray[buffer ]
  (-> buffer (cvt-color! COLOR_RGB2GRAY) (cvt-color! COLOR_GRAY2RGB)))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/to-gray</span>","value":"#'talented-silence/to-gray"}
;; <=

;; @@

(defn render-two [ left right ]
  (let [ output (new-mat (rows left) (* 2 (cols left))  CV_8UC3 (new-scalar 255 255 255))
  ol_ (submat output (new-rect 0 0 (cols left) (rows left)))
  or_ (submat output (new-rect (cols left) 0 (cols left) (rows left)))
  ]
  (copy-to left ol_)
  (resize! right (size or_))
  (copy-to right or_)
  ;(u/resize-by output 0.3)
  output))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/render-two</span>","value":"#'talented-silence/render-two"}
;; <=

;; @@
(u/cams-window
   {:devices [
     {:device 0 :width 200 :height 150 :fn to-gray}
     {:device 1 :width 200 :height 150 :fn identity}
    ]
    :video { :fn  render-two }
    :frame
    {:width 650 :height 300 :title "OneOfTheSame"}})
;; @@
;; ->
;;; {:frame {:color 00, :title OneOfTheSame, :width 650, :height 300}, :devices [{:device 0, :width 200, :height 150, :fn #function[talented-silence/to-gray]} {:device 1, :width 200, :height 150, :fn #function[clojure.core/identity]}], :video {:fn #function[talented-silence/render-two]}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
