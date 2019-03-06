(ns opencv4.manga
  (:require [opencv4.core :refer :all]))

(defn -main[ & args]
	(let [
		in (first args) fd (clojure.java.io/as-file in) 
		out (str "bw_" (.getName fd)) 
		out2 (str "wb_" (.getName fd))]
  (println in ">" out)
  (-> in
  	 imread 
  	 (cvt-color! COLOR_RGB2GRAY)
  	 (gaussian-blur! (new-size 7 7) 1.5 1.5)
  	 (threshold! 100 255 THRESH_BINARY)
		 (imwrite out)
		;  (bitwise-not!)
		;  (imwrite out2)
		 )))