(ns opencv3.seesawing
(:require
  [opencv3.core :refer :all]
  [opencv3.utils :as u])
(:use seesaw.core)
(:require [seesaw.bind :as b])
(:gen-class))

(def picts (into []
  (map #(.getPath %)
  (filter
  #(let [name (.toLowerCase(.getName %))]
  (or (.endsWith name "jpg") (.endsWith name "png")))
  (file-seq (clojure.java.io/as-file "resources"))))))

(defn get-random-pict[]
  (let [ rr (rand-nth picts)]
  (println "Loading:" rr)
  (-> rr imread (resize! (new-size 300 300)))))


  (defn process-image [ _img state  ]
  (let [tmp (->
   _img
   (cvt-color! COLOR_RGB2GRAY)
   (canny! (double (state :b)) (double (state :a)) 3 true))]
   (if (state :bitwise) (bitwise-not! tmp)) tmp))

(def state
  (atom {:bitwise true :IMG (get-random-pict) :a 0 :b 300}))

(def f
  (frame
  :title "OpenCV Small Canny Example"
  :on-close :dispose
  :content
  (vertical-panel

   :items [
    (label
      :id :img
      :icon (icon (u/mat-to-buffered-image (@state :IMG)) ))

    (text :id :mytext :multi-line? true :text "Canny Level")

    (slider :id :slider1 :min 0 :max 200
       :listen [:state-changed
       (fn [e] (let [v (-> e (.getSource) (.getValue))]
       (swap! state assoc-in [:a] v)))])

    (slider :id :slider2 :min 0 :max 500
      :listen [:state-changed
      (fn [e] (let [v (-> e (.getSource) (.getValue))]
      (swap! state assoc-in [:b] v)))  ])

    (checkbox :id :c :text "Inverse"
    :listen [:state-changed
     (fn [_]
     (swap! state assoc-in [:bitwise] (not (@state :bitwise))))])

   (button :text "Save Pict" :listen [:mouse-clicked (fn [_]
     (let [ fname  (str "canny_" (System/currentTimeMillis) ".png"  ) ]
     (println "imwrite:" fname)
     (imwrite
      (-> (@state :IMG) clone (process-image @state)) fname))) ])

    (button :text "Load Random Pict" :listen
       [:mouse-pressed (fn [_]
        (swap! state assoc-in [:IMG] (get-random-pict))) ])])))

(defn- refresh-img []
 (config!
   (select f [:#img])
   :icon (icon
     (-> (@state :IMG) clone (process-image @state) (u/mat-to-buffered-image)))))

(add-watch state :state (fn [ _ _ _ new-state]
  (refresh-img)
  (config!
    (select f [:#mytext]) :text
    (str "Canny Level > a: " (@state :a) " b: " (@state :b)))))
; (remove-watch a :canny )

(defn -main[& args]
  (invoke-later
    (-> f
    pack!
    show!)))
