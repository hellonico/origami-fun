(ns opencv3.seesawing
(:require [opencv3.core :refer :all] [opencv3.utils :as u])
(:use seesaw.core)
(:require [seesaw.bind :as b]))

; events
; https://github.com/daveray/seesaw/blob/develop/src/seesaw/event.clj

; binding
; https://daveray.github.io/seesaw/seesaw.bind-api.html


(defn get-random-pict[]
  (-> (rand-nth picts) imread (resize! (new-size 200 200))))

(defn get-canny [ _img a ]
(->
 _img
 (cvt-color! COLOR_RGB2GRAY)
 (canny! (double 300) (double a) 3 true)
 bitwise-not!))

(defn refresh-img []
 (config! mylabel :icon (icon
    (-> @IMG clone (get-canny @a) (u/mat-to-buffered-image)))))

(def picts ["resources/images/cat.jpg" "resources/images/cat2.png" "resources/images/cat3.png"])
(def IMG (atom (get-random-pict)))
(def mylabel (label :id :img :icon (icon (u/mat-to-buffered-image @IMG) )))
(def myslide (slider :id :slider :min 0 :max 100 :listen [:state-changed (fn [_] (refresh-img)) ]))

(def a (atom 0.0))
(def mytext (text :multi-line? true :text "hello" :border 5) )
(add-watch a :canny (fn [ key reference old-state new-state]
  (refresh-img)
  (config! mytext :text (str "slider value: " new-state))))
(remove-watch a :canny )
(b/bind myslide a)

(def f
  (frame
  :title "Seesaw Substance/Insubstantial Example"
  :on-close :dispose
  :content
  (vertical-panel
   :items [
   mylabel
   mytext
   myslide
   (button :text "Random Pict" :listen
    [:mouse-pressed (fn [_] (reset! IMG (get-random-pict)) (reset! a 0))])
    ])))

(invoke-later
(-> f
pack!
show!))

;
; (b/bind (b/property (select f  t :text)
;     (b/tee (b/bind (b/transform #(.toUpperCase %)) (b/property upper :text))
;          (b/bind (b/transform #(.toLowerCase %)) (b/property lower :text))))

;  (checkbox :text "A checkbox")
;  (combobox :model ["A combobox" "more" "items"])
;  (horizontal-panel
;    :border "Some radio buttons"
;    :items (map (partial radio :text)
;                ["First" "Second" "Third"]))
;  (scrollable (listbox :model (range 100)))
