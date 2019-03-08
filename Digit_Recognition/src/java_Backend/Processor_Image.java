package java_Backend;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Processor_Image {
	
	//declare variables
	private BufferedImage currentImage = null;
	private BufferedImage grayscaleImg = null;
	
	
	public BufferedImage convertRGBToGrayscale(BufferedImage img) {
		
		//convert image from RGB to Grayscale
		this.grayscaleImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int y = 0; y < img.getHeight(); y++) {
			for(int x = 0; x < img.getWidth(); x++) {
				
				int rgbvalue = img.getRGB(x,y);

				int alpha = (rgbvalue >> 24) & 0xff;
				int red = (rgbvalue >> 16) & 0xff;
				int green = (rgbvalue >> 8) & 0xff;
				int blue = (rgbvalue) & 0xff;
				
				int grayscale = (int) ((0.3 * red) + (0.59 * green) + (0.11 * blue));
				int new_pixel_value = 0xFF000000 | ( grayscale << 16 ) |
						(grayscale << 8 ) |
						(grayscale);
				
				this.grayscaleImg.setRGB(x, y, new_pixel_value);
				
			}
		}
		
		return this.grayscaleImg;
	}
	//resize grayscale image
	public void resizeGreyScaleImage (int newWidth, int newHeight ) {
		BufferedImage slectedImage = null;
		BufferedImage tempImage = null;
		try { 
			slectedImage = this.getImage();
			java.awt.Image imageForResize = slectedImage.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
			tempImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graph2d = tempImage.createGraphics();
			graph2d.drawImage(imageForResize, 0, 0, null);
			graph2d.dispose();
			this.setImage(tempImage);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//set and get functions
	public BufferedImage getImage() {
		return currentImage;
	}
	
	public void setImage(BufferedImage chosenImage) {
		this.currentImage = chosenImage;
	}
}
	