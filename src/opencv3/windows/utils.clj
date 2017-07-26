(ns opencv3.windows.utils
  (:use [gorilla-repl.image])
  (:require [opencv3.windows.core :as cv])
  (:import [org.opencv.core Size CvType Core Mat MatOfByte]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.videoio VideoCapture]
    [org.opencv.imgproc Imgproc]
    [javax.imageio ImageIO]
    [javax.swing ImageIcon JFrame JLabel]
    [java.awt.event MouseAdapter]
    [java.awt FlowLayout]))

;;;
; MAT OPERATIONS
;;;
(defn resize-by[ mat factor]
  (let [height (.rows mat) width (.cols mat)]
    (cv/resize! mat (cv/new-size (* width factor) (* height factor)) )))

(defn matrix-to-mat [matrix mat array-fn]
(map-indexed
 (fn[i line]
  (map-indexed
    (fn[j val] (.put mat i j (array-fn [val])))
    line))
matrix))

(defn matrix-to-mat [matrix]
  (let[
    flat (flatten matrix)
    rows (count matrix)
    cols (count (first matrix))
    mat (Mat. rows cols CvType/CV_32F)
    total (.total mat)
    bytes (float-array total)
    ]
    (doseq [^int i (range 0 total)]
      (aset-float bytes i (nth flat i)))
    (.put mat 0 0 bytes)
    mat))

(defn mat-from [src]
  (Mat. (.rows src) (.cols src) (.type src)))

(defn mat-to-buffered-image [src]
  (let[ matOfBytes (MatOfByte.)]
  (Imgcodecs/imencode ".jpg" src matOfBytes)
  (ImageIO/read
    (java.io.ByteArrayInputStream. (.toArray matOfBytes)))))

(defn buffered-image-to-mat[bi]
  (let [mat (Mat. (.getHeight bi) (.getWidth bi) (CvType/CV_8UC3))
  bytes
  (-> bi
    (.getRaster)
    (.getDataBuffer)
    (.getData)
    )]
    (.put mat 0 0 bytes)
    mat))

; points
(defn middle-of-two-points [p1 p2]
  (cv/new-point
    (/ (+ (.-x p1) (.-x p2)) 2)
    (/ (+ (.-y p1) (.-y p2)) 2)))

; gorilla repl
(defn mat-view[img]
    (image-view (mat-to-buffered-image img)))

(defn image-from-url[url]
  (let[ connection (->  url
  (java.net.URL.)
    (.openConnection))]
    (.setRequestProperty connection
      "User-Agent"
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
      (ImageIO/read (.getInputStream connection))))

(defn mat-from-url[url]
  (buffered-image-to-mat (image-from-url url)))

;;;
; CONTOURS
;;;
(defn draw-contours-with-rect!
([buffer contours] (draw-contours-with-rect! buffer contours false))
([buffer contours save-frame]
  (doseq [c contours]
  (let [area (cv/contour-area c)]
     (if (> area 1000)
       (let [rect (cv/bounding-rect c)]
       (if save-frame (cv/imwrite (.submat buffer rect) (str "video/" (java.util.Date.) ".png")))
       (cv/rectangle
         buffer
         (cv/new-point (.x rect) (.y rect))
         (cv/new-point (+ (.width rect) (.x rect)) (+ (.y rect) (.height rect)))
         (cv/new-scalar 255 0 0) 3))))
         buffer
         )))

(defn draw-contours-with-line! [img contours]
(dotimes [i (.size contours)]
   (let [c (.get contours i)
      m2f (cv/new-matofpoint2f (.toArray c))
      len (cv/arc-length m2f true)
      ret (cv/new-matofpoint2f)
      approx (cv/approx-poly-dp m2f ret (* 0.005 len) true)
      nb-sides (.size (.toList ret))]
; (println ">" nb-sides)
(cv/draw-contours img contours i
 (condp = nb-sides
  3  (cv/new-scalar 56.09 68.05 66.27)
  4  (cv/new-scalar 356.09 51.57 43.73)
  7   (cv/new-scalar 26.09 51.57 43.73)
  8 (cv/new-scalar 146.09 51.57 43.73)
  9 (cv/new-scalar 266.09 51.57 43.73)
  10  (cv/new-scalar 236.09 51.57 43.73)
  11 (cv/new-scalar 206.09 68.05 66.27)
  12 (cv/new-scalar 0 0 255)
     (cv/new-scalar 127 50 0))
  -1)))
 img)

;;;
; SWING
;;;
(defn show[src]
  (let [
    buf (mat-to-buffered-image src)
    frame (JFrame. "image")
    pane (.getContentPane frame)
    image (ImageIcon. buf)
    label (JLabel. image)
    ]
    (.setLayout pane (FlowLayout.))
    (.add pane label)
    (.addMouseListener label
      (proxy [MouseAdapter] []
       (mousePressed [event]
         (println "clicked")
         (ImageIO/write
           (.getImage (.getIcon label))
           "png"
           (clojure.java.io/as-file (str "webcam_" (System/currentTimeMillis) ".png"))
           ))))
    (doto frame
      (.pack)
      (.setVisible true)
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE))
      pane))

(defn re-show[pane mat]
  (let[image (.getIcon (first (.getComponents pane)))]
  (.setImage image (mat-to-buffered-image mat))
  (doto pane
    (.revalidate)
    (.repaint))))
