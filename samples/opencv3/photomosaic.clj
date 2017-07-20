(ns opencv3.photomosaic
 (:require
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))

(defn get-average-hue-mat[mat]
  (-> mat
  (resize! (new-size 50 50))
  (median-blur! 3)
  (cvt-color! COLOR_BGR2HSV)
  mean
  (.-val)
  first))

(defn get-average-hue[path]
  (->
  path
  imread
  get-average-hue-mat))

; (get-average-hue
;   "resources/images/cat.jpg")

(defn find-closest[test-mat indexed]
  (let [test-hue (get-average-hue-mat test-mat)]
  (loop [f indexed nearest (first indexed)]
    ; (println "*" nearest)
    (if (empty? f)
      nearest
      (let [c (first f) ]
      (if (<
        (Math/abs (- test-hue (second c)))
        (Math/abs (- test-hue (second nearest))))
        (recur (rest f) c)
        (recur (rest f) nearest)))))))
;
; (-> "resources/images/cat.jpg"
;  imread
;  (find-closest indexed)
;  first
;  imread
;  u/show)

(defn get-cache-image[cache path width height]
  (let[ hit (.get cache path) ]
  (if (not (nil? hit))
  hit
  (do
    (let [new-e (-> path
      imread
      (resize! (new-size width height)))]
      (.put cache path new-e)
      new-e)))))

(defn tile [org indexed ^long grid-x ^long grid-y]
  (let[
    dst (u/mat-from org)
    k (atom 0)
    width (/ (.cols dst) grid-x)
    height (/ (.rows dst) grid-y)
    total (* grid-x grid-y)
    cache (java.util.HashMap.)
    ]
    (doseq [^long i (range 0 grid-y)]
      (doseq [^long j (range 0 grid-x)]
      (let [
        square (.submat org (new-rect (* j width) (* i height) width height ))
        best (first (find-closest square indexed))
        img  (get-cache-image cache best  width height)
        ]
          ; imread
          ;  (resize! (new-size width height))
         (.copyTo img (.submat dst (new-rect (* j width) (* i height) width height )))
         (println @k "/" total " ... done:" best))
         (swap! k inc)))
    dst))

(defn collect-pictures
  ([top-folder] (collect-pictures top-folder "JPG"))
  ([top-folder ext]
  (->>
    top-folder
    clojure.java.io/as-file
    file-seq
    (filter #(.endsWith (.getName %) ext))
    (map #(.getPath %)))))

(defn photomosaic
  [images-folder target-image output grid-x grid-y ]
  (let [files   (collect-pictures images-folder)
        indexed (zipmap files (map get-average-hue files))
        target  (imread target-image )]
    (->
      (tile target indexed grid-x grid-y)
      (imwrite output))))

(defn -main[& args]
  (photomosaic
    (nth args 0)
    (nth args 1)
    (nth args 2)
    (Integer/parseInt (nth args 3))
    (Integer/parseInt (nth args 4))))

(comment
  (photomosaic
  "/Users/niko/Dropbox/manonico"
  "/Users/niko/Dropbox/manonico/DSC_1889.JPG"
  "output/collage.png"
  50
  50)
  )

(count
  (collect-pictures "/Users/niko/Dropbox/写真/IPHONE"))
