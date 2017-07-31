(ns opencv3.cartoon
 (:require
  [opencv3.core :refer :all]
  [opencv3.utils :as u]))

;
;  edge preserving smoothing via bilateral filter
;

(defn cartoon
  [buffer]
  (->
    (cvt-color! buffer COLOR_RGB2GRAY))
    (let [ c (clone buffer)]
    ; (bilateral-filter buffer c 9 9 7)
    (bilateral-filter buffer c 10 250 50)
    (-> c
     (median-blur! 7)
     (adaptive-threshold! 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 9 3)
     (cvt-color! COLOR_GRAY2RGB))))

(defn cartoon-2
   [buffer]
   (->
     (cvt-color! buffer COLOR_RGB2GRAY))
     (let [ c (clone buffer)]
     (bilateral-filter buffer c 9 9 7)
    ;  (bilateral-filter buffer c 10 250 50)
     (-> c
      (median-blur! 7)
      (adaptive-threshold! 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 9 2)
      (cvt-color! COLOR_GRAY2RGB))))

(defn -main[ & args]
  (->
    (first args)
    imread
    cartoon-2
    (imwrite (second args))))

(comment

(-> "http://www.petmd.com/sites/default/files/sleepy-cat-125522297.jpg"
u/mat-from-url
cartoon
(imwrite "output/cartoon/cat-1.png")
)

(-> "http://www.readersdigest.ca/wp-content/uploads/2011/01/4-ways-cheer-up-depressed-cat.jpg"
u/mat-from-url
cartoon
(imwrite "output/cartoon/cat-2.png"))

(-> "https://hyatoky.com/wp-content/uploads/cute-cat-wallpapers-hd-1080x580.jpg"
u/mat-from-url
cartoon
(imwrite "output/cartoon/cat-3.png"))

(-> "resources/images/marmotte.jpg"
imread
cartoon
(imwrite "output/cartoon/marmotte-2.png"))

(-> "resources/matching/rose_flower.jpg"
imread
cartoon
(imwrite "output/cartoon/rose-1.png"))

(-> "resources/matching/rose_flower.jpg"
imread
cartoon-2
(imwrite "output/cartoon/rose-2.png"))

  )
