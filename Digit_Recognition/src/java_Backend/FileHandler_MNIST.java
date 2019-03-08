package java_Backend;
import java.io.*;

public abstract class FileHandler_MNIST {
	
	//declare variables
	File fp;
	String source;
	String destination;
	int fileSize;
	int directory;
	String fileType;
	
	
	public FileHandler_MNIST() {}
	public FileHandler_MNIST(String source) {
		this.source = source;
		this.fp = new File(this.source);
	}
	
	public FileHandler_MNIST(String source_file, String dest_file) {
		this.source = source_file;
		this.destination = dest_file;
		this.fp = new File(this.source);
	}
	
	public abstract void writeFile(String file_name) throws IOException;
	public abstract void readFile() throws IOException;
	
	//create set and get methods
	public File getFp() {
		return fp;
	}
	public void setFp(File fp) {
		this.fp = fp;
	}
	public String getsource() {
		return source;
	}
	public void setsource(String source) {
		this.source = source;
	}
	public String getdestination() {
		return destination;
	}
	public void setdestination(String destination) {
		this.destination = destination;
	}
	public int getdirectory() {
		return directory;
	}
	public void setdirectory(int directory) {
		this.directory = directory;
	}
	public String getfileType() {
		return fileType;
	}
	public void setfileType(String fileType) {
		this.fileType = fileType;
	}
	public int getfileSize() {
		return fileSize;
	}
	public void setfileSize(int fileSize) {
		this.fileSize = fileSize;
	}
}
