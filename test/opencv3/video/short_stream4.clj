(ns opencv3.video.short-stream4
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :as v]
    [opencv3.utils :as u]))

(defn color-filter [_mod image]
  (let [ total (* 3 (.total image))
         bytes (byte-array total)]
        (.get image 0 0 bytes)
        (doseq[^int i (range 0 total)]
          (if (= 0 (mod (+ i _mod) 3))
            (aset-byte bytes i 0)))
        (.put image 0 0 bytes)
        image))

(defn custom-gray-filter [image]
 (let[ target (new-mat (.cols image) (.rows image) CV_8SC1) crange (range 0 (.cols image)) ]
   (doseq [row (range 0 (.rows image))]
    (doseq [col crange]
     (let [rgb (.get image row col)]
       (.put target row col
         ;(byte-array (Math/min (first rgb) (Math/min (second rgb) (last rgb))))))))
         (byte-array (nth rgb 1))))))
         target))

(def yellow-filter
  (partial color-filter 0))
(def blue-filter
  (partial color-filter 1))
(def red-filter
  (partial color-filter 2))

(defn -main[ & args]

  (u/simple-cam-window
    {:frame {:width 660 :height 520 :color 0 :title "filters"} :video {:device 0 :width 600 :height 200}}
    (fn [buffer]
     (-> buffer
    ;  (u/resize-by 0.5)
     (blue-filter))))

     )

(comment

  (u/simple-cam-window
    {:frame {:width 660 :height 520 :color 0 :title "filters"} :video {:device 0 :width 300 :height 300}}
    (fn[buffer]
      (-> buffer
        ; (u/resize-by 0.5)
        (custom-gray-filter))))

  (u/simple-cam-window (fn [buffer]
    (-> buffer
     (u/resize-by 0.5)
     (red-filter))))

   (u/simple-cam-window (fn [buffer]
     (let [ resized (u/resize-by buffer 0.25)
      yellow (yellow-filter (clone resized))
      blue (blue-filter (clone resized))
      red (red-filter resized)
      output (new-mat)
      ]
      (vconcat [yellow blue red] output)
      output
      )))

  )
