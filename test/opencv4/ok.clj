(ns opencv4.ok
	(:require [opencv4.core :refer :all]))

(defn -main [& args]
  (println "Using OpenCV Version: " VERSION "..\n")
  (let [output "output/grey-neko.jpg"]
	(->
    (imread "resources/images/cat.jpg")
    (cvt-color! COLOR_RGB2GRAY)
    (imwrite output)
    (println))
  (println "A new gray neko has arise! " output)))
