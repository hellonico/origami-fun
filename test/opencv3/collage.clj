(ns opencv3.collage
 (:require [opencv3.core :refer :all]))

; http://answers.opencv.org/question/15589/make-a-collage-with-other-images/

 (defn tile [dst array grid-x grid-y]
   (let[ width (/ (.cols dst) grid-x) height (/ (.rows dst) grid-y) ]
     (doseq [i (range 0 grid-y)]
       (doseq [j (range 0 grid-x)]
         (-> (nth array (+ i j))
          (resize! (new-size width height))
          (.copyTo (.submat dst (new-rect (* j width) (* i height) width height ))))))
     dst))

(def n-cats
  (into []
   (map imread
    (take 32
      (repeatedly
        #(rand-nth
          ["resources/images/cat.jpg" "resources/images/cat2.png"]))))))

(->
  (new-mat 1200 1600 CV_8UC3)
  (tile n-cats 5 5)
  (imwrite "output/collage.png"))
