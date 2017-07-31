(ns opencv3.video.short-stream4
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :as v]
    [opencv3.utils :as u]))

(defn filter-buffer [_mod image]
  (let [ total (* 3 (.total image))
         bytes (byte-array total)]
        (.get image 0 0 bytes)
        (doseq[^int i (range 0 total)]
          (if (= 0 (mod (+ i _mod) 3))
            (aset-byte bytes i 0)))
        (.put image 0 0 bytes)
        image))

(def yellow-filter
  (partial filter-buffer 0))
(def blue-filter
  (partial filter-buffer 1))
(def red-filter
  (partial filter-buffer 2))

(defn -main[ & args]

  (u/simple-cam-window (fn [buffer]
    (-> buffer
     (u/resize-by 0.5)
     (red-filter))))

     )

(comment

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
