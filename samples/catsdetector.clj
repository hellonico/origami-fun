(ns catsdetector
  (:require
    [opencv3.colors.rgb :as rgb]
    [opencv3.core :refer :all]))

(def detector
  (->
   "XML/haarcascade_frontalcatface.xml"
   (clojure.java.io/resource)
   (.getPath)
   (new-cascadeclassifier)))

(defn add-label! [buffer rects]
  (put-text! buffer (str (count (.toArray rects) ) " cat(s) " )
     (new-point 30 100) FONT_HERSHEY_PLAIN 2 rgb/magenta-2 2))

(defn draw-rects! [buffer rects]
   (doseq [r (.toArray rects)]
     (-> buffer
      (submat r)
      (apply-color-map! COLORMAP_WINTER)
      (copy-to (submat buffer r))))
      buffer)

(defn detect-cats! [mat]
  (let [  rects (new-matofrect) ]
   (.detectMultiScale detector mat rects)
   (-> mat
    (draw-rects! rects)
    (add-label! rects))))

(defn -main[ & args ]
  (if-let [ c (first args)]
  (let [input (-> (str c) (imread))
        output (or (second args) "output.png")]
   (println "Reading: " c)
   (println "Writing: " output)
   (-> input
     (detect-cats!)
     (imwrite output)))
     (println "need input file ...")))
