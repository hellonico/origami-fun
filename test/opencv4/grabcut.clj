(ns opencv4.grabcut
 (:require
  [opencv4.core :refer :all]
  [opencv4.utils :as u]))

; https://stackoverflow.com/questions/33071111/setting-a-new-mask-grabcut-java
; http://answers.opencv.org/question/72690/grabcut-implementation-in-java/
; http://docs.opencv.org/3.2.0/d8/d83/tutorial_py_grabcut.html

; should work better with a mask on the cat
(def source "resources/images/kidrunning.jpeg")
(def img (-> source imread (u/resize-by 0.5)))
; (u/show img)
(def mask (new-mat))
(def rect (new-rect 50 50 250 350))
(grab-cut img mask rect (new-mat) (new-mat) 5 GC_INIT_WITH_RECT)

(def fg-mask (clone mask))
(def pfg-mask (clone mask))

(def source1 (new-mat 1 1 CV_8U (new-scalar 3.0)))
(compare mask source1 pfg-mask CMP_EQ)

(def source2 (new-mat 1 1 CV_8U (new-scalar 1.0)))
(compare mask source2 fg-mask CMP_EQ)

(def fg_foreground (new-mat (.size img) (.type img) (new-scalar 0 0 0)))
(def pfg_foreground (new-mat (.size img) (.type img) (new-scalar 0 0 0)))

(def final-mask (new-mat))
(bitwise-or pfg-mask fg-mask final-mask)
(.copyTo img fg_foreground fg-mask)
(.copyTo img pfg_foreground pfg-mask)

;(u/show pfg_foreground)

(def foreground (new-mat (.size fg_foreground) CV_8UC3))
(bitwise-or fg_foreground pfg_foreground foreground)

(u/show foreground)
