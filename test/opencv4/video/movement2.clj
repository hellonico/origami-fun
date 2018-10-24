(ns opencv4.video.movement2
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :refer :all]
    [opencv4.utils :as u]))

; https://stackoverflow.com/questions/45299447/python-overlapping-boxes-around-motion-detected
(defn find-movement[ avg buffer]
  (let [gray (-> buffer clone (cvt-color! COLOR_BGR2GRAY) (gaussian-blur! (new-size 3 3) 0) (convert-to! CV_32F))
       frame-delta (new-mat)
       output (new-mat)
       contours (new-arraylist)]
     (if (nil? @avg)
      (reset! avg gray))
     (do
      (accumulate-weighted gray @avg 0.05 (new-mat))
      (absdiff gray @avg frame-delta)
      (-> frame-delta
       (threshold! 35 255 THRESH_BINARY)
       (dilate! (new-mat))
       (convert-to! CV_8UC3)
       (find-contours contours (new-mat) RETR_EXTERNAL CHAIN_APPROX_SIMPLE))
       ; choose one
       (u/draw-contours-with-line! buffer contours )
       ; (u/draw-contours-with-rect! buffer contours )

      (cvt-color! frame-delta COLOR_GRAY2RGB)
      (u/resize-by frame-delta 0.5)
      (u/resize-by buffer 0.5)
      (hconcat [frame-delta buffer] output))
      output
      ))

(defn -main [ & args]
  (u/simple-cam-window
    (partial find-movement (atom nil)))
  )
