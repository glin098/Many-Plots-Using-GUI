import java.awt.Color;

public class Driver{
    public static void main (String[] args){
        Random2D r = new Random2D(-6,0);
        Constant2D c = new Constant2D(5);
        double[] coeff = {1};
        int[] exponent = {2};
        Polynomial p = new Polynomial(coeff, exponent);

        Plot2D myPlot = new Plot2D();
        myPlot.plot(p);
        myPlot.plot(c, Color.BLUE);
        myPlot.plot(r, Color.GREEN);

        try{
            myPlot.saveImage("functions.png");
        }
        catch(Exception e){
            System.out.println(e);
        }
        myPlot.close();
    }
}