package hw3.hash;

import org.junit.Test;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        /* TODO: Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */
        int lngth = 52;
        SimpleOomage [][][] spom = new SimpleOomage[lngth][lngth][lngth];
        for (int k = 0; k < lngth; k ++ ) {
            for (int i = 0; i < lngth; i ++ ) {
                for (int j = 0; j < lngth; j ++) {
                    spom[i][j][k] = new SimpleOomage(i*5, j*5, k*5);

                    int ord = posToOrd(i, j, k);
                    for (int o = ord - 1; o >= 0; o--) {
                        int[] pos = ordToPos(o);
                        int x = pos[0];
                        int y = pos[1];
                        int z = pos[2];


                        int sum1 = i + j + k;
                        int sum2 = x + y + z;
                        //System.out.println(spom[x][y][z].red + ", " + spom[x][y][z].green + ", " + spom[x][y][z].blue + "/5= " + sum2);
                        //System.out.println(spom[i][j][k].red + ", " + spom[i][j][k].green + ", " + spom[i][j][k].blue + "/5= " + sum1);
                        assertTrue(spom[x][y][z].hashCode() != spom[i][j][k].hashCode());
                        //System.out.println((spom[i][j][k].hashCode() == spom[x][y][z].hashCode()) +"=====");

                    }
                }
            }
        }

    }

    public int posToOrd(int x, int y, int z) {
        int lngth = 52;
        return z*(lngth*lngth) + x * lngth + y;
    }

    public int[] ordToPos (int ord) {
        int lngth = 52;
        int[] ret = new int[3];
        ret[2] = ord / (lngth * lngth);
        ret[0] = ord % (lngth*lngth) / lngth;
        ret[1] = ord % (lngth*lngth) % lngth;
        return ret;
    }

    @Test
    public void testOrdToPos() {
        int[] toPrint = ordToPos(posToOrd(1, 2, 3));
        for (int i = 0; i < 3; i ++) {
            System.out.print(toPrint[i] + " ");
        }
        System.out.println();
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(0, 15, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    /* TODO: Uncomment this test after you finish haveNiceHashCode Spread in OomageTestUtility */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
