(defproject origami-fun "0.1.0-SNAPSHOT"
  :plugins [[hellonico/lein-gorilla "0.4.2"]]
  :test-paths ["test" "samples"]
  :resource-paths ["rsc"]
  :gorilla-options {:load-scan-exclude #{".git" ".svn" "src" "test" "project.clj"}}
  :repositories [["vendredi" "https://repository.hellonico.info/repository/hellonico/"]]
  :aliases {
            "notebook" ["gorilla" ":ip" "0.0.0.0" ":port" "10000"]
            "cv_ok" ["run" "-m" "opencv4.ok"]
            }
  :profiles {:dev {
    :resource-paths ["resources"]
    :dependencies [
    [seesaw "1.4.5"]
    [hellonico/gorilla-repl "0.4.1"]
  ]}}
  :dependencies [
   [org.clojure/clojure "1.9.0"]
   [org.clojure/tools.cli "0.3.5"]
   [origami "4.0.0-beta4-SNAPSHOT"]
])