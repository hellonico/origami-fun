(defproject origami "0.1.0-SNAPSHOT"
  :injections [
   (clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
  ]
  :plugins [[lein-gorilla "0.4.0"]]
  :test-paths ["test" "samples"]
  :resource-paths ["rsc"]
  :gorilla-options {:load-scan-exclude #{".git" ".svn" "src" "test" "project.clj"}}
  :repositories [["vendredi" "https://repository.hellonico.info/repository/hellonico/"]]
  :aliases {"notebook" ["gorilla" ":ip" "0.0.0.0" ":port" "10000"]}
  :profiles {:dev {
    :resource-paths ["resources"]
    :dependencies [
    ; used for proto repl
    [org.clojure/tools.nrepl "0.2.11"]
    ; proto repl
    [proto-repl "0.3.1"]
    ; use to start a gorilla repl
    [gorilla-repl "0.4.0"]
    [seesaw "1.4.5"]

     ]}}
  :dependencies [
   [org.clojure/clojure "1.8.0"]
   [org.clojure/tools.cli "0.3.5"]
   [origami "0.1.11-SNAPSHOT"]

])