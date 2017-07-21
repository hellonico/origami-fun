This is an opencv generated wrapper for Clojure which allows some of the opencv code to be written in a concise way.

```
(->
 (imread "resources/images/cat.jpg")
 (cvt-color! COLOR_RGB2GRAY)
 (canny! 300.0 100.0 3 true)
 (bitwise-not!)
 (imwrite "output/canny-cat.jpg"))
```
