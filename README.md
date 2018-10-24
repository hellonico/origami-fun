This is the playground for [origami](https://github.com/hellonico/origami), an opencv generated wrapper for Clojure which allows some of the opencv code to be written in a concise way.

```
(require
  '[opencv4.utils :as u]
  '[opencv4.core :refer :all])

(->
 (imread "resources/images/cat.jpg")
 (cvt-color! COLOR_RGB2GRAY)
 (canny! 300.0 100.0 3 true)
 (bitwise-not!)
 (u/resize-by 0.5)
 (imwrite "output/canny-cat.jpg"))
```

![](output/canny-cat.jpg)