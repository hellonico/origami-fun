(ns opencv3.video.movement
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :refer :all]
    [opencv3.utils :as u]))

; https://stackoverflow.com/questions/45299447/python-overlapping-boxes-around-motion-detected

(def capture (new-videocapture))
(.open capture 0)
(def height 400)
(def width 300)
(.set capture CAP_PROP_FRAME_WIDTH width)
(.set capture CAP_PROP_FRAME_HEIGHT height)

(def window
  (u/show (new-mat height width CV_8UC3 (new-scalar 255 255 255))))

(defn quick-draw-contours[contours buffer]
  (doseq [c contours]
    (let [area (contour-area c)]
       (if (> area 2000)
         (let [rect (bounding-rect c)]
         (rectangle
           buffer
           (new-point (.x rect) (.y rect))
           (new-point (+ (.width rect) (.x rect)) (+ (.y rect) (.height rect)))
           (new-scalar 50 135 135) 3))))))

(let [buffer (new-mat) avg (atom nil) ]
  (dotimes [i 200]
   (.read capture buffer)
   (let [gray (-> buffer clone (cvt-color! COLOR_BGR2GRAY) (gaussian-blur! (new-size 3 3) 0) (convert-to! CV_32F))
        frame-delta (new-mat)
        contours (new-arraylist)]
      ; needed for accumulate weighted
     (if (nil? @avg)
      (reset! avg gray)
      (do
       (accumulate-weighted gray @avg 0.05 (new-mat))
       ; up to here is a pretty cool ghost effect
       (absdiff gray @avg frame-delta)
       (-> frame-delta
        (threshold! 35 255 THRESH_BINARY)
        (dilate! (new-mat))
        (convert-to! CV_8UC3)
        (find-contours contours (new-mat) RETR_TREE CHAIN_APPROX_SIMPLE))
        (quick-draw-contours contours buffer)
       ;  see movement
       ;  (u/re-show window frame-delta)
       (u/re-show window buffer))))))

(.release capture)
