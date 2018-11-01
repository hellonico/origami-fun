;; gorilla-repl.fileformat = 1

;; **
;;; # Working with simple mats 
;;; 
;;; This is where it starts, we start opencv pure mat object with matrix of pixel values.
;; **

;; @@
(ns calm-galaxy
  (:require
    [opencv4.core :refer :all]
    [opencv4.colors.rgb :as rgb]
    [opencv4.utils :as u]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; You can think of image containers in OpenCV as matrices. You usually define them with a number of rows, a number of columns and a type. 
;;; The type defines what kind of content each pixel can receive. 
;; **

;; @@
(def a (new-mat 100 100 CV_8UC3 (new-scalar 150 150 150)))
(u/mat-view a)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAA/0lEQVR4Xu3RoQEAIAzAsP1/wN4FzwNURMTUdnb30DFv4C9DYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJuVXymVbWw7nMAAAAAElFTkSuQmCC\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x78b97ae6 \"BufferedImage@78b97ae6: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(-> (new-mat 100 100 CV_8UC3)
    (set-to (new-scalar 250 150 150))
	(u/mat-view))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAABAElEQVR4Xu3RIQEAIBDAQPoH+LAY8BRg4sSZ2a2ZfehYb+AvQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMibl3Edx3ev5b3QAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x13fecbe4 \"BufferedImage@13fecbe4: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(-> 
  (new-mat 100 100 CV_8UC3 (new-scalar 100 200 100))
  (u/mat-view))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAABAElEQVR4Xu3RIQEAIADAMMJCV1qBpwAXEzO3H3OvQ8d4A38ZEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIzAUmth7d+uIN1AAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x34bc06b3 \"BufferedImage@34bc06b3: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(-> 
  (new-mat 100 100 CV_8UC3 (new-scalar 100 200 100))
  (bitwise-not!)
  (u/mat-view))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAABAElEQVR4Xu3RIQEAIADAMFrRFcKCpwAXEzO3H3uuQ8d4A38ZEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIjCExhsQYEmNIzAVkOHkzK7YFZgAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x62a46487 \"BufferedImage@62a46487: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(u/mat-view
  (new-mat 100 100 CV_8UC3 rgb/red-2))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAABAElEQVR4Xu3RIQHAMADEwPcvdhZaPgMNOHAkNPu2Q8f+gbcMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEkxpAYQ2IMiTEk5gLo0T42j4gnawAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x4b397270 \"BufferedImage@4b397270: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(u/mat-view
  (new-mat 100 100 CV_8UC3 rgb/yellow-2))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAA/0lEQVR4Xu3RoQEAIAzAsP1/7F4AzwNURMTUdnbn0DFv4C9DYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJuevykTL7ADSgAAAAAElFTkSuQmCC\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x3d1fc866 \"BufferedImage@3d1fc866: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(println rgb/yellow-2)
(.dump (new-mat 3 3 CV_8UC3 rgb/yellow-2))
;; @@
;; ->
;;; #object[org.opencv.core.Scalar 0x323ac256 [0.0, 238.0, 238.0, 0.0]]
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;[  0, 238, 238,   0, 238, 238,   0, 238, 238;\\n   0, 238, 238,   0, 238, 238,   0, 238, 238;\\n   0, 238, 238,   0, 238, 238,   0, 238, 238]&quot;</span>","value":"\"[  0, 238, 238,   0, 238, 238,   0, 238, 238;\\n   0, 238, 238,   0, 238, 238,   0, 238, 238;\\n   0, 238, 238,   0, 238, 238,   0, 238, 238]\""}
;; <=

;; @@
(def a (new-mat 3 3 CV_8UC3))

(.put a 0 1 (byte-array [0 238 238]))
(.put a 0 2 (byte-array [0 238 238]))

(.put a 1 0 (byte-array [0 238 238]))
(.put a 1 1 (byte-array [0 238 238]))
(.put a 1 2 (byte-array [0 238 238]))

(.put a 2 0 (byte-array [0 238 238]))
(.put a 2 1 (byte-array [0 238 238]))
(.put a 2 2 (byte-array [0 238 238]))
(u/mat-view a)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAALklEQVR4XmOY3M/w/90r8f8f3vH/Z3j9XATMefWC8z/D549C/1+/5Pr/8QPHfwDk9hljgLAL5QAAAABJRU5ErkJggg==\" width=\"3\" height=\"3\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x69f17360 \"BufferedImage@69f17360: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 3 height = 3 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@

(def row 100)
(def col 100)
(def yellow (byte-array [0 238 238]) )

(def a (new-mat row col CV_8UC3))

(dotimes [i row]
  (dotimes [j col]
	 (.put a i j yellow)))

(u/mat-view a)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAA/0lEQVR4Xu3RoQEAIAzAsP1/7F4AzwNURMTUdnbn0DFv4C9DYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJMSTGkBhDYgyJuevykTL7ADSgAAAAAElFTkSuQmCC\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x2cf88ad3 \"BufferedImage@2cf88ad3: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(def row 100)
(def col 150)
(defn scalar->bytes [s]
  (byte-array (take 3 (.val s ))))

(def yellow (scalar->bytes (new-scalar 210 250 250)))

(def a (new-mat row col CV_8UC3))

(dotimes [i row]
  (dotimes [j col]
	 (.put a i j yellow)))

(u/mat-view a)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAABkCAYAAABkW8nwAAABGUlEQVR4Xu3SoQEAIACAMP8/z2+0aDdLW1ghM9aeB34bb4AfjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCSMRcJYJIxFwlgkjEXCWCQuIwSxRUxgzSIAAAAASUVORK5CYII=\" width=\"150\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x5624bc8b \"BufferedImage@5624bc8b: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 150 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(defn matrix-to-mat [matrix]
  (let [ row (count matrix) col (count (first matrix)) b (new-mat row col CV_8UC3)]
   (dotimes [i row]
    (dotimes [j col ]
	 (.put b i j (scalar->bytes (nth (nth matrix i) j )))))
  b
  ))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;calm-galaxy/matrix-to-mat</span>","value":"#'calm-galaxy/matrix-to-mat"}
;; <=

;; @@
(u/mat-view
  (matrix-to-mat [ 
   [rgb/red-2 rgb/red-2 rgb/blue rgb/blue] 
   [rgb/red-2 rgb/red-2 rgb/blue rgb/blue]  
   [rgb/red-2 rgb/red-2 rgb/blue rgb/blue] ]))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAADCAYAAAC09K7GAAAAFUlEQVR4XmN4z8DwH4QZGP9BMEEBAEpfF4mqjyfaAAAAAElFTkSuQmCC\" width=\"4\" height=\"3\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x1c102b6b \"BufferedImage@1c102b6b: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 4 height = 3 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(u/mat-view
  (matrix-to-mat 
    (into [] 
      (clojure.core/repeat 50 
		(clojure.core/take 70 (repeatedly #(rand-nth [rgb/blue-3- rgb/beige])))))))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAAAyCAYAAADhna1TAAABIUlEQVR4Xu3QTU7DMBCGYbf5KQlJezCOAQhxCBCcoqi3qOAUqHQPFTtQC0UJTdKw/PAYqTdg9y5Gjp0Ze+ZxxfhUn1/SrunV7l+199E2P5pMTrTZSOsPqRifh9hupWbX+5xViLZ/DmtVS0kylXMLucFCw3iuPLvR+5sURff+fBnOLQbu0e8tlkpGs1Bbf/v60Z3idKra77vuRX23Ul1JaTrzuU8hf+jvsjuzoytF8UOoqaq/3m2G3PdoPWf5tX93fujHcrPsNvyzOWwm+y6LS63trLw4zGnrcXkmBwwwwAADDDDAAAMMMMAAAwwwwAADDDDAAAMMMMAAAwwwwAADDDDAAAMMMMAAAwwwwAADDDDAAAMMMMAAAwwwwAADzP/C/ALs+RMfKFwjMAAAAABJRU5ErkJggg==\" width=\"70\" height=\"50\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x6f7d15ea \"BufferedImage@6f7d15ea: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@4e1cbc92 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 70 height = 50 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=
