package fr.dauphine.robombastic;

import java.util.Set;

/** Context send to a bot by the game. (A BotContext should be unique for each Bot) 
 *  This interface permits the bot to interact with the game
 *  by asking information using {@link #info(Class) info(infoType)} or
 *  acting using {@link #nextTurn(Action) nextTurn(action)}.
 * 
 * @author remi, florian, benjamin 
 * @version 1.0
 */
public interface BotContext {
  /** marker interface of all informations provided by the game.
   * 
   * @see BotContext#infoTypes()
   */
  public interface Info {
    // marker interface
  }
  
  /** marker interface of all actions allowed to a bot.
   * @param <R> type of the response to an action.
   * 
   * @see BotContext#actionTypes()
   */
  public interface Action<R extends ActionResponse> {
   // marker interface
  }
  
  /** marker interface of all responses retruns by the game. 
   * 
   * @see BotContext#nextTurn(BotContext.Action)
   */
  public interface ActionResponse {
    // marker interface
  }
  
  
  /**
   * @return the GeneralInfo object that contains rules about the game. 
   */
  public GeneralInfo getGeneralInfo();
  
  /**
   * @return the CurrentBotInfo object attached to the current bot. (Contains bot specific informations.)
   */
  public CurrentBotInfo getCurrentBotInfo();
  
  
  /** Informations interfaces provided by the game to the bot. 
   *  Theses classes can be used as argument of {@link #info(Class) info(Class<Info>)}
   *  to obtain an object implementing the interface.
   *  
   *  version 1 of the game required interfaces : {@link CurrentBotInfo GameInfo},
   *  {@link ArenaInfo ArenaInfo} and {@link GeneralInfo BombInfo}.
   *  
   *  @return a set of all interfaces provided by the game.
   * 
   *  @see #info(Class)
   */
  public Set<? extends Class<? extends Info>> infoTypes();
  
  /** return an object provided by the game that implements interfaces
   *  specified by infoType.
   *  
   * @param <I> type of the interface.
   * @param infoType class of the interface.
   * @return an object that implements the interface.
   * 
   * @exception IllegalArgumentException if infoType is not one of
   *  interfaces returns by {@link #infoTypes() infoType()}.
   * 
   * @see #infoTypes()
   */
  public <I extends Info> I info(Class<I> infoType);
  
  /** Ask the game to execute an action by the current bot.
   *  This method blocks until the action is treated by the game.
   * 
   * @param <A> type of the action.
   * @param <R> type of the response corresponding to the action.
   * @param action to performs, this action may be null if the bot just want
   *   to wait until the next game turn.
   * @return a response to the requested action.
   * 
   * @exception DeadBotException if the current bot is dead.
   * @exception IllegalArgumentException if the action is not a recognized
   *  action of the game.
   *  
   * @see CurrentBotInfo#isAlive()
   * @see CurrentBotInfo#getTourTime()
   */
  public <R extends ActionResponse,A extends Action<R>> R nextTurn(A action)
    throws DeadBotException;
}
