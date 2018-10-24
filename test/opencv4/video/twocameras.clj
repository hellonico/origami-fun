(ns opencv4.video.twocameras
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :refer :all]
    [opencv4.utils :as u])
    (:gen-class))

(defn render-two [ left right ]
  (let [ output (new-mat (rows left) (* 2 (cols left))  CV_8UC3 (new-scalar 255 255 255))
  ol_ (submat output (new-rect 0 0 (cols left) (rows left)))
  or_ (submat output (new-rect (cols left) 0 (cols left) (rows left)))
  ]
  (copy-to left ol_)
  (resize! right (size or_))
  (copy-to right or_)
  (u/resize-by output 0.3)
  output))

(defn to-gray[buffer ]
  (-> buffer (cvt-color! COLOR_RGB2GRAY) (cvt-color! COLOR_GRAY2RGB)))


; start two streams from two different devices.
; different functions to be applied per device.
;
(defn mode-one[]
  (u/cams-window
    {:devices [
      {:device 0
       :width 250
       :height 150
       :fn  to-gray}
      {:device 1
       :width 250
       :height 150
       :fn to-gray}
     ]
     :video {
       :fn render-two
     }
     :frame
      {:width 650
       :height 300
       :title "TwoCellos"}}))


 ; start two streams from the same device
 ; different functions to be applied per device
(defn mode-two[]
 (u/cams-window
  {:devices [
    {:device 0
     :width 100
     :height 80
     :fn  to-gray}
    {:device 0
     :width 100
     :height 80
     :fn (comp to-gray #(flip! % 1))}
   ]
   :video {
     :fn render-two
   }
   :frame
    {:width 650
     :height 300
     :title "TwoOfTheSame"}}))

(defn mode-three[]
  (u/cams-window
   {:devices [
     {:device 0 :width 200 :height 150 :fn to-gray}
    ]
    :video { :fn identity }
    :frame
    {:width 650 :height 300 :title "OneOfTheSame"}})

  )

(defn mode-four[]
    (u/cams-window
     {:devices [
       {:device 0 :width 1280 :height 960 :fn to-gray}
      ]
      :video { :fn identity
        :recording {:width 500 :height 400 :output "nico.avi" :frame-rate 30}
      }
      :frame
      {:width 650 :height 300 :title "OneOfTheSame"}})
    )

(defn -main [& args]
  (if (empty? args)
    (println "Usage: lein run -m opencv4.video.twocameras <mode>\n
      Where mode is one of:
      one           |   run two different cameras
      two           |   run the same camera twice with different filters
      three         |   run one camera, one filter
      fourt         |   run one camera, one filter, recording
      ")
    (let [ mode (first args)]
      (condp = mode
    "one"  (mode-one)
    "two" (mode-two)
    "three" (mode-three)
    "four" (mode-four)
    (do)))))
