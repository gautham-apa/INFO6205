package edu.neu.coe.info6205.sort;
import edu.neu.coe.info6205.sort.elementary.HeapSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSortBasic;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_Basic;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.SorterBenchmark;
import edu.neu.coe.info6205.util.TimeLogger;
import edu.neu.coe.info6205.util.Utilities;

import static edu.neu.coe.info6205.sort.InstrumentedHelper.logger;

public class SortStatsBenchmark {
    private static final double LgE = Utilities.lg(Math.E);
    int nRuns = 1;
    public static void main(String args[]) {
        SortStatsBenchmark benchmark = new SortStatsBenchmark();
        benchmark.computeBenchmark();
    }

    private void computeBenchmark() {
        heapSort(10000);
        mergeSort(10000);
        quickSort(10000);
    }


    private void heapSort(int size) {
        System.out.println("HeapSort: Array size = "+size+" --------------------");
        Integer[] randomArray = generateRandomArray(size);
        Config config = Config.setupConfig("true", "0", "1", "", "");
        Helper<Integer> helper = HelperFactory.create("HeapSort", size, config);
        helper.init(size);
        SortWithHelper<Integer> sorter = new HeapSort<Integer>(helper);
        runBenchmarks(sorter, randomArray, nRuns);
    }

    private void mergeSort(int size) {
        System.out.println("MergeSort: Array size = "+size+" --------------------");
        Integer[] randomArray = generateRandomArray(size);
        Config config = Config.setupConfig("true", "0", "1", "", "");
        Helper<Integer> helper = HelperFactory.create("MergeSort", size, config);
        helper.init(size);
        SortWithHelper<Integer> sorter = new MergeSortBasic<Integer>(helper);
        runBenchmarks(sorter, randomArray, nRuns);
    }

    private void quickSort(int size) {
        System.out.println("QuickSort: Array size = "+size+" --------------------");
        Integer[] randomArray = generateRandomArray(size);
        Config config = Config.setupConfig("true", "0", "1", "", "");
        Helper<Integer> helper = HelperFactory.create("QuickSort", size, config);
        helper.init(size);
        SortWithHelper<Integer> sorter = new QuickSort_Basic<Integer>(helper);
        runBenchmarks(sorter, randomArray, nRuns);
    }

    private void runBenchmarks(SortWithHelper<Integer> sorter, Integer[] xs, int nRuns) {
        sorter.preProcess(xs);
        Integer[] ys = sorter.sort(xs);
        sorter.postProcess(ys);
        System.out.println(sorter.getHelper().showStats());
    }


    private Integer[] generateRandomArray(int size) {
        final Config config = Config.setupConfig("true", "0", "1", "", "");
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
