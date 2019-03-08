package java_Backend;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class FileHandler_Image extends FileHandler_MNIST {

	//declare variables
	String image_type;
	BufferedImage img;
	
	int width;
	int height;
	
	
	public FileHandler_Image() {}
		
	public void readFile() throws IOException {
		if (fp.isFile() && fp.exists()) {
			img = ImageIO.read(fp);
		}
	}

	public void writeFile(String file_name) throws IOException {
		// TODO Auto-generated method stub

	}
	
	public BufferedImage readFile(String file_name) {
		
		try {
			img = ImageIO.read(new File(file_name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			img = null;
		}
		return img;
	}
	
	public BufferedImage getImage() {
		return this.img;
	}
	
	
	public FileHandler_Image(String file_name) {
		super(file_name);
		img = null;
	}
}
