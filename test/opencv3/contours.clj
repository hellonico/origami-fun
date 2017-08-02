(ns opencv3.contours
  (:require
    [opencv3.utils :as u]
    [opencv3.core :refer :all]))

;
; find contours
;
; http://answers.opencv.org/question/12056/findcontours-in-java-not-giving-desired-results/

(def image (imread "resources/images/cat.jpg"))
(def hsv (new-mat (.cols image) (.rows image) CV_8UC4))
(def blurred (new-mat (.cols image) (.rows image)  CV_8UC4))
(def image-a (new-mat (.cols image) (.rows image)  CV_32F))
(cvt-color image hsv COLOR_BGR2GRAY)
(gaussian-blur hsv blurred (new-size 5 5) 0 0)
(adaptive-threshold blurred image-a 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 7 5)
(imwrite image-a "output/contours.png")

(def image-b (clone image-a))
(def contours
  (new-arraylist))
(def mask (new-mat))
(find-contours image-a contours mask RETR_LIST CHAIN_APPROX_SIMPLE)
(doseq [c contours]
  (if (> (contour-area c) 100 )
    (let [ rect (bounding-rect c)]
      (if (> (.height rect) 28)
      (rectangle
        image-b
        (new-point (.x rect) (.y rect))
        (new-point (+ (.width rect) (.x rect)) (+ (.y rect) (.height rect)))
        (new-scalar 50 135 135)
        3)))))
(imwrite image-b "output/contours.png")

;
; Find countours 2
; draw contours using bounding boxes
; https://stackoverflow.com/questions/32145348/why-doesnt-opencv-findcontour-method-always-find-closed-outer-contour?rq=1
;
(def headphones (imread "resources/morph/headphone.png"))
(def image-c (clone headphones))
(cvt-color! headphones COLOR_BGR2GRAY)
(def contours (java.util.ArrayList.))
(def mask (new-mat))
(find-contours headphones contours mask RETR_LIST CHAIN_APPROX_SIMPLE)
(doseq [c contours]
  (if (> (contour-area c) 100 )
    (let [ rect (bounding-rect c)]
      (if (> (.height rect) 28)
      (rectangle
        image-c
        (new-point (.x rect) (.y rect))
        (new-point (+ (.width rect) (.x rect)) (+ (.y rect) (.height rect)))
        (new-scalar 255 100 100)
        3)))))
(imwrite image-c "output/contours.png")

;
; Find countours 3
; draw contours using pen
;

(def headphones
  (imread "resources/morph/headphone.png"))
(def image-c (clone headphones))
(cvt-color! headphones COLOR_BGR2GRAY)
(def contours (new-arraylist))
(def mask (new-mat))
(find-contours headphones contours mask RETR_LIST CHAIN_APPROX_SIMPLE)
(dotimes [ci (.size contours)]
 (draw-contours image-c contours ci (new-scalar 255 100 100) 3))
(imwrite image-c "output/contours.png")

;
; Find countours 3
; draw contours using circles
;
(def headphones
  (imread "resources/morph/headphone.png"))
(def image-c (clone headphones))
(cvt-color! headphones COLOR_BGR2GRAY)
(def contours (new-arraylist))
(def mask (new-mat))
(find-contours headphones contours mask RETR_LIST CHAIN_APPROX_SIMPLE)
(dotimes [ci (.size contours)]
 (draw-contours image-c contours ci (new-scalar 255 100 100) 3))
(imwrite image-c "output/contours.png")

;
; contours using masking
;
(def im  (imread "resources/images/cat.jpg"))
(def hsv (new-mat))
(def mask (new-mat))
(def im2 (new-mat))
(cvt-color im hsv COLOR_BGR2GRAY)
(in-range hsv (new-scalar 40 10 0) (new-scalar 120 255 255) mask)
(.copyTo im im2 mask)
(imwrite im2 "output/cat3.png")
(imwrite mask "output/cat4.png")
