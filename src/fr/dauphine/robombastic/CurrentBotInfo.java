package fr.dauphine.robombastic;

import java.util.List;
import java.util.Map;

/** Informations available to the current Bot. There is one CurrentBotInfo object per BotContext.
 * 
 * @author remi, florian, benjamin 
 * @version 1.0
 * 
 * @see fr.dauphine.robombastic.BotContext#infoTypes()
 * @see fr.dauphine.robombastic.BotContext#info(Class)
 */
public interface CurrentBotInfo extends BotContext.Info{
	
  /** Returns all alive bots from the same army that the current bot.
   * @return a read-only list of bots of the same army that the current bot.
   * 
   * @exception DeadBotException if the current bot is dead.
   * 
   * @see #isAlive()
   */
  public List<? extends Bot> getAllAliveFriendlyBots() throws DeadBotException;
  
  /** Return true if the current bot is alive.
   * @return true if the current bot is alive, false else.
   * 
   * @see #getAllAliveBots()
   */
  public boolean isAlive();
  

  /** Returns a snapshot of the items with their position near current bot.
   *  Each items are stored in a map with their position
   *  relative to the position of the current bot.
   *  Example: an item with position (-1,0) is on the
   *  same line at the left of the bot. 
   *  
   *  Implementation Note: 
   *   At position (0,0), a FRIEND_BOT will never be reported.
   *   At other position if BOMB and ENEMY_BOT (resp. FRIEND_BOT)
   *   are on the same cell only an ENEMY_BOT (resp. FRIEND_BOT) will be
   *   reported.
   *   Futhermore, EMPTY items are not stored in the map.
   *  
   * @return a read-only map containing a pairs of position and arena item.
   * @throws DeadBotException if the current bot is dead.
   * 
   * @see CurrentBotInfo#isAlive()
   */
  public Map<? extends Position,ArenaItem> radar() throws DeadBotException;
  
}
