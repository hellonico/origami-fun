(ns opencv3.hough
  (:require
    [opencv3.utils :as u]
    [opencv3.colors.rgb :as color]
    [opencv3.core :refer :all]))

(defn same-image? [ img1 img2]
  (let [result (new-mat)]
    (subtract img1 img2 result)
    (let[ total (* 3 (.total result))
          bytes (byte-array total)]
      (.get result 0 0 bytes)
      (= 0 (count (filter #(not (= 0 %)) bytes ))))))

(same-image?
  (imread "resources/images/cat.jpg")
  (imread "resources/images/cat.jpg"))

;
; hough lines p starts here
;
(def parking    (imread "resources/images/lines/parking.png"))
(def gray       (-> parking clone (cvt-color! COLOR_BGR2GRAY)))
(def blur-gray  (-> gray clone (gaussian-blur! (new-size 3 3) 0 )))
(def edges      (-> blur-gray clone (canny! 50 150 )))

(def rho 1) ; distance resolution in pixels of the Hough grid
(def theta  (/ Math/PI 180)) ;  # angular resolution in radians of the Hough grid
(def min-intersections 15) ;  # minimum number of votes (intersections in Hough grid cell)
(def min-line-length  50) ;  # minimum number of pixels making up a line
(def max-line-gap  20 ) ; # maximum gap in pixels between connectable line segments

(def lines (new-mat))
(hough-lines-p edges lines rho theta min-intersections min-line-length  max-line-gap)

(def result (clone parking))
(dotimes [ i (.rows lines)]
(let [ val (.get lines i 0)]
  (line result
    (new-point (nth val 0) (nth val 1))
    (new-point (nth val 2) (nth val 3))
    color/teal
    2)))

(def output (new-mat))
(vconcat [
  parking
  (-> blur-gray clone (cvt-color! COLOR_GRAY2RGB))
  (-> edges clone (cvt-color! COLOR_GRAY2RGB))
  result] output)
(imwrite output "output/hough.png")

; (imwrite result "output/hough.png")
; http://answers.opencv.org/question/2966/how-do-the-rho-and-theta-values-work-in-houghlines/

;
; I wanna find the intersection between all those lines
;
; http://answers.opencv.org/question/961/can-i-use-cvfitline-to-robustly-find-an-intersection-point/
