(defproject origami-fun "0.1.0-SNAPSHOT"
  :test-paths ["test" "samples"]
  :gorilla-options {:load-scan-exclude #{".git" "project.clj" ".svn" "samples" "src" "test"}}
  :repositories [["vendredi" "https://repository.hellonico.info/repository/hellonico/"]]
  :aliases {"jnotebook" ["jupyter" "notebook"]
            "notebook" ["with-profile" "-dev" "gorilla" "gorilla" ":ip" "0.0.0.0" ":port" "10000"]
            "cv_ok" ["run" "-m" "opencv4.ok"]}
  :profiles {:dev {:dependencies [[clojupyter "0.3.1"]]
                   :plugins [[hellonico/lein-jupyter "0.1.17"]]}
  :jupyter {

    :dependencies [ [clojupyter "0.3.1"]]
    :plugins [[hellonico/lein-jupyter "0.1.17"]]}
             :gorilla {:dependencies [[hellonico/gorilla-repl "0.4.1"]]
                       :plugins [[hellonico/lein-gorilla "0.4.2"]]}}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/tools.cli "0.3.5"]
                 [seesaw "1.4.5"]
                 [origami/origami "4.5.1-6"]
                 [origami/filters "1.11"]])
