package java_Backend;
import java.awt.image.BufferedImage;


public class BufferImage {
	
	public int label;
	public BufferedImage currentImg;
	
	public BufferImage(int label, BufferedImage currentImg) {
		this.label = label;
		this.currentImg = currentImg;
		
	}
	
	public int getLabel() {
		return label;
	}
	
	public void setLabel(int providedLabel) {
		this.label = providedLabel;
	}
	
	public BufferedImage getcurrentImg() {
		return this.currentImg;
	}
	
	
	public void setcurrentImg(BufferedImage providedImg) {
		this.currentImg = providedImg;
	}

	public int getRGB(int col, int row) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	

}