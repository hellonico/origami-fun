# wrong method in mean!

(defn mean!
([org_opencv_core_mat_0 ] 
  (Core/mean org_opencv_core_mat_0 org_opencv_core_mat_0 ) org_opencv_core_mat_0 )
; ([org_opencv_core_mat_0 ] 
;   (Core/mean org_opencv_core_mat_0 ) org_opencv_core_mat_0 )
)

# missing method

(defn in-range! 
  ([org_opencv_core_mat_0 org_opencv_core_scalar_1 org_opencv_core_scalar_2] 
  (Core/inRange org_opencv_core_mat_0 org_opencv_core_scalar_1 org_opencv_core_scalar_2 org_opencv_core_mat_0 )
  ))


# renaming
:rename '{capitalize cap, trim trm}

WARNING: min already refers to: #'clojure.core/min in namespace: opencv3.core, being replaced by: #'opencv3.core/min
WARNING: max already refers to: #'clojure.core/max in namespace: opencv3.core, being replaced by: #'opencv3.core/max
WARNING: compare already refers to: #'clojure.core/compare in namespace: opencv3.core, being replaced by: #'opencv3.core/compare
WARNING: merge already refers to: #'clojure.core/merge in namespace: opencv3.core, being replaced by: #'opencv3.core/merge
WARNING: sort already refers to: #'clojure.core/sort in namespace: opencv3.core, being replaced by: #'opencv3.core/sort
WARNING: reduce already refers to: #'clojure.core/reduce in namespace: opencv3.core, being replaced by: #'opencv3.core/reduce
WARNING: repeat already refers to: #'clojure.core/repeat in namespace: opencv3.core, being replaced by: #'opencv3.core/repeat
WARNING: reduce already refers to: #'clojure.core/reduce in namespace: opencv3.ok, being replaced by: #'opencv3.core/reduce
WARNING: sort already refers to: #'clojure.core/sort in namespace: opencv3.ok, being replaced by: #'opencv3.core/sort
WARNING: min already refers to: #'clojure.core/min in namespace: opencv3.ok, being replaced by: #'opencv3.core/min
WARNING: merge already refers to: #'clojure.core/merge in namespace: opencv3.ok, being replaced by: #'opencv3.core/merge
WARNING: max already refers to: #'clojure.core/max in namespace: opencv3.ok, being replaced by: #'opencv3.core/max
WARNING: compare already refers to: #'clojure.core/compare in namespace: opencv3.ok, being replaced by: #'opencv3.core/compare
WARNING: repeat already refers to: #'clojure.core/repeat in namespace: opencv3.ok, being replaced by: #'opencv3.core/repeat