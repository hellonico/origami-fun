(set-env!
    :source-paths #{"src" "test"}
    :repositories #(conj % [
    	"vendredi" {:url "http://hellonico.info:8081/repository/hellonico/"}
    	"clojars" {:url "https://clojars.org/repo/"}
    	])
    :dependencies '

    [
    ; [org.clojure/clojure "1.9.0-alpha20"]
    [org.clojure/tools.cli "0.3.5"]

	[opencv/opencv "3.3.0-rc"]
	[opencv/opencv-native "3.3.0-rc" :classifier "osx"]
	[opencv/opencv-native "3.3.0-rc" :classifier "linux"]
	[opencv/opencv-native "3.3.0-rc" :classifier "win"]

    [org.clojure/clojure "1.8.0"]])

(task-options!
   repl {
   		 ;:eval  (clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
         ;; my user ns in dev folder
         ; :init-ns 'user
         ;; skip the Boot automatic ns injection, also called `user`
         :skip-init true})
