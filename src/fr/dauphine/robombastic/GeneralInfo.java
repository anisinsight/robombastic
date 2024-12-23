package fr.dauphine.robombastic;

/** General informations about the game given to the Bot. There should be only one GeneralInfo object shared among all BotContext objects.    
 * 
 * @author remi, florian, benjamin 
 * @version 1.0
 * 
 * @see fr.dauphine.robombastic.BotContext#infoTypes()
 * @see fr.dauphine.robombastic.BotContext#info(Class)
*/
public interface GeneralInfo  extends BotContext.Info {
	
 /** Returns the arena width.
  * @return the arena width in cells.
  */
	public int getArenaWidth();
	  
	/** Returns the arena height
	 * @return the arena height in cells.
	 */
	public int getArenaHeight();
		
	  /** Returns the size of the radar.
	   *  By example, with size=1 the radar
	   *  cover the square from (-1,-1) to (1,1)
	   *  relative to the current bot position.
	   * @return the size of the radar in cells. 
	   */
	
	public int getRadarSize();
	  
	
  /** Returns delay in turn between a drop and the explosion of
   * bombs.
   * @return the duration of a bomb.
   */
  public int getBombDuration();
  
  /** Returns the maximum number of bombs dropped in the same time. 
   * @return the maximum number of bombs.
   */
  public int getMaxBombs();
  
  /** Returns the size of bomb explosion.
   * @return a size in cells or zero if
   *  the size is infinity.
   */
  public int getBombSize();
  

  /** Returns the time of a tour in milliseconds.
   * @return the time of a tour.
   */
  public long getTourTime();
}
