(ns opencv4.luminosity
  (:require
    [opencv4.core :refer :all]
    [opencv4.utils :as u]))

(def channels (new-arraylist))
(->
"resources/images/cat.jpg"
 imread
 (u/resize-by 0.25)
 (cvt-color! COLOR_BGR2YUV)
 (split channels) )

(defn byte-to-mat[bytes mat]
  ; (let [mat (new-mat size)]
     (.put mat 0 0 bytes);
     mat)

(defn mat-to-bytes[mat]
  (let [bytes (byte-array (* (.total mat) (.channels mat))) ]
  (.get mat 0 0 bytes)
  bytes))

(defn average [coll]
  (/ (apply + coll) (count coll)))

;
; COLORS
;

(def channels (new-arraylist))
(def chan 2)
(def img (->
"resources/images/cat.jpg"
 imread
 (u/resize-by 0.25)))

(split img channels)

(def new-ch
  (byte-to-mat
   (byte-array (map #(byte (/ (+ % 10) 128)) (mat-to-bytes (nth channels chan)) ))
   (new-mat 300 400 CV_8U)   ))

(.set channels chan new-ch)

(def target (new-mat))
(merge channels target)
(u/show target)

;
; LUMINOSITY
;

(def img (->
"resources/images/cat.jpg"
 imread
 (u/resize-by 0.25)))

(defn update-channel! [ mat fnc chan]
  (let [ channels (new-arraylist)  target (new-mat)]
    (split img channels)
    (let [
      old-ch (nth channels chan)
      new-ch
    (byte-to-mat
     (byte-array (map fnc (mat-to-bytes old-ch) ))
     (new-mat (.height mat) (.width mat) (.type old-ch) )  )]
     (.set channels chan new-ch)
     (merge channels target)
     target)))

(comment
; luma[y] = (luma[y]-128)*1.10+128

(-> img
  clone
  ; (update-channel! (fn [x] (if (> x 30) 30 x)) 0) ; goes yellow
  ; (update-channel! (fn [x] (if (> x 30) 30 x)) 1) ; goes red
  ; (update-channel! identity 2)
  (update-channel! (fn [x] (let [y (+ x 128)] (if (< y 0) (spit "test.log" y :append true))) x) 2) ; goes green
  (cvt-color! COLOR_YUV2RGB)
  (imwrite "output/channels.png"))

(-> img
  clone
  ; (cvt-color! COLOR_BGR2YUV)
  (update-channel! identity 2)
  ; (cvt-color! COLOR_YUV2BGR)
  (imwrite "output/channels.png"))

)
