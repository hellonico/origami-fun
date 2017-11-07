import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Date;

import static java.lang.System.loadLibrary;
import org.opencv.videoio.Videoio;

class MatPanel extends JPanel {
    public Mat mat;

    @Override
    public void paint(Graphics g) {
        g.drawImage(WebcamExample.MatToBufferedImage(mat), 0, 0, this);
    }
}

public class WebcamExample {
    static {
        loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
      // sample01();
      sample02();
    }

    static void sample01() {
      do_still_captures(10,1,0);
    }

    static void do_still_captures(int frames, int lapse, int camera_id) {
      VideoCapture camera = new VideoCapture(camera_id);
      camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 320);
      camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 240);

      Mat frame = new Mat();
      for(int i = 0 ; i <frames;i++) {
          if (camera.read(frame)){
            String filename = "video/"+new Date()+".jpg";
            Imgcodecs.imwrite(filename, frame);
            try {Thread.sleep(lapse*1000);} catch (Exception e) {e.printStackTrace();}
          }
      }
      camera.release();
    }


    static void sample02() {


      MatPanel t = new MatPanel();
      JFrame frame0 = new JFrame();
      frame0.getContentPane().add(t);
      frame0.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame0.setSize(320, 240);
      frame0.setVisible(true);
      frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      VideoCapture camera = new VideoCapture(0);
      camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 320);
      camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 240);
      Mat frame = new Mat();
      while(true){
          if (camera.read(frame)){
              Imgproc.cvtColor(frame,frame, Imgproc.COLOR_RGB2GRAY);
              Mat target = new Mat();
              Imgproc.Canny(frame,target,100.0,150.0,3,true);
              t.mat=target;
              t.repaint();
          }
      }
    }

    public static BufferedImage MatToBufferedImage(Mat frame) {
        int type = 0;
        if(frame==null) return null;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);
        return image;
    }
}
