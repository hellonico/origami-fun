(ns opencv4.seesaw2
(:require
  [opencv4.core :refer :all :exclude [add!]]
  [opencv4.utils :as u])
(:use seesaw.core))

(def state
  (atom {
    :img  (-> "resources/nico.jpg" (imread 0))
    :bitwise false
    :a 0
    :b 300}))

(def frame
(->
(frame
:title "Small OpenCV Pannel"
:on-close :dispose
:content
 (vertical-panel
  :items [
  (label :text "inverse")
  (checkbox :id :c :text "Inverse"
  :listen [:state-changed
   (fn [_]
   (swap! state assoc-in [:bitwise] (not (@state :bitwise))))])

  (label :text "Threshold Parameter 1")
  (slider :min 0 :max 200
     :listen [:state-changed
     (fn [e] (let [v (-> e (.getSource) (.getValue))]
     (swap! state assoc-in [:a] v)))])
  (label :text "Threshold Parameter 2")
  (slider :min 0 :max 200
     :listen [:state-changed
     (fn [e] (let [v (-> e (.getSource) (.getValue))]
     (swap! state assoc-in [:b] v)))])]))
 pack!
 show!))

(defn redraw [ new-state ]
  ; (clojure.pprint/pprint new-state)
  (->
    (new-state :img)
    (clone)
    (threshold! (new-state :a) (new-state :b) 1)
    ((fn [x] (if (true? (new-state :bitwise)) (bitwise-not! x) x)))
    (imwrite "output/nicosaw.png")))

(add-watch state :state (fn [ _ _ _ new-state]
  (redraw new-state)))

; (remove-watch state :state)
; (println @state)
