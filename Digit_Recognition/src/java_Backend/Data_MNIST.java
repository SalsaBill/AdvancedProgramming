package java_Backend;
import java.awt.image.*;
import java.io.*;

public class Data_MNIST {
	
	//declare variables
	private int Label_MNIST; 
	private BufferedImage Buffer_MNIST;
	private double Distance; 
	
	public Data_MNIST() {}
	
	//create get and set methods
	 public int getLabel_MNIST() {
		return this.Label_MNIST;
	}
	
	public void setLabel_MNIST(int suppliedLbl) {
		this.Label_MNIST = suppliedLbl;
	}
	
	public double getDistance() {
		return Distance;
	} 
	
	public void setDistance(double tempValue) {
		this.Distance = tempValue;
	}
	
	public BufferedImage getBuffer_MNIST()  {
		return this.Buffer_MNIST;
	}

	public void setBuffer_MNIST(BufferedImage tempimage) {
		this.Buffer_MNIST = tempimage;
	}
}