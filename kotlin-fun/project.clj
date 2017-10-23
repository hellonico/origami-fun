(defproject opencv-java-fun "0.1.0-SNAPSHOT"

  :repositories [
  ["vendredi" "http://hellonico.info:8081/repository/hellonico/"]]
  ; :javac-options ["-target 1.8"]
  :plugins [
  ; [lein-kotlin "0.0.3"]
  [hellonico/lein-kotlin "0.0.3"]
  [lein-auto "0.1.3"]
  ]
  :profiles {
    :dev {:dependencies [[org.jetbrains.kotlin/kotlin-runtime "1.1.4-3"]]}
    :repl {:dependencies [[org.jetbrains.kotlin/kotlin-compiler "1.1.4-3"]]}}
  :prep-tasks ["javac" "compile" "kotlin" ]
  :kotlin-source-path "kotlin"
  :java-source-paths ["java" "kotlin"]
  :auto {:default {:file-pattern #"\.(kt)$"}}
  :dependencies [
   [org.clojure/clojure "1.8.0"]
   [opencv/opencv "3.3.0-rc"]
   [no.tornado/tornadofx "1.7.11"]
   [opencv/opencv-native "3.3.0-rc" :classifier "osx"]
   ; [opencv/opencv-native "3.3.0-rc" :classifier "linux"]
   ; [opencv/opencv-native "3.3.0-rc" :classifier "win"]
   ])
