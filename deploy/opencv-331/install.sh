mvn install:install-file \
-DgroupId=opencv \
-DartifactId=opencv \
-Dversion=3.3.1 \
-Dpackaging=jar \
-Dfile=opencv-331.jar

mvn install:install-file \
-DgroupId=opencv \
-DartifactId=opencv-native \
-Dversion=3.3.1 \
-Dpackaging=jar \
-Dfile=opencv-native.jar


mvn install:install-file  -DgroupId=opencv \
-DartifactId=opencv-native-osx-noffmpeg \
-Dversion=3.3.1 \
-Dpackaging=jar \
-Dfile=opencv-native-osx-noffmpeg.jar \
