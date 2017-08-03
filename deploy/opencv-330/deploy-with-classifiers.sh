
mvn deploy:deploy-file -DgroupId=opencv \
-DartifactId=opencv-native \
-Dversion=3.3.0-rc \
-Dfile=opencv.jar \
-Dclassifiers=osx,linux,win \
-Dfiles=opencv-native-osx.jar,opencv-native-linux.jar,opencv-native-windows.jar \
-Dtypes=jar,jar,jar \
-Dpackaging=jar \
-DrepositoryId=vendredi \
-Durl=http://hellonico.info:8081/repository/hellonico/