package edu.neu.coe.info6205.sort.InsertionSort;

import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.HelperFactory;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

public class InsertionSortAnalyzer {
    public static void main(String args[]) {
        InsertionSortAnalyzer analyzer = new InsertionSortAnalyzer();
        analyzer.runTestCases();
    }

    private void runTestCases() {
        // Array size = 1000
        runTestForArray(1000);
        // Array size = 2000
        runTestForArray(2000);
        // Array size = 4000
        runTestForArray(4000);
        // Array size = 8000
        runTestForArray(8000);
        // Array size = 16000
        runTestForArray(16000);
        // Array size = 32000
        runTestForArray(32000);
    }

    private void runTestForArray(int size) {
        Integer[] randomArr = randomElementArray(size);
        Integer[] partlySortedArr = partiallySortedArray(size);
        Integer[] fullySortedArr = sortedArray(size);
        Integer[] reverseSortedArray = reverseSortedArray(size);

        runTest(reverseSortedArray, size, "Reverse sorted array");
        runTest(randomArr, size, "Random element array");
        runTest(partlySortedArr, size, "Partially sorted array");
        runTest(fullySortedArr, size, "Fully sorted array");
        System.out.println("\n");
    }

    private void runTest(Integer[] arr, int size, String type) {
        SortWithHelper<Integer> sorter = new InsertionSort<Integer>();
        Benchmark_Timer timer = new Benchmark_Timer("Insertion sort for "+type,
                k -> {
            Integer[] ys = sorter.sort((Integer[])k);
        });
        double elapsedTime = timer.runFromSupplier(()->{
            return arr;
        }, 20);
        System.out.println("Insertion sort for type "+type+" of size "+size+" executed in "+elapsedTime+"(ms)");
    }

    private Integer[] randomElementArray(int size) {
        final Config config = Config.setupConfig("true", "0", "1", "", "");
        Helper<Integer> helper = HelperFactory.create("InsertionSort", size, config);
        helper.init(size);
        Integer[] randomArray = helper.random(Integer.class, r -> r.nextInt(5000));
        return randomArray;
    }

    private Integer[] partiallySortedArray(int size) {
        Integer[] arr = new Integer[size];
        Integer[] randomArr = randomElementArray(size);
        for(int i=0; i<size/2; i++) arr[i] = i+1;
        for(int i=size/2; i<size; i++) arr[i] = randomArr[i];
        return arr;
    }

    private Integer[] sortedArray(int size) {
        Integer[] arr = new Integer[size];
        for(int i=1; i<=size; i++) arr[i-1] = i;
        return arr;
    }

    private Integer[] reverseSortedArray(int size) {
        Integer[] arr = new Integer[size];
        for(int i=0; i<size; i++) arr[i] = size-i;
        return arr;
    }
}
