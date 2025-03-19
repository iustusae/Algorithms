package Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholds;
    private final int trials;
    private final double meanVal;
    private final double stddevVal;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Both n and trials must be positive integers");
        }

        this.trials = trials;
        this.thresholds = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                // Select a random blocked site
                int row, col;
                do {
                    row = StdRandom.uniformInt(1, n + 1);
                    col = StdRandom.uniformInt(1, n + 1);
                } while (percolation.isOpen(row, col));

                percolation.open(row, col);
            }

            // Calculate the percolation threshold for this trial
            // Total number of open sites divided by total number of sites
            thresholds[t] = (double) percolation.numberOfOpenSites() / (n * n);
        }

        // Precompute statistical values
        this.meanVal = StdStats.mean(thresholds);
        this.stddevVal = StdStats.stddev(thresholds);
        double confidenceFactor = 1.96 * stddevVal / Math.sqrt(trials);
        this.confidenceLo = meanVal - confidenceFactor;
        this.confidenceHi = meanVal + confidenceFactor;
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddevVal;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: java Percolation.PercolationStats <gridSize> <trialCount>");
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.printf("mean                    = %f\n", stats.mean());
        StdOut.printf("stddev                  = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }
}