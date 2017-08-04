(defproject egami "0.1.0-SNAPSHOT"
  :injections [
  (clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
  ]
  :plugins [
  [lein-gorilla "0.4.0"]]
  :test-paths ["test" "samples"]
  :repositories [["vendredi" "http://hellonico.info:8081/repository/hellonico/"]]
  :profiles {:dev {
    ; :repl-options {:init-ns opencv3.affine}
    :dependencies [
    ; used for proto repl
    [org.clojure/tools.nrepl "0.2.11"]
    ; proto repl
    [proto-repl "0.3.1"]
    ; used for api code only
    [camel-snake-kebab "0.4.0"]
    ; use to start a gorilla repl
    [gorilla-repl "0.4.0"]
    [seesaw "1.4.5"]
     ]}}
  :dependencies [
   [org.clojure/clojure "1.8.0"]
   [org.clojure/tools.cli "0.3.5"]

   [opencv/opencv "3.3.0-rc"]
   [opencv/opencv-native "3.3.0-rc" :classifier "osx"]
   [opencv/opencv-native "3.3.0-rc" :classifier "linux"]
   [opencv/opencv-native "3.3.0-rc" :classifier "win"]

  ])
