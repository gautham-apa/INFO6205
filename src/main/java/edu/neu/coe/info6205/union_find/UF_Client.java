package edu.neu.coe.info6205.union_find;
import java.util.*;
public class UF_Client {
    public static void main(String args[]) {
        System.out.println("Enter the value for n: ");
        Scanner br = new Scanner(System.in);
        int n = br.nextInt();
        System.out.println("Number of generated pairs = "+count(n));
        System.out.println("\n\n");
        generateDataPoints(10);
    }

    public static int count(int n) {
        Random rand = new Random();
        UF_HWQUPC h = new UF_HWQUPC(n);
        int generatedCount = 0;

        while (h.components() > 1) {
            int node1 = rand.nextInt(n);
            int node2 = rand.nextInt(n);
            generatedCount++;
            if(!h.connected(node1, node2)) {
                h.union(node1, node2);
            }
        }
        return generatedCount;
    }

    private static void generateDataPoints(int runs) {
        for(int i=1; i<24; i++) {
            int n = 1<<i;
            int avgPairsGenerated = 0;
            for(int j=0; j<runs; j++) {
                avgPairsGenerated += count(n);
            }
            avgPairsGenerated /= runs;
            System.out.println("Average number of pairs generated for n="+n+" is "+avgPairsGenerated);
        }
    }
}
