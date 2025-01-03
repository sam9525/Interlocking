public class Arcs {

  TrackSections departure;
  TrackSections destination;
  int weight;

  public Arcs(TrackSections departure, TrackSections destination, int weight) {
    this.departure = departure;
    this.destination = destination;
    this.weight = weight;
  }

  /**
   * Get the departure track section
   *
   * @return the departure track section
   */
  private TrackSections getDeparture() {
    return this.departure;
  }

  /**
   * Get the destination track section
   *
   * @return the destination track section
   */
  private TrackSections getDestination() {
    return this.destination;
  }

  /**
   * Get the weight of the arc
   *
   * @return the weight of the arc
   */
  private int getWeight() {
    return this.weight;
  }
}
