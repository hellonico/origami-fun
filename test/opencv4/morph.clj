(ns opencv4.morph
  (:require [opencv4.core :refer :all]))
; http://docs.opencv.org/3.0-beta/doc/py_tutorials/py_imgproc/py_morphological_ops/py_morphological_ops.html

; 1. Erosion
; The basic idea of erosion is just like soil erosion only, it erodes away the boundaries of foreground object (Always try to keep foreground in white). So what it does? The kernel slides through the image (as in 2D convolution). A pixel in the original image (either 1 or 0) will be considered 1 only if all the pixels under the kernel is 1, otherwise it is eroded (made to zero).

(->
  (imread "resources/morph/j.png")
  (erode! (get-structuring-element MORPH_RECT (new-size 5 5)))
  (imwrite  "output/morph1.png"))

; 2. Dilation
; It is just opposite of erosion. Here, a pixel element is ‘1’ if atleast one pixel under the kernel is ‘1’. So it increases the white region in the image or size of foreground object increases. Normally, in cases like noise removal, erosion is followed by dilation. Because, erosion removes white noises, but it also shrinks our object. So we dilate it. Since noise is gone, they won’t come back, but our object area increases. It is also useful in joining broken parts of an object.
(->
  (imread "resources/morph/j.png")
  (dilate! (get-structuring-element MORPH_RECT (new-size 5 5)))
  (imwrite "output/morph2.png"))

; 3. Opening
; Opening is just another name of erosion followed by dilation. It is useful in removing noise, as we explained above. Here we use the function, cv2.morphologyEx()
(->
  (imread "resources/morph/opening.png")
  (morphology-ex! MORPH_OPEN (get-structuring-element MORPH_RECT (new-size 5 5)))
  (imwrite "output/morph3.png"))

; 4. Closing
; Closing is reverse of Opening, Dilation followed by Erosion. It is useful in closing small holes inside the foreground objects, or small black points on the object.

(->
  (imread "resources/morph/closing.png")
  (morphology-ex! MORPH_CLOSE (get-structuring-element MORPH_RECT (new-size 5 5)))
  (imwrite "output/morph4.png"))

; 5. Morphological Gradient
; It is the difference between dilation and erosion of an image.
; The result will look like the outline of the object.
(->
  (imread "resources/morph/gradient.png")
  (morphology-ex! MORPH_GRADIENT (get-structuring-element MORPH_RECT (new-size 5 5)))
  (imwrite "output/morph5.png"))

; 6. Top Hat
; It is the difference between input image and Opening of the image. Below example is done for a 9x9 kernel.
(def kernel (get-structuring-element MORPH_RECT (new-size 5 5)))
(->
  (imread "resources/morph/tophat.png")
  (morphology-ex! MORPH_TOPHAT kernel)
  (imwrite "output/morph6.png"))

; 7. Black Hat
; It is the difference between the closing of the input image and input image.
(->
  (imread "resources/morph/blackhat.png")
  (morphology-ex! MORPH_BLACKHAT (get-structuring-element MORPH_RECT (new-size 10 10)))
  (imwrite "output/morph7.png"))
