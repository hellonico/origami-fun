Installing OpenCV

http://docs.opencv.org/3.1.0/d7/d9f/tutorial_linux_install.html

# git clone https://github.com/opencv/opencv.git
# mkdir build && cd build && cmake -D CMAKE_BUILD_TYPE=Release -D CMAKE_INSTALL_PREFIX=/usr/local ..
# make -j8
# copy bin/opencv.jar in the opencvlib folder


# 
# NikoMacBook% find . -name *java*.dylib 
# ./lib/libopencv_java330.dylib

```
mkdir -p native/macosx/x86_64
cp ./lib/libopencv_java330.dylib native/macosx/x86_64
jar -cMf opencv-native.jar native
```

lein localrepo install bin/opencv-330.jar opencv/opencv 3.3.0-rc
lein localrepo install opencv-native.jar opencv/opencv-native 3.3.0-rc



# where native folder is as below

# NicolassMacBook% tree opencvlib

# opencvlib
# ├── native
# │   └── macosx
# │       └── x86_64
# │           └── libopencv_java2413.dylib
# ├── opencv-2413.jar
# └── opencv-native-2413.jar

# 3 directories, 3 files

lein localrepo install opencvlib/opencv-2413.jar opencv/opencv 2.4.13
lein localrepo install opencvlib/opencv-native-2413.jar opencv/opencv-native 2.4.13


# notes

org.opencv.highgui.Highgui; to org.opencv.imgcodecs.Imgcodecs; ,
Highgui.imread() to imgcodecs.imread() ,
Core.rectangle() to imgproc.rectangle() ,
Highgui.imwrite() to imgcodecs.imwrite()
