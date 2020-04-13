//TO CROP CHARACTER FROM NUMBER PLATE
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import com.sun.media.jai.widget.DisplayJAI;

/**
* This class is used crop separate characters from number plate in PNG format and store in MATRIX.
* @author Nikhil Kansari,Larenge Kamal,Abhinav Bajaj,Pranjal Goel
* @version 1.2
*/
public class Crop
{static float x,y,width,height;
static float x1,y1,width1,height1;
static String number,number2,number3;
	static int [][] a = new int[20][2];//stores vertical values
	static int [][] b = new int[20][2];//stores horizontal values
	static int size = 0;
	static String s = "cropped";
	
/**
* This method is main function to separate characters from number plate.
* @param imname It is the name of number plate. 
* @return String It is the number recognized from number plate.
*/ 
public static String doer(String imname)
{
// We need five arguments: the image filename and four values that
// define the area for cropping: x, y, width and height.
/*if (args.length != 4)
{
System.err.println("Usage: java operators.Crop image x y "+
"width height");
System.exit(0);
}*/
// Parse the parameters.
	System.out.println("Inside Doer");
	//System.out.println(a[0][0]);
	//b[0][0]=5;
	//b[0][1]=70;
//	 Read the number plate
	PlanarImage input = JAI.create("fileload", imname);
	
	for(int i=0;i<size;i++)
	{	
	x = a[i][0];
	 y = 0;
	 width = (a[i][1] - a[i][0]);
	 height = input.getHeight();
//	 Read the image.
	
//	 Create a ParameterBlock with information for the cropping.
	ParameterBlock pb = new ParameterBlock();
	pb.addSource(input);
	pb.add(x);
	pb.add(y);
	pb.add(width);
	pb.add(height);
//	 Create the output image by cropping the input image.
	PlanarImage output = JAI.create("crop",pb,null);
	//JAI.create("filestore",output,"crop.jpg","JPEG");
	//PlanarImage output1 = JAI.create("crop",pb,null);
	
//	 A cropped image will have its origin set to the (x,y) coordinates,
//	 and with the display method we use it will cause bands on the top
//	 and left borders. A simple way to solve this is to shift or
//	 translate the image by (-x,-y) pixels.
	pb = new ParameterBlock();
	pb.addSource(output);
	pb.add(-x);
	pb.add(-y);
//	 Create the output image by translating itself.
	PlanarImage output1 = JAI.create("translate",pb,null);
	JAI.create("filestore",output1,s+i+".png","PNG");
//	 Create a JFrame for displaying the results.
	/*JFrame frame = new JFrame();
	//frame.setTitle("Cropping image "+args[0]+
	//" starting at ("+x+","+y+"), size "+width+" x "+height);
//	 Add to the JFrame's ContentPane an instance of DisplayJAI with the
//	 processed image.
	frame.getContentPane().add(new JScrollPane(new DisplayJAI(output1)));
//	 Save the image on a file.

//	 Set the closing operation so the application is finished.
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack(); // adjust the frame size using preferred dimensions.
	frame.setVisible(true); // show the frame.}*/
//	 Create a ParameterBlock with information for the cropping.
	
//	for(int i=0;i<size;i++)
//{
	

	}
/*
// Create a JFrame for displaying the results.
JFrame frame = new JFrame();
//frame.setTitle("Cropping image "+args[0]+" starting at ("+x+","+y+"), size "+width+" x "+height);
// Add to the JFrame's ContentPane an instance of DisplayJAI with the
// processed image.
frame.getContentPane().add(new JScrollPane(new DisplayJAI(output)));

// Save the image on a file.

// Set the closing operation so the application is finished.

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.pack(); // adjust the frame size using preferred dimensions.
frame.setVisible(true); // show the frame.*/
	b = ImageInfo1.horizontalcoordinatesSender();
	number2 = horizontalClipper(imname);
	return number2;
}
/**
* This method is used to clip in horizontal from number plate.
* @param imname It is the name of number plate. 
* @return String It is the number recognized from number plate.
*/ 
public static String horizontalClipper(String imname)
{
	for(int i=0;i<size;i++)
	{
		PlanarImage input = JAI.create("fileload", s+i+".png");
		x1 = 0;
		 y1 = b[i][0];
		 width1 = input.getWidth();
		 height1 = (b[i][1] - b[i][0]);
		 ParameterBlock pb = new ParameterBlock();
			pb.addSource(input);
			pb.add(x1);
			pb.add(y1);
			pb.add(width1);
			pb.add(height1);
			PlanarImage output = JAI.create("crop",pb,null);
			pb = new ParameterBlock();
			pb.addSource(output);
			pb.add(-x1);
			pb.add(-y1);
			PlanarImage output1 = JAI.create("translate",pb,null);
			JAI.create("filestore",output1,s+i+".png","PNG");
			
			//CALLING NUMBER RECOGNIZER
			
	}
	number = numberId.number(imname);
	return number;
	//matrixsaver();
}
/**
* This method is used to separate characters from number plate and store in PNG form and store in matrix.
* @param imname It is the name of number plate. 
* @return pix It is array of separated characters.TYPE :int[][][]
*/ 
public static int[][][] matrixsaver(String imname)
{System.out.println("Inside matrixsaver");
a =   ImageInfo1.xysender();
b = ImageInfo1.horizontalcoordinatesSender();

 size = ImageInfo1.sizesender();
 int pix[][][] = new int[size][(b[0][1]-b[0][0])*2][(a[0][1]-a[0][0])*2];
 PlanarImage pi = JAI.create("fileload", imname);
	File image = new File(imname);
	int width = pi.getWidth();
	int height = pi.getHeight();
	SampleModel sm = pi.getSampleModel();
	int nbands = sm.getNumBands();
	
	 
	 Raster inputRaster = pi.getData();
	 WritableRaster outputRaster = inputRaster.createCompatibleWritableRaster();
	 int[] pixels = new int[nbands*width*height];
	 inputRaster.getPixels(0,0,width,height,pixels);
	 int offset;
 for(int i=0;i<size;i++)
 {
	
	//System.out.println("hulululu");
	
	
	 int verdiff = (b[i][1]-b[i][0])+1;
	 int hordiff = (a[i][1]-a[i][0])+1;
	 for(int h=b[i][0],h1=0;h<=b[i][1];h++,h1++)
	 {  for(int w=a[i][0],w1=0;w<=a[i][1];w++,w1++)
	 {
		 offset = h*width*nbands+w*nbands;
		 pix[i][h1][w1] = pixels[offset];
		 
	 }}
 	 
	 for(int s=0;s<size;s++)
	 {
	 for(int h=0;h<verdiff;h++)
	 {
		 for(int w=0;w<hordiff;w++) 
		 {
			System.out.print(pix[s][h][w]); 
		 }
		 }
	 System.out.println('\n');}
	 System.out.println("nbands= "+nbands);
 }
 return pix;}


/**
* This method is used to get XY coordinates of separated characters ('a' for horizontal,'b' for vertical)
* @param imname It is the name of number plate. 
* @return String It is the number recognized from number plate.
*/ 	
public static String xygetter(String imname)
{
	a =   ImageInfo1.xysender();
	size = ImageInfo1.sizesender();
	 // System.out.println(a[0][1]);
	number3 = doer(imname);
	return number3;
}

}
