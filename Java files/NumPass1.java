// MAIN CLASS OF NUMBER PLATE RECOGNITION SYSTEM
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Transparency;
 import java.awt.image.*;
 import java.io.File;
 import javax.media.jai.*;
import java.awt.image.renderable.*;
import javax.media.jai.iterator.*;
import java.lang.Math;

/**
* This class is the main class of Number plate recognition .It calls all other methods .It uses JPanel for GUI.
* @author Nikhil Kansari,Larenge Kamal,Abhinav Bajaj,Pranjal Goel
* @version 1.2
*/
public class NumPass1 extends JPanel implements ActionListener {
  static private final String newline = "\n";

  JButton openButton, extractButton;
  static int flag=0; 
  JTextArea log;
   File file;
  JFileChooser fc;
/**
* This is constructor of NumPass1 class It creates panel and inserts Buttons and text area .
*/
  public NumPass1() {
    super(new BorderLayout());

    //Create the log first, because the action listeners
    //need to refer to it.
    log = new JTextArea(20, 80);
    log.setMargin(new Insets(5, 5, 5, 5));
    log.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane(log);

    //Create a file chooser
    fc = new JFileChooser();

    //Uncomment one of the following lines to try a different
    //file selection mode. The first allows just directories
    //to be selected (and, at least in the Java look and feel,
    //shown). The second allows both files and directories
    //to be selected. If you leave these lines commented out,
    //then the default mode (FILES_ONLY) will be used.
    //
    //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    //Create the open button. We use the image from the JLF
    //Graphics Repository (but we extracted it from the jar).
    openButton = new JButton("Open a File");
    openButton.addActionListener(this);
    
    extractButton = new JButton("Extract number");
    extractButton.addActionListener(this);
    

    //For layout purposes, put the buttons in a separate panel
    JPanel buttonPanel = new JPanel(); //use FlowLayout
    buttonPanel.add(openButton);
    //buttonPanel.add(saveButton);
    JPanel buttonPane2 = new JPanel(); //use FlowLayout
    buttonPane2.add(extractButton);
    //Add the buttons and the log to this panel.
    add(buttonPanel, BorderLayout.PAGE_START);
    add(buttonPane2, BorderLayout.PAGE_END);
    add(logScrollPane, BorderLayout.CENTER);
  }
/**
* This method handles mouse event and checks which button(eg .open) is pressed.
* @param e It is Object of ActionEvent to check which button(eg .open) is pressed . 
* @return void
*/ 
  public void actionPerformed(ActionEvent e) {

    //Handle open button action.
    if (e.getSource() == openButton) {
      int returnVal = fc.showOpenDialog(NumPass1.this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        file = fc.getSelectedFile();
        //This is where a real application would open the file.
        log.append("Opening: " + file.getName() + "." + newline);
        
        //////***************SOURCE CODE*************//////////////
        PlanarImage pi = JAI.create("fileload", file.getName());
       Viewer.disp(file.getName());
         // Get the image file size (non-JAI related).
        File image = new File(file.getName());
          System.out.println("Image file size: "+image.length()+" bytes.");
       log.append("Image file size: "+image.length()+" bytes."+newline);
       // Show the image dimensions and coordinates.
          System.out.print("Dimensions: ");
       log.append("Dimensions: ");
          System.out.print(pi.getWidth()+"x"+pi.getHeight()+" pixels");
       log.append(pi.getWidth()+"x"+pi.getHeight()+" pixels");
       // Remember getMaxX and getMaxY return the coordinate of the next point!
          System.out.println(" (from "+pi.getMinX()+","+pi.getMinY()+" to " +(pi.getMaxX()-1)+","+(pi.getMaxY()-1)+")");
       log.append(" (from "+pi.getMinX()+","+pi.getMinY()+" to " +(pi.getMaxX()-1)+","+(pi.getMaxY()-1)+")"+newline);
       log.append("Press Extract Button to get number "+newline );  
       
       //System.exit(0);                      
       flag=1;
       //frame.setVisible(false);
       
       /////////////*********ssssssssssssssss***************////////////////
      } else {
        log.append("Open command cancelled by user." + newline);
        flag=1;
      }
      log.setCaretPosition(log.getDocument().getLength());

      
    }
    //Handle Extract Button
    if (e.getSource() == extractButton && flag==1) {
      //int returnVal = fc.showOpenDialog(NumPass1.this);

      //if (returnVal == JFileChooser.APPROVE_OPTION) 
      // 
        //File file = fc.getSelectedFile();
        //This is where a real application would open the file.
       log.append("Extracting Number from: " + file.getName() + "." + newline);
      
        // function to extract number from image of car
       extractchar.extract(file.getName(),log);
       //function to get number
       String name =ImageInfo1.getnumber("cropinjpg0.jpg");
       log.append("Number Plate: " +name);
         flag=0;
       //System.exit(0);                                           
       } 
       
         log.setCaretPosition(log.getDocument().getLength());                                            
       
  }
/**
* This method Returns an ImageIcon, or null if the path was invalid.
* @param path It is path of the file . 
* @return ImageIcon It returns ImageIcon or null
*/ 
 
  protected static ImageIcon createImageIcon(String path) {
    java.net.URL imgURL = NumPass1.class.getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
/**
* This method Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
* @param void  
* @return void 
*/ 
 private static void createAndShowGUI() {
    //Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);

    //Create and set up the window.
    JFrame frame = new JFrame("Number Plate Recognition");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Create and set up the content pane.
    JComponent newContentPane = new NumPass1();
    newContentPane.setOpaque(true); //content panes must be opaque
    frame.setContentPane(newContentPane);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }
/**
* This is the main function of extractchar class .It calls createAndShowGUI() function .
* @param args command line argument . It is of no use .   
* @return void 
*/
  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
}