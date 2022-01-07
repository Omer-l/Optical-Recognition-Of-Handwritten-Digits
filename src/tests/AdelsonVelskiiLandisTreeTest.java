package tests;

import org.junit.Test;
import toolKit.AdelsonVelskiiLandisTree;
import toolKit.Row;

public class AdelsonVelskiiLandisTreeTest {

    //traversing the list
    @Test
    public void insert() {
        AdelsonVelskiiLandisTree bst = new AdelsonVelskiiLandisTree();
        Row row1 = new Row(new double[]{1.0, 2.0, 3.0}, 2);
        row1.setDistance(4);
        Row row2 = new Row(new double[]{1.0, 2.0, 3.0}, 3);
        row2.setDistance(2);
        Row row3 = new Row(new double[]{1.0, 2.0, 3.0}, 4);
        row3.setDistance(99);
        Row row4 = new Row(new double[]{1.0, 2.0, 3.0}, 5);
        row4.setDistance(55);
        Row row5 = new Row(new double[]{1.0, 2.0, 3.0}, 5);
        row5.setDistance(1);
        Row row6 = new Row(new double[]{1.0, 2.0, 3.0}, 5);
        row6.setDistance(3);
        Row row7 = new Row(new double[]{1.0, 2.0, 3.0}, 5);
        row7.setDistance(21);
        Row row8  = new Row(new double[]{1.0, 2.0, 3.0}, 5);
        row8.setDistance(43);

        bst.insert(row1);
        bst.insert(row2);
        bst.insert(row3);
        bst.insert(row4);
        bst.insert(row5);
        bst.insert(row6);
        bst.insert(row7);
        bst.insert(row8);
    }
    //edge cases:
    //traversing an empty list
    //traversing list where 1+ stored values are NULL
    //operations (if applicable):
    //deleting from the list
    //inserting into the list
    //inserting a sub-list into the linked list
    //traversing the list backwards (if doubly-linked list)
    //concurrency tests (if applicable):
    //race condition tests
}
