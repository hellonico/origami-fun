(defproject opencv-java-fun "0.1.0-SNAPSHOT"
  :java-source-paths ["java"]
  :repositories [["vendredi" "http://hellonico.info:8081/repository/hellonico/"]]
  :plugins [[lein-auto "0.1.3"]]
  :auto {:default {:file-pattern #"\.(java)$"}}
  :dependencies [
   [org.clojure/clojure "1.8.0"]
   [opencv/opencv "3.3.0-rc"]
   [opencv/opencv-native "3.3.0-rc" :classifier "osx"]
   ; [opencv/opencv-native "3.3.0-rc" :classifier "linux"]
   ; [opencv/opencv-native "3.3.0-rc" :classifier "win"]
   ])