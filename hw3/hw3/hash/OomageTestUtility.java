package hw3.hash;

import org.junit.Test;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] arrCnt = new int[M];
        for (Oomage o: oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            arrCnt[bucketNum] += 1;
        }
        int mx = maxArrElem(arrCnt);
        int mn = minArrElem(arrCnt);
        System.out.println("min: " + mn + "; max: " + mx);
        return ((mx < oomages.size()/2.5) & (mn > oomages.size()/50));
        //System.out.println(mx + ", " + M);
        //return mx < M;
    }

    private static int minArrElem(int[] arr) {
        int ret = arr[0];
        for (int i: arr) {
            if (i < ret) {
                ret = i;
            }
        }
        return ret;
    }

    private static int maxArrElem(int[] arr) {
        int ret = arr[0];
        for (int i: arr) {
            if (i > ret) {
                ret = i;
            }
        }
        return ret;
    }

    @Test
    public void testMinMax() {
        int [] arr = {1, 2, 3, 4, 6, 5, 4, 19, -2, 29};
        System.out.println(minArrElem(arr));
        System.out.println(maxArrElem(arr));
    }
}
