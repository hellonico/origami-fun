Grasping OpenCV from Clojure

Watershed
; http://docs.opencv.org/3.1.0/d3/db4/tutorial_py_watershed.html

Distance
; https://www.tutorialspoint.com/opencv/opencv_distance_transformation.htm

Rotation
; https://stackoverflow.com/questions/13629737/crop-and-rotate-picture-opencv

Photomosaic
; https://github.com/DavideA/photomosaic
; https://stackoverflow.com/questions/5478519/photo-mosaic-algorithm-how-to-create-a-mosaic-photo-given-the-basic-image-and-a

Background Subtraction
; http://docs.opencv.org/trunk/d1/dc5/tutorial_background_subtraction.html

Copy Image into camera frame
; https://stackoverflow.com/questions/22428448/copying-a-small-image-into-the-camera-frame-with-opencv-for-android

TO READ
; http://urbanhonking.com/ideasfordozens/2013/01/14/making-photomosaics-in-processing/

; add convert shape detection-shape
http://www.pyimagesearch.com/2016/02/08/opencv-shape-detection/

Find shapes
https://rdmilligan.wordpress.com/2016/02/13/detect-shapes-using-opencv/
https://opencvproject.wordpress.com/projects-files/detection-shape/
https://stackoverflow.com/questions/11424002/how-to-detect-simple-geometric-shapes-using-opencv

http://www.pyimagesearch.com/2016/02/08/opencv-shape-detection/
http://www.pyimagesearch.com/2016/02/15/determining-object-color-with-opencv/

Make sure all examples are converter.
http://opencvexamples.blogspot.com/2013/10/change-image-type-convert-8u-to-32f-or.html

Make sure all the tutorial is converted
http://docs.opencv.org/trunk/dd/d49/tutorial_py_contour_features.html

HSV COLORS
http://colorizer.org/

Clean the repl
```
(map #(ns-unmap *ns* %) (keys (ns-interns *ns*)))
```
