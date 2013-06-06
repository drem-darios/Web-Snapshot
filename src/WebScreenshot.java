import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class WebScreenshot {
     
	private static final String EXT = "jpeg";
	private static final Logger logger = Logger.getLogger(WebScreenshot.class.getName());
	
	public static void main(String [] args) throws Exception 
     {
         Desktop.getDesktop().browse(new URI(args[0]));
         Robot robot = new Robot();
         Thread.sleep(5000);
         Image image = robot.createScreenCapture(getScreenResolutionSize());
         saveToDisk(image);
     }

	private static void saveToDisk(Image image) 
	{
		BufferedImage buffered = toBufferedImage(image);
		String fileName = UUID.randomUUID().toString();
        File file = new File(fileName + "." + EXT);
        try 
        {
        	boolean result = ImageIO.write(buffered, EXT, file);
            logger.log(Level.INFO,"SAVE RESULT: " + Boolean.toString(result));
        } catch(IOException e) {
            logger.log(Level.SEVERE, "Could not save " + file.getPath() +": " + e.getMessage());
        }
	}

	private static Rectangle getScreenResolutionSize() 
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		return new Rectangle(d);
	}
	
	private static BufferedImage toBufferedImage(Image src) 
	{
		int w = src.getWidth(null);
		int h = src.getHeight(null);
		int type = BufferedImage.TYPE_INT_RGB;
		
		BufferedImage img = new BufferedImage(w, h, type);
		Graphics2D g2 = img.createGraphics();
		g2.drawImage(src, 0, 0, null);
		g2.dispose();
		
		return img;
	 }
  }