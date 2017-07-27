(ns opencv3.affine
  (:require [opencv3.core :refer :all]
        [opencv3.utils :as u]))

; http://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/warp_affine/warp_affine.html
;
; (def )
; Rect roi = Imgproc.boundingRect(contour);
; // we only work with a submat, not the whole image:
; Mat mat = image.submat(roi);
; RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
; Mat rot = Imgproc.getRotationMatrix2D(rotatedRect.center, rotatedRect.angle, 1.0);
; // rotate using the center of the roi
; double[] rot_0_2 = rot.get(0, 2);
; for (int i = 0; i < rot_0_2.length; i++) {
;     rot_0_2[i] += rotatedRect.size.width / 2 - rotatedRect.center.x;
; }
; rot.put(0, 2, rot_0_2);
; double[] rot_1_2 = rot.get(1, 2);
; for (int i = 0; i < rot_1_2.length; i++) {
;     rot_1_2[i] += rotatedRect.size.height / 2 - rotatedRect.center.y;
; }
; rot.put(1, 2, rot_1_2);
; // final rotated and cropped image:
; Mat rotated = new Mat();
; Imgproc.warpAffine(mat, rotated, rot, rotatedRect.size);

; http://docs.opencv.org/trunk/d2/dbd/tutorial_distance_transform.html

(def rot-mat (new-mat 2 3 CV_32FC1))
(def warp-mat (new-mat 2 3 CV_32FC1))

(def rose
  (imread "resources/matching/rose_flower.jpg"))
(def warp-dst (u/mat-from rose))

; https://github.com/opencv/opencv/blob/master/modules/imgproc/misc/java/test/ImgprocTest.java
(def src (new-matofpoint2f
  (into-array org.opencv.core.Point [(new-point 2 3) (new-point 3 1) (new-point 1 4)])))
(def dst (new-matofpoint2f
  (into-array org.opencv.core.Point [(new-point 3 3) (new-point 7 4) (new-point 5 6)])))
(def transform-mat (get-affine-transform src dst))
(.dump transform-mat)

(def truth (new-mat 2 3 CV_8SC1))
(.put truth 0 0 (double-array [-8 -6 37]))
(.put truth 1 0 (double-array [-7 -4 29]))

(.dump truth)
