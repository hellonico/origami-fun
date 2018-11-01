(ns opencv4.average-color
 (:require
   [opencv4.utils :as u]
   [opencv4.core :refer :all]))

;
; find average color of a picture using opencv core
; mean function
;

(defn average-color[input]
  (let [src    (-> input (imread) (u/resize-by 0.25))
        mean_  (-> src u/mat-from (set-to! (mean src))) ]
    (-> src
        (u/mat-from)
        (set-to! (mean src))
        (cons [src])
        (vconcat!)  )))

(-> 
 "resources/images/cat.jpg" 
 average-color 
 (imwrite "hello2.jpg"))
