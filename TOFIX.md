# wrong method in mean!

(defn mean!
([org_opencv_core_mat_0 ] 
  (Core/mean org_opencv_core_mat_0 org_opencv_core_mat_0 ) org_opencv_core_mat_0 )
; ([org_opencv_core_mat_0 ] 
;   (Core/mean org_opencv_core_mat_0 ) org_opencv_core_mat_0 )
)

# too long 


; (defn rectify-3-collinear
; ([org_opencv_core_mat_0 org_opencv_core_mat_1 org_opencv_core_mat_2 org_opencv_core_mat_3 org_opencv_core_mat_4 org_opencv_core_mat_5 java_util_list_6 java_util_list_7 org_opencv_core_size_8 org_opencv_core_mat_9 org_opencv_core_mat_10 org_opencv_core_mat_11 org_opencv_core_mat_12 org_opencv_core_mat_13 org_opencv_core_mat_14 org_opencv_core_mat_15 org_opencv_core_mat_16 org_opencv_core_mat_17 org_opencv_core_mat_18 org_opencv_core_mat_19 double_20 org_opencv_core_size_21 org_opencv_core_rect_22 org_opencv_core_rect_23 int_24 ]
;   (Calib3d/rectify3Collinear org_opencv_core_mat_0 org_opencv_core_mat_1 org_opencv_core_mat_2 org_opencv_core_mat_3 org_opencv_core_mat_4 org_opencv_core_mat_5 java_util_list_6 java_util_list_7 org_opencv_core_size_8 org_opencv_core_mat_9 org_opencv_core_mat_10 org_opencv_core_mat_11 org_opencv_core_mat_12 org_opencv_core_mat_13 org_opencv_core_mat_14 org_opencv_core_mat_15 org_opencv_core_mat_16 org_opencv_core_mat_17 org_opencv_core_mat_18 org_opencv_core_mat_19 double_20 org_opencv_core_size_21 org_opencv_core_rect_22 org_opencv_core_rect_23 int_24 ))
; )

; (defn rectify-3-collinear!
; ([org_opencv_core_mat_0 org_opencv_core_mat_2 org_opencv_core_mat_3 org_opencv_core_mat_4 org_opencv_core_mat_5 java_util_list_6 java_util_list_7 org_opencv_core_size_8 org_opencv_core_mat_9 org_opencv_core_mat_10 org_opencv_core_mat_11 org_opencv_core_mat_12 org_opencv_core_mat_13 org_opencv_core_mat_14 org_opencv_core_mat_15 org_opencv_core_mat_16 org_opencv_core_mat_17 org_opencv_core_mat_18 org_opencv_core_mat_19 double_20 org_opencv_core_size_21 org_opencv_core_rect_22 org_opencv_core_rect_23 int_24 ]
;   (Calib3d/rectify3Collinear org_opencv_core_mat_0 org_opencv_core_mat_0 org_opencv_core_mat_2 org_opencv_core_mat_3 org_opencv_core_mat_4 org_opencv_core_mat_5 java_util_list_6 java_util_list_7 org_opencv_core_size_8 org_opencv_core_mat_9 org_opencv_core_mat_10 org_opencv_core_mat_11 org_opencv_core_mat_12 org_opencv_core_mat_13 org_opencv_core_mat_14 org_opencv_core_mat_15 org_opencv_core_mat_16 org_opencv_core_mat_17 org_opencv_core_mat_18 org_opencv_core_mat_19 double_20 org_opencv_core_size_21 org_opencv_core_rect_22 org_opencv_core_rect_23 int_24 ) org_opencv_core_mat_0 )
; )


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