(ns opencv4.colors
  (:require
    [opencv4.core :refer :all]
    [opencv4.utils :as u]))

(def test-mat (new-mat 100 100 CV_8UC3))
(set-to test-mat (opencv4.colors.html/->scalar "#0068B7"))
(u/show test-mat)
