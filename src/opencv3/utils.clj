(ns opencv3.utils
  (:use [gorilla-repl.image])
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
    rows (count vertical-matrix)
    cols (count (first vertical-matrix))
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
