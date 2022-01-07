package toolKit;

/**
 * Has the necessary functions to merge sort distances to all Rows
 * by the distance to get to them.
 */
public class EuclideanDistanceMergeSorter {

    private final Row[] dataPoints;

    public EuclideanDistanceMergeSorter(Row sourceDataPoint,Row[] dataPoints) {
        this.dataPoints = dataPoints;
        //assign distances to data points
        for(int rowIndex = 0; rowIndex < dataPoints.length; rowIndex++) {
            Row row = dataPoints[rowIndex];
            double distanceToRow = sourceDataPoint.getEuclideanDistanceTo(row);
            row.setDistance(distanceToRow);
        }
    }

    public static Row[] sort(Row[] dataPoints) {
        if(dataPoints.length <= 1) return dataPoints;

        Row[] left = new Row[dataPoints.length / 2];
        Row[] right = new Row[dataPoints.length - (dataPoints.length / 2)];
        System.arraycopy(dataPoints, 0, left,0, dataPoints.length / 2);
        System.arraycopy(dataPoints, dataPoints.length / 2 + 1, right, 0, dataPoints.length - (dataPoints.length / 2));

        sort(left);
        sort(right);

        return merge(left, right);
    }

    private static Row[] merge(Row[] left, Row[] right) {
        Row[] mergedArray = new Row[left.length + right.length];

        int mergeIndex = mergedArray.length - 1;

        for(int index = 0; index < left.length; index++, mergeIndex--) {
            double distanceToLeft = left[index].getDistance();
            double distanceToRight = right[index].getDistance();
            if(distanceToLeft > distanceToRight)
                mergedArray[mergeIndex] = right[index];
             else
                mergedArray[mergeIndex] = left[index];
        }
        return null;
    }
}
