(ns opencv3.watermarking
   (:require
     [opencv3.utils :as u]
     [opencv3.core :refer :all]))

(def source (-> "resources/images/kidrunning.jpeg" imread (u/resize-by 0.5)))
(def mark (-> "resources/images/nicologo.gif" imread (u/resize-by 0.5)))
; (def submat (.submat source (.rows mark) (* 2 (.rows mark)) (.cols mark) (* 2 (.cols mark) )))
(println mark)
(def subma-t (.submat source (new-rect 50 50 (.cols mark) (.rows mark) )))
(.copyTo mark subma-t)

(u/show source
  {:frame {:width 600}}
  )
