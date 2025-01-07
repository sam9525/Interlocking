import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterlockingImpl implements Interlocking {

  // Map of trains to their current track section
  private Map<String, Trains> trains = new HashMap<>();

  // Map of track sections to their current number of tokens
  private Map<Integer, TrackSections> trackSections = new HashMap<>();

  // List of transitions
  private List<Transitions> transitions = new ArrayList<>();

  public InterlockingImpl() {
    initializeTrackSections();
    initializeTransitions();
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
}
