// Extract charcter from image.
import java.awt.Transparency;
 import java.awt.image.*;
 import java.io.*;
 import javax.media.jai.*;
import java.awt.image.renderable.*;
import javax.media.jai.iterator.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import com.sun.media.jai.widget.DisplayJAI;
import java.lang.Math;
import javax.imageio.*;
import javax.swing.*;

/**
* This class Extract Number plate from image of Car.This class contains 2 functions (1) cropimage() function to crop Number plate from car (2)extract() function for number plate detection and cropping .
* @author Nikhil Kansari,Larenge Kamal,Abhinav Bajaj,Pranjal Goel 
* @version 1.2
*/
 public class extractchar
 {
 static int num=0;
/**
* This method Crops image generally rectangular area from image of car .It calls magnify()function of dips class .
* @param input It is Object of PlanarImage of input image to be cropped .
* @param x It is left most X coordinate of area to be cropped .  
* @param y It is left most Y coordinate of area to be cropped .
* @param width It is width of area to be cropped .
* @param height It is height of area to be cropped .
* @return void
*/
public static void cropimage(PlanarImage input,float x,float y,float width,float height)
{   String s,s1,s2;
    int factor;
    ParameterBlock pb = new ParameterBlock();
    pb.addSource(input);
    pb.add(x);
    pb.add(y);
    pb.add(width);
    pb.add(height);
    // Create the output image by cropping the input image.
    PlanarImage output = JAI.create("crop",pb,null);
    // A cropped image will have its origin set to the (x,y) coordinates,
    // and with the display method we use it will cause bands on the top
    // and left borders. A simple way to solve this is to shift or
    // translate the image by (-x,-y) pixels.
    pb = new ParameterBlock();
    pb.addSource(output);
    pb.add(-x);
    pb.add(-y);
    // Create the output image by translating itself.
    output = JAI.create("translate",pb,null);
    s="crop"+num+".png";
    s1="cropinjpg"+num+".jpg";
    s2="cropinjpgmag"+num+".jpg";
    JAI.create("filestore",output,s,"PNG");
    //To read the image, simply provide the ImageIO.read() method a File object for the source image. This will return a BufferedImage.
    //Create file for the source
    // ******************* CONVERTING PNG TO JPEG ***************************
    try {
     File input1 = new File(s);
    //Read the file to a BufferedImage
     BufferedImage image1 = ImageIO.read(input1);
     //Once you have the BufferedImage, you can write the image as a JPG. You will need to create a File object for the destination image. When calling the write() method, specify the type string as "jpg".
     //Create a file for the output
     File output2 = new File(s1);
     //Write the image to the destination as a JPG
     ImageIO.write(image1, "jpg", output2);
       }
       catch (IOException e) {
    }
    // ******************* CONVERTING PNG TO JPEG END ***************************
    num++;
    PlanarImage pi2 = JAI.create("fileload", s1);
    System.out.print(pi2.getWidth()+"x"+pi2.getHeight()+" pixels");
    factor=(int)(538/pi2.getWidth());
    /* if(pi2.getWidth()<528)
      {PlanarImage pi3 =MagnificationInterpolation.magnify(s1);
       PlanarImage pi4 =dips.thresholding(pi3,s1,50,s1);
      }*/
    // Create a JFrame for displaying the results.
    JFrame frame = new JFrame();
    //frame.setTitle("Cropping image "+args[0]+" starting at ("+x+","+y+"), size "+width+" x "+height);
    // Add to the JFrame's ContentPane an instance of DisplayJAI with the
    // processed image.
    frame.getContentPane().add(new JScrollPane(new DisplayJAI(output)));
    // Save the image on a file.
    
    // Set the closing operation so the application is finished.
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack(); // adjust the frame size using preferred dimensions.
    frame.setVisible(true); // show the frame.
}

/**
* This method defines how to find Number plate from image of car .It calls rgb2gray(pi,name),filter(pi,name),thresholding(pi,name) function of dips class and cropimage() function of same class .
* @param name It is name of input image in .JPG format.
* @param log It is object of JTextArea to write into window
* @return void
*/
public static void extract(String name,JTextArea log)
 {
PlanarImage pi = JAI.create("fileload", name);
File image = new File(name);
System.out.println("Image file size: "+image.length()+" bytes.");
System.out.print("Dimensions: ");
System.out.print(pi.getWidth()+"x"+pi.getHeight()+" pixels");
System.out.println(" (from "+pi.getMinX()+","+pi.getMinY()+" to " +(pi.getMaxX()-1)+","+(pi.getMaxY()-1)+")");
pi=dips.rgb2gray(pi,name);
pi=dips.filter(pi,name);
pi=dips.thresholding(pi,name,128,"threshold.jpg");
//pi = JAI.create("fileload", "threshold.jpg");
System.out.println("*****************EXTRACTING NUMBER PLATE***************************\n");
log.append("*****************EXTRACTING NUMBER PLATE***************************\n");
int width = pi.getWidth();
int height = pi.getHeight();
SampleModel sm = pi.getSampleModel();
int nbands = sm.getNumBands();
Raster inputRaster = pi.getData();
int[] pixels = new int[nbands*width*height];
inputRaster.getPixels(0,0,width,height,pixels);
int offset,counthp=0,countvp=0,counthn=0,countvn=0,imaspect=0;
int[][] widthm = new int[80][3];
int widthc=0,x1,y1,width1,height1,aspect,diff,diff_pix=0,diff2,x2,y2,x11,y11,x22,y22;
int hei1,hei2,diff_height;
// CHECKING FOR RECTANGLE
for(int h=5;h<height;h++)
{
counthp=0;
for(int w=5;w<width;w++)
{
	
offset = h*width*nbands+w*nbands;
if(pixels[offset]>=230) 
 {counthp++;
  //System.out.println("gray scal \n "+pixels[offset]);
 }
 else
 {   if(counthp>100)
       {      System.out.println("count hp is"+counthp+"at :h= "+h+"at w = "+w+"value of counter :: "+widthc);
              widthm[widthc][0]=h;
              widthm[widthc][1]=w;
              widthm[widthc][2]=counthp;
              widthc++;
              countvp=0;w=w-1; counthp=0;   

       } //if
       else
       {counthp=0;
       }
     
 }
 } //for 

       }  //for 
   for(int i=0;i<widthc;i++)
      System.out.println("value of width= "+widthm[i][2]);
   for(int i=0;i<widthc;i++)
      {
      for(int j=i;j<widthc;j++)
           {       
                 if(i!=j)
          			{  //System.out.println("inside widthc\n ");
          			     if(widthm[i][2]>widthm[j][2])
          			     diff_pix=widthm[i][2]-widthm[j][2];  //difference in width of 2 values
	  				        if(widthm[i][2]<widthm[j][2])
	  				        diff_pix=widthm[j][2]-widthm[i][2]; //difference in width of 2 values
	  				       
	  				       if(diff_pix>=0 && diff_pix <10)    //if of same width
	  				          {
		  				        System.out.println("diff in pixel = "+diff_pix);      
	  					          diff=diff_pix*3;
	  							  x1=widthm[i][1]-widthm[i][2];
      							  y1=widthm[i][0];
      							  x2=widthm[j][1]-widthm[j][2];
      							  y2=widthm[j][0];
      							  if(x1>x2)
      							  diff2=x1-x2;
      							  else
      							  diff2=x2-x1;
      							  if(diff2>=0 && diff2 <15)    //if at same x coordinate
	  				          {
		  				          if(y1>y2)
		  				          {hei1=y2;hei2=y1;}
		  				          else
		  				          {hei1=y1;hei2=y2;}
		  				          System.out.println("x1 = "+x1+" y1= "+y1+" width1 ="+widthm[i][2]);
		  				          System.out.println("x2 = "+x2+" y2= "+y2+" width2 ="+widthm[j][2]);
		  				          /*countvp=0;
		  				          for(i=hei1;i<=hei2;i++)
		  				          {
			  				         offset = i*width*nbands+x1*nbands;
                                     if(pixels[offset]>=230) 
                                       countvp++;
                                     else
                                        break;  
 
		  				          }    
		  				          if(y1>y2)
      							  diff2=y1-y2;
      							  else
      							  diff2=y2-y1;
      							  if(diff2>countvp)
      							  diff_height=diff2-countvp;
      							  else
      							  diff_height=countvp-diff2;
      							  if(diff_height>=0 && diff_height <10)    //if vertical line
	  				          {*/
      							  width1=widthm[i][2];
      							  if(widthm[i][0]>widthm[j][0])
      							  height1=widthm[i][0]-widthm[j][0];
      							  else
      							  height1=widthm[j][0]-widthm[i][0];
      					          aspect=width1/height1;
      							  System.out.println("aspect ration is : "+aspect);
      
                                    if(aspect >4 && aspect <=7)
                                    //if(aspect >1)
                                         { System.out.println("**CROPPING IMAGE**\n ");
                                           System.out.println("aspect ration inside : "+aspect);
                                           System.out.println("coordinates are  : "+x1+" "+y1);
	     								   PlanarImage pi1 = JAI.create("fileload", name);
                                           cropimage(pi1,x1+diff+2,y1,width1-diff-6,height1);
                                           System.out.println("***********CROPPED IMAGE***********\n ");
                                         }
                                    
                            }         
                             }
                    }
                                      
        }       
   }

}
}