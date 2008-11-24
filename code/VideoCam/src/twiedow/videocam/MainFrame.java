

package twiedow.videocam;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;

import twiedow.videocam.filter.BlueboxFilter;


public class MainFrame extends JFrame {


  public final static String FILE_PATTERN = "(.)*[.]((jpg)|(JPG)|(png)|(PNG)|(bmp)|(BMP))";

  private VideoCamPanel      camPanel;
  private VideoCamPanel      camBlueboxPanel;
  private JLabel             inputLabel;
  private JLabel             outputLabel;
  private JLabel             backgroundLabel;
  private JLabel             blueboxLabel;
  private JButton            captureBckgrdImgBtn;
  private JButton            captureBlueboxImgBtn;
  private JButton            loadBlueboxImgBtn;
  private JFileChooser       fileChooser;
  private ImagePanel         bckgrdImgPanel;
  private ImagePanel         blueboxImgPanel;


  public MainFrame() {
    super("Video");
    setBounds(100, 100, 1024, 768);
    setResizable(false);
    setLayout(null);

    inputLabel = new JLabel("Input");
    inputLabel.setBounds(50, 10, 50, 20);
    add(inputLabel);

    outputLabel = new JLabel("Output");
    outputLabel.setBounds(500, 10, 50, 20);
    add(outputLabel);

    backgroundLabel = new JLabel("Background");
    backgroundLabel.setBounds(70, 320, 100, 20);
    add(backgroundLabel);

    blueboxLabel = new JLabel("Bluebox");
    blueboxLabel.setBounds(500, 320, 100, 20);
    add(blueboxLabel);

    try {
      camPanel = new VideoCamPanel(60, 40);
      camPanel.startCamera();
      add(camPanel);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    captureBckgrdImgBtn = new JButton("Capture Background");
    captureBckgrdImgBtn.setBounds(110, 620, 175, 50);
    captureBckgrdImgBtn.addActionListener(new ActionListener() {


      public void actionPerformed(ActionEvent e) {
        captureBackgroundImage();
      }
    });
    add(captureBckgrdImgBtn);

    captureBlueboxImgBtn = new JButton("Capture Bluebox");
    captureBlueboxImgBtn.setBounds(540, 620, 175, 50);
    captureBlueboxImgBtn.addActionListener(new ActionListener() {


      public void actionPerformed(ActionEvent e) {
        captureBlueboxImage();
      }
    });
    add(captureBlueboxImgBtn);

    loadBlueboxImgBtn = new JButton("Load Bluebox");
    loadBlueboxImgBtn.setBounds(740, 620, 175, 50);
    loadBlueboxImgBtn.addActionListener(new ActionListener() {


      public void actionPerformed(ActionEvent e) {
        loadBlueboxImage();
      }
    });
    add(loadBlueboxImgBtn);

    bckgrdImgPanel = new ImagePanel();
    bckgrdImgPanel.setBounds(60, 350, 320, 240);
    add(bckgrdImgPanel);

    fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setFileFilter(new FileFilter() {


      public boolean accept(File file) {
        return file.getPath().matches(FILE_PATTERN);
      }


      public String getDescription() {
        return "Images (jpg, bmp, png)";
      }
    });

    blueboxImgPanel = new ImagePanel();
    blueboxImgPanel.setBounds(510, 350, 320, 240);
    add(blueboxImgPanel);

    try {
      camBlueboxPanel = new VideoCamPanel(510, 40);
      camBlueboxPanel.startCamera();
      // camBlueboxPanel.addFilter(new GreyFilter());
      // camBlueboxPanel.addFilter(new InvertFilter());
      camBlueboxPanel.addFilter(new BlueboxFilter(bckgrdImgPanel, blueboxImgPanel));
      add(camBlueboxPanel);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void captureBackgroundImage() {
    try {
      bckgrdImgPanel.setImage(camPanel.getClonedCopyOfCurrentImage());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void captureBlueboxImage() {
    try {
      blueboxImgPanel.setImage(camPanel.getClonedCopyOfCurrentImage());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void loadBlueboxImage() {
    fileChooser.setSelectedFile(null);
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      try {
        blueboxImgPanel.setImage(ImageIO.read(fileChooser.getSelectedFile()));
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }


  public static void main(String[] args) {
    new MainFrame().setVisible(true);
  }

}
