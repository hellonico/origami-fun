

(import '[com.drew.imaging ImageMetadataReader])

;(def img (clojure.java.io/as-file "resources/chapter04/emilie1.jpg"))
(def img )

(defn print-metadata [path]
 (let [img (clojure.java.io/as-file path)
      meta (ImageMetadataReader/readMetadata img)]
 (doseq [dir (.getDirectories meta)]
   (doseq [t (.getTags dir)]
   (println (.toString t))))))

(print-metadata "/Users/niko/Dropbox/kodomo shashin/1.jpg")
