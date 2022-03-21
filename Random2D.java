import java.util.Random;
public class Random2D implements Function2D
{
   private double min;
   private double max;
   Random randomGenerator;   
   public Random2D(double min, double max)
   {
      randomGenerator = new Random();
      this.min = min;
      this.max = max;
   }

   public double eval(double x)
   {
      boolean negative = randomGenerator.nextBoolean();
      double y = randomGenerator.nextDouble();
      if (negative)
      {
         y = min * y;
      }
      else
      {
         y = max * y;
      }
      return y;
   }
}
