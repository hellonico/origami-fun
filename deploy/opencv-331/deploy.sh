# opencv core

mvn deploy:deploy-file -DgroupId=opencv \
-DartifactId=opencv \
-Dversion=3.3.1 \
-Dpackaging=jar \
-Dfile=opencv-331.jar \
-DrepositoryId=vendredi \
-Durl=http://hellonico.info:8081/repository/hellonico/

# opencv native
mvn deploy:deploy-file -DgroupId=opencv \
-DartifactId=opencv-native \
-Dversion=3.3.1 \
-Dfile=opencv-native.jar \
-Dclassifiers=osx,linux,windows \
-Dfiles=opencv-native-osx.jar,opencv-native-linux.jar,opencv-native-windows.jar \
-Dtypes=jar,jar,jar \
-Dpackaging=jar \
-DrepositoryId=vendredi \
-Durl=http://hellonico.info:8081/repository/hellonico/