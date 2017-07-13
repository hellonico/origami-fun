(ns opencv-fun.05-filter)

(import '[org.opencv.core Core CvType Mat Size]
  org.opencv.imgcodecs.Imgcodecs)

(def blue-filter #(byte-array [(first %) (second %) 0] ))
(def yellow-filter #(byte-array [0 (second %) (last %)] ))
(def red-filter #(byte-array [(first %) 0 (last %)] ))

; manual filter very slow
(defn filter-color[image-3 _filter]
  (doseq [row (range 0 (.rows image-3))]
    (doseq [col (range 0 (.cols image-3))]
      (let [rgb (.get image-3 row col)]
        (.put image-3 row col
          (_filter rgb))))))

(defn rw-filter [source _filter]
   (let [image-3 (Imgcodecs/imread source)]
     (filter-color image-3 _filter)
     (Imgcodecs/imwrite "target/img1.png" image-3)))
(rw-filter "resources/images/frame3.png" yellow-filter)

; manual filter faster
(defn filter-buffer [image-3 _mod]
  (let [ total (* 3 (.total image-3))
         bytes (byte-array total)]
        (.get image-3 0 0 bytes)
        (doseq[^int i (range 0 total)]
          (if (= 0 (mod i _mod))
            (aset-byte bytes i 0)))
        (.put image-3 0 0 bytes)))

(time 
(let [img (Imgcodecs/imread "resources/images/frame3.png")]
  (filter-buffer img 1)
(Imgcodecs/imwrite
  "target/img1.png"
  img)))
