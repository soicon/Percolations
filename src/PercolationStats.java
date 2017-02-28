/**
 * Created by Dung on 2/27/2017.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    double[] fraction;
    int count;
    public PercolationStats(int n, int trials) {
        if(n <=0 || trials <= 0){
            throw new IllegalArgumentException("must be larger than 0");
        }
        count = trials;
        fraction = new double[count];
        for (int i = 0; i < count; i++) {
            Percolation p = new Percolation(n);
            int opensite =0;
            while(!p.percolates()){
                int con = StdRandom.uniform(1,n+1);
                int cac = StdRandom.uniform(1,n+1);
                if(!p.isOpen(con,cac)){
                    p.open(con,cac);
                    opensite++;
                }
            }

            double frac=  (double)opensite/(n*n);

            fraction[i]=frac;

        }

    }   // perform trials independent experiments on an n-by-n grid
    public double mean()   {
        return StdStats.mean(fraction);

    }                       // sample mean of percolation threshold
    public double stddev()  {
        return StdStats.stddev(fraction);

    }                      // sample standard deviation of percolation threshold
    public double confidenceLo(){
        return mean()-((1.96*stddev())/Math.sqrt(count));

    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi()      {

        return mean()+((1.96*stddev())/Math.sqrt(count));
    }            // high endpoint of 95% confidence interval

    public static void main(String[] args)    {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());;
    }
}
