(ns opencv3.video.filter
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :refer :all]
    [opencv3.utils :as u]))

(def height 300)
(def width 400)

(def capture (new-videocapture))
(doto capture
  (.open 0)
  (.set CAP_PROP_FRAME_WIDTH width)
  (.set CAP_PROP_FRAME_HEIGHT height))

(def window
  (u/show (new-mat height width CV_8UC3 (new-scalar 255 255 255))))

(defn filter-stream[ seconds ]
 (let [  timeout seconds ; seconds
         start (System/currentTimeMillis)
         ellapsed (atom 0)
         buffer (new-mat)
         lower-red  (new-scalar 0 70 70)
         upper-red (new-scalar 10 255 255)]
     (while (< @ellapsed timeout)
       (reset! ellapsed (/ (- (System/currentTimeMillis) start) 1000))
       (.read capture buffer)
       (u/resize-by buffer 0.5)
       (let [
         hsv (-> buffer clone (cvt-color! COLOR_BGR2HSV))
         mask (new-mat)
         res (new-mat)
         output (new-mat)
         ]
         (in-range hsv lower-red upper-red mask)
         (bitwise-and! buffer res mask)
         (hconcat [ buffer hsv (cvt-color! mask COLOR_GRAY2RGB) res] output)
         (u/re-show window output)))))

(comment
  (filter-stream 10)
  )
