;; gorilla-repl.fileformat = 1

;; **
;;; # Warping
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
;;; ### warping
;; **

;; @@
(def mt   
  (atom nil))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/mt</span>","value":"#'talented-silence/mt"}
;; <=

;; @@
(def points1
  [[100 10] 
   [200 100] 
   [28 200] 
   [389 390]])

(def points2
  [[70 0] 
   [200 140] 
   [28 400] 
   [389 390]])

(reset! mt 
  (get-perspective-transform
   (u/matrix-to-matofpoint2f points1)
   (u/matrix-to-matofpoint2f points2)))

(dump @mt)
;; @@
;; ->
;;; [2.886257017081082 0.5416654100983967 -170.5772506586461]
;;; [1.468397820944848 1.95276337269669 -166.3674158214515]
;;; [0.007917085869390437 -0.002792137050410389 1]
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn warp! [ buffer ]
  (-> buffer
    (warp-perspective! @mt (size buffer ))))

(defn to-gray! [ buffer ]
  (-> buffer (cvt-color! COLOR_RGB2GRAY) ))

(u/simple-cam-window
  {:video {:device 0} 
   :frame {:width 650  :height 400 :title "Warped"}}
  (comp to-gray! warp!))
;; @@
;; ->
;;; {:frame {:color 00, :title Gray Cello, :width 650, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=
