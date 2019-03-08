package java_UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

import java_Backend.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.event.*;
public class UI {

   //set JFrame size
    private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 600;

   //Initialise variables
    private Digit_Canvas canvas;
    
    private JFrame mainFrame;
    
    private JPanel mainPanel;
    private JPanel predictPanel;  
    private JPanel outputPanel;
    
    private JFileChooser fileChooser;
    
    private final Font sansSerifBold = new Font("SansSerif", Font.BOLD, 10);

    private JButton btnOpen;
	private JTextField JText;
	private JLabel lblFileName;
	private JLabel image;
	private String selectFile = "";

	//set up exceptions
    public UI() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));
    }

    //Initialise GUI
    public void initUI() {
        //create main frame
        mainFrame = createMainFrame();
        //create main panel
        mainPanel = new JPanel();
        //set panel to border layout
        mainPanel.setLayout(new BorderLayout());
        //call addTopPanel function
        addTopPanel();
        //create drawAndDigitPredictionPanel panel and give it a grid layout
        predictPanel = new JPanel(new GridLayout());
        //call addActionPanel function
        addActionPanel();
        //call addcanvasAndPredictionArea function
        addcanvasAndPredictionPanel();
        //add addcanvasAndPredictionArea to the center of the main panel
        mainPanel.add(predictPanel, BorderLayout.CENTER);
        //add mainpanel to the center of the mainFrame
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        //make sure the mainframe is visible when initialised
        mainFrame.setVisible(true);

    }

    private void addActionPanel() {
    	//create button to recognise digit
        JButton recognize = new JButton("Recognise");
        //add actionlistener to recognise digit button
        recognize.addActionListener(e -> {
//        	 
            });
        
        //create clear button
        JButton clear = new JButton("Clear");
        //action listener to repaint draw area white 
        clear.addActionListener(e -> {
                canvas.setImage(null);
                canvas.repaint();
                predictPanel.updateUI();
                image.setIcon( null );    
		        outputPanel.removeAll();
		        JText.setText("");
            });
        
        JPanel layer1Panel = new JPanel();
        layer1Panel.setLayout(new BoxLayout(layer1Panel, BoxLayout.Y_AXIS));
        layer1Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.LEFT,
                TitledBorder.TOP, sansSerifBold, Color.BLACK));
        
        JPanel actionPanel = new JPanel(new GridLayout(4, 1));
        
        JPanel ImagePanel = new JPanel();
        ImagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Image Selected",
                TitledBorder.LEFT,
                TitledBorder.TOP, sansSerifBold, Color.BLACK));
        image = new JLabel(" ");
        ImagePanel.add(image);
        
     
        
        actionPanel.add(recognize);
        actionPanel.add(clear);
        actionPanel.add(ImagePanel);
        
        layer1Panel.add(actionPanel);
        layer1Panel.add(ImagePanel);
        
        mainPanel.add(layer1Panel, BorderLayout.SOUTH);
    }

    private void addcanvasAndPredictionPanel() {

        canvas = new Digit_Canvas();
        
        predictPanel.add(canvas);

    	outputPanel = new JPanel();
    	
    	   	

    	outputPanel.  setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Predicted Number",
                TitledBorder.LEFT,
                TitledBorder.TOP, sansSerifBold, Color.red));
    }
   
    
    private void addTopPanel() {
  
        JPanel fileSelectPanel = new JPanel();
        
        lblFileName = new JLabel ("File: ");
        JText = new JTextField(50);
        JText.setEnabled(false);        
        btnOpen = new JButton("Browse");
		
        // Add components to the JPanel
        fileSelectPanel.add(lblFileName);
        fileSelectPanel.add(JText);
        fileSelectPanel.add(btnOpen);
 
		btnOpen.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File selectedFile = showFileChooserDialog();
				
				if(selectedFile != null) {
					JText.setText(selectedFile.getAbsolutePath());
					selectFile = selectedFile.getAbsolutePath();
					try {
	    				
	    				if (selectFile != null)  {
	    					
	    					FileHandler_Image currentfileHandler = new FileHandler_Image(selectFile); 
	    			        currentfileHandler.readFile(); 
	    			        Processor_Image ICObject = new Processor_Image();
	    			        ICObject.setImage(currentfileHandler.getImage());
	    			        ICObject.convertRGBToGrayscale(ICObject.getImage());
	    			        ICObject.resizeGreyScaleImage(28, 28);
	    			        
	    			        KNN DILoader = new KNN();
	    			        DILoader.setImageController(ICObject);
	    			        DILoader.loadItemArray();		     
	    			        DILoader.computingEcludianDidst();  
	    	                canvas.setImage(null);
	    	                canvas.repaint();
	    	                predictPanel.updateUI();
	    	                	    	                			             			     
	    			        outputPanel.removeAll();             	               
	    	                outputPanel.updateUI();
	    	                
	    	            
	    					
	    				}
	    			} catch (Exception ex) {
	    				ex.printStackTrace();
	    			}

				}
				else
					JText.setText("No file selected");	
			}
		});	
        

        mainPanel.add(fileSelectPanel, BorderLayout.NORTH);
    }
    
    //file chooser function
	private File showFileChooserDialog() {
		//set up file chooser
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new 
				File(System.getProperty("user.home")));
	    int status = fileChooser.showOpenDialog(this.mainFrame);
	    File selected_file = null;
	    if (status == JFileChooser.APPROVE_OPTION) {
	        selected_file = fileChooser.getSelectedFile();
	        try {
	        	//get image of selected file and add it to JLabel(image) to display the chosen image on the JPanel, resize image to 200 by 200
                image.setIcon(new ImageIcon(ImageIO.read(selected_file).getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
            } catch (IOException e) {
                e.printStackTrace();
            }     
	    }
	    return selected_file;
	}
  
    

    public static BufferedImage toBufferedImage(Image img) {
        // Create a buffered image
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    private static double[] transformImageToOneDimensionalVector(BufferedImage img) {
        double[] imageGray = new double[28 * 28];
        int w = img.getWidth();
        int h = img.getHeight();
        int index = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color color = new Color(img.getRGB(j, i), true);
                int red = (color.getRed());
                int green = (color.getGreen());
                int blue = (color.getBlue());
                double v = 255 - (red + green + blue) / 3d;
                imageGray[index] = v;
                index++;
            }
        }
        return imageGray;
    }

    
    //method to create and display the main Frame
    private JFrame createMainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Digit Recognition Software");
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //give mainframe height and width 
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        
        mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.exit(0);
                }
            });

        return mainFrame;
    }


}