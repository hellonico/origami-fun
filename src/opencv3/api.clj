(ns opencv3.api
  (:use camel-snake-kebab.core)
  (:import
    [java.util ArrayList]
    [org.opencv.photo Photo]
    [org.opencv.core RotatedRect MatOfDMatch MatOfInt MatOfKeyPoint MatOfPoint MatOfPoint2f MatOfRect Point Rect Mat Size Scalar Core CvType Mat MatOfByte MatOfKeyPoint MatOfRect Point Rect Mat Size Scalar Core]
    [org.opencv.core CvType Core Mat]
    [org.opencv.videoio Videoio VideoCapture]
    [org.opencv.imgproc Imgproc]))

(defn print-fields[klass]
  (let[ fields (.getDeclaredFields klass)]
  (doseq [field fields]
    (let[ms (.getModifiers field)]
    (if (java.lang.reflect.Modifier/isPublic ms)

   (println
    (str "(def " (.getName field) " " (.getSimpleName klass) "/" (.getName field) ")")))))))

(defn param-name[pname]
  (->  pname
  (clojure.string/replace  #"\." "_")
  (clojure.string/replace  #";" "")
  (clojure.string/replace  #"class " "")
  (clojure.string/replace  #"interface " "")
  (clojure.string/replace  #"\[" "array_")
  (clojure.string/lower-case)))

(defn filter-m-like[methods method-name]
  (doall
    (filter
      #(.equalsIgnoreCase (.getName %) method-name)
      methods)))

(defn- positions
  [pred coll]
  (keep-indexed (fn [idx x]
                  (when (pred x)
                    idx))
                coll))

(defn is-first-with-those-params[m others]
 (let [
   c-param (count (.getParameterTypes m))
   result (atom true)
   position (first (positions #{m} others)) ; the method has to be in the list it was taken from
   ]
  (dotimes [ i (count others) ]
      (let[ o (nth others i) o-params (count (.getParameterTypes o))]
        (if (and
          (= c-param o-params)
          (> position i)
          (not (.equals m o))) ; not equal and not same number of params
          (reset! result false))
      ))
      @result
      ))
;
; (def methods (.getDeclaredMethods Imgproc))
; (def cannies (filter-m-like methods "canny"))
;
; (doseq[ c cannies ]
;   (println (.getName c) ">" (count (.getParameterTypes c)) (is-first-with-those-params c cannies))
;   ; (println (positions #{c} cannies))
;   )

(defn print-sub
  ([m others]
    (print-sub m others false))
  ([m others bikkuri-mode]
    (spit "debug.log"  (str "print-sub:" m) :append true)
  (let[
    sname (.getName m)
    params (.getParameterTypes m)
    count-params (count params)
    klass (.getSimpleName (.getDeclaringClass m))
    ]
  (if (is-first-with-those-params m others)
  (do
  (print "([")
  (doseq [i (range 0 count-params)]
    (if (and bikkuri-mode (= i 1))
     (do)
     (print (str (param-name (nth params i)) "_" (str i) " "))))

  (print "] ")
  (print (str "\n  (" klass "/" sname " "))

  (doseq [i (range 0 count-params)]
    (if (and bikkuri-mode (= i 1))
     (print (str (param-name (nth params 0)) "_0"  " "))
     (print (str (param-name (nth params i)) "_" (str i) " "))))
  (print ")")

  ; returns last parameter as well in bikkuri mode
  (if bikkuri-mode
    (print (str " " (param-name (nth params 0)) "_0" " ")))
  (print ")\n"))))))

(defn print-cv-method[methods method-name]
  (let[ fmethods (filter-m-like methods method-name)]
  (do
    (println (str "(defn " (->kebab-case method-name)))
    (doseq [m fmethods]
      (print-sub m fmethods))
  (println ")\n"))))

(defn print-bikkuri-method[methods method-name]
  (let[
    fmethods (filter-m-like methods method-name)
    m (first fmethods)
    all-params (.getParameterTypes m)]
    (if (>= (count all-params) 2)
    (let[
     params (take 2 all-params)
     ]
  (if
    (and
      (.equals (first params) Mat)
      (.equals (first params) (second params)))
    (do
    (println (str "(defn " (->kebab-case method-name) "!"))
    (doseq [m fmethods]
      (print-sub m fmethods true))
    (println ")\n")))))))

(defn print-cv-methods[klass]
  (let[ methods (.getDeclaredMethods klass) done (java.util.ArrayList.)]
  (doseq [m methods]
    (if (not (.contains done (.getName m)))
       (if
         (java.lang.reflect.Modifier/isPublic (.getModifiers m))
         (do
           (print-cv-method methods (.getName m))
           (print-bikkuri-method methods (.getName m))
           (.add done (.getName m))))))))

(defn print-constructors
 [klass]
 (let[
   cs (.getConstructors klass)
   sname (clojure.string/lower-case (.getSimpleName klass))
   ]
 (print (str "(defn new-" sname " "))
 (doseq[ c cs ]
  (let [
   params (.getParameterTypes c)
   count-params (count params)
   ]

 (if (is-first-with-those-params c cs)
  (do
   (print "\n([")
   (doseq [i (range 0 count-params)]
    (print (str (param-name (nth params i)) "_" (str i) " ")))

  (print "] ")
  (print (str "\n  (new " (.getName klass) " "))

 (doseq [i (range 0 count-params)]
    (print (str (param-name (nth params i)) "_" (str i) " ")))
 (print "))")))))

 (print ")\n")))

(defn generate-api
  ([] (generate-api "cv1.clj"))
  ([output-file]
(with-open [w (-> output-file clojure.java.io/writer)]
  (binding [*out* w]

    (doseq [klass #{VideoCapture RotatedRect Point Scalar MatOfByte Size MatOfInt ArrayList MatOfPoint Mat Rect MatOfPoint2f }]
      (print-constructors klass))

    (print-cv-methods Imgproc)
    (print-fields Imgproc)
    (println ";;;")
    (print-fields Core)
    (print-cv-methods Core)
    (println ";;; CvType ")
    (print-fields CvType)
    (println ";;; Photo")
    (print-cv-methods Photo)

    ))))


(comment
(generate-api)

(with-open [w (-> "output2.clj" clojure.java.io/writer)]
  (binding [*out* w]
      (print-constructors RotatedRect)
  ))
)
