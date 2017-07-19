(ns opencv3.affine)

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
