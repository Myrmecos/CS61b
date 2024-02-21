package hw2;

import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*takes an integer of the side len of grid
  throw exception if side len <= 0
 */
public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    //elements in grid: 0-blocked; 1-empty; 2-filled with whatsoever liquid
    //numberOfOpenSites: total number of empty but not filled sites
    private int N;
    int nOpenedSites;
    int[] openCloseLst;
    QuickUnionUF quu_topSentinel;
    QuickUnionUF quu_doubleSentinel;

    public Percolation(int N) {
        if (N <= 0) {throw new java.lang.IllegalArgumentException("invalid input");}
        this.N = N;
        int baseLen = N * N; //total number of blocks
        this.openCloseLst = new int[baseLen]; // a list of int for recording open/close state of each block
        for (int i = 0; i < N * N; i ++ ) {
            openCloseLst[i] = 0;
        }
        this.quu_topSentinel = new QuickUnionUF(baseLen + 1); // disjoint set with only top connected to sentinel
        this.quu_doubleSentinel = new QuickUnionUF(baseLen + 2); // disjoint set with top row connected to one sentinel baselen
        // and bottom row connected to another sentinel baselen + 1

        int finalBase = N * (N - 1) - 1;
        for (int i = 0; i < N; i ++) {
            quu_topSentinel.union(i, baseLen);
            quu_doubleSentinel.union(i, baseLen);
            quu_doubleSentinel.union(finalBase + 1 + i, baseLen + 1);
        }
    }



    /* open the site (row, col) if it is not open already
    *@ param row, col number (starting from 0)
    * @return void
    * method: first change state of openCloseLst from 0 to 1, if the site is not opened yet
    * add 1 to space count
    * link spaces
     */
    public void open(int row, int col)   {
        int ord = grid2ord(row, col);
        if (isOpen(row, col)) {
            return;
        }
        this.openCloseLst[ord] = 1;
        nOpenedSites += 1;
        int oneLessRow = row - 1;
        int oneLessCol = col - 1;
        int oneMoreRow = row + 1;
        int oneMoreCol = col + 1;
        System.out.println(ord);
        if (oneLessCol >= 0 && isOpen(row, oneLessCol)) {
            int ord1 = grid2ord(row, oneLessCol);
            link(ord, ord1);
            //System.out.println("link " + ord + ord1 );
        }
        if (oneMoreCol < this.N && isOpen(row, oneMoreCol)) {
            int ord1 = grid2ord(row, oneMoreCol);
            link(ord, ord1);
            //System.out.println("link" + ord + ord1);
        }
        if (oneLessRow >= 0 && isOpen(oneLessRow, col)) {
            int ord1 = grid2ord(oneLessRow, col);
            link(ord, ord1);
            //System.out.println("link" + ord + ord1);
            }
        if (oneMoreRow < this.N && isOpen(oneMoreRow, col)) {
            int ord1 = grid2ord(oneMoreRow, col);
            link(ord, ord1);
            //System.out.println("link" + ord + ord1);
        }
    }

    /*link the two sets containing ord1 and ord2*/
    private void link(int ord1, int ord2) {
        quu_doubleSentinel.union(ord1, ord2);
        quu_topSentinel.union(ord1, ord2);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int ord = grid2ord(row, col);
        return openCloseLst[ord] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int ord = grid2ord(row, col);
        return (quu_topSentinel.connected(N*N, ord) && isOpen(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return nOpenedSites;
    }
    // does the system percolate?
    public boolean percolates() {
        return this.quu_doubleSentinel.connected(N*N, N*N+1);
    }              // does the system percolate?
    /* use for unit testing (not required)
     */

    //grid location to order
    public int grid2ord(int row, int col) {
        return this.N * (row) + col;
    }

    public int[] ord2grid(int ord) { return new int[] {ord / this.N, ord % this.N}; }
    public static void main(String[] args) {
       Percolation p = new Percolation(4);
       //constructor
       boolean b = p.quu_doubleSentinel.connected(3, 16);
       System.out.println(b);
       //grid2ord
       System.out.println(p.grid2ord(1, 1));//5 expected
        //open
        p.open(0, 0);
        p.open(0, 2);
        p.open(1, 2);
        p.open(2, 2);
        p.open(2, 1);
        p.open(2, 0);
        p.open(3, 0);
        System.out.println(p.quu_doubleSentinel.connected(0, 3));
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites());

        //isfull
        System.out.println("=================");
        p.open(3, 3);
        System.out.println(p.isFull(3, 0));
        System.out.println(p.isFull(3, 3));

    }
}

