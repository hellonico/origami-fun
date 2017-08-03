
  # opencv core

  # mvn deploy:deploy-file -DgroupId=opencv \
  # -DartifactId=opencv \
  # -Dversion=3.3.0-rc \
  # -Dpackaging=jar \
  # -Dfile=opencv.jar \
  # -DrepositoryId=vendredi \
  # -Durl=http://hellonico.info:8081/repository/hellonico/

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

  # mvn deploy:deploy-file -DgroupId=opencv \
  # -DartifactId=opencv-native \
  # -Dversion=3.3.0-rc \
  # -Dclassifiers=linux \
  # -Dpackaging=jar \
  # -Dfile=opencv-native-linux.jar \
  # -DrepositoryId=vendredi \
  # -Durl=http://hellonico.info:8081/repository/hellonico/

  # # windows
  # mvn deploy:deploy-file -DgroupId=opencv \
  # -DartifactId=opencv-native \
  # -Dversion=3.3.0-rc \
  # -Dpackaging=jar \
  # -Dclassifiers=win
  # -Dfile=opencv-native-windows.jar \
  # -DrepositoryId=vendredi \
  # -Durl=http://hellonico.info:8081/repository/hellonico/
