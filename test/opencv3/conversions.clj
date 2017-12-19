(ns opencv3.conversions
  (:require [opencv3.core :refer :all])
  (:require [opencv3.video :refer :all])
  (:require [opencv3.utils :as u]))



(-> 
	"resources/chapter03/emilie1.jpg" 
	(imread IMREAD_REDUCED_COLOR_8)
	(convert-to! CV_32FC1)
	(dump))
