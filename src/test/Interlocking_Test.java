package test;

import static org.junit.Assert.assertEquals;

import main.Interlocking;
import main.InterlockingImpl;
import org.junit.Before;
import org.junit.Test;

public class Interlocking_Test {

  private Interlocking interlocking;

  @Before
  public void setUp() {
    interlocking = new InterlockingImpl();
  }

  @Test
  public void testAddTrain() {
    interlocking.addTrain("train1", 1, 9);
    assertEquals("train1", interlocking.getSection(1));
    assertEquals(1, interlocking.getTrain("train1"));

    interlocking.addTrain("train2", 2, 8);
    assertEquals("train2", interlocking.getSection(2));
    assertEquals(2, interlocking.getTrain("train2"));

    interlocking.addTrain("train3", 3, 11);
    assertEquals("train3", interlocking.getSection(3));
    assertEquals(3, interlocking.getTrain("train3"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTrainInvalidPath() {
    interlocking.addTrain("train1", 1, 3);
    interlocking.addTrain("train2", 2, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTrainOccupied() {
    interlocking.addTrain("train1", 1, 9);
    interlocking.addTrain("train1", 2, 8);
  }

  @Test(expected = IllegalStateException.class)
  public void testAddTrainTrackSectionOccupied() {
    interlocking.addTrain("train1", 1, 9);
    interlocking.addTrain("train2", 1, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSectionInvalidTrackSection() {
    interlocking.getSection(20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTrainInvalidTrainName() {
    interlocking.getTrain("train1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTrainsInvalidTrainName() {
    interlocking.moveTrains(new String[] { "train1", "train2", "train3" });
  }

  @Test
  public void testMoveTrainsLeftToRight() {
    interlocking.addTrain("train1", 1, 9);
    interlocking.addTrain("train2", 2, 10);
    interlocking.addTrain("train3", 3, 11);
    interlocking.moveTrains(new String[] { "train1", "train2", "train3" });
    assertEquals("train1", interlocking.getSection(5));
    assertEquals("train2", interlocking.getSection(6));
    assertEquals("train3", interlocking.getSection(7));

    interlocking.moveTrains(new String[] { "train1", "train2", "train3" });
    assertEquals("train1", interlocking.getSection(9));
    assertEquals("train2", interlocking.getSection(10));
    assertEquals("train3", interlocking.getSection(11));

    interlocking.moveTrains(new String[] { "train1", "train2", "train3" });
    assertEquals(null, interlocking.getSection(9));
    assertEquals(null, interlocking.getSection(10));
    assertEquals(null, interlocking.getSection(11));

    interlocking.addTrain("train4", 3, 4);
    interlocking.moveTrains(new String[] { "train4" });
    assertEquals("train4", interlocking.getSection(4));

    interlocking.moveTrains(new String[] { "train4" });
    assertEquals(null, interlocking.getSection(4));
  }

  @Test
  public void testMoveTrainsRightToLeft() {
    interlocking.addTrain("train1", 9, 1);
    interlocking.addTrain("train2", 10, 2);
    interlocking.addTrain("train3", 11, 3);
    interlocking.moveTrains(new String[] { "train1", "train2", "train3" });
    assertEquals("train1", interlocking.getSection(5));
    assertEquals("train2", interlocking.getSection(6));
    assertEquals("train3", interlocking.getSection(7));

    interlocking.moveTrains(new String[] { "train1", "train2", "train3" });
    assertEquals("train1", interlocking.getSection(1));
    assertEquals("train2", interlocking.getSection(2));
    assertEquals("train3", interlocking.getSection(3));

    interlocking.moveTrains(new String[] { "train1", "train2", "train3" });
    assertEquals(null, interlocking.getSection(1));
    assertEquals(null, interlocking.getSection(2));
    assertEquals(null, interlocking.getSection(3));

    interlocking.addTrain("train4", 4, 3);
    interlocking.moveTrains(new String[] { "train4" });
    assertEquals("train4", interlocking.getSection(3));

    interlocking.moveTrains(new String[] { "train4" });
    assertEquals(null, interlocking.getSection(3));
  }
}
