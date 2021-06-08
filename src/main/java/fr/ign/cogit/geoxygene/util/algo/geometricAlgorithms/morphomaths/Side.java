/**
 * @author julien Gaffuri 29 juin 2009
 */
package fr.ign.cogit.geoxygene.util.algo.geometricAlgorithms.morphomaths;

/**
 * @author julien Gaffuri 29 juin 2009
 */
public enum Side {
  /**
	 */
  LEFT,
  /**
	 */
  RIGHT;

  public Side inverse() {
    if (this.equals(LEFT))
      return RIGHT;
    return LEFT;
  }
}
