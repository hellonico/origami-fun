;; gorilla-repl.fileformat = 1

;; @@
(ns opencv4.photomosaic
 (:require
   [opencv4.utils :as u]
   [opencv4.core :refer :all]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn mean-average-bgr [mat]
  (let [_mean (new-matofdouble)]
  (-> mat clone
  (median-blur! 3)
  (mean-std-dev _mean (new-matofdouble)))
	_mean))

(defn collect-pictures
  ([top-folder] (collect-pictures top-folder "jpg"))
  ([top-folder ext]
  (->>
    top-folder
    clojure.java.io/as-file
    file-seq
    (filter #(.endsWith (.getName %) ext))
    (map #(.getPath %)))))

(defn indexing [files for-size]
  (zipmap files (map #(-> % imread (resize! for-size) mean-average-bgr) files)))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;opencv4.photomosaic/indexing</span>","value":"#'opencv4.photomosaic/indexing"}
;; <=

;; @@
(defn apply-to-vals [m f]
  (into {} (for [[k v] m] [k (f v)])))

(defn find-closest [ target indexed ]
  (let [mean-bgr-target (get-averages-bgr-mat target)]
     (first (sort-by val < (apply-to-vals indexed #(norm mean-bgr-target %))))))

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
    ;k (atom 0)
    width (/ (.cols dst) grid-x)
    height (/ (.rows dst) grid-y)
    total (* grid-x grid-y)
    cache (java.util.HashMap.)
    ]
    (doseq [^long i (range 0 grid-y)]
      (doseq [^long j (range 0 grid-x)]
      (let [
        square (submat org (new-rect (* j width) (* i height) width height ))
        best (first (find-closest square indexed))
        img  (get-cache-image cache best  width height)
        sub (submat dst (new-rect (* j width) (* i height) width height ))
        ]
         (copy-to img sub)
         ;(println @k "/" total " ... done:" best)
         )
         ;(swap! k inc)
        ))
    dst))


(defn photomosaic
  [images-folder target-image grid-x grid-y ]
  (let [files   (collect-pictures images-folder)
        indexed (indexing (collect-pictures images-folder) (new-size grid-x grid-y))
        target  (imread target-image )]
    (tile target indexed grid-x grid-y)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;opencv4.photomosaic/photomosaic</span>","value":"#'opencv4.photomosaic/photomosaic"}
;; <=

;; @@
(def target "resources/chapter03/emilie1.jpg")
(def lechat 
  (photomosaic "resources/cat_photos" target 100 100))
(imwrite (hconcat! [(-> target imread) lechat]) "chat6.jpg")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#object[org.opencv.core.Mat 0x39b402fe &quot;Mat [ 3264*4896*CV_8UC3, isCont=true, isSubmat=false, nativeObj=0x7fc7638e2770, dataAddr=0x156ea0000 ]&quot;]</span>","value":"#object[org.opencv.core.Mat 0x39b402fe \"Mat [ 3264*4896*CV_8UC3, isCont=true, isSubmat=false, nativeObj=0x7fc7638e2770, dataAddr=0x156ea0000 ]\"]"}
;; <=

;; @@
(def e
  (-> "resources/chapter03/emilie1.jpg" 
    (imread IMREAD_REDUCED_COLOR_8) 
    get-averages-bgr-mat ))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;opencv4.photomosaic/e</span>","value":"#'opencv4.photomosaic/e"}
;; <=

;; @@

(def ex1 (u/matrix-to-mat [[0 1 2]]))
(def ex2 (u/matrix-to-mat [[0 1 3]]))
(def ex3 (u/matrix-to-mat [[0 1 7]]))
(norm ex1 ex2)
(norm ex1 ex3)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>5.0</span>","value":"5.0"}
;; <=

;; @@

;; @@
