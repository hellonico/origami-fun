(defproject egami "0.1.0-SNAPSHOT"
  :injections [
  (clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
  ]
  :plugins [
  ; [lein-asciidoctor "0.1.12"]
  ; [lein-codox "0.10.3"]
  [lein-gorilla "0.4.0"]]
  :test-paths ["test" "samples"]
  ; :asciidoctor [{:sources ["docs/*.ascii"]
  ;             :to-dir "doc-generated"
  ;             :compact true
  ;             :format :html5
  ;             :extract-css true
  ;             :toc :left
  ;             :title "Just an example"
  ;             :source-highlight true}]
  :repositories [["vendredi" "http://hellonico.info:8081/repository/hellonico/"]]
  :profiles {:dev {
    :repl-options {:init-ns opencv3.affine}
    :dependencies [
    [org.clojure/tools.nrepl "0.2.11"]
    [proto-repl "0.3.1"]
    [camel-snake-kebab "0.4.0"]
    [gorilla-repl "0.4.0"] ]}}
  :dependencies [
   [org.clojure/clojure "1.8.0"]
   [org.clojure/tools.cli "0.3.5"]

   ; OSX and LINUX 3.3
   [opencv/opencv "3.3.0-rc"]
   [opencv/opencv-native "3.3.0-rc"]
  ;  [opencv/opencv-native "3.3.0-rc-linux"]

  ; WINDOWS 3.2
  ; coz I cannot get 3.3 to compile under windows :'(
  ; [opencv/opencv "3.2.0"]
  ; [opencv/opencv-native "3.2.0-windows-x64"]
  ; [opencv/opencv-native "3.2.0-osx"]

  ])
