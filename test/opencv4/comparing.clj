(ns opencv4.comparing
  (:require
    [opencv4.utils :as u]
    [opencv4.colors.rgb :as color]
    [opencv4.core :refer :all]))


(defn same-image? [ img1 img2]
  (let [result (new-mat)]
    (subtract img1 img2 result)
    (let[ total (* 3 (.total result))
          bytes (byte-array total)]
      (.get result 0 0 bytes)
      (= 0 (count (filter #(not (= 0 %)) bytes ))))))

(same-image?
  (imread "resources/images/cat.jpg")
  (imread "resources/images/cat.jpg"))
