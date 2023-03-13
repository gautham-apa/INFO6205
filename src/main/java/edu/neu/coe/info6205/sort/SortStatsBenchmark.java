package edu.neu.coe.info6205.sort;
import edu.neu.coe.info6205.sort.elementary.HeapSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSortBasic;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_Basic;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.util.*;

import java.io.IOException;
import java.text.NumberFormat;

import static edu.neu.coe.info6205.sort.InstrumentedHelper.logger;

public class SortStatsBenchmark {
    private static final double LgE = Utilities.lg(Math.E);

    private final Config config;

    public static void main(String args[]) throws IOException {
        SortStatsBenchmark benchmark = new SortStatsBenchmark();
        benchmark.computeBenchmark();
    }

    private SortStatsBenchmark() throws IOException {
        config = Config.load();
    }

    private void computeBenchmark() {
        int nRuns = 32;
        for(int size=8000; size<=256000; size=size*2) {
                heapSort(size, nRuns);
                mergeSort(size, nRuns);
                quickSort(size, nRuns);
            System.out.println("\n");
        }
    }

    private void heapSort(int size, int nRuns) {
        System.out.println("HeapSort: Array size = "+size+" --------------------");
        Helper<Integer> helper = HelperFactory.create("HeapSort", size, config);
        helper.init(size);
        SortWithHelper<Integer> sorter = new HeapSort<Integer>(helper);
        runBenchmarks(sorter, size, nRuns);
    }

    private void mergeSort(int size, int nRuns) {
        System.out.println("MergeSort: Array size = "+size+" --------------------");
        Helper<Integer> helper = HelperFactory.create("MergeSort", size, config);
        helper.init(size);
        SortWithHelper<Integer> sorter = new MergeSortBasic<Integer>(helper);
        runBenchmarks(sorter, size, nRuns);
    }

    private void quickSort(int size, int nRuns) {
        System.out.println("QuickSort: Array size = "+size+" --------------------");
        Helper<Integer> helper = HelperFactory.create("QuickSort", size, config);
        helper.init(size);
        SortWithHelper<Integer> sorter = new QuickSort_DualPivot<>(helper);
        runBenchmarks(sorter, size, nRuns);
    }

    private void runBenchmarks(SortWithHelper<Integer> sorter, int size, int nRuns) {
        Integer[] xs = generateRandomArray(size);
        SorterBenchmark<Integer> sorterBenchmark = new SorterBenchmark<>(Integer.class, null, sorter, xs, nRuns, timeLoggersLinearithmic);
        sorterBenchmark.run(size);
        System.out.println("Stats= " + sorter.getHelper().showStats());
    }


    private Integer[] generateRandomArray(int size) {
        Helper<Integer> helper = HelperFactory.create("Sort", size, config);
        helper.init(size);
        Integer[] randomArray = helper.random(Integer.class, r -> r.nextInt(5000000));
        return randomArray;
    }

    public final static TimeLogger[] timeLoggersLinearithmic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n log n): ", (time, n) -> time / minComparisons(n) / 6 * 1e6)
    };

    static double minComparisons(int n) {
        double lgN = Utilities.lg(n);
        return n * (lgN - LgE) + lgN / 2 + 1.33;
    }
}
