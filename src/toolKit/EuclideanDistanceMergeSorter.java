package toolKit;

/**
 * Has the necessary functions to merge sort distances to all Rows
 * by the distance to get to them.
 */
public class EuclideanDistanceMergeSorter {

    //calls the recursive function to sort.
    public static void sort(Row sourceDataPoint,Row[] rows) {
        assignDistances(sourceDataPoint, rows);
        int beginningIndex = 0;
        int lastIndex = rows.length - 1;
        sort(rows, beginningIndex, lastIndex);
    }

    //Calculates the distance from a source data point to all other rows/data points
    private static void assignDistances(Row sourceDataPoint,Row[] rows) {
        //assign distances to data points
        for(Row row : rows) {
            double distanceToRow = sourceDataPoint.getEuclideanDistanceTo(row);
            row.setDistance(distanceToRow);
        }
    }

    /** divides and conquers using a merge sort approach.
     * @param rows              the array to sort
     * @param beginningIndex    the beginning index of the array
     * @param lastIndex         the last index of the array
     */
    private static void sort(Row[] rows, int beginningIndex, int lastIndex) {
        if (beginningIndex < lastIndex) {
            // Find the middle point
            int m =beginningIndex+ (lastIndex-beginningIndex)/2;

            // Sort first and second halves
            sort(rows, beginningIndex, m);
            sort(rows, m + 1, lastIndex);

            // Merge the sorted halves
            divideAndConquer(rows, beginningIndex, m, lastIndex);
        }
    }

    /**
     * Divides and then conquers by sorting the divided arrays
     * @param rows              is the array to sort
     * @param beginningIndex    is the beginning index of the array
     * @param middleIndex       is the middle index of the array
     * @param lastIndex         is the last index of the array
     */
    private static void divideAndConquer(Row[] rows, int beginningIndex, int middleIndex, int lastIndex) {
        //lengths of the two subarrays, this will be used to merge them
        int lengthOfSubArray1 = middleIndex - beginningIndex + 1;
        int lengthOfSubArray2 = lastIndex - middleIndex;

        //arrays for merging, these divide the array
        Row[] leftArray = new Row[lengthOfSubArray1];
        Row[] rightArray = new Row[lengthOfSubArray2];

        //copy data into divided arrays
        for (int leftArrayIndex = 0; leftArrayIndex < lengthOfSubArray1; leftArrayIndex++)
            leftArray[leftArrayIndex] = rows[beginningIndex + leftArrayIndex];
        for (int rightArrayIndex = 0; rightArrayIndex < lengthOfSubArray2; rightArrayIndex++)
            rightArray[rightArrayIndex] = rows[middleIndex + 1 + rightArrayIndex];

        conquer(rows, leftArray, rightArray, beginningIndex, middleIndex, lastIndex);
    }

    //Inserts left and right array back into the merged array in sorted order
    private static void conquer(Row[] rows, Row[] leftArray, Row[] rightArray, int beginningIndex, int middleIndex, int lastIndex) {
        int leftArrayIndex = 0;
        int rightArrayIndex = 0;
        //lengths of the two subarrays, this will be used to merge them
        int lengthOfSubArray1 = middleIndex - beginningIndex + 1;
        int lengthOfSubArray2 = lastIndex - middleIndex;

        //initial index of the merged subarray
        int mergedArrayIndex = beginningIndex;
        //merges the left and right arrays
        while(leftArrayIndex < lengthOfSubArray1 && rightArrayIndex < lengthOfSubArray2) {
            double leftDistance = leftArray[leftArrayIndex].getDistance();
            double rightDistance = rightArray[rightArrayIndex].getDistance();
            //assigns values to merged array in sorted order
            if (leftDistance <= rightDistance)
                rows[mergedArrayIndex++] = leftArray[leftArrayIndex++];
            else
                rows[mergedArrayIndex++] = rightArray[rightArrayIndex++];
        }

        /* Remaining elements of leftArray[] */
        while(leftArrayIndex < lengthOfSubArray1)
            rows[mergedArrayIndex++] = leftArray[leftArrayIndex++];

        /* Remaining elements of rightArray[] */
        while(rightArrayIndex < lengthOfSubArray2)
            rows[mergedArrayIndex++] = rightArray[rightArrayIndex++];
    }
}
