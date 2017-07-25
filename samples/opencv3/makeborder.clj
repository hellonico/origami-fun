(ns opencv3.makeborder
  (:require
    [opencv3.utils :as u]
    [opencv3.core :refer :all]))

;(def top-bottom (* 0.05 (.rows img)))
;(def left-right (* 0.05 (.cols img)))
; (copy-make-border! img
;   top-bottom top-bottom left-right left-right BORDER_CONSTANT border-color)

(defn add-thin-border![img]
  (let[ border-color (new-scalar 0 0 0) border 1]
  (copy-make-border! img
    border border border border BORDER_CONSTANT border-color))
  img)

(comment

  (-> "resources/morph/cjy6M.jpg"
  imread
  add-thin-border!
  imwrite "output/borders.png")

)
; lein run -m opencv3.makeborder resources/images/cat2.png cb.png

(defn -main[& args]
  (-> (first args) imread add-thin-border! (imwrite (second args))))
