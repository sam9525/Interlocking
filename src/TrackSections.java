public class TrackSections {

  int id;
  int tokens;

  public TrackSections(int id, int tokens) {
    this.id = id;
    this.tokens = tokens;
  }

  /**
   * Get the tokens of the track section
   *
   * @return the tokens of the track section
   */
  private int getTokens() {
    return this.tokens;
  }

  /**
   * Add the tokens to the track section
   *
   * @param tokens the tokens to add to the track section
   */
  private void addTokens(int tokens) {
    this.tokens += tokens;
  }

  /**
   * Remove the tokens from the track section
   *
   * @param tokens the tokens to remove from the track section
   */
  private void removeTokens(int tokens) {
    this.tokens -= tokens;
  }

  /**
   * Check if the track section has tokens
   *
   * @param count the number of tokens to check
   * @return true if the track section has tokens, false otherwise
   */
  private boolean hasTokens(int count) {
    return this.tokens >= count;
  }
}
