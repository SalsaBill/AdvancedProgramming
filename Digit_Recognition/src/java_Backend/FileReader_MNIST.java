package java_Backend;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileReader_MNIST {
	
	BufferImage[] train_dataset = null;
	
	public BufferImage[] getTrainDataSet() {
		return this.train_dataset;
	}
	
	public void Read() throws IOException {
	//declare variables
	String train_label_filename = "C:\\Users\\Salsa Bill\\Documents\\Java\\Assignment1\\src\\MNIST Dataset\\train-labels.idx1-ubyte";
	String train_image_filename = "C:\\Users\\Salsa Bill\\Documents\\Java\\Assignment1\\src\\MNIST Dataset\\train-images.idx3-ubyte";
	
	//using byte streams
	DataInputStream label_data_stream = null;
	DataInputStream image_data_stream= null;
	
	try {
		label_data_stream = new DataInputStream(new FileInputStream(train_label_filename));
		image_data_stream = new DataInputStream(new FileInputStream(train_image_filename));
		
		
		
		//read first 4 bytes 
		int startcode_img = image_data_stream.readInt();
		int startcode_label = label_data_stream.readInt();
		
		System.out.println("start code: images = " + startcode_img +
			" startcode labels = " + startcode_label);
		
		// read the size: 4 bytes
		int number_of_labels = label_data_stream.readInt();
		int number_of_images = image_data_stream.readInt();
	
		System.out.println("number of labels: " + number_of_labels + " " +
				"number of images: " + number_of_images);
		
		int image_height = image_data_stream.readInt();
		int image_width = image_data_stream.readInt();
		
		System.out.println("image size: " + image_width + " x "
		+ image_height);
		
		//byte array for labels
		byte[] label_data = new byte[number_of_labels];
		label_data_stream.read(label_data);
		train_dataset = new BufferImage[number_of_labels];
		
		//byte array for images
		int image_size = image_height * image_width;
		byte[] image_data = new byte[image_size * number_of_images];
		
		BufferedImage currentImg = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);
		int[][] image;
		//access individual images and labels
		for(int i = 0; i < number_of_labels; i++)
		{
			int label = label_data[i];
			//System.out.println(label);
			
			image = new int[image_width][image_height];
			
			for(int row = 0; row < image_height; row++) {
				for(int col = 0; col < image_width; col++){
					
					image[row][col] = image_data[(i*image_size)+((row*image_width) + col)];
					currentImg.setRGB(col, row, image[row][col]);

				}
			}
			train_dataset[i] = new BufferImage(label, currentImg);
			//print out labels with matching buffered images
//			System.out.println(train_dataset[i].getLabel());
//			System.out.println(train_dataset[i].getcurrentImg());
		}
		
	}
	
	finally{
		}	
	}	

}
