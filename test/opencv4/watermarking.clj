(ns opencv4.watermarking
   (:require
     [opencv4.utils :as u]
     [opencv4.core :refer :all]))

(def source (-> "resources/images/kidrunning.jpeg" imread (u/resize-by 0.5)))
(def mark (-> "resources/images/nicologo.gif" imread (u/resize-by 0.5)))

(def subma-t (submat source (new-rect 50 50 (.cols mark) (.rows mark) )))
(copy-to mark subma-t)

(u/show source {:frame {:width 600}})
