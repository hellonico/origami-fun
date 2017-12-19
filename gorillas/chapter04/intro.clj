;; gorilla-repl.fileformat = 1

;; **
;;; # Video Intro
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
;;; ### a la mano
;; **

;; @@

(def capture (v/new-videocapture))
(.open capture 0)

(def window
 (u/show (new-mat 200 200 CV_8UC3 (new-scalar 255 255 255))))

(def buffer (new-mat))
(dotimes [i 200]
 (.read capture buffer)
 (u/re-show
  window
  buffer))

(.release capture)
;; @@
;; ->
;;; {:frame {:color 00, :title image, :width 400, :height 400}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; #### simple streaming
;; **

;; @@
(u/simple-cam-window identity)
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### streaming with window settings
;; **

;; @@
(u/simple-cam-window 
 {:frame {:color "#ffcc88", :title "video", :width 200, :height 200} 
  :video {:device 0, :width 100, :height 120}}
  identity)
;; @@
;; ->
;;; {:frame {:color #ffcc88, :title video, :width 200, :height 200}, :video {:device 0, :width 100, :height 120}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### simple resize
;; **

;; @@
(u/simple-cam-window 
  (fn[buffer] (-> 
                buffer 
                (put-text! (str (java.util.Date.)) (new-point 100 50) FONT_HERSHEY_PLAIN 2 rgb/white 1)
                (u/resize-by 0.7) 
                ;(apply-color-map! COLORMAP_AUTUMN)
                (cvt-color! COLOR_BGR2GRAY)
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
;;; ### resize, text, and colormap
;; **

;; @@
(u/simple-cam-window 
  (fn[buffer] (-> 
                buffer 
                (put-text! (str (java.util.Date.)) (new-point 100 50) FONT_HERSHEY_PLAIN 2 rgb/white 1)
                (u/resize-by 0.7) 
                (apply-color-map! COLORMAP_OCEAN)
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
;;; ### two frames, different things
;; **

;; @@
(u/simple-cam-window
    (fn [buffer]
    (u/resize-by buffer 0.4)
    (vconcat! [
         (-> buffer 
          clone
          (cvt-color! COLOR_RGB2GRAY) 
          (cvt-color! COLOR_GRAY2RGB)
          (put-text! 
             (str (java.util.Date.)) 
             (new-point 10 50) FONT_HERSHEY_PLAIN 1 (new-scalar 255 255 0) 1))
         (-> buffer clone (flip! -1)) ])))
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### warping
;; **

;; @@

(def points1
  [[0 0] [250 50] [30 300] [500 300]])

(def points2
  [[0 0] [200 0] [30 300] [500 300]]) ; no transformation

(def points2
  [[0 0] [200 0] [30 300] [500 300]])
;  [[70 10] [200 52] [28 200] [389 390]])

(defn warp [ buffer ]
  (-> buffer
    (warp-perspective!
      (get-perspective-transform
        (u/matrix-to-matofpoint2f points1)
        (u/matrix-to-matofpoint2f points2))
        (size buffer ))))

(defn to-gray[ buffer ]
  (-> buffer (cvt-color! COLOR_RGB2GRAY) (cvt-color! COLOR_GRAY2RGB)))

(u/simple-cam-window
  {:video {:device 0} :frame {:width 650  :height 400 :title "Gray Cello"}}
  (comp to-gray warp))

;; @@
;; ->
;;; {:frame {:color 00, :title Gray Cello, :width 650, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### high low
;; **

;; @@
; from change colors
(defn low-high!
  ([image t1 color1 color2 ]
    (low-high! image t1 255 THRESH_BINARY color1 t1 255 THRESH_BINARY_INV color2))
  ([image a1 a2 a3 color1 b1 b2 b3 color2 ]
  (let [_copy (-> image clone (cvt-color! COLOR_BGR2HSV))
        _work (clone image)
        _thresh-1 (new-mat)
        _thresh-2 (new-mat)]

    (threshold _copy _thresh-1 a1 a2 a3)
    (cvt-color! _thresh-1 COLOR_BGR2GRAY)
    (set-to _work color1 _thresh-1)

    (threshold _copy _thresh-2 b1 b2 b3)
    (cvt-color! _thresh-2 COLOR_BGR2GRAY)
    (set-to _work color2 _thresh-2)
    _work)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;talented-silence/low-high!</span>","value":"#'talented-silence/low-high!"}
;; <=

;; @@
; rewrite
(defn high-low
  ([resize-factor color-limit c11 c12 c21 c22 buffer]
  (let [ buffer-1 (-> buffer (u/resize-by resize-factor))
         buffer-2 (clone buffer-1)
         output (new-mat)]
  (vconcat [
    (low-high! buffer-1 90 c11 c12)
    (low-high! buffer-2 90 c21 c22)
    ] output)
    output)))

(def high-low-0
  (partial high-low 0.5 90 rgb/violetred rgb/wheat rgb/violetred-4 rgb/papayawhip))
(def high-low-1
  (partial high-low 0.5 90 rgb/gray rgb/blue rgb/orange rgb/green))
(def high-low-2
  (partial high-low 0.75 90 rgb/crimson rgb/skyblue-1 rgb/green rgb/orange))

(u/simple-cam-window high-low-1)
;; @@
;; ->
;;; {:frame {:color 00, :title video, :width 400, :height 400}, :video {:device 0, :width 200, :height 220}}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### find red
;; **

;; @@

(defn find-red[ buffer ]
   (let [
    work (new-mat)
    mat1 (new-mat)
    output (new-mat)
    ]
  (->  buffer
  (cvt-color! COLOR_BGR2HSV)
  (in-range! (new-scalar 0 30 30) (new-scalar 30 255 255) work))
     
  (copy-to buffer output work)
  (cvt-color output output COLOR_HSV2BGR))
  )

(u/simple-cam-window find-red)
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
