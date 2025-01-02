public class Trains {

  String name;
  int destinationTrackSection;
  int currentTrackSection;

  public Trains(
    String name,
    int destinationTrackSection,
    int currentTrackSection
  ) {
    this.name = name;
    this.destinationTrackSection = destinationTrackSection;
    this.currentTrackSection = currentTrackSection;
  }

  /**
   * Get the name of the train
   *
   * @return the name of the train
   */
  private String getTrainName() {
    return this.name;
  }

  /**
   * Get the destination track section of the train
   *
   * @return the destination track section of the train
   */
  private int getDestinationTrackSection() {
    return this.destinationTrackSection;
  }

  /**
   * Get the current track section of the train
   *
   * @return the current track section of the train
   */
  private int getCurrentTrackSection() {
    return this.currentTrackSection;
  }

  /**
   * Set the current track section of the train
   *
   * @param currentTrackSection the current track section of the train
   */
  private void setCurrentTrackSection(int currentTrackSection) {
    this.currentTrackSection = currentTrackSection;
  }
}
