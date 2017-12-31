;; gorilla-repl.fileformat = 1

;; **
;;; # Two Frames
;; **

;; @@
(ns balmy-spring
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
;;; ### two frames, three frames
;; **

;; @@
(u/simple-cam-window
    (fn [buffer]
    (hconcat! [
     (-> buffer 
         clone
         (cvt-color! COLOR_RGB2GRAY) 
         (cvt-color! COLOR_GRAY2RGB))
     (-> buffer clone (flip! -1)) ])))
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(u/simple-cam-window
    (fn [buffer]
    (hconcat! [
     (-> buffer clone (put-text! 
           (str (java.util.Date.)) 
             (new-point 10 50) FONT_HERSHEY_PLAIN 1 rgb/black 1))
     (-> buffer 
         clone
         (cvt-color! COLOR_RGB2GRAY) 
         (cvt-color! COLOR_GRAY2RGB))
     (-> buffer clone (flip! -1)) ])))

;; @@

;; @@
(u/simple-cam-window
    (fn [buffer]
    (hconcat! [
     (-> buffer clone (apply-color-map! COLORMAP_JET))
     (-> buffer clone (apply-color-map! COLORMAP_BONE))
     (-> buffer clone (apply-color-map! COLORMAP_PARULA))])))
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### two devices
;; **

;; @@
(defn to-gray[buffer ]
  (-> buffer clone (cvt-color! COLOR_RGB2GRAY) (cvt-color! COLOR_GRAY2RGB)))

(u/cams-window
   {:devices [
     {:device 0 :width 300 :height 200 :fn identity}
     {:device 1 :width 300 :height 200 :fn identity}
    ]
    :video { :fn 
             #(hconcat! [
                (-> %1 (resize! (new-size 300 200)))
                (-> %2 (resize! (new-size 300 200))) ])}  
    :frame
    {:width 650 :height 250 :title "OneOfTheSame"}})
;; @@
;; ->
;;; {:frame {:color 00, :title OneOfTheSame, :width 650, :height 250}, :devices [{:device 0, :width 300, :height 200, :fn #function[clojure.core/identity]} {:device 1, :width 300, :height 200, :fn #function[clojure.core/identity]}], :video {:fn #function[balmy-spring/eval10725$fn--10726]}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=
