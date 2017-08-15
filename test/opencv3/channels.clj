(ns opencv3.channels
  (:require
    [opencv3.utils :as u]
    [opencv3.core :refer :all]))

(defn custom-filter! [ img fnc]
  (let [ total (* 3 (.total img))
  bytes (byte-array total)]
  (.get img 0 0 bytes)
  (doseq [^int i (range 0 total)]
  (fnc bytes i))
  (.put img 0 0 bytes)
  img))
  (defn yellow-filter[ bytes ^long i]
    (let [ _mod 0]
      (if (= 0 (mod (+ i _mod) 3))
      (aset-byte bytes i 0))))

; 2000ms
(comment
(time
  (->
    "resources/images/cat.jpg"
    (imread)
    (custom-filter! yellow-filter )
    (imwrite "output/filter-cat.png")))
)

(defn custom-filter2! [ img _mod]
  (let [ total (* 3 (.total img))
  bytes (byte-array total)]
  (.get img 0 0 bytes)
  (doseq [^int i (range 0 total)]
  (if (= 0 (mod (+ i _mod) 3))
  (aset-byte bytes i 0)))
  (.put img 0 0 bytes)
  img))

; 2000ms
(comment
(time
  (->
    "resources/images/cat.jpg"
    (imread)
    (custom-filter2! 1 )
    (imwrite "output/filter-cat.png")))
)

(defn filter-color[img]
  (doseq [^int row (range 0 (.rows img))]
    (doseq [^int col (range 0 (.cols img))]
      (let [^byte rgb (.get img row col)]
        (.put img row col
          (byte-array [(first rgb) (second rgb) 0])))))
          img)

; "Elapsed time: 51206.066888 msecs"
(comment
(time
  (->
    "resources/images/cat.jpg"
    (imread)
    (filter-color )
    (imwrite "output/filter-cat.png")))
)

(defn custom-filter3! [ img ]
  (let [ total (* 3 (.total img))
  bytes (byte-array total)]
  (.get img 0 0 bytes)
  (doseq [[i bb] (map-indexed (fn [idx itm] [idx itm]) (partition 3 (range 0 total))) ]
   (aset-byte bytes (inc (* 3 i)) 0)
   )
  (.put img 0 0 bytes)
  img))

; "Elapsed time: 5257.925347 msecs"
(comment
(time
  (->
    "resources/images/cat.jpg"
    (imread)
    (custom-filter3! )
    (imwrite "output/filter-cat.png")))
)

(defn byte-to-mat[bytes mat]
     (.put mat 0 0 bytes);
     mat)

(defn mat-to-bytes[mat]
  (let [bytes (byte-array (* (.total mat) (.channels mat))) ]
  (.get mat 0 0 bytes)
  bytes))

(defn update-channel! [ mat fnc chan]
  (let [ channels (new-arraylist)  target (new-mat)]
    (split mat channels)
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
; "Elapsed time: 1543.476998 msecs"
(time
 (->
   "resources/images/cat.jpg"
   (imread)
   (update-channel! (fn [x] 0) 0)
   (imwrite "output/filter-cat.png")))

; "Elapsed time: 2470.600356 msecs"
(time
(->
  "resources/images/cat.jpg"
  (imread)
  (update-channel! (fn [x] 0) 1)
  (update-channel! (fn [x] 0) 2)
  (imwrite "output/filter-cat.png")))

(time
(->
  "resources/images/cat.jpg"
  (imread)
  (u/resize-by 0.5)
  (cvt-color! COLOR_RGB2HSV)
  ; (update-channel! (fn [x] (Math/min 0 (- 150 x))) 0)
  (update-channel! (fn [x] 150) 2)
  (cvt-color! COLOR_HSV2RGB)
  (imwrite "output/filter-cat.png")))

(time
(->
  "resources/images/cat.jpg"
  (imread)
  (cvt-color! COLOR_RGB2YUV)
  ; (update-channel! (fn [x] (let [ y (+ 30 x)] (if (< 0 y) x y))) 0)
  (update-channel! (fn [y] (if (< 0 y) 0 y)) 0)
  (cvt-color! COLOR_YUV2RGB)
  (imwrite "output/filter-cat.png")))

(time
(->
  "resources/images/cat.jpg"
  (imread)
  (u/resize-by 0.5)
  (cvt-color! COLOR_RGB2Luv)
  (update-channel! (fn [x] (Math/max 250 (+ 150 x))) 0)
  ; (update-channel! (fn [x] ) 0)
  (cvt-color! COLOR_Luv2RGB)
  (imwrite "output/filter-cat.png")))


(time
(->
  "resources/images/cat.jpg"
  (imread)
  (u/resize-by 0.5)
  (cvt-color! COLOR_RGB2HLS)
  ; (update-channel! (fn [x] 150) 0) ; pink filter
  (update-channel! (fn [x] 120) 0) ; red filter
  (cvt-color! COLOR_HLS2RGB)
  (imwrite "output/filter-cat.png")))
)
