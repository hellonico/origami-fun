(ns opencvfun.proof)

(import '[org.opencv.core MatOfKeyPoint MatOfRect Point Rect Mat Size Scalar Core])

(doseq [m (.getDeclaredMethods Core)]
 (println (.getName m)
 ))
