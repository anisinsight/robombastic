package fr.dauphine.robombastic;

/** Bot movements in the Arena.
 *  All enum values can be used to indicate a
 *  movement using
 *  {@link fr.dauphine.robombastic.BotContext#nextTurn(BotContext.Action) BotContext.nextTurn(action)}.
 *  The possible return values are enum values of
 *  {@link fr.dauphine.robombastic.ArenaItem ArenaItem}.
 * 
 * @author remi, florian, benjamin 
 * @version 1.0
 * 
 * @see fr.dauphine.robombastic.BotContext#nextTurn(BotContext.Action)
 */
public enum ArenaAction implements BotContext.Action<ArenaItem> {
  /** ask the game to move the bot up.
   */
  UP,
  
  /** ask the game to move the bot down.
   */
  DOWN,
  
  /** ask the game to move the bot left.
   */
  LEFT,
  
  /** ask the game to move the bot right.
   */
  RIGHT;
}
