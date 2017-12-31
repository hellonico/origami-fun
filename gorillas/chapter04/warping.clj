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
(defn my-fn[mat]
  (-> mat 
    (put-text! (str (java.util.Date.)) (new-point 100 50) FONT_HERSHEY_PLAIN 1 rgb/white 1) 
    (apply-color-map! COLORMAP_AUTUMN)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/my-fn</span>","value":"#'talented-silence/my-fn"}
;; <=

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
  [[70 10] 
   [200 140] 
   [20 200] 
   [389 390]])

(reset! mt 
  (get-perspective-transform
   (u/matrix-to-matofpoint2f points1)
   (u/matrix-to-matofpoint2f points2)))

(dump @mt)
;; @@
;; ->
;;; [1.789337561985906 0.3234215275201738 -94.5799621372129]
;;; [0.7803091692375479 1.293303360247406 -78.45137776386103]
;;; [0.002543030309135725 -3.045754676722361E-4 1]
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn warp! [ buffer ]
  (-> buffer
    (warp-perspective! @mt (size buffer ))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/warp!</span>","value":"#'talented-silence/warp!"}
;; <=

;; @@
(-> "resources/chapter03/ai5.jpg"
 	imread
    (u/resize-by 0.7)
    warp!
    u/imshow)
;; @@
;; ->
;;; {:frame {:color 00, :title image, :width 400, :height 400}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#object[javax.swing.JPanel 0x77260939 &quot;javax.swing.JPanel[null.contentPane,0,0,400x378,layout=java.awt.FlowLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=16777225,maximumSize=,minimumSize=,preferredSize=java.awt.Dimension[width=400,height=400]]&quot;]</span>","value":"#object[javax.swing.JPanel 0x77260939 \"javax.swing.JPanel[null.contentPane,0,0,400x378,layout=java.awt.FlowLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=16777227,maximumSize=,minimumSize=,preferredSize=java.awt.Dimension[width=400,height=400]]\"]"}
;; <=

;; @@
(u/simple-cam-window warp!)
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
