package java_Backend;
import java.awt.image.*;
import java.util.*;
import java.io.*; 
//import view.*;

public class KNN {
	
	private Processor_Image tempImageController = null;
	// import MNIST images
	String train_label_filename = "C:\\Users\\Salsa Bill\\Documents\\Final AdvProg\\Digit_Recognition\\src\\java_Backend\\MNISTData\\train-labels.idx1-ubyte";
	String train_image_filename = "C:\\Users\\Salsa Bill\\Documents\\Final AdvProg\\Digit_Recognition\\src\\java_Backend\\MNISTData\\train-images.idx3-ubyte";
	
	//create variables for file and data input stream
	FileInputStream in_stream_labels = null;
	FileInputStream in_stream_images = null;
	
	DataInputStream dataStreamLables = null; 
	DataInputStream dataStreamImages = null; 

	public void loadItemArray() { 
		
		
		try {
			in_stream_labels = new FileInputStream(new File(train_label_filename));
			in_stream_images = new FileInputStream(new File(train_image_filename));
	
			dataStreamLables = new DataInputStream(in_stream_labels);
			dataStreamImages = new DataInputStream(in_stream_images); 
			
			int labelStartCode = dataStreamLables.readInt(); 
			int labelCount = dataStreamLables.readInt();
			int imageStartCode = dataStreamImages.readInt();
			int imageCount = dataStreamImages.readInt();
			int imageHeight = dataStreamImages.readInt(); 
			int imageWidth = dataStreamImages.readInt();
			
			currentDataItems = new Data_MNIST[labelCount]; 
			
			int imageSize = imageHeight * imageWidth; 
			byte[] labelData = new byte[labelCount];
			byte[] imageData = new byte[imageSize * imageCount];
			BufferedImage tempImage;
			
			dataStreamLables.read(labelData);
			dataStreamImages.read(imageData);
			
			for (int currentRecord = 0; currentRecord < labelCount; currentRecord++) {
				int currentLabel = labelData[currentRecord];				
				Data_MNIST newlabeledimage = new Data_MNIST();
				newlabeledimage.setLabel_MNIST(currentLabel);			
				tempImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);				
				int[][] imageDataArray = new int[imageWidth][imageHeight];
				for (int row = 0; row < imageHeight; row++) {
					for(int column = 0; column < imageWidth; column++) { 
						imageDataArray[column][row] = imageData[(currentRecord * imageSize)+((row*imageWidth) + column)] | 0xFF000000;
						tempImage.setRGB(column, row, imageDataArray[column][row]);
					}
					
				}
				
				newlabeledimage.setBuffer_MNIST(tempImage); 
				currentDataItems[currentRecord] = newlabeledimage;
			}
			if (in_stream_labels != null) {
				in_stream_labels.close();
			}
			if (in_stream_images != null) {
				in_stream_images.close();
			}			
		} catch (FileNotFoundException fn) {
			fn.printStackTrace();	   
		} catch (IOException e)	{
		   
		   e.printStackTrace();
	   }
	
	}
	
	// ADD IN ARRAY SORT !!!!! BY KNN DISTANCE VALUE , FUNCTIONS // BUBBLE SOrt ?????
	public void sortArray() {  
	    int n = currentDataItems.length;  
	    Data_MNIST tempDI = null;  
	    for(int i = 0; i < n; i++){  
	    	for(int j = 1; j < (n-i); j++){  
	    		if(currentDataItems[j - 1].getDistance() > currentDataItems[j].getDistance()){                       
                   tempDI = currentDataItems[j - 1];  
                   currentDataItems[j - 1] = currentDataItems[j];  
                   currentDataItems[j] = tempDI;  
	    		}                  
	    	}  
	    }	
	}
	
	public void computingEcludianDidst() throws NullPointerException {
		Data_MNIST[] processedMDIArray = new Data_MNIST[this.getDIArray().length];
		Data_MNIST[]  tempDataArray = this.getDIArray();
		BufferedImage ComparisonImage= tempImageController.getImage();
		if (ComparisonImage != null) {
			int currentImageWidth = ComparisonImage.getWidth();
			int currentImageHeight = ComparisonImage.getHeight();
			double mseValue = 0.0;
			double squareSum = 0.0; 
			for(int i =0; i < tempDataArray.length; i++) {
				Data_MNIST currentComparisonMNISTDataItem = tempDataArray[i]; 
				BufferedImage currentComparisonImage = currentComparisonMNISTDataItem.getBuffer_MNIST();
				for (int y = 0; y < currentImageHeight; y++) {
					for(int x = 0; x < currentImageWidth; x++) {
					int imagetoComparePixels = ComparisonImage.getRGB(x, y);
				    int currentComparisonImagePixelValue =  currentComparisonImage.getRGB(x, y); 
				    squareSum += (Math.pow((imagetoComparePixels - currentComparisonImagePixelValue), 2));
				    
					}
				}
				mseValue = squareSum / (currentComparisonImage.getWidth() * currentComparisonImage.getHeight());
				currentComparisonMNISTDataItem.setDistance(mseValue);
				processedMDIArray[i] = currentComparisonMNISTDataItem;
				
			}
			this.setDIArray(processedMDIArray);			
		}
	}
	
	public double getConfidence(int k) {   
		int maxCountLabel = 0; 
	    int maxCount = 0;
	    try {
	    	this.sortArray(); 
			int[] labelArray = new int [k];
			Data_MNIST[] tempMDIArray = this.getDIArray();
			for(int internalCount = 0; internalCount < k; internalCount++) { 
				Data_MNIST lblIndex = tempMDIArray[internalCount];
				labelArray[internalCount] = lblIndex.getLabel_MNIST();
			}
			int [][ ] countTrackingArray = new int[9][1];
			for (int selectedNumber = 1; selectedNumber <= 9; selectedNumber++) {
				int totalCount = 0; 
				for(int actualCount = 0; actualCount < labelArray.length; actualCount++) {
					if (labelArray[actualCount] == selectedNumber) {
						totalCount = totalCount + 1;
						
					}
				}
				countTrackingArray[(selectedNumber-1) ][0] = totalCount;			
			}
			for (int iteratedNumber =  0; iteratedNumber < 9; iteratedNumber++) {
				if (maxCount < countTrackingArray[iteratedNumber][0]) {
					maxCount = countTrackingArray[iteratedNumber][0]; 
					maxCountLabel  = iteratedNumber + 1; 
				}
			}	

	    	 } catch (Exception e) {
	    		 e.printStackTrace();
	    	 }
	    	this.setRecogniseDidgit(maxCountLabel);
		    double accuracyratio = ((double) maxCount / k) * 100; 
			return accuracyratio;	
		}
	

	public int getRecogniseDidgit() {
		return this.recognizedDigit;
	}
	
	public void setRecogniseDidgit( int suppliedDidgit) {
		this.recognizedDigit = suppliedDidgit;
	}
	
	
	
	private Data_MNIST[] currentDataItems = null;
	private int recognizedDigit;
	
	public Processor_Image getImageController() {
		return this.tempImageController;
	}
	
	public void setImageController (Processor_Image providedIC) {
		this.tempImageController = providedIC;
	}
	
	public Data_MNIST[] getDIArray() {
		return this.currentDataItems;
	}
	
	public void setDIArray(Data_MNIST[] providedDIArray) {
		this.currentDataItems = providedDIArray;
	}	
	
	

}
