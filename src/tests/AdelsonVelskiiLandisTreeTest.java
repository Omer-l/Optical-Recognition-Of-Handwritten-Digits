package tests;

import org.junit.Test;
import toolKit.AdelsonVelskiiLandisTree;
import toolKit.Row;

public class AdelsonVelskiiLandisTreeTest {

    //traversing the list
    @Test
    public void insert() {
        AdelsonVelskiiLandisTree avlTree = new AdelsonVelskiiLandisTree();
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

        avlTree.insert(row1);
        avlTree.insert(row2);
        avlTree.insert(row3);
        avlTree.insert(row4);
        avlTree.insert(row5);
        avlTree.insert(row6);
        avlTree.insert(row7);
        avlTree.insert(row8);
    }

    @Test
    public void getNearestNeighbours() {
        AdelsonVelskiiLandisTree avlTree = new AdelsonVelskiiLandisTree();
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


        avlTree.insert(row1);
        avlTree.insert(row2);
        avlTree.insert(row3);
        avlTree.insert(row4);
        avlTree.insert(row5);
        avlTree.insert(row6);
        avlTree.insert(row7);
        avlTree.insert(row8);

        avlTree.getNearestNeighbours(10);
    }
}
