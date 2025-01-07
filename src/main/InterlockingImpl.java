package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterlockingImpl implements Interlocking {

  // Map of trains to their current track section
  private Map<String, Trains> trains = new HashMap<>();

  // Map of track sections to their current number of tokens
  private static Map<Integer, TrackSections> trackSections = new HashMap<>();

  // List of transitions
  private List<Transitions> transitions = new ArrayList<>();

  public InterlockingImpl() {
    initializeTrackSections();
    initializeTransitions();
    initializeArcs();
  }

  /**
   * Initialize the track sections
   */
  private void initializeTrackSections() {
    int[] sectionIds = {
      1,
      5,
      9,
      2,
      6,
      10,
      8,
      3,
      7,
      11,
      4,
      51,
      50,
      61,
      60,
      71,
      70,
    };

    // Add track sections to the map
    for (int id : sectionIds) {
      trackSections.put(
        id,
        new TrackSections(id, id == 50 || id == 60 || id == 70 ? 1 : 0)
      );
    }
  }

  /**
   * Initialize the transitions
   */
  private void initializeTransitions() {
    String[] transitionIds = {
      "ts15",
      "ts59",
      "ts51",
      "ts95",
      "ts26",
      "ts610",
      "ts106",
      "ts62",
      "ts58",
      "ts85",
      "ts69",
      "ts96",
      "ts68",
      "ts86",
      "ts37",
      "ts711",
      "ts117",
      "ts73",
      "ts34",
      "ts43",
    };

    // Add transitions to the list
    for (String id : transitionIds) {
      transitions.add(new Transitions(id));
    }
  }

  /**
   * Initialize the arcs
   */
  private void initializeArcs() {
    // add arcs
    addArcs(transitions.get(0), 1, 50, 51, 5, 1); // ts1-5
    addArcs(transitions.get(1), 5, 51, 9, 50, 1); // ts5-9
    addArcs(transitions.get(2), 5, 51, 1, 50, 1); // ts5-1
    addArcs(transitions.get(3), 9, 50, 5, 51, 1); // ts9-5
    addArcs(transitions.get(4), 2, 60, 61, 6, 1); // ts2-6
    addArcs(transitions.get(5), 6, 61, 10, 60, 1); // ts6-10
    addArcs(transitions.get(6), 10, 60, 6, 61, 1); // ts10-6
    addArcs(transitions.get(7), 6, 61, 2, 60, 1); // ts6-2
    addArcs(transitions.get(8), 5, 51, 8, 50, 1); // ts5-8
    addArcs(transitions.get(9), 8, 50, 5, 51, 1); // ts8-5
    addArcs(transitions.get(10), 6, 61, 9, 60, 1); // ts6-9
    addArcs(transitions.get(11), 9, 60, 6, 61, 1); // ts9-6
    addArcs(transitions.get(12), 6, 61, 8, 60, 1); // ts6-8
    addArcs(transitions.get(13), 8, 60, 6, 61, 1); // ts8-6
    addArcs(transitions.get(14), 3, 70, 7, 71, 1); // ts3-7
    addArcs(transitions.get(15), 7, 71, 11, 70, 1); // ts7-11
    addArcs(transitions.get(16), 11, 70, 7, 71, 1); // ts11-7
    addArcs(transitions.get(17), 7, 71, 3, 70, 1); // ts7-3
    addArcs3To4(transitions.get(18), 3, 4, 1); // ts3-4
    addArcs3To4(transitions.get(19), 4, 3, 1); // ts4-3
  }

  /**
   * Add arcs for transitions with 2 inputs and 2 outputs
   *
   * @param ts the transition
   * @param input1 the first input
   * @param input2 the second input
   * @param output1 the first output
   * @param output2 the second output
   * @param weight the weight of the arcs
   */
  public static void addArcs(
    Transitions ts,
    int input1,
    int input2,
    int output1,
    int output2,
    int weight
  ) {
    ts.addInputArc(new Arcs(trackSections.get(input1), null, weight));
    ts.addInputArc(new Arcs(trackSections.get(input2), null, weight));
    ts.addOutputArc(new Arcs(null, trackSections.get(output1), weight));
    ts.addOutputArc(new Arcs(null, trackSections.get(output2), weight));
  }

  /**
   * Add arcs for transitions with 1 input and 1 output
   *
   * @param ts the transition
   * @param input the input
   * @param output the output
   * @param weight the weight of the arcs
   */
  public static void addArcs3To4(
    Transitions ts,
    int input,
    int output,
    int weight
  ) {
    ts.addInputArc(new Arcs(trackSections.get(input), null, weight));
    ts.addOutputArc(new Arcs(null, trackSections.get(output), weight));
  }
}
