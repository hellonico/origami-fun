(ns opencv4.bw
  (:require [opencv4.core :refer :all]))

(defn -main[ & args]
  (let [in (first args) fd (clojure.java.io/as-file in) out (str "bw_" (.getName fd))]
  (println in ">" out)
  (-> in
  	 imread 
  	 (cvt-color! COLOR_RGB2GRAY)
  	 (imwrite out))))