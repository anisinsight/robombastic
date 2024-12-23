package fr.dauphine.robombastic;

/** Represents position of arena item on the radar.
 * 
 * @author remi, florian, benjamin 
 * @version 1.0
 * 
 * @see fr.dauphine.robophine.ArenaInfo#radar()
 */
public interface Position {
  /** Returns the x coordinate of the position.
   * @return the x coordinate
   */
  int getX();
  
  /** Return the y coordinates of the position
   * @return the y coordinate
   */
  int getY();
}