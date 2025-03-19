package Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Constants for site states
    private static final boolean OPEN = true;
    private static final boolean BLOCKED = false;

    // n-by-n grid to track opened/blocked sites
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;      // For percolation and full check
    private final WeightedQuickUnionUF ufFull;  // For full check only (to avoid backwash)
    private int openSites;
    private final int n;              // Grid size
    private final int virtualTop;     // Virtual top site index
    private final int virtualBottom;  // Virtual bottom site index

    // Creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }

        this.n = n;
        grid = new boolean[n][n];

        // Initialize grid with all sites blocked (false)
        // No need for Arrays.fill(), just use the default boolean value (false)

        // Initialize Union-Find with n*n sites plus 2 virtual sites
        int totalSites = n * n + 2;
        uf = new WeightedQuickUnionUF(totalSites);
        ufFull = new WeightedQuickUnionUF(totalSites - 1); // No virtual bottom for full check

        virtualTop = n * n;
        virtualBottom = n * n + 1;
        openSites = 0;
    }

    // Convert 2D coordinates to 1D index
    private int convert2DTo1D(int row, int col) {
        return row * n + col;
    }

    // Opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);

        // Adjust to 0-indexed
        row--;
        col--;

        if (grid[row][col] == OPEN) return; // Already open

        grid[row][col] = OPEN;
        openSites++;

        int siteIndex = convert2DTo1D(row, col);

        // Connect to virtual top if in first row
        if (row == 0) {
            uf.union(siteIndex, virtualTop);
            ufFull.union(siteIndex, virtualTop);
        }

        // Connect to virtual bottom if in last row
        if (row == n - 1) {
            uf.union(siteIndex, virtualBottom);
            // Don't union with bottom in ufFull to avoid backwash
        }

        // Connect to adjacent open sites
        connectIfOpen(row, col, row - 1, col); // Up
        connectIfOpen(row, col, row + 1, col); // Down
        connectIfOpen(row, col, row, col - 1); // Left
        connectIfOpen(row, col, row, col + 1); // Right
    }

    // Connect current site to adjacent site if it's open
    private void connectIfOpen(int row1, int col1, int row2, int col2) {
        // Check if adjacent site is within bounds
        if (row2 < 0 || row2 >= n || col2 < 0 || col2 >= n) {
            return;
        }

        // Connect if adjacent site is open
        if (grid[row2][col2] == OPEN) {
            int site1 = convert2DTo1D(row1, col1);
            int site2 = convert2DTo1D(row2, col2);
            uf.union(site1, site2);
            ufFull.union(site1, site2);
        }
    }

    // Validate that indices are within bounds
    private void validateIndices(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return grid[row - 1][col - 1] == OPEN; // Adjust to 0-indexed
    }

    // Is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        if (!isOpen(row, col)) return false;

        int siteIndex = convert2DTo1D(row - 1, col - 1);
        return ufFull.find(siteIndex) == ufFull.find(virtualTop);
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // Does the system percolate?
    public boolean percolates() {
        // Special case for n=1
        if (n == 1) {
            return isOpen(1, 1);
        }
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    // Test client
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);

        // Open sites to create a percolating system
        percolation.open(1, 2); // (0,1) in 0-indexed
        percolation.open(2, 1); // (1,0) in 0-indexed
        percolation.open(2, 2); // (1,1) in 0-indexed
        percolation.open(2, 3); // (1,2) in 0-indexed
        percolation.open(3, 3); // (2,2) in 0-indexed

        // Test assertions
        StdOut.println("Open sites: " + percolation.numberOfOpenSites());
        StdOut.println("Site (1,2) is full: " + percolation.isFull(1, 2));
        StdOut.println("Site (2,2) is full: " + percolation.isFull(2, 2));
        StdOut.println("Site (3,3) is full: " + percolation.isFull(3, 3));
        StdOut.println("System percolates: " + percolation.percolates());
    }
}