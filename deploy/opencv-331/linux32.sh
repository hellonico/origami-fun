# opencv core
mvn deploy:deploy-file -DgroupId=opencv \
-DartifactId=opencv-native-linux32 \
-Dversion=3.3.1 \
-Dpackaging=jar \
-Dfile=opencv-native-linux32.jar \
-DrepositoryId=vendredi \
-Durl=http://hellonico.info:8081/repository/hellonico/
