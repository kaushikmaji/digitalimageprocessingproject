//Class to convert image into GRAY SCALE AND MANY MORE
import java.awt.Transparency;
 import java.awt.image.*;
 import java.io.File;
 import javax.media.jai.*;
import java.awt.image.renderable.*;
/**
* This class implements various functions for example:: RGB to Gray scale conversion,Thresholding,Scaling,Edge detection.
* @author Nikhil Kansari,Larenge Kamal,Abhinav Bajaj,Pranjal Goel
* @version 1.2
*/
public class dips
 {
/**
* This method Converts RGB image into Gray scale and store image as "rgb2gray.jpg".
* @param pi It is Object of PlanarImage of input image
* @param fname It is name of image stored in .JPG extension 
* @return src It returns PlanarImage obect of the image converted to Gray scale .
*/
// FOR RGB TO GRAY CONVERSION	 
 public static  PlanarImage rgb2gray(PlanarImage pi,String fname)
 {System.out.println("\n***************RGB TO GRAY CONVERSION***************\n");
 // Get the image file size (non-JAI related).
 File image = new File(fname);
 System.out.println("Image file size: "+image.length()+" bytes.");
 // Show the image dimensions and coordinates.
 System.out.print("Dimensions: ");
 System.out.print(pi.getWidth()+"x"+pi.getHeight()+" pixels");
 // Remember getMaxX and getMaxY return the coordinate of the next point!
System.out.println(" (from "+pi.getMinX()+","+pi.getMinY()+" to " +(pi.getMaxX()-1)+","+(pi.getMaxY()-1)+")");
// typical weights for converting RGB to Grayscale
// gray = 0.3*red + 0.59*green + 0.11*blue
double[][] matrix = {{ 0.3D, 0.59D, 0.11D, 0D }};
ParameterBlock pb = new ParameterBlock();
pb.addSource(pi);
pb.add(matrix);
PlanarImage src = JAI.create("BandCombine", pb, null);
JAI.create("filestore",src,"rgb2gray.jpg","JPEG");
return src;
}
/**
* This method Scales/magnify the input image and store image as imag.
* @param imag It is name of input image file in .JPG extension before crop.
* @param out It is name of output image of file in .JPG extension after crop.
* @param factor It is scaling factor 
* @return void .
*/
// FOR SCALING IMAGE
public static void magnify(String imag,String out,float factor)
 {
 // Open the image (using the name passed as a command line parameter)
 PlanarImage pi = JAI.create("fileload", imag);
 // Get the image file size (non-JAI related).
 File image = new File(imag);
 System.out.println("Image file size: "+image.length()+" bytes.");
 // Show the image dimensions and coordinates.
 System.out.print("Dimensions: ");
 System.out.print(pi.getWidth()+"x"+pi.getHeight()+" pixels");
 // Remember getMaxX and getMaxY return the coordinate of the next point!
System.out.println(" (from "+pi.getMinX()+","+pi.getMinY()+" to " +
 (pi.getMaxX()-1)+","+(pi.getMaxY()-1)+")");

float scale = factor;
ParameterBlock pb = new ParameterBlock();
pb.addSource(pi);
pb.add(scale);
pb.add(scale);
pb.add(0.0f);
pb.add(0.0f);
pb.add(new InterpolationNearest());
PlanarImage scaledImage = JAI.create("scale", pb);

JAI.create("filestore",scaledImage,out,"JPEG");
}
/**
* This method thresholds the input image and is called from thresholding() ,store image as "threshold.jpg".
* @param pi It is Object of PlanarImage of input image .TYPE :PlanarImage 
* @param limit Threshold limit for thresholding .TYPE :int
* @param outfile It is name of output file to store threshold image .TYPE :String  
* @return res It returns PlanarImage obect of the image thresholded .
*/
// FOR THRESHOLDING
public static PlanarImage threshold(PlanarImage pi,int limit,String outfile)
{
int offset;
int width = pi.getWidth();
int height = pi.getHeight();
SampleModel sm = pi.getSampleModel();
 int nbands = sm.getNumBands();
Raster inputRaster = pi.getData();
WritableRaster outputRaster = inputRaster.createCompatibleWritableRaster();
int[] pixels = new int[nbands*width*height];
inputRaster.getPixels(0,0,width,height,pixels);
for(int h=0;h<height;h++)
{for(int w=0;w<width;w++)
 {
offset = h*width*nbands+w*nbands;
for(int band=0;band<nbands;band++)
{if (pixels[offset+band] <limit) 
{pixels[offset+band] = 0;}
else
{pixels[offset+band] = 255 ;}
}
}
}
outputRaster.setPixels(0,0,width,height,pixels);
 TiledImage ti = new TiledImage(pi,1,1);
 ti.setData(outputRaster);
PlanarImage res=JAI.create("filestore",ti,outfile,"JPEG");
return res;
}

/**
* This method thresholds the input image and store image as "threshold.jpg".
* @param pi It is Object of PlanarImage of input image
* @param fname It is name of image stored in .JPG extension
* @param limit Threshold limit for thresholding .TYPE :int
* @param outfile It is name of output file to store threshold image .TYPE :String   
* @return edge1 It returns PlanarImage obect of the image thresholded .
*/
// FOR THRESHOLDING
public static  PlanarImage thresholding(PlanarImage pi,String fname,int limit,String outfile)
 {
System.out.println("\n***************THRESHOLDING***************\n");
 File image = new File(fname);
 System.out.println("Image file size: "+image.length()+" bytes.");
 // Show the image dimensions and coordinates.
 System.out.print("Dimensions: ");
 System.out.print(pi.getWidth()+"x"+pi.getHeight()+" pixels");
 // Remember getMaxX and getMaxY return the coordinate of the next point!
System.out.println(" (from "+pi.getMinX()+","+pi.getMinY()+" to " +(pi.getMaxX()-1)+","+(pi.getMaxY()-1)+")"); 
PlanarImage edge1=threshold(pi,limit,outfile);
return edge1;
}
/**
* This method helps filter() function and stores image as name.
* @param pi It is Object of PlanarImage of input image
* @param name It is name of image to be stored in .JPG extension 
* @param submat It is the filter matrix 
* @return output11 It returns PlanarImage obect of the image with line detected .
*/
// FOR FILTER AND EDGE DETECTION
public static PlanarImage allfilter(PlanarImage pi,float [] submat,String name )
{
KernelJAI kernelh = new KernelJAI(3,3,submat);
PlanarImage output1 = JAI.create("convolve",pi, kernelh);
JAI.create("filestore",output1,name,"JPEG");
return output1;
}
/**
* This method applies all filter on input image and  store it as "hori.jpg" for horizontal line ,"ver.jpg" for vertical line,"p45.jpg" for line at +45 ,"n45.jpg" for line at -45,"edge1.jpg" for all lines.
* @param pi It is Object of PlanarImage of input image
* @param fname It is name of image stored in .JPG extension 
* @return output1 It returns PlanarImage obect of the image with lines detected .
*/
public static  PlanarImage filter(PlanarImage pi,String fname)
 {
System.out.println("\n***************FILTER***************\n");
 File image = new File(fname);
 System.out.println("Image file size: "+image.length()+" bytes.");
 // Show the image dimensions and coordinates.
 System.out.print("Dimensions: ");
 System.out.print(pi.getWidth()+"x"+pi.getHeight()+" pixels");
 // Remember getMaxX and getMaxY return the coordinate of the next point!
System.out.println(" (from "+pi.getMinX()+","+pi.getMinY()+" to " +(pi.getMaxX()-1)+","+(pi.getMaxY()-1)+")"); 
float sbhMatrixh[] = {-4.0f, -4.0f, -4.0f,
                           8.0f,  8.0f,8.0f,
                           -4.0f,  -4.0f, -4.0f};
 PlanarImage edge1=allfilter(pi,sbhMatrixh,"hori.jpg");
 float sbhMatrixh1[] = {-4.0f, 8.0f, -4.0f,
                           -4.0f,  8.0f, -4.0f,
                           -4.0f,  8.0f, -4.0f};      
 PlanarImage edge2=allfilter(pi,sbhMatrixh1,"ver.jpg");
 float sbhMatrixh2[] = {-1.0f, -1.0f, 2.0f,
                           -1.0f,  2.0f, -1.0f,
                           2.0f,  -1.0f, -1.0f};      
 PlanarImage edge3=allfilter(pi,sbhMatrixh2,"p45.jpg");
 float sbhMatrixh3[] = {2.0f, -1.0f, -1.0f,
                           -1.0f,  2.0f, -1.0f,
                           -1.0f,  -1.0f, 2.0f};      
 PlanarImage edge4=allfilter(pi,sbhMatrixh2,"n45.jpg");
ParameterBlock pb = new ParameterBlock();
//pb.addSource(pi);
pb.addSource(edge1);
pb.addSource(edge2);
pb.addSource(edge3);
pb.addSource(edge4);
PlanarImage output1 = JAI.create("add", pb);
JAI.create("filestore",output1,"edge.jpg","JPEG");
return output1;
}

}