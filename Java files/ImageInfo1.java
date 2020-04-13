//TO SEPARATE CHARACTERS FROM NUMBER PLATE
import java.awt.Transparency; 
import java.awt.image.*;
 import java.io.File;
 import javax.media.jai.*; 
/**
* This class is used crop separate characters from number plate in PNG format and store in MATRIX.
* @author Nikhil Kansari,Larenge Kamal,Abhinav Bajaj,Pranjal Goel
* @version 1.2
*/
public class ImageInfo1
 {static int f=0,f1=0;
	static int[][] b = new int[20][2];
	static int[][] c = new int[20][2];
	static String s = "cropped"; 
	static String number;
	
/**
* This method is used to send array 'b'(which stores horizontal values) to "Crop.java".
* @param void 
* @return b Array which stores horizontal values of characters.
*/ 
	public static int[][] xysender()
	 {return b;
		}
/**
* This method is used to send  size of character to "Crop.java".
* @param void
* @return f size of characters.
*/ 	
	public static int sizesender()
	 {return f;
		 }
	static int flagb1,flag1,ww1,ww2;
/**
* This method is used to get number from number plate .It calls various other functions for number recognition from plate.
* @param imname It is name of the number plate. 
* @return number It is the number(String) recognized from number plate.
*/ 	
 public static String getnumber(String imname)
 {
int[] a = new int[256];

int w1 = 0,w2 = 0,flag = 0,flagb = 0;// flag checks first black pixel; flagb checks for atleast a single black pixel in each column
for(int i=0;i<256;i++)
a[i] = 0;
 // Open the image (using the name passed as a command line parameter)
 PlanarImage pi = JAI.create("fileload", imname);
 // Get the image file size (non-JAI related).
 File image = new File(imname);
 System.out.println("Image file size: "+image.length()+" bytes.");
 // Show the image dimensions and coordinates.
 System.out.print("Dimensions: ");

 System.out.print(pi.getWidth()+"x"+pi.getHeight()+" pixels");
 // Remember getMaxX and getMaxY return the coordinate of the next point!
System.out.println(" (from "+pi.getMinX()+","+pi.getMinY()+" to " +(pi.getMaxX()-1)+","+(pi.getMaxY()-1)+")");


int width = pi.getWidth();
 int height = pi.getHeight();
 SampleModel sm = pi.getSampleModel();
 int nbands = sm.getNumBands();
 Raster inputRaster = pi.getData();
 WritableRaster outputRaster = inputRaster.createCompatibleWritableRaster();
 int[] pixels = new int[nbands*width*height];
 inputRaster.getPixels(0,0,width,height,pixels);
 int offset;
 for(int w=0;w<width;w++)
 {flagb = 0;
 for(int h=0;h<height;h++)
 {
	 offset = h*width*nbands+w*nbands;
 

a[pixels[offset]] = a[pixels[offset]]+1;
    if(pixels[offset]<50)
     {flagb = 1;
    	if(flag==0)
         {w1 = w;
          flag = 1;
         }
     
    	w2 = w;
	    }
    
    
} 
	 if(flagb==0 && flag==1)
	 {
		 
		b[f][0] = w1;
		b[f][1] = w2;
		f++;
		flag = 0;
		flagb = 0;
		
	 }
}
 
 
 
for(int i=0;i<256;i++)
{System.out.print(i+" : ");
  for(int j=0;j<a[i];j++)
   {System.out.print("*");
}
System.out.print(" "+a[i]);
System.out.print("\n");}

System.out.print("\n");
//System.out.print(w1);
//System.out.print(w2);
for(int i=0;i<10;i++)
{for(int j=0;j<2;j++)
  {System.out.print(b[i][j]);
  System.out.println("\t");
  }
System.out.print("\n");
}
System.out.println("The No. of Characters are = "+f);
number = Crop.xygetter(imname);
return number;

}
/**
* This method handles mouse event and checks which button(eg .open) is pressed.
* @param void
* @return c Array which stores horizontal values of separated characters.
*/  
public static int[][] horizontalcoordinatesSender()
{
System.out.println("Inside horizontalcoordinatesSender()");
	for(int p=0;p<f;p++)
	{   System.out.println("Inside loop");
		PlanarImage pi = JAI.create("fileload", s+p+".png");
		File image = new File(s+p+".png");
		int width = pi.getWidth();
		 int height = pi.getHeight();
		 SampleModel sm = pi.getSampleModel();
		 int nbands = sm.getNumBands();
		 Raster inputRaster = pi.getData();
		 WritableRaster outputRaster = inputRaster.createCompatibleWritableRaster();
		 int[] pixels = new int[nbands*width*height];
		 inputRaster.getPixels(0,0,width,height,pixels);
		 int offset;
		 for(int h=0;h<height;h++)
		 {flagb1 = 0;
			 for(int w=0;w<width;w++) 
			 {
				 offset = h*width*nbands+w*nbands;
				 if(pixels[offset]<50)
			     {flagb1 = 1;
			    	if(flag1==0)
			         {ww1 = h;
			          flag1 = 1;
			         }
			     
			    	ww2 = h;
				    }
			 }
			 
			 if(flagb1==0 && flag1==1)
			 {
				 
				c[f1][0] = ww1;
				c[f1][1] = ww2;
				f1++;
				flag1 = 0;
				flagb1 = 0;
				
			 }
		 }
		 
	}
	
	
	
	/*for(int i=0;i<f1;i++)
	{
		System.out.println(c[i][0]);
		//System.out.println("\n");
		System.out.println(c[i][1]);
	}*/
	return c;
	}
 
}
