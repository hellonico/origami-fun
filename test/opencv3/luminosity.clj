(ns opencv3.luminosity
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

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

; (average (mat-to-bytes (first channels)))

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
    (let [new-ch
    (byte-to-mat
     (byte-array (map fnc (mat-to-bytes (nth channels chan)) ))
     (new-mat (.height mat) (.width mat) CV_8U)   )]
     (.set channels chan new-ch)
     (merge channels target)
     target)))

(-> img
  clone
  ; (cvt-color! COLOR_BGR2YUV)
  ; (update-channel! #(int (mod (+ % 0) 256)) 0)
  (update-channel! (fn[x] -200) 2)
  ; (cvt-color! COLOR_YUV2BGR)
  (imwrite "output/channels.png"))
