(ns opencv4.sampling
  (:require [opencv4.core :refer :all])
  (:require [opencv4.utils :as u]))

(def im (imread  "resources/face.jpg"))
(def gp1 (new-mat))
; downsample the image
(pyr-down gp1 gp1)
; upsample the image
(pyr-up gp1 gp1)
; subtract original
(subtract im gp1 gp1)
(imwrite gp1 "output/sampling.png")
