package main;

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
  public int getTokens() {
    return this.tokens;
  }

  /**
   * Add the tokens to the track section
   */
  public void addTokens() {
    this.tokens++;
  }

  /**
   * Remove the tokens from the track section
   */
  public void removeTokens() {
    if (this.tokens > 0) {
      this.tokens--;
    }
  }

  /**
   * Check if the track section has tokens
   *
   * @param count the number of tokens to check
   * @return true if the track section has tokens, false otherwise
   */
  public boolean hasTokens(int count) {
    return this.tokens >= count;
  }
}
