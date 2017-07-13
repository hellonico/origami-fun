(defproject opencv-fun "0.1.0-SNAPSHOT"
  :injections [(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)]
  :plugins [[lein-gorilla "0.4.0"]]
  :repositories [["vendredi" "http://hellonico.info:8081/repository/hellonico/"]]
  :profiles {:dev {:dependencies [[gorilla-repl "0.4.0"] ]}}
  :dependencies [
  [org.clojure/clojure "1.8.0"]
  [org.clojure/tools.nrepl "0.2.11"]
  [proto-repl "0.3.1"]
  [opencv/opencv "3.3.0-rc"]
  [opencv/opencv-native "3.3.0-rc"]
  ])
