package java_UI;
import javax.swing.*;

import java_Backend.FileReader_MNIST;

import java.io.IOException;
import java.util.concurrent.Executors;

//main class to run UI from
public class Main_UI {

    private static JFrame mainFrame = new JFrame();

    public static void main(String[] args) throws Exception {

    	FileReader_MNIST filereader = new FileReader_MNIST();
    	
        UI ui = new UI();
        Executors.newCachedThreadPool().submit(()->{
                try {
                	filereader.Read();
                    ui.initUI();
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
                    mainFrame.dispose();
                }
         });
    }
}
