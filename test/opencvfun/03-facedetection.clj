(ns opencv-fun.03-facedetection)
; OPENCV3

; face detection

; http://stackoverflow.com/questions/26845885/java-opencv-org-opencv-core-core-rectangle-method-missing

(import '[org.opencv.core Core Mat MatOfRect Point Rect Scalar]
        '[org.opencv.objdetect CascadeClassifier]
        '[org.opencv.imgcodecs Imgcodecs]
        '[org.opencv.imgproc Imgproc])

(defn opencv-detection [_detector _image]
  (let[
       faceDetector (CascadeClassifier. _detector)
       image (Imgcodecs/imread _image)
       faceDetections  (MatOfRect.)]
    (.detectMultiScale faceDetector image faceDetections)
    (let [
          rect-array (.toArray faceDetections)
          len (alength rect-array)]
      ; output for the fun of it
      (doseq [^Rect rect rect-array]
        (Imgproc/rectangle image
          (Point. (.-x rect) (.-y rect))
          (Point. (+ (.-x rect) (.width rect)) (+ (.-y rect) (.height rect)))
          (Scalar. 0 255 0)))
      (Imgcodecs/imwrite "output/img1.png" image))))

(opencv-detection
  "resources/lbpcascade_frontalface.xml"
  "resources/nico.jpg")

(opencv-detection
  "resources/lbpcascade_frontalface.xml"
  "resources/images/frame3.png")

(opencv-detection
  "resources/lbpcascade_frontalface.xml"
  "target/frame.png")
