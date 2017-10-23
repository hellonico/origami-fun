(ns opencv3.resizeall
 (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))

(def folder "/Users/niko/Downloads/Camera Uploads(1)")

(defn resize-one [one factor output-folder]
  (->
    (.getPath one)
    (imread)
    (u/resize-by factor)
    (imwrite (str output-folder "/" (.getName one))))  )

(defn resize-all [ input-folder factor output-folder]
(let [images
      (filter
      #(str/includes? (.getName %) ".jpg")
      (file-seq (io/as-file folder)))]
      (doall
        (map #(resize-one % factor output-folder) images) )))

(defn -main[& args]
  (resize-all folder 0.25 "/Users/niko/Desktop/output")
)
