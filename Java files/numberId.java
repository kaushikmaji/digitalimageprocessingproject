//NUMBER PLATE RECOGNITION SYSTEM
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
* This class is used to get numbers from number plate extracted by extractchar.java
* @author Nikhil Kansari,Larenge Kamal,Abhinav Bajaj,Pranjal Goel
* @version 1.2
*/
public class numberId 
{static	int size;
static char num[] = new char [20];
int count = 0;
/**
* This method checks whether "pixel" stored in matrix is WHITE or BLACK 
* @param a It is the pixel value to check.
* @return boolean
*/ 
public static boolean check(int a)
{//int i = 0,j = 1;
if(a<50)
	return true;   //for Black
else 
	return false;   //for white
}
/**
* This method is used to get number from separated characters(number plate recognition alogrithm).
* @param imname It is the name of number plate. 
* @return String It is the number from plate .
*/ 
public static String number(String imname)
{  
	
	 int [][] a = new int[20][2];//stores vertical values
	 int [][] b = new int[20][2];//stores horizontal values
	  a =   ImageInfo1.xysender();
	  b = ImageInfo1.horizontalcoordinatesSender();
	  size = ImageInfo1.sizesender();
	  int pix[][][] = new int[size][(b[0][1]-b[0][0])*2][(a[0][1]-a[0][0])*2];
	  pix = Crop.matrixsaver(imname);
	  
	  for(int i=0;i<size;i++)
	  {
		  int cx0 = 0,cy0 = 0;
		  int cx1 = ((a[i][1]-a[i][0])/2)-1 , cy1 = 0;
		  int cx2 = ((a[i][1]-a[i][0]))-1 , cy2 = 0;
		  int cx3 = 0 , cy3 = ((b[i][1]-b[i][0])/2)-1;
		  int cx4 = cx1 , cy4 = cy3;
		  int cx5 = cx2 , cy5 = cy3;
		  int cx6 = 0 , cy6 = ((b[i][1]-b[i][0]))-1;
		  int cx7 = cx1 , cy7 = cy6;
		  int cx8 = cx2 , cy8 = cy6;
		  int cx9 = cx1/2 , cy9 = cy3/2;
		  int cx10 = cx1+((cx2-cx1)/2) , cy10 = cy5+((cy8-cy5)/2);
		  int cx11 = 0 , cy11 = (cy3-cy0)/2;
		  int cx12 = cx2 , cy12 = cy11;
		  int cx13 = cx2 , cy13 = cy10;
		  int cx14 = 0 , cy14 = cy13;
		  
		  
		  
	 
	 
	 
//ACTUAL NUMBER RECOGNITION
	if(!check(pix[i][cy0][cx0]) && !check(pix[i][cy1][cx1]) && !check(pix[i][cy2][cx2]) && !check(pix[i][cy3][cx3]) && !check(pix[i][cy4][cx4]) && !check(pix[i][cy5][cx5]) && !check(pix[i][cy6][cx6]) && !check(pix[i][cy7][cx7]) && !check(pix[i][cy9][cx9]))	
	{
		System.out.println("4");
		 System.out.println('\n');
		 num[i] = '4';
		 
	}
		  else if(check(pix[i][cy0][cx0]) && check(pix[i][cy2][cx2]) && check(pix[i][cy6][cx6]) && check(pix[i][cy8][cx8]))
		//E I K H M N X Z 	  
	  {
			  if(check(pix[i][cy1][cx1]) && check(pix[i][cy7][cx7]))
			  //E I Z
			  {   if(check(pix[i][cy5][cx5]))
				  //I
			      {
				  System.out.println("I");
					 System.out.println('\n');
					 num[i] = 'I';
					 
			      }
			  else if(check(pix[i][cy3][cx3]))
				  //E
				  {
					  System.out.println("E");
						 System.out.println('\n');
						 num[i] = 'E';
						 
				  }
				  else
					  //Z
				  {
					  System.out.println("Z");
						 System.out.println('\n');
						 num[i] = 'Z';
						 
				  }
			  }
			  //K H M N X
			  else  if(!check(pix[i][cy3][cx3]))
			  {
				  System.out.println("X");
					 System.out.println('\n');
					 num[i] = 'X';
					 
			  }
			  else if(!check(pix[i][cy5][cx5]))
			  {
				  System.out.println("K");
					 System.out.println('\n');
					 num[i] = 'K';
					 
			  }
			  else if(check(pix[i][cy9][cx9]) && check(pix[i][cy10][cx10]))
			  {
				  System.out.println("N");
					 System.out.println('\n');
					 num[i] = 'N';
					 
			  }
			  else if(check(pix[i][cy9][cx9]) && !check(pix[i][cy10][cx10]))
			  {
				  System.out.println("M");
					 System.out.println('\n');
					 num[i] = 'M';
					
			  }
			  else
			  {
				  System.out.println("H");
					 System.out.println('\n');
					 num[i] = 'H';
					 
			  }
			  
	  }
		  
		  else if(!check(pix[i][cy0][cx0]) && !check(pix[i][cy2][cx2]) && !check(pix[i][cy6][cx6]) && !check(pix[i][cy8][cx8]))
		  //C G O S 3 6 8 9 0 5
		  {
			  if(check(pix[i][cy1][cx1]) && check(pix[i][cy3][cx3]) && check(pix[i][cy5][cx5]) && check(pix[i][cy7][cx7]))
				  //O 0
			  {   //System.out.println((cy6-cy0)/(cx2-cx0));
				  if(((cy6-cy0)/(cx2-cx0))==2)
				  {
				  System.out.println("0");
					 System.out.println('\n');
					 num[i] = '0';
					 
				  }
				  else
				  {
					  System.out.println("O");
						 System.out.println('\n'); 
						 num[i] = 'O';
						 
				  }
			  }
			  
			  else if(!check(pix[i][cy3][cx3]))
			  //8 3 S 9 5
			  {
			     if(check(pix[i][cy5][cx5]))  
			     // 9
			     {
			    	
			    		System.out.println("9");
						 System.out.println('\n');
						 num[i] = '9';
						 
			    	
			    	
			     }
			     
			     else
			     //8 3 S 5
			     {
			    	 if(!check(pix[i][cy14][cx14]) && check(pix[i][cy4][cx4]))
			    	 {
			    		 System.out.println("3");
						 System.out.println('\n');
						 num[i] = '3';
						 
			    	 }
			    	 else if(check(pix[i][cy14][cx14]))
			    	 {//S or 8
			    		// System.out.println((cy6-cy0)/(cx2-cx0));
			    		if(((cy6-cy0)/(cx2-cx0))==2)
			    		{
			    		 System.out.println("8");
						 System.out.println('\n');
						 num[i] = '8';
						 
			    		}
			    		else
			    		{
			    			System.out.println("S");
							 System.out.println('\n');
							 num[i] = 'S';
							 
			    		}
			    	 }
			    	 else
			    	 {
			    		 System.out.println("5");
						 System.out.println('\n');
						 num[i] = '5';
						 
			    	 }
			    	 
			     }
				 
				   
				  
			  }
			  //C G 6
			  
			  else if(check(pix[i][cy13][cx13]))
				  
				  //C G 6   
			      { if(((cy6-cy0)/(cx2-cx0))==2)
			        {
			    	  System.out.println("6");
						 System.out.println('\n');
						 num[i] = '6';
						
			        }
			      
			        else if(check(pix[i][cy4+5][cx4+15]))
			        {
				     System.out.println("G");
					 System.out.println('\n');
					 num[i] = 'G';
					 
			        }
			        else
			        {
			        	System.out.println("C");
						 System.out.println('\n');
						 num[i] = 'C';
						 
			        }
			      }
			      
			  
			  }
		  
	//REST OF EM
		  
		  else if(check(pix[i][cy0][cx0]) && check(pix[i][cy1][cx1]))
			  //B D F P R T W 7
		  {
			 if(check(pix[i][cy3][cx3]) && check(pix[i][cy4][cx4]) && check(pix[i][cy6][cx6])) 
				 //B F P R
			 {
				 if(check(pix[i][cy2][cx2]))
				 {
					 System.out.println("F");
					 System.out.println('\n');
					 num[i] = 'F';
					 
				 }
				 else if(check(pix[i][cy7][cx7]))
				 {
					 System.out.println("B");
					 System.out.println('\n');
					 num[i] = 'B';
					 
				 }
				 else if(check(pix[i][cy8][cx8]))
				 {
					 System.out.println("R");
					 System.out.println('\n');
					 num[i] = 'R';
					 
				 }
				 else
				 {
					 System.out.println("P");
					 System.out.println('\n');
					 num[i] = 'P';
					 
				 }
			 }
			 
			 else
			 //D T W 7
			 {
				 if(check(pix[i][cy6][cx6]))
				 {
					 System.out.println("D");
					 System.out.println('\n');
					 num[i] = 'D';
					 
				 }
				 else if(check(pix[i][cy7][cx7]))
				 {
					 System.out.println("T");
					 System.out.println('\n');
					 num[i] = 'T';
					 
				 }
				 else if(!check(pix[i][cy4][cx4])) 
				 {
					 System.out.println("W");
					 System.out.println('\n');
					 num[i] = 'W';
					 
				 }
				 else
				 {
					 System.out.println("7");
					 System.out.println('\n');
					 num[i] = '7';
					 
				 }
			 }
			 
			 
		  }
	 
		  else	  
		  //A J L Q U V Y 1 2 X Z
		  {
			 if(check(pix[i][cy8][cx8]))
				 //A L 1 2 Q X Z
			 {
				 if(check(pix[i][cy6][cx6]))
					 //A L 2 Z
				 {
					 if(check(pix[i][cy4][cx4]))
					 {
						 System.out.println("Z");
						 System.out.println('\n'); 
						 num[i] = 'Z';
						 
					 }
					 else if(check(pix[i][cy3][cx3]))
					 {
						 System.out.println("L");
						 System.out.println('\n'); 
						 num[i] = 'L';
						 
					 }
					 else if(check(pix[i][cy7][cx7]))
					 {
						 System.out.println("2");
						 System.out.println('\n'); 
						 num[i] = '2';
						 
					 }
					 else
					 {
						 System.out.println("A");
						 System.out.println('\n'); 
						 num[i] = 'A';
						 
					 }
				 }
				 else
					 //Q 1 X
				 {
					 if(check(pix[i][cy2][cx2]))
					 {
						 System.out.println("1");
						 System.out.println('\n');
						 num[i] = '1';
						 
					 }
					 else if(check(pix[i][cy3][cx3]))
					 {
						 System.out.println("Q");
						 System.out.println('\n');
						 num[i] = 'Q';
						 
					 }
					 else
					 {
						 System.out.println("X");
						 System.out.println('\n');
						 num[i] = 'X';
						 
					 }
				 }
				 
			 }
			 
			 else
				 //J U V Y 
			 {
				 if(check(pix[i][cy0][cx0]) && check(pix[i][cy2][cx2]))
				 //U V Y
				 {
					 if(check(pix[i][cy5][cx5]))
					 {
						 System.out.println("U");
						 System.out.println('\n');
						 num[i] = 'U';
						 
					 }
					 else if(check(pix[i][cy4][cx4]))
					 {
						 System.out.println("Y");
						 System.out.println('\n');
						 num[i] = 'Y';
						 
					 }
					 else
					 {
						 System.out.println("V");
						 System.out.println('\n');
						 num[i] = 'V';
						 
					 }
				 }
				 else
				 //J 
				 {  
					 if(check(pix[i][cy5][cx5]))
				      {
					 
						 System.out.println("J");
						 System.out.println('\n');
						 num[i] = 'J';
						 
				      }
					 
					 
					 
					 
					 
				 }
			 }
			 
		  }
	
	
	 
	
	}
	  String s=new String(num);
	  System.out.println("number is :: "+s);
	  return s;
}
}
