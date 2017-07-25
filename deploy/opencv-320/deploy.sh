
  mvn deploy:deploy-file -DgroupId=opencv \
  -DartifactId=opencv \
  -Dversion=3.2.0 \
  -Dpackaging=jar \
  -Dfile=opencv-320.jar \
  -DrepositoryId=vendredi \
  -Durl=http://hellonico.info:8081/repository/hellonico/

  # mvn deploy:deploy-file -DgroupId=opencv \
  # -DartifactId=opencv-native \
  # -Dversion=3.2.0-osx \
  # -Dpackaging=jar \
  # -Dfile=opencv-native-osx-320.jar \
  # -DrepositoryId=vendredi \
  # -Durl=http://hellonico.info:8081/repository/hellonico/
