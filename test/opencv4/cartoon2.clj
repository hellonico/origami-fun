(ns opencv4.cartoon2
 (:require
  [opencv4.core :refer :all]
  [opencv4.utils :as u]))

; http://www.askaswiss.com/2016/01/how-to-create-pencil-sketch-opencv-python.html

; Using OpenCV and Python, an RGB color image can be converted into a pencil sketch in four simple steps:
;
;     Convert the RGB color image to grayscale.
;     Invert the grayscale image to get a negative.
;     Apply a Gaussian blur to the negative from step 2.
;     Blend the grayscale image from step 1 with the blurred negative from step 3 using a color dodge.

(def img
  (-> "https://cdn.theculturetrip.com/wp-content/uploads/2016/01/canals2.jpg"
  u/mat-from-url
  (u/resize-by 0.3)
  ))
; (u/show img)

(def gray (-> img clone (cvt-color! COLOR_BGR2GRAY)))
; (u/show gray)

(def inverted
  (-> gray clone (bitwise-not!)))
; (u/show inverted)

(def gaussed
  (-> inverted clone (gaussian-blur! (new-size 21 21) 0.0 0.0))
  )
; (u/show gaussed)

(defn dodge-v2! [img_ mask]
  (let [ output (clone img_) ]
  (divide img_ (bitwise-not! (-> mask clone)) output 256.0)
  output))

(u/show (dodge-v2! gray gaussed))

(defn burn-v2! [ image mask]
  (bitwise-not! (dodge-v2! image mask)))
(u/show (burn-v2! gray gaussed))

(def canvas (imread "resources/canvas.jpg" 0))
(resize! canvas (new-size (.cols gray) (.rows gray)))
(u/show canvas)
(def output (new-mat))
(multiply (dodge-v2! gray gaussed) canvas output  (/ 1 256.0))
(u/show output)
