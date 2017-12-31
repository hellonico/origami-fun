;; gorilla-repl.fileformat = 1

;; **
;;; # Detect Movement
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

;; @@
(defn gray-clean![buffer]
  (-> buffer 
      clone 
      (cvt-color! COLOR_BGR2GRAY) 
      (gaussian-blur! (new-size 3 3) 0) 
      (convert-to! CV_32F)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/gray-clean!</span>","value":"#'talented-silence/gray-clean!"}
;; <=

;; @@
(defn test-gray![buffer]
  (-> buffer 
      gray-clean! 
      (convert-to! CV_8UC3)
      (cvt-color! COLOR_GRAY2RGB)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/test-gray!</span>","value":"#'talented-silence/test-gray!"}
;; <=

;; @@
(u/simple-cam-window test-gray!)
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### find diff
;; **

;; @@
(defn find-movement [ avg buffer]
  (let [gray (gray-clean! buffer) frame-delta (new-mat)]
   (if (nil? @avg)
    (reset! avg gray))

   (accumulate-weighted gray @avg 0.05 (new-mat))
    
   (absdiff gray @avg frame-delta)

   (-> frame-delta
    (threshold! 35 255 THRESH_BINARY)
    (dilate! (new-mat))
    (convert-to! CV_8UC3)
    (bitwise-not!)
    (cvt-color! COLOR_GRAY2RGB)
    (u/resize-by 0.8))
    
    ))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/find-movement</span>","value":"#'talented-silence/find-movement"}
;; <=

;; @@
(def find-movements! (partial find-movement (atom nil)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/find-movements!</span>","value":"#'talented-silence/find-movements!"}
;; <=

;; @@
(u/simple-cam-window find-movements!)
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### apply on color buffer
;; **

;; @@
(defn find-movement [ avg buffer]
  (let [ gray (base-gray! buffer)
         frame-delta (new-mat)
         contours (new-arraylist)]
    
    (if (nil? @avg)
      (reset! avg gray))
    
    (accumulate-weighted gray @avg 0.05 (new-mat))
    (absdiff gray @avg frame-delta)

    (-> frame-delta
     (threshold! 35 255 THRESH_BINARY)
     (dilate! (new-mat))
     (convert-to! CV_8UC3) 
     (find-contours contours (new-mat) RETR_EXTERNAL CHAIN_APPROX_SIMPLE))

   (-> frame-delta   
    (bitwise-not!)
    (cvt-color! COLOR_GRAY2RGB)
    (u/resize-by 0.8))

    (-> buffer
     (u/draw-contours-with-line! contours) ; (u/draw-contours-with-rect! contours )
     (u/resize-by 0.8))

    (hconcat! [frame-delta buffer])))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/find-movement</span>","value":"#'talented-silence/find-movement"}
;; <=

;; @@
(u/simple-cam-window
  (partial find-movement (atom nil)))  
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### diffing
;; **

;; @@
(def base-image (atom nil))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/base-image</span>","value":"#'talented-silence/base-image"}
;; <=

;; @@
(u/simple-cam-window 
  (fn[buffer] (reset! base-image buffer) ))
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(u/imshow @base-image)
;; @@
;; ->
;;; {:frame {:color 00, :title image, :width 400, :height 400}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#object[javax.swing.JPanel 0x1e6d9746 &quot;javax.swing.JPanel[null.contentPane,0,0,400x378,layout=java.awt.FlowLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=16777227,maximumSize=,minimumSize=,preferredSize=java.awt.Dimension[width=400,height=400]]&quot;]</span>","value":"#object[javax.swing.JPanel 0x1e6d9746 \"javax.swing.JPanel[null.contentPane,0,0,400x378,layout=java.awt.FlowLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=16777227,maximumSize=,minimumSize=,preferredSize=java.awt.Dimension[width=400,height=400]]\"]"}
;; <=

;; @@
(u/simple-cam-window (fn[buffer] 
                       (let[ output (new-mat)]
                         (absdiff buffer @base-image output)
                         output)))
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
