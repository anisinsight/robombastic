package fr.dauphine.robombastic;

/** Bomb action.
 *  The only possible action is to drop a bomb.
 *  There are two possible responses :
 *  <ol>
 *   <li>BOMB_DROPPED : the bomb was dropped on the arena
 *       at the current bot position
 *   <li>NO_MORE_BOMB : if all bombs available for the current bot
 *       are already in the arena. 
 *       see {@link fr.dauphine.robombastic.GeneralInfo#getMaxBombs() BombInfo.getMaxBombs()}.
 *       The bot needs to wait until some bomb exploded.
 *  </ol>
 * 
 * @author remi, florian, benjamin 
 * @version 1.0
 * 
 * @see fr.dauphine.robombastic.BotContext#nextTurn(BotContext.Action)
 */
public enum BombAction implements BotContext.Action<BombAction.BombResponse> {
  /** action to drop a bomb.
   */
  DROP_BOMB;
  
  /** values returned by a call
   * {@link BotContext#nextTurn(BotContext.Action) BotContext.nextTurn(DROP_BOMB)}
   */
  public enum BombResponse implements BotContext.ActionResponse {
    /** the bomb was drop at the bot position.
     */
    BOMB_DROPPED, 
    
    /** all bombs available are already dropped.
     */
    NO_MORE_BOMB
  }
}
