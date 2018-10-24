(ns opencv4.applycolormaps
 (:require
  [opencv4.core :refer :all]
  [opencv4.colormaps :as cm]
  [opencv4.utils :as u])
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
