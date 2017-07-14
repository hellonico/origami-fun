(ns opencvfun.utils
  (:use [gorilla-repl.image])
  (:import [org.opencv.core CvType Core Mat MatOfByte]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.videoio VideoCapture]
    [org.opencv.imgproc Imgproc]
    [javax.imageio ImageIO]
    [javax.swing ImageIcon JFrame JLabel]
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

(defn mat-view[img]
  	(image-view (mat-to-buffered-image img)))

;;;
; IMAGE HANDLING
;;;
(defn frame-to-file[file frame]
    (Imgcodecs/imwrite file frame))
(defn quick-save[img]
  (frame-to-file "target/img1.png" img))

; loading
(defn load-img[path]
  (Imgcodecs/imread path))
(defn load-jpg[path]
   (load-img (str "resources/images/" path ".jpg")))
(defn load-neko[]
  (load-jpg  "cat2"))

(defn load-url[url]
  (let[ connection (->  url
	(java.net.URL.)
    (.openConnection))]
    (.setRequestProperty connection
      "User-Agent"
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
      (ImageIO/read (.getInputStream connection))))

(defn load-mat-from-url[url]
  (buffered-image-to-mat (load-url url)))

;;;
; SWING
;;;
(defn swing-show-image[src]
  (let [
    buf (mat-to-buffered-image src)
    frame (JFrame. "image")
    pane (.getContentPane frame)
    ]
    (.setLayout pane (FlowLayout.))
    (.add pane (JLabel. (ImageIcon. buf)))
    (doto frame
      (.pack)
      (.setVisible true)
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE))))

;;;
; IMAGE PROCESSING
;;;
(defn turn-to-gray[img]
    (let[ grayed (Mat. (.rows img) (.cols img) (.type img))]
    (Imgproc/cvtColor img grayed Imgproc/COLOR_BGRA2GRAY)
    (Core/normalize grayed grayed 0 255 Core/NORM_MINMAX)
    grayed))

(defn quick-zoom [_source _factor _inter ]
  (let [_target (Mat. (.intValue (* _factor (.rows _source)))  (.intValue (* _factor (.cols _source))) (.type _source))]
    (Imgproc/resize _source _target (.size _target) _factor _factor _inter)
    _target))
