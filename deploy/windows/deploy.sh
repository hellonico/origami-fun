
  mvn deploy:deploy-file -DgroupId=opencv \
  -DartifactId=opencv \
  -Dversion=3.2.0 \
  -Dpackaging=jar \
  -Dfile=opencv.jar \
  -DrepositoryId=vendredi \
  -Durl=http://hellonico.info:8081/repository/hellonico/

  mvn deploy:deploy-file -DgroupId=opencv \
  -DartifactId=opencv-native \
  -Dversion=3.2.0-windows-x64 \
  -Dpackaging=jar \
  -Dfile=opencv-native-windows.jar \
  -DrepositoryId=vendredi \
  -Durl=http://hellonico.info:8081/repository/hellonico/
