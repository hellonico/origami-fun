;; gorilla-repl.fileformat = 1

;; **
;;; # Working with simple mats 
;;; 
;;; This is where it starts, we start creating matrix of values that are turned into colors. 
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
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAApklEQVR42u3RQREAAAjDsPkXMLtgg+PyqIEmbUd3iglABASIgAARECACAsQIIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIEBMACIgQAQEiIB8bwFV8plWIGmmowAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x477a03be \"BufferedImage@477a03be: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(-> (new-mat 100 100 CV_8UC3)
    (set-to (new-scalar 250 150 150))
	(u/mat-view))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAAp0lEQVR42u3RMQ0AAAgEMfwLeLEsYIOQDmfgWkmP7lQmABEQIAICRECACAgQI4AICBABASIgQAQEiIAICBABASIgQAQEiIAICBABASIgQAQEiIAICBABASIgQAQEiIAICBABASIgQAQEiIAICBABASIgQAQEiIAICBABASIgQAQEiIAICBABASIgQAQEiIAICBABASIgQAQEiIAAMQGIgAARECAC8r0FdxHcdxBOmt4AAAAASUVORK5CYII=\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x55c9027e \"BufferedImage@55c9027e: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(-> 
  (new-mat 100 100 CV_8UC3 (new-scalar 100 200 100))
  (u/mat-view))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAApklEQVR42u3RQREAAAQAQWHpqhU1jNnHFbiN7BrdKUwAIiBABASIgAARECBGABEQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAQFiAhABASIgQATkewsmth7dSAHdAAAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x4f6353c3 \"BufferedImage@4f6353c3: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(-> 
  (new-mat 100 100 CV_8UC3 (new-scalar 100 200 100))
  (bitwise-not!)
  (u/mat-view))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAApklEQVR42u3RQREAAAQAQa10JSw1jNnHFbiNzhrdKUwAIiBABASIgAARECBGABEQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAREQIAICRECACAgQAQFiAhABASIgQATkewtkOHkz6ejY2wAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x1aaf3dc9 \"BufferedImage@1aaf3dc9: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(u/mat-view
  (new-mat 100 100 CV_8UC3 rgb/red-2))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAApklEQVR42u3RMQ0AMAzAsPAHWworjWryEQJxU093ygQgAgJEQIAICBABAWIEEAEBIiBABASIgAAREAEBIiBABASIgAAREAEBIiBABASIgAAREAEBIiBABASIgAAREAEBIiBABASIgAAREAEBIiBABASIgAAREAEBIiBABASIgAAREAEBIiBABASIgAAREAEBIiBABASIgAARECAmABEQIAICREB+bwHo0T42gquDjQAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x7ab58f0e \"BufferedImage@7ab58f0e: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(u/mat-view
  (new-mat 100 100 CV_8UC3 rgb/yellow-2))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAApklEQVR42u3RQREAAAjDsPkXOwtgg+PyqIEmbUZ3iglABASIgAARECACAsQIIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIEBMACIgQAQEiIB8bwHr8pEybjjxngAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x15651dc5 \"BufferedImage@15651dc5: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(println rgb/yellow-2)
(.dump (new-mat 3 3 CV_8UC3 rgb/yellow-2))
;; @@
;; ->
;;; #object[org.opencv.core.Scalar 0x539be07 [0.0, 238.0, 238.0, 0.0]]
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
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAALElEQVR42mOQ5mT4/+Fe0v93Hxn+M7y7k/D/za24/2/egzhAkfefgPiN8H8Az4kZArvGQ+kAAAAASUVORK5CYII=\" width=\"3\" height=\"3\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x47669caf \"BufferedImage@47669caf: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 3 height = 3 #numDataElements 3 dataOff[0] = 2\"]"}
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
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAApklEQVR42u3RQREAAAjDsPkXOwtgg+PyqIEmbUZ3iglABASIgAARECACAsQIIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIAICRECACAgQAQEiIEBMACIgQAQEiIB8bwHr8pEybjjxngAAAABJRU5ErkJggg==\" width=\"100\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x9baed50 \"BufferedImage@9baed50: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 100 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(def row 100)
(def col 150)
(defn scalar->bytes [s]
  (byte-array (take 3 (.val s ))))

(def yellow (scalar->bytes (new-scalar 250 150 150)))

(def a (new-mat row col CV_8UC3))

(dotimes [i row]
  (dotimes [j col]
	 (.put a i j yellow)))

(u/mat-view a)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAABkCAYAAABkW8nwAAAAzUlEQVR42u3SMREAAAjEMPwLeLEsYIKBIUMM9FpJD1wrETAWxsJYQmAsjIWxwFgYC2OBsTAWxgJjYSyMBcbCWBgLjIWxMBYYC2NhLDAWxsJYYCyMhbHAWBgLY4GxMBbGAmNhLIwFxsJYGAuMhbEwFhgLY2EsMBbGwlhgLIyFscBYGAtjgbEwFsYCY2EsjAXGwlgYC4yFsTAWGAtjYSwwFsbCWGAsjIWxwFgYC2OBsTAWxgJjYSyMhbFEwFgYC2OBsTAWxgJjYSyMBcbitwXJh0rBxAuFLgAAAABJRU5ErkJggg==\" width=\"150\" height=\"100\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x3c9afc82 \"BufferedImage@3c9afc82: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 150 height = 100 #numDataElements 3 dataOff[0] = 2\"]"}
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
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAADCAYAAAC09K7GAAAAFklEQVR42mN4zsj9H4Q5GD6DMQNBAQBLHxeJcOYlAAAAAABJRU5ErkJggg==\" width=\"4\" height=\"3\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x30a60c \"BufferedImage@30a60c: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 4 height = 3 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@
(u/mat-view
  (matrix-to-mat 
    (into [] 
      (clojure.core/repeat 50 
		(clojure.core/take 70 (repeatedly #(rand-nth [rgb/blue-3- rgb/beige])))))))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAAAyCAYAAADhna1TAAABQElEQVR42u3QsUrDUBjF8aQxvcmgaR9Ld3H2UZxEBJ2kIlIQNx9AHAQH0Ul0cVBULJQatE1Skybr8Wvewe0/fITc5J5z788ry7mKX6nXX9fPVJrmUtLf0CSVut1Tdbx7ed6j/OBKgRtqPJGc21cnuFEYnmlm/zfNu+r6SVWVKreMON6R79/avpf2GUUHKjKpWnypqkeaFZbtjmwG7XpuGaE7luffye9cW+65JtYTR7uW8WDzav2XCqNB258kWyrmUml59eLTMj9UVmNltha5PZtDZctc61ld21Zqd+klmyqsZ3nGpnm2877Z3pG9f7dZiX1fZofuRMHKhTxggAEGGGCAAQYYYIABBhhggAEGGGCAAQYYYIABBhhggAEGGGCAAQYYYIABBhhggAEGGGCAAQYYYIABBhhggAEGGGD+F+YPzES9WimKxEAAAAAASUVORK5CYII=\" width=\"70\" height=\"50\" alt=\"\" />","value":"#object[java.awt.image.BufferedImage 0x169c23e9 \"BufferedImage@169c23e9: type = 5 ColorModel: #pixelBits = 24 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@576ad4d9 transparency = 1 has alpha = false isAlphaPre = false ByteInterleavedRaster: width = 70 height = 50 #numDataElements 3 dataOff[0] = 2\"]"}
;; <=

;; @@

;; @@
