
  # mvn deploy:deploy-file -DgroupId=opencv \
  # -DartifactId=opencv \
  # -Dversion=3.3.0-rc \
  # -Dpackaging=jar \
  # -Dfile=opencv.jar \
  # -DrepositoryId=vendredi \
  # -Durl=http://hellonico.info:8081/repository/hellonico/


  # mvn deploy:deploy-file -DgroupId=opencv \
  # -DartifactId=opencv-native \
  # -Dversion=3.3.0-rc-linux \
  # -Dpackaging=jar \
  # -Dfile=opencv-native-linux.jar \
  # -DrepositoryId=vendredi \
  # -Durl=http://hellonico.info:8081/repository/hellonico/

  mvn deploy:deploy-file -DgroupId=opencv \
  -DartifactId=opencv-native \
  -Dversion=3.3.0-rc-win \
  -Dpackaging=jar \
  -Dfile=opencv-native-windows.jar \
  -DrepositoryId=vendredi \
  -Durl=http://hellonico.info:8081/repository/hellonico/
