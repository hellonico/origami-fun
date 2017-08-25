(ns opencv3.cartoon3
 (:require
  [opencv3.core :refer :all]
  [opencv3.utils :as u]))

; http://www.askaswiss.com/2016/01/how-to-create-cartoon-effect-opencv-python.html

(def img
  (-> "https://cdn.theculturetrip.com/wp-content/uploads/2016/01/canals2.jpg"
  u/mat-from-url
  (u/resize-by 0.3)))
(u/show img)

; Step 1: Edge-aware smoothing using a bilateral filter
(def factor 4)
(def work (clone img))
(dotimes [_ factor]
(pyr-down! work))
(println work)
; (bilateral-filter work 9 9 7)
; pfff ... opencv ... can't use the function! because src.data == dst.data
(def output (new-mat))
(bilateral-filter work output 9 9 7)
(dotimes [_ factor]
 (pyr-up! output))

(u/show output)

; Steps 2-3: Reduce noise using a median filter
; Step 4: Create an edge mask using adaptive thresholding
; detect and enhance edges
(def edge
  (-> img
    clone
    (resize! (new-size (.cols output) (.rows output)))
    (cvt-color! COLOR_RGB2GRAY)
    (median-blur! 7)
    (adaptive-threshold! 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 9 7)
    (cvt-color! COLOR_GRAY2RGB)))
(u/show edge)

; Step 5: Combine color image with edge mask
(bitwise-and output edge output)
(u/show output
  {:frame {:width 600 :height 600}}
  )


; turning all this in functions

(defn smoothing!
  ([ img ] (smoothing! img 5 9 7))
  ([img factor filter-size filter-value]
  (let [ work (clone img) output (new-mat)]
  (dotimes [_ factor] (pyr-down! work))
  (bilateral-filter work output filter-size filter-size filter-value)
  (dotimes [_ factor] (pyr-up! output))
  (resize! output (new-size (.cols img) (.rows img))))))

; let's make functions out of the code above
(defn edges!
  ([ img ] (edges! img 7 9 7))
  ([img e1 e2 e3]
  (-> img
    clone
    (cvt-color! COLOR_RGB2GRAY)
    (median-blur! e1)
    (adaptive-threshold! 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY e2 e3)
    (cvt-color! COLOR_GRAY2RGB))))

(comment
  ; canny edges
(defn edges! [img]
  (-> img clone
  (cvt-color! COLOR_RGB2GRAY)
  (canny! 150.0 100.0 3 true)
  (bitwise-not!)
  (cvt-color! COLOR_GRAY2RGB)))

  )

(defn cartoonize!
  ([ img ]
  (let [ output (smoothing! img) edge (edges! img)]
  (bitwise-and output edge output)
  output))
  ([ img s1 s2 s3 e1 e2 e3]
    (let [ output (smoothing! img s1 s2 s3) edge (edges! img e1 e2 e3)]
    (bitwise-and output edge output)
    output))
    )

(->
  "resources/landscape/landscape-nature-sky-blue.jpg"
  imread
  (u/resize-by 0.2)
  (cartoonize! 6 9 7  7 9 11)
  (imwrite "output/landscape.png"))

(->
  "resources/landscape/amazing-beautiful-beauty-blue.jpg"
  imread
  (cartoonize! 5 9 7  7 7 5)
  (u/resize-by 0.3)
  (imwrite "output/landscape.png"))

(->
  "resources/landscape/amazing-beautiful-beauty-blue.jpg"
  imread
  (u/resize-by 0.3)
  (cartoonize! 5 9 7  7 7 5)
  (imwrite "output/landscape.png"))
