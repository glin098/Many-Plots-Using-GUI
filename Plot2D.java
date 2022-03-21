import javax.swing.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class Plot2D extends JPanel
{ 
   private double minX, minY, maxX, maxY;
   private double stepSize;
   private int scale;
   private GeneralPath path;
   private GeneralPath xCoord;
   private GeneralPath yCoord;

   private JFrame frame; 
   private ArrayList<GeneralPath> objects = new ArrayList<>(0);
   private ArrayList<Color> color = new ArrayList<>(0);

   public Plot2D()
   {
      this.minX=-10;
      this.minY=-10;
      this.maxX=10;
      this.maxY=10;
      this.stepSize = 0.01;
      this.scale = 40;
      setPanelSize();

      setCoordinates();

      frame =new JFrame(); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.add(this);

      frame.pack(); 

      frame.setVisible(true);
   }

   private void setPanelSize()
   {
      int x_dim = (int)(maxX-minX)*this.scale;
      int y_dim = (int)(maxY-minY)*this.scale;
      this.setPreferredSize(new Dimension(x_dim, y_dim));
   }

   private void setCoordinates()
   {
      xCoord = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 2);
      xCoord.moveTo(0, scale*maxY);
      xCoord.lineTo(scale *(maxX-minX), scale*maxY);

      yCoord = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 2);
      yCoord.moveTo(scale*(0-minX), 0);
      yCoord.lineTo(scale*(0-minX), scale*(maxY-minY));
   }

   public void plot(Function2D f)
   {
      int numPoints = (int)((maxX-minX)/stepSize);
      this.path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, numPoints);
      path.moveTo(scale*0, scale*(maxY-f.eval(minX)));
      objects.add(this.path);
      color.add(Color.RED);

      double current = minX;
      while (current <= maxX)
      {
         current = current + stepSize;
         double yValue = f.eval(current);
         path.lineTo(scale*(current-minX), scale*(maxY-yValue));
      }
      this.repaint();
   }


   public void plot(Function2D f, Color c)
   {
      int numPoints = (int)((maxX-minX)/stepSize);
      this.path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, numPoints);
      objects.add(this.path);
      color.add(c);
      path.moveTo(scale*0, scale*(maxY-f.eval(minX)));

      double current = minX;
      while (current <= maxX)
      {
         current = current + stepSize;
         double yValue = f.eval(current);
         path.lineTo(scale*(current-minX), scale*(maxY-yValue));
      }
      this.repaint();
   }

   @Override
   public void paint(Graphics g)
   {
      super.paint(g);
      Graphics2D g2 = (Graphics2D)g;

      g.setColor(Color.BLACK);
      g2.draw(this.xCoord);
      g2.draw(this.yCoord);

      int k = 0;
      for(GeneralPath i : objects){
         g.setColor(color.get(k));
         g2.draw(i);
         k++;
      }
   }

   public void saveImage(String fileName) throws IOException
   {
      Container cont = frame.getContentPane();
      BufferedImage image = new BufferedImage(
         cont.getWidth(), 
         cont.getHeight(), 
         BufferedImage.TYPE_INT_RGB);
      Graphics2D graphics2D = image.createGraphics();
      frame.paint(graphics2D);
      ImageIO.write(image, "png", new File(fileName));
   }

   public void close()
   {
      frame.setVisible(false);
      frame.dispose();
   }

}
  
