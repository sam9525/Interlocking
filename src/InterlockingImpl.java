import java.util.HashMap;
import java.util.Map;

public class InterlockingImpl implements Interlocking {

  // Map of trains to their current track section
  private Map<String, Trains> trains = new HashMap<>();

  // Map of track sections to their current number of tokens
  private Map<Integer, TrackSections> trackSections = new HashMap<>();

  public InterlockingImpl() {
    initializeTrackSections();
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

    for (int id : sectionIds) {
      trackSections.put(
        id,
        new TrackSections(id, id == 50 || id == 60 || id == 70 ? 1 : 0)
      );
    }
  }
}
