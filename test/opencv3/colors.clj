(ns opencv3.colors
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

(def test-mat (new-mat 100 100 CV_8UC3))
(set-to test-mat (opencv3.colors.html/->scalar "#0068B7"))
(u/show test-mat)
