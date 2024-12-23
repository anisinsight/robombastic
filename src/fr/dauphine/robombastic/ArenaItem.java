package fr.dauphine.robombastic;

/** This enumeration represents all item types of the arena.
 *  This enum is used as a response for a movement using
 *  {@link fr.dauphine.robombastic.BotContext#nextTurn(BotContext.Action) BotContext.nextTurn(action)}
 *  or to indicate visible items using the
 *  {@link fr.dauphine.robophine.ArenaInfo#radar() radar}. 
 * 
 * @author remi, florian, benjamin 
 * @version 1.0
 * 
 * @see fr.dauphine.robombastic.BotContext#nextTurn(BotContext.Action)
 * @see fr.dauphine.robophine.ArenaInfo#radar()
 */
public enum ArenaItem implements BotContext.ActionResponse {
  /** represents an empty cell.
   */
  EMPTY, 
  
  /** represents a bomb.
   */
  BOMB, 
  
  /** represents a wall.
   */
  WALL, 
  
  /** represents a bot from another army.
   */
  ENEMY_BOT, 
  
  /** represents a bot from the same army that the current bot.
   */
  FRIEND_BOT

}