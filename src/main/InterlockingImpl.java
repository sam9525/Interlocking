package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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

  /**
   * Add a train to the map
   *
   * @param trainName the name of the train
   * @param entryTrackSection the track section the train is entering from
   * @param destinationTrackSection the track section the train is exiting to
   * @throws IllegalArgumentException if the train name is already in use, or there is no valid path from the entry to the destination
   * @throws IllegalStateException if the entry track is already occupied
   */
  public void addTrain(
    String trainName,
    int entryTrackSection,
    int destinationTrackSection
  ) {
    // Check if train already exists
    if (trains.containsKey(trainName)) {
      throw new IllegalArgumentException("Train already exists");
    }

    // Check if the train is entering from the correct track section and have a valid path to the destination
    boolean isEntryFromStart = entryTrackSection == 1 || entryTrackSection == 2;
    boolean isEntryFromEnd =
      entryTrackSection == 8 ||
      entryTrackSection == 9 ||
      entryTrackSection == 10;

    boolean isValidDestinationFromStart =
      destinationTrackSection == 8 ||
      destinationTrackSection == 9 ||
      destinationTrackSection == 10;
    boolean isValidDestinationFromEnd =
      destinationTrackSection == 1 || destinationTrackSection == 2;
    boolean isValidFreightEntryFromStart = entryTrackSection == 3;
    boolean isValidFreightEntryFromEnd =
      entryTrackSection == 4 || entryTrackSection == 11;
    boolean isValidFreightDestinationFromStart =
      destinationTrackSection == 11 || destinationTrackSection == 4;
    boolean isValidFreightDestinationFromEnd = destinationTrackSection == 3;

    if (
      (isEntryFromStart && !isValidDestinationFromStart) ||
      (isEntryFromEnd && !isValidDestinationFromEnd) ||
      (
        !isEntryFromStart &&
        !isEntryFromEnd &&
        !isValidFreightEntryFromStart &&
        !isValidFreightEntryFromEnd
      ) ||
      (isValidFreightEntryFromStart && !isValidFreightDestinationFromStart) ||
      (isValidFreightEntryFromEnd && !isValidFreightDestinationFromEnd)
    ) {
      throw new IllegalArgumentException("Invalid path");
    }

    // Check if the entry track section is occupied
    if (trackSections.get(entryTrackSection).hasTokens(1)) {
      throw new IllegalStateException("Entry track section is occupied");
    }

    // Add the train to the map
    trains.put(
      trainName,
      new Trains(trainName, destinationTrackSection, entryTrackSection)
    );
    trackSections.get(entryTrackSection).addTokens();
  }

  /**
   * Move the trains
   *
   * @param trainNames the names of the trains to move
   * @return the number of trains that have moved
   * @throws IllegalArgumentException if the train name does not exist or is no longer in the rail corridor
   */
  public int moveTrains(String[] trainNames) {
    // Check if the train exists
    for (String trainName : trainNames) {
      if (!trains.containsKey(trainName)) {
        throw new IllegalArgumentException("Train does not exist");
      }
    }

    int trainsMoved = 0;

    // Check if the train is in the destination track section
    // If it is, remove the tokens from the destination track section
    for (String trainName : trainNames) {
      Trains curTrain = trains.get(trainName);

      if (
        curTrain.getCurrentTrackSection() ==
        curTrain.getDestinationTrackSection()
      ) {
        trackSections.get(curTrain.getDestinationTrackSection()).removeTokens();
        trains.remove(trainName);
        trainsMoved++;
      }
    }

    // Move the trains
    for (String trainName : trainNames) {
      Trains curTrain = trains.get(trainName);

      // Move the train to the next track section
      if (curTrain != null) {
        int trainCurrentTrackSection = curTrain.getCurrentTrackSection();
        int trainDestinationTrackSection = curTrain.getDestinationTrackSection();

        Transitions transitionToFire = findTransitionToFire(
          trainCurrentTrackSection,
          trainDestinationTrackSection
        );

        // Check if the transition can be fired and fire it
        if (transitionToFire != null && transitionToFire.isEnabled()) {
          transitionToFire.fire();
          trainsMoved++;

          int newSection = getNewSection(
            trainCurrentTrackSection,
            trainDestinationTrackSection
          );

          // Update train's position
          trains.get(trainName).setCurrentTrackSection(newSection);
        }
      }
    }

    return trainsMoved;
  }

  /**
   * Find the transition to fire by using breadth first search
   *
   * @param currentSection the current track section
   * @param destinationSection the destination track section
   * @return the transition to fire
   */
  private Transitions findTransitionToFire(
    int currentSection,
    int destinationSection
  ) {
    Set<Integer> visited = new HashSet<>();
    Queue<int[]> queue = new LinkedList<>();

    queue.offer(new int[] { currentSection, -1 });

    while (!queue.isEmpty()) {
      int[] current = queue.poll();
      int section = current[0];
      int transitionIndex = current[1];

      // If we've reached the destination, return the first transition
      if (section == destinationSection) {
        return transitionIndex != -1 ? transitions.get(transitionIndex) : null;
      }

      // Skip if we've already visited this section
      if (visited.contains(section)) {
        continue;
      }
      visited.add(section);

      // Explore all possible transitions from this section
      for (int i = 0; i < transitions.size(); i++) {
        Transitions transition = transitions.get(i);
        if (isValidTransition(transition, section)) {
          int nextSection = getNextSection(transition, section);
          // If the next section hasn't been visited, add it to the queue
          if (!visited.contains(nextSection)) {
            queue.offer(
              new int[] {
                nextSection,
                // If this is the first transition, store its index
                transitionIndex == -1 ? i : transitionIndex,
              }
            );
          }
        }
      }
    }

    return null;
  }

  /**
   * Check if the transition is valid
   *
   * @param transition the transition
   * @param currentSection the current track section
   * @return true if the transition is valid, false otherwise
   */
  private boolean isValidTransition(
    Transitions transition,
    int currentSection
  ) {
    for (Arcs inputArc : transition.inputArcs) {
      if (inputArc.getDeparture().id == currentSection) {
        return true;
      }
    }

    return false;
  }

  /**
   * Get the next section
   *
   * @param transition the transition
   * @param currentSection the current track section
   * @return the next track section
   */
  private int getNextSection(Transitions transition, int currentSection) {
    // Find the next section
    for (Arcs outputArc : transition.outputArcs) {
      if (outputArc.getDestination().id != currentSection) {
        return outputArc.getDestination().id;
      }
    }

    return -1;
  }

  /**
   * Get the new section
   *
   * @param currentSection the current track section
   * @param destinationSection the destination track section
   * @return the new track section
   */
  private int getNewSection(int currentSection, int destinationSection) {
    switch (currentSection) {
      case 1:
      case 9:
        if (destinationSection == 2) return 6;
        return 5;
      case 2:
      case 10:
        return 6;
      case 3:
        if (destinationSection == 4) return destinationSection;
        return 7;
      case 4:
        if (destinationSection == 3) return destinationSection;
        return 7;
      case 8:
        if (destinationSection == 1) return 5;
        break;
      case 5:
      case 6:
      case 7:
        return destinationSection;
      case 11:
        return 7;
    }

    return currentSection; // This should never happen
  }

  /**
   * Get the train name at a given track section
   *
   * @param trackSection the track section
   * @return the train name
   * @throws IllegalArgumentException if the track section does not exist
   */
  public String getSection(int trackSection) {
    if (!trackSections.containsKey(trackSection)) {
      throw new IllegalArgumentException("Track section does not exist");
    }

    TrackSections section = trackSections.get(trackSection);

    if (section.getTokens() > 0) {
      for (Trains train : trains.values()) {
        if (train.getCurrentTrackSection() == trackSection) {
          return train.getTrainName();
        }
      }
    }

    return null;
  }

  /**
   * Get the track section of a given train
   *
   * @param trainName the train name
   * @return the track section
   * @throws IllegalArgumentException if the train does not exist
   */
  public int getTrain(String trainName) {
    if (!trains.containsKey(trainName)) {
      throw new IllegalArgumentException("Train does not exist");
    }

    return trains.get(trainName).getCurrentTrackSection();
  }
}
