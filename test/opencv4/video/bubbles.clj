(ns opencv4.bubbles
  (:require [opencv4.core :refer :all]
            [opencv4.utils :as u]
            [opencv4.colors.rgb :as rgb]))

(def ^:dynamic  *bubble-count* 10)

(defn- in-range? [x a b]
  (cond (< x a) false
        (> x b) false
        :else true))

(def state (ref {:run true}))
(def bubbles (ref (list)))

(defstruct bubble :x :y :size)

(defn generate-random-bubbles []
  (repeatedly #(struct-map bubble :x (+ 10 (rand-int 620)) :y (- 100 (rand-int 460)) :size (+ 10 (rand-int 10)))))

  (defn initialize-bubbles [i]
    (dosync (ref-set bubbles (take i (generate-random-bubbles)))))

  (defn bubble-in-rect [b rects]
    (if (nil? rects)
      false
      (not (nil? (some (fn [[x y w h]] (and (in-range? (:x b) x (+ x w)) (in-range? (:y b) y (+ y h)))) rects)))))

  (defn update-bubbles [rects]
    (if (= 0 (count @bubbles))
      (initialize-bubbles *bubble-count*)
      (dosync (ref-set bubbles
                       (concat
                        (remove #(bubble-in-rect % rects) (remove #(> (:y %) 480) (map #(assoc % :y (+ (:y %) 1)) @bubbles)))
                        (take (- *bubble-count* (count @bubbles)) (generate-random-bubbles)))))))


  (defn make-awesome [image rects]
    (update-bubbles rects)
    (doseq [b @bubbles]
      (circle image (new-point (:x b) (:y b)) (:size b) rgb/pink 5)))

(initialize-bubbles *bubble-count*)
(dosync (alter state assoc :prev nil))

 (u/simple-cam-window 
  {:video {:device 1 :height 300 :width 1024} :frame {:height 300 :width 1024}} 
  (fn [buffer]
    
    (let [curr (-> buffer clone)
          contours (new-arraylist)
          prev (let [p (:prev @state)] (if (nil? p) curr p))
          difff (new-mat)
          dummy  (absdiff curr prev difff)
          processed (-> difff
                        (cvt-color! COLOR_RGB2GRAY)
                        (gaussian-blur! (new-size 9 9) 0 0)
                        (threshold! 30 255 THRESH_BINARY_INV))]
  
      (find-contours processed contours (new-mat) RETR_LIST CHAIN_APPROX_TC89_KCOS)
      (doall (println (into [] (map bounding-rect contours))))
      (dosync (alter state assoc :prev (clone curr)))
  (try
    (make-awesome difff (into [] (map bounding-rect contours)))
    (catch Exception e (spit "hello.out" e :append true)))
      (doseq [rect (map bounding-rect contours)]
        (rectangle difff rect (new-scalar 255 0 0) 5))
    difff
    )))