(ns opencv4.conversions
  (:require [opencv4.core :refer :all])
  (:require [opencv4.video :refer :all])
  (:require [opencv4.utils :as u]))



(-> 
	"resources/chapter03/emilie1.jpg" 
	(imread IMREAD_REDUCED_COLOR_8)
	(convert-to! CV_32FC1)
	(dump))
