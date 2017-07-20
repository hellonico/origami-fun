(ns opencv3.collage
 (:require [opencv3.core :refer :all]))

; http://answers.opencv.org/question/15589/make-a-collage-with-other-images/

 (defn tile [dst array grid-x grid-y]
   (let[
     k (atom 0)
     width (/ (.cols dst) grid-x)
     height (/ (.rows dst) grid-y) ]
     (doseq [i (range 0 grid-y)]
       (doseq [j (range 0 grid-x)]
         (->
          (nth array @k)
          (resize! (new-size width height))
          (.copyTo
            (.submat dst (new-rect (* j width) (* i height) width height ))))
          (swap! k inc)))
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

(defn imwrite2 [file mat]
  (imwrite mat file))
(defn tile2 [dst grid-x grid-y array]
  (tile dst array grid-x grid-y))

(defn tiles[folder output width height x y]
    (->>
     folder
     (clojure.java.io/as-file)
     (file-seq)
     (filter #(.endsWith (.getName %) "JPG"))
     (map #(.getPath %))
     (shuffle) ; without this is pretty cool too
     (take (* x y))
     (map imread)
     (into [])
     (tile2 (new-mat height width CV_8UC3) x y)
     (imwrite2 output)))

(tiles
  "/Users/niko/Dropbox/manonico"
  "output/collage.png"
  1600
  1200
  5
  6)
