import java.awt.*;
import java.awt.image.*;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.*;
import javax.swing.*;
import com.sun.media.jai.widget.DisplayJAI;
import javax.media.jai.operator.*;

public class MagnificationInterpolation
{
    public static PlanarImage magnify(String args)
    {
        PlanarImage pi=JAI.create("fileload",args);
        double [][] matrix={{0.3D, 0.59D, 0.11D, 0D}};
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(pi);
        pb.add(matrix);
        PlanarImage pi2=JAI.create("BandCombine",pb,null);
	int width = pi2.getWidth();
        int height = pi2.getHeight();
	ParameterBlock pb1 = new ParameterBlock();
        pb1.addSource(pi2);
	pb1.add(0);
	pb1.add(width);
	pb1.add(0);
	pb1.add(height);
        double fill[] = {100D};
        pb1.add(new BorderExtenderConstant(fill));
        pb1.add(100D);
	PlanarImage pi3=JAI.create("Border",pb1);
	width = pi3.getWidth();
        height = pi3.getHeight();
        SampleModel sm = pi3.getSampleModel();
        Raster inputRaster = pi3.getData();
        WritableRaster outputRaster = inputRaster.createCompatibleWritableRaster();
        int[] pixels = new int[width*height];
	int[] pixels1 = new int[width*height];
        inputRaster.getPixels(0,0,width,height,pixels);

        //Defining Filter
        float[][] filter = { {1/4f, 1/2f, 1/4f},
                             {1/2f, 1f, 1/2f},
                             {1/4f, 1/2f, 1/4f} };
        int pos;
        float sum;
        //Zero Padding
	for(int h=0;h<height;h++)
        {
            for(int w=0;w<width;w++)
            {
               
                if(w%2==0 || h%2==0)
			pixels1[h*width+w]=0;
		else
			pixels1[h*width+w] = pixels[(h/2)*width+(w/2)];
            }
        }
	for(int i=0;i<height*width;i++)
		pixels[i]=pixels1[i];
        //Applying Filter
        for(int h=0;h<height;h++)
        {
            for(int w=0;w<width;w++)
            {
                if(h%2==0 || w%2==0)
		{
			sum=0;
              	  	pos=h*width+w;
                	for(int i=-1;i<2;i++)
                	{
                     		for(int j=-1;j<2;j++)
                     		{
                        		if((pos+i*width+j >= 0) && (pos+i*width+j < width*height) && !(h+i<0) && !(h+i>=height) && !(w+j<0) && !(w+j>=width))
                            			sum = sum + (pixels1[pos+i*width+j] * filter[i + 1][j + 1]);
                     		}
                	}
                	pixels[pos] = (int) sum;
		}
            }
        }
        outputRaster.setPixels(0,0,width,height,pixels);
        TiledImage ti = new TiledImage(pi3,1,1);    
        ti.setData(outputRaster);
        
        pos = args.indexOf('.');
        String name = args.substring(0,pos);
        JAI.create("filestore",ti,name + "mag.jpg","JPEG");
        
        String imageInfo = "Dimensions: " + ti.getWidth() + "x" + ti.getHeight() + " Bands:" + ti.getNumBands();
        JFrame frame = new JFrame();
        frame.setTitle("DisplayJAI: "+args);
        // Get the JFrame’s ContentPane.
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        // Create an instance of DisplayJAI.
        DisplayJAI dj = new DisplayJAI(ti);
        // Add to the JFrame’s ContentPane an instance of JScrollPane containing the DisplayJAI instance.
        contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);
        // Add a text label with the image information.
        contentPane.add(new JLabel(imageInfo),BorderLayout.SOUTH);
        // Set the closing operation so the application is finished.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,200); // adjust the frame size.
        frame.setVisible(true); // show the frame.
        return ti;
    }
}