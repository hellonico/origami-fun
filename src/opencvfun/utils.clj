(ns opencvfun.utils
  (:import [org.opencv.core Core Mat MatOfByte]
    [org.opencv.imgcodecs Imgcodecs]
    [org.opencv.videoio VideoCapture]
    [org.opencv.imgproc Imgproc]
    [javax.imageio ImageIO]
    [javax.swing ImageIcon JFrame JLabel]
    [java.awt FlowLayout]))

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
