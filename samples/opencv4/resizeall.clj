(ns opencv4.resizeall
 (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [opencv4.utils :as u]
   [opencv4.core :refer :all]))


(defn debug[mat filename factor]
  (println "Processing \t: " 
    (.getName filename) 
    " [" (.width mat) "x" (.height mat) "] -> "
    " [" (* factor (.width mat)) "x" (* factor (.height mat)) "] ")
  mat
  )

(defn resize-one [one factor output-folder]
  
  (->
    (.getPath one)
    (imread)
    (debug one factor)
    (u/resize-by factor)
    (imwrite (str output-folder "/" (.getName one))))  )

(defn resize-all [ input-folder factor output-folder]
 (.mkdirs (io/as-file output-folder))
 (let [images
   (filter
    #(str/includes? (.toLowerCase (.getName %)) ".jpg")
    (file-seq (io/as-file input-folder)))]
    (doall
      (map #(resize-one % factor output-folder) images) )))

(defn -main[args]
  (resize-all 
    (first args) 
    (Float/parseFloat (second args))
    (nth args 2)))

(comment 
  (resize-all 
    "/Users/niko/Downloads/hikkoshi" 
    0.25 
    "/Users/niko/Desktop/output"))
