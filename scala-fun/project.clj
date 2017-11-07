(defproject opencv-scala-fun "0.1.0-SNAPSHOT"
  :java-source-paths ["scala"]
  :repositories [["vendredi" "http://hellonico.info:8081/repository/hellonico/"]]
  :main SimpleOpenCV
  :plugins [
  [lein-zinc "1.2.0"]
  [lein-auto "0.1.3"]]
  :prep-tasks ["zinc" "compile"]
  :auto {:default {:file-pattern #"\.(scala)$"}}
  :dependencies [
   [org.clojure/clojure "1.8.0"]
   [org.scala-lang/scala-library "2.12.4"]
   [opencv/opencv "3.3.1"]
   [opencv/opencv-native "3.3.1"]
])
