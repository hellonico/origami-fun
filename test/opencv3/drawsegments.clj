(ns opencv3.drawsegments
  (:require
    [opencv3.utils :as u]
    [opencv3.colors.rgb :as color]
    [opencv3.core :refer :all]))



;
; find liines in a picture using draw segment
;
; http://docs.opencv.org/java/3.0.0/org/opencv/imgproc/LineSegmentDetector.html

(def parking2    (imread "resources/images/lines/parking.png" CV_8UC1))
(def det (create-line-segment-detector))
(def lines (new-mat))
(def result (clone parking2))
(.detect det parking2 lines)
(.drawSegments det result lines)
(def output (new-mat))
(vconcat [(cvt-color! parking2 COLOR_GRAY2BGR) result] output)
(imwrite output "output/hough2.png")
