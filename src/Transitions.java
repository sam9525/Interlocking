import java.util.ArrayList;
import java.util.List;

public class Transitions {

  String id;
  List<Arcs> inputArcs = new ArrayList<>();
  List<Arcs> outputArcs = new ArrayList<>();

  public Transitions(String id) {
    this.id = id;
  }

  /**
   * Add an input arc to the transition
   *
   * @param arc the input arc to add
   */
  public void addInputArc(Arcs arc) {
    this.inputArcs.add(arc);
  }

  /**
   * Add an output arc to the transition
   *
   * @param arc the output arc to add
   */
  public void addOutputArc(Arcs arc) {
    this.outputArcs.add(arc);
  }

  /**
   * Check if the transition is enabled
   *
   * @return true if the transition is enabled, false otherwise
   */
  public boolean isEnabled() {
    for (Arcs arc : this.inputArcs) {
      if (!arc.getDeparture().hasTokens(arc.getWeight())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Fire the transition
   */
  public void fire() {
    if (this.isEnabled()) {
      for (Arcs arc : this.inputArcs) {
        arc.getDeparture().removeTokens();
      }
      for (Arcs arc : this.outputArcs) {
        arc.getDestination().addTokens();
      }
    }
  }
}
