/**
 * Created by Dung on 2/27/2017.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

    private boolean [][] matrix;
    private WeightedQuickUnionUF wf;
    private WeightedQuickUnionUF preventBW;
    private int top = 0;
    private int bottom;
    private int size;
    private int opensite = 0;

    public Percolation(int n) {
        if(n<= 0){
            throw new IllegalArgumentException();
        }
        size = n;
        bottom   = n*n +1;
        matrix = new boolean[n][n];
        wf = new WeightedQuickUnionUF(n*n+2);
        preventBW = new WeightedQuickUnionUF(n*n+1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = false;
            }
        }
    }
    // create n-by-n grid, with all sites blocked
    public void open(int row, int col) {
        if (matrix[row-1][col-1]){
            return;
        }
        if (!isOpen(row, col)){
            matrix[row-1][col-1] = true;
            opensite++;
        }



        if (row == 1) {
            wf.union(getQFindex(row, col), top);
            preventBW.union(getQFindex(row,col),top);
        }
        if (row == size) {
            wf.union(getQFindex(row, col), bottom);
        }
        if (row > 1 && isOpen(row-1, col)) {
            wf.union(getQFindex(row, col), getQFindex(row-1, col));
            preventBW.union(getQFindex(row, col),getQFindex(row-1, col));
        }
        if (row < size  && isOpen(row+1, col)) {
            wf.union(getQFindex(row, col), getQFindex(row+1, col));
            preventBW.union(getQFindex(row, col),getQFindex(row+1, col));
        }
        if (col > 1 && isOpen(row , col-1)) {
            wf.union(getQFindex(row, col) , getQFindex(row, col-1));
            preventBW.union(getQFindex(row, col),getQFindex(row, col-1));
        }
        if (col < size && isOpen(row, col+1)) {
            wf.union(getQFindex(row, col), getQFindex(row, col+1));
            preventBW.union(getQFindex(row, col),getQFindex(row, col+1));
        }

    }   // open site (row, col) if it is not open already
    public boolean isOpen(int row, int col) {

        return matrix[row-1][col-1];
    } // is site (row, col) open?
    public boolean isFull(int row, int col)  {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            return false;
        }
        if (row == 1) {
            return true;
        }

        return preventBW.connected(top, getQFindex(row, col));
    } // is site (row, col) full?
    public int numberOfOpenSites()   {

        return opensite;
    }    // number of open sites
    public boolean percolates()       {

        return wf.connected(top, bottom);
    }       // does the system percolate?
    private int getQFindex(int row, int col) {
        return size*(row-1)+col;
    }
    public static void main(String[] args) {


    }   // test client (optional)s
}
