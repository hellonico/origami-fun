(ns opencv3.testing2
  (:import
    [org.opencv.core CvType Core Mat]
    [org.opencv.imgproc Imgproc])
  (:require [opencv3.core :refer :all]))

; mosaic with cv3
(def im1 (imread "resources/images/cat.jpg"))
(resize! im1 (new-size) 0.1 0.1 Imgproc/INTER_NEAREST)
(resize! im1 (new-size) 10 10 Imgproc/INTER_NEAREST)
(imwrite im1 "output/5.jpg")

(-> (imread "resources/images/cat.jpg")
 (resize! (new-size) 0.1 0.1 INTER_NEAREST)
 (resize! (new-size) 10 10 INTER_NEAREST)
 (imwrite "output/5.jpg"))

;
; find contours
;
; http://answers.opencv.org/question/12056/findcontours-in-java-not-giving-desired-results/
(def image (imread "resources/images/cat.jpg"))
(def hsv (Mat. (.size image) CvType/CV_8UC4))
(def blurred (Mat. (.size image) CvType/CV_8UC4))
(def image-a (Mat. (.size image) CvType/CV_32F))
(cvt-color image hsv Imgproc/COLOR_BGR2GRAY)
(gaussian-blur hsv blurred (new-size 5 5) 0 0)
(Imgproc/adaptiveThreshold blurred image-a 255 Imgproc/ADAPTIVE_THRESH_MEAN_C Imgproc/THRESH_BINARY 7 5)

(imwrite image-a "output/6.jpg")
(def image-b (clone image-a))
(def contours (java.util.ArrayList.))
(def mask (new-mat))
(Imgproc/findContours image-a contours mask Imgproc/RETR_LIST Imgproc/CHAIN_APPROX_SIMPLE)
(doseq [c contours]
  (if (> (Imgproc/contourArea c) 100 )
    (let [ rect (Imgproc/boundingRect c)]
      (if (> (.height rect) 28)
      (Imgproc/rectangle
        image-b
        (new-point (.x rect) (.y rect))
        (new-point (+ (.width rect) (.x rect)) (+ (.y rect) (.height rect)))
        (new-scalar 50 135 135)
        3)))))
(imwrite image-b "output/7.jpg")

;
; Find countours 2
; draw contours using bounding boxes
; https://stackoverflow.com/questions/32145348/why-doesnt-opencv-findcontour-method-always-find-closed-outer-contour?rq=1
;
(def headphones (imread "resources/morph/headphone.png"))
(def image-c (clone headphones))
(cvt-color! headphones Imgproc/COLOR_BGR2GRAY)
(def contours (java.util.ArrayList.))
(def mask (new-mat))
(Imgproc/findContours headphones contours mask Imgproc/RETR_LIST Imgproc/CHAIN_APPROX_SIMPLE)
(doseq [c contours]
  (if (> (Imgproc/contourArea c) 100 )
    (let [ rect (Imgproc/boundingRect c)]
      (if (> (.height rect) 28)
      (Imgproc/rectangle
        image-c
        (new-point (.x rect) (.y rect))
        (new-point (+ (.width rect) (.x rect)) (+ (.y rect) (.height rect)))
        (new-scalar 255 100 100)
        3)))))
(imwrite image-c "output/headphones.jpg")

;
; Find countours 3
; draw contours using pen
;
(def headphones (imread "resources/morph/headphone.png"))
(def image-c (clone headphones))
(cvt-color! headphones Imgproc/COLOR_BGR2GRAY)
(def contours (java.util.ArrayList.))
(def mask (new-mat))
(Imgproc/findContours headphones contours mask Imgproc/RETR_LIST Imgproc/CHAIN_APPROX_SIMPLE)
(dotimes [ci (.size contours)]
(Imgproc/drawContours image-c contours ci (new-scalar 255 100 100) 3))
(imwrite image-c "output/headphones.jpg")

(def headphones
  (imread "resources/morph/headphone.png"))
(def image-c (clone headphones))
(cvt-color! headphones COLOR_BGR2GRAY)
(def contours (java.util.ArrayList.))
(def mask (new-mat))
(find-contours headphones contours mask RETR_LIST CHAIN_APPROX_SIMPLE)
(dotimes [ci (.size contours)]
 (draw-contours image-c contours ci (new-scalar 255 100 100) 3))
(imwrite image-c "output/headphones.jpg")

;
; masking
;
(def im  (imread "resources/images/cat.jpg"))
(def hsv (new-mat))
(def mask (new-mat))
(def im2 (new-mat))
(cvt-color im hsv Imgproc/COLOR_BGR2HSV)
(in-range hsv (new-scalar 40 10 0) (new->scalar 120 255 255) mask)
(.copyTo im im2 mask)
(imwrite im2 "output/cat3.png")
(imwrite mask "output/cat4.png")
