(ns opencv3.applycolormaps
 (:require
  [opencv3.core :refer :all]
  [opencv3.colormaps :as cm]
  [opencv3.utils :as u])
  )

; (def colors-maps (cm/colors-maps))

(defn -main[ & args]
  (let [ source
    (-> (first args) imread)
    targets
    (cons
      (-> source clone (cm/watermark "ORIGINAL"))
      (map (partial cm/change-color source) cm/colors-maps))]
  (imwrite
    (vconcat! targets)
    (second args))))

(comment
 (-main "resources/nico.jpg" "test.jpg")
)
