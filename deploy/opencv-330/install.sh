mvn install:install-file \
-DgroupId=opencv \
-DartifactId=opencv \
-Dversion=3.3.0-rc \
-Dpackaging=jar \
-Dfile=opencv.jar

mvn install:install-file \
-DgroupId=opencv \
-DartifactId=opencv-native \
-Dversion=3.3.0-rc \
-Dpackaging=jar \
-Dfile=opencv-native.jar
