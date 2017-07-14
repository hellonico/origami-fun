(ns opencvfun.ok
	(:require [opencvfun.utils :as u]))

(defn -main [& args]
	(let [neko (u/load-img "resources/images/cat.jpg")
		  gray-neko (u/turn-to-gray neko)]
		(u/frame-to-file "grey-neko.jpg" gray-neko)
		(println "A new gray neko has arise!")))