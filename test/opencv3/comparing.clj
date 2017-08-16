(ns opencv3.comparing
  (:require
    [opencv3.utils :as u]
    [opencv3.colors.rgb :as color]
    [opencv3.core :refer :all]))


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
