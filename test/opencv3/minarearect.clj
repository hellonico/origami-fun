(ns opencv3.minarearect
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]
    [opencv3.colors.rgb :as color]))

;
; generate image
;
(def img (new-mat 1000 1000 CV_8UC3))
(set-to img color/white)

(line img (new-point  400 400) (new-point 511 511) color/cyan-3 120)
(line img (new-point  300 300) (new-point 700 500) color/cyan-4- 120)

(-> img (u/resize-by 0.2) u/show)

;
; find contours
;
(def work (-> img clone (cvt-color! COLOR_BGR2GRAY) (bitwise-not!) ))
(u/show work)
(def contours (new-arraylist))
(find-contours work contours (new-mat) RETR_TREE CHAIN_APPROX_SIMPLE)

(def image-c (clone img))
(dotimes [ci (.size contours)]
 (draw-contours image-c contours ci color/black 1))
(imwrite image-c "output/minarearect.png" )

; rotated rect
(def rect
  (min-area-rect
    (new-matofpoint2f
      (.toArray (first contours)))))

(def angle (.angle rect))
(def img-rows (rows img))
(def img-cols (cols img))
(def M
  (get-rotation-matrix-2-d
    (new-point (/ img-rows 2) (/ img-cols 2)) angle 1))

(def work2 (clone img))
(warp-affine! work2 M (new-size img-cols img-rows))
(imwrite work2 "output/minarearect.png" )
;
; (def contours (new-arraylist))
; (find-contours (cvt-color! work2 COLOR_BGR2GRAY) contours (new-mat) RETR_TREE CHAIN_APPROX_SIMPLE)

(def brect
  (bounding-rect (first contours)))
(println brect)
(def work3 (clone img))
(def work4 (.submat work3 brect))

(def M2
  (get-rotation-matrix-2-d
    (new-point (/ (.height brect) 2) (/ (.width brect) 2)) angle 1))

; (warp-affine! work4 M2 (.size work3) INTER_CUBIC BORDER_TRANSPARENT color/red-1-)
(imwrite work4 "output/minarearect.png" )


; https://stackoverflow.com/questions/12852578/image-rotation-with-opencv-in-android-cuts-off-the-edges-of-an-image

; (def sinv (first (.get M 0 1)))
; (def cosv (first (.get M 0 0)))
; (def dst-size (new-size
;   (+
;   (* cosv (.width img ))
;   (* sinv (.height img)))
;   (+
;   (* sinv (.width img ))
;   (* cosv (.height img)))   ))
; (.put M2 1 2 (double-array [(* (.width img ) sinv)]))
