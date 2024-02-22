package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import java.lang.Math;

public class PercolationStats {

    double[] spaceBefPerc; //number of spaces before perculates
    int T;
    int N;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf)  {
        this.N = N;
        this.T = T;
        spaceBefPerc = new double[T];
        for (int i = 0; i < T; i ++) {
            Percolation p = pf.make(N);
            int[] ind = new int[N*N];
            for (int j = 0; j < N*N; j++) {
                ind[j] = j;
            }
            StdRandom.shuffle(ind);
            int totalNum = N*N;
            for (int k = 0; k < N*N; k ++) {
                int [] coord = ord2grid(ind[k]);
                p.open(coord[0], coord[1]);
                if (p.percolates()) {
                    spaceBefPerc[i] = ((double) k) / totalNum;
                    break;
                }
            }

        }

    }

    private int[] ord2grid(int ord) {

        int row = ord / this.N;
        int col = ord % this.N;
        int[] ret = new int[] {row, col};
        return ret;
    }

    // sample mean of percolation threshold
    public double mean()  {
        double sum = 0;
        for (double i: spaceBefPerc) {
            sum += i;
        }
        return sum / this.T;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sumOfSquare = 0;
        for (double i: spaceBefPerc) {
            sumOfSquare += i * i;
        }
        double stdvSq = ((double) sumOfSquare)/(this.T - 1) - mean()* mean() * this.T/(this.T - 1);
        double stdv = Math.sqrt(stdvSq);
        return stdv;
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev()/Math.sqrt(this.T);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev()/Math.sqrt(this.T);
    }


    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(20, 100, pf);
        /* for (int i = 0; i < 10; i ++) {
            System.out.println(ps.spaceBefPerc[i]);
        } */
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }

}
