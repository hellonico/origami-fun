(ns opencv3.applycolormaps
 (:require
  [opencv3.core :refer :all]
  [opencv3.colormaps :refer :all]
  [opencv3.utils :as u]))


(defn -main[ & args]
  (let [ source
    (-> (first args) imread)
    targets
    (cons
      (-> source clone (watermark "ORIGINAL"))
      (map (partial change-color source) colors-maps))]
  (imwrite
    (vconcat! targets)
    (second args))))

(-main "resources/nico.jpg" "test.jpg")
