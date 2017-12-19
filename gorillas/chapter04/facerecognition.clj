;; gorilla-repl.fileformat = 1

;; **
;;; # face recognition
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
;;; ### face recognition
;; **

;; @@
(def detector
  (new-cascadeclassifier
    "resources/lbpcascade_frontalface.xml"))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/detector</span>","value":"#'talented-silence/detector"}
;; <=

;; **
;;; #### draw rects around faces
;; **

;; @@
(defn draw-rects! [buffer rects]
 (doseq [rect (.toArray rects)]
  (rectangle
   buffer
   (new-point (.-x rect) (.-y rect))
   (new-point (+ (.-width rect) (.-x rect)) (+ (.-height rect) (.-y rect)))
   rgb/blue
   5))
  
   buffer)


(u/simple-cam-window
  (fn [buffer]
   (let [rects (new-matofrect)]
    (.detectMultiScale detector buffer rects)
    (-> buffer
        (draw-rects! rects)
        (u/resize-by 0.7))
     )))
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; #### change color map of the face 
;; **

;; @@
(defn draw-rects! [buffer rects]
   (doseq [r (.toArray rects)]
     (-> buffer
      (submat r) 
      (apply-color-map! COLORMAP_COOL)
      (copy-to (submat buffer r))))
   (put-text! buffer (str (count (.toArray rects) ) )
      (new-point 30 100) FONT_HERSHEY_PLAIN 5 rgb/magenta-2 2))

(u/simple-cam-window
  (fn [buffer]
   (let [rects (new-matofrect)]
    (.detectMultiScale detector buffer rects)
    (-> buffer
        (draw-rects! rects)
        (u/resize-by 0.5)))))
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=
