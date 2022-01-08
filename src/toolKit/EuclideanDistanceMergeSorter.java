package toolKit;

/**
 * Has the necessary functions to merge sort distances to all Rows
 * by the distance to get to them.
 */
public class EuclideanDistanceMergeSorter {

    //calls the recursive function to sort.
    public static void sort(Row sourceDataPoint,Row[] dataPoints) {
        assignDistances(sourceDataPoint, dataPoints);
        int beginningIndex = 0;
        int lastIndex = dataPoints.length - 1;
        sort(dataPoints, beginningIndex, lastIndex);
    }

    //Calculates the distance from a source data point to all other data points
    private static void assignDistances(Row sourceDataPoint,Row[] dataPoints) {
        //assign distances to data points
        for(int rowIndex = 0; rowIndex < dataPoints.length; rowIndex++) {
            Row row = dataPoints[rowIndex];
            double distanceToRow = sourceDataPoint.getEuclideanDistanceTo(row);
            row.setDistance(distanceToRow);
        }
    }

    /** divides and conquers using a merge sort approach.
     * @param arr   the array to sort
     * @param l     the beginning index of the array
     * @param r     the last index of the array
     */
    private static void sort(Row[] arr, int l, int r) {
        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    /**
     * merge sorts the divided array
     * @param arr   the array to sort
     * @param l     the beginning index of the array
     * @param m     the middle index of the array
     * @param r     the last index of the array
     */
    private static void merge(Row[] arr, int l, int m, int r) {
        //lengths of the two subarrays, this will be used to merge them
        int n1 = m - l + 1;
        int n2 = r - m;

        //arrays for merging
        Row[] L = new Row[n1];
        Row[] R = new Row[n2];

        //copy data into temp arrays
        for (int i = 0; i < n1; i++)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[m + 1 + j];

        int i = 0;
        int j = 0;

        //initial index of the merged subarray
        int k = l;

        while(i < n1 && j < n2)
            if(L[i].getDistance() <= R[j].getDistance())
                arr[k++] = L[i++];
            else
                arr[k++] = R[j++];

        /* Remaining elements of L[] */
        while(i < n1)
            arr[k++] = L[i++];

        /* Remaining elements of R[] */
        while(j < n2)
            arr[k++] = R[j++];
    }
}
