(ns opencv3.shapes
  (:require [opencv3.core :refer :all])


(defn find-color[src range-1 range-2 min max]
  (let[ bgr-image (clone src) ogr-image (clone bgr-image)] circles (new-mat)

   (imwrite bgr-image "output/3.jpg")
   (median-blur! bgr-image 3)
   (cvt-color! bgr-image COLOR_BGR2HSV)
   (in-range! bgr-image range-1 range-2 )

   (imwrite bgr-image "output/2.jpg")
   ; smooth it, otherwise a lot of false circles may be detected
   (gaussian-blur! bgr-image (new-size 9 9) 2 2)
   (hough-circles bgr-image circles CV_HOUGH_GRADIENT 1 (/ (.rows bgr-image) 16) max min 0 0)
   (doseq [i (range 0 (.cols circles))]
     (let [ circle (.get circles 0 i) x (nth circle 0) y (nth circle 1) r (nth circle 2)  p (new-point x y)]
     (opencv3.core/circle ogr-image p r (new-scalar 0 255 0) 5)))
    ogr-image))

(def bgr-image (imread "resources/detect/circles.jpg"))

(defn find-red-balls[url range-1 range-2 min max]
(let [bgr-image (u/mat-from-url url)]
 (imwrite
  (find-color bgr-image range-1 range-2  min max)
  "output/1.jpg")))

(find-red-balls
  "http://www.keepitsweet.co.uk/images/Foil-Balls-Red-1KNN-MCH-BAR.jpg"
  (new-scalar 0 70 70)
  (new-scalar 10 255 255)
  30 150)

(find-red-balls
  "http://www.finsecpartners.com.au/wp-content/uploads/2013/09/Finsec-red-balls.png"
  (new-scalar 0 70 70)
  (new-scalar 10 255 255)
  45 150)

(find-red-balls
  "http://media.candystore.com/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/r/e/red-cherry-fruit-sours-candy-balls-75lb_2.jpg"
  (new-scalar 0 70 70)
  (new-scalar 10 200 245)
  35 150)

(find-red-balls
  "https://nuts.com/images/auto/801x534/assets/8461ba2e83fbdcad.jpg"
  (new-scalar 0 70 70)
  (new-scalar 20 255 255)
  60 150)
