/* 
 * Viewer.java
 */

import java.awt.*;
import java.awt.event.*;
/**
* This class is used for displaying image in frame.
* @author Nikhil Kansari,Larenge Kamal,Abhinav Bajaj,Pranjal Goel
* @version 1.2
*/
public class Viewer extends Frame {
	private Image image;
/**
* This is constructor of Viewer class
* @param fileName It is name of input image to be displayed . 
*/
	public Viewer(String fileName) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		image = toolkit.getImage(fileName);
		MediaTracker mediaTracker = new MediaTracker(this);
		mediaTracker.addImage(image, 0);
		try
		{
			mediaTracker.waitForID(0);
		}
		catch (InterruptedException ie)
		{
			System.err.println(ie);
			//System.exit(1);
		}
		addWindowListener(new WindowAdapter() {
      		public void windowClosing(WindowEvent e) {
        		//System.exit(0);
      		}
		});
		setSize(image.getWidth(null), image.getHeight(null));
		setTitle(fileName);
		show();
	}
/**
* This method draws image on frame.
* @param graphics It is Object of Graphics used for drawing image .
* @return void 
*/       
	public void paint(Graphics graphics) {
		graphics.drawImage(image, 0, 0, null);
	}
/**
* This method displays image on frame.
* @param imag It is name of image to be viewed .
* @return void 
*/ 	
	public static void disp(String imag) {
		//final JFileChooser fc = new JFileChooser();
		new Viewer(imag);
	}

	
}