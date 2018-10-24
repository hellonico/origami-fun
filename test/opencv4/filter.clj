(ns opencv4.filter
  (:require
    [opencv4.utils :as u]
    [opencv4.core :refer :all]))

(def blue-filter
  #(byte-array [(first %) (second %) 0] ))
(def yellow-filter
  #(byte-array [0 (second %) (last %)] ))
(def red-filter
  #(byte-array [(first %) 0 (last %)] ))

; replace the pixels one by one
; possible but very slow
(defn filter-color[image-3 _filter]
  (doseq [row (range 0 (.rows image-3))]
    (doseq [col (range 0 (.cols image-3))]
      (let [rgb (.get image-3 row col)]
        (.put image-3 row col
          (_filter rgb)))))
          image-3)
; so do not do it this way :)
; (->
;   "resources/images/cat.jpg"
;   (imread)
;   (filter-color blue-filter)
;   (imwrite "output/cat3.png"))

; manual filter faster


(defn filter-buffer [image-3 _mod]
  (let [ total (* 3 (.total image-3))
         bytes (byte-array total)]
        (.get image-3 0 0 bytes)
        ;(println (count bytes))
        (doseq[^int i (range 0 total)]
          (if (= 0 (mod (+ i _mod) 3))
            (aset-byte bytes i 0)))
        (.put image-3 0 0 bytes)
        image-3))

(comment


(->
  "resources/images/cat.jpg"
  (imread)
  (filter-buffer 2)
  (imwrite "output/cat3.png"))

(def source (imread "resources/images/cat.jpg"))
(def target (new-mat))
 (map #(-> % (clone) (filter-buffer) )))

(vconcat
  (into []
  (map #(filter-buffer (clone source) %) (range 0 3)))
  target)

(imwrite target "output/cat3.png")

)
