package fr.dauphine.robombastic;

/** Exceptions raised when a dead bot try to
 *  interact with the game by calling some methods. 
 *  
 * @author remi, florian, benjamin 
 * @version 1.0
 * 
 * @see fr.dauphine.robombastic.Bot
 * @see fr.dauphine.robombastic.CurrentBotInfo#isAlive()
 * @see fr.dauphine.robombastic.BotContext#nextTurn(BotContext.Action)
 * @see fr.dauphine.robophine.ArenaInfo#radar()
 * @see fr.dauphine.robombastic.CurrentBotInfo#getAllAliveBots()
 */
public class DeadBotException extends Exception {
  /**
   * Constructs a new exception with the specified detail message.
   * @param message detail message of the exception
   */
  public DeadBotException(String message) {
    super(message);
  }
  
  /**
   * Constructs a new exception with the specified detail message
   * and a cause.
   * @param message detail message of the exception
   * @param cause cause of the exception
   */
  public DeadBotException(String message,Throwable cause) {
    super(message,cause);
  }
  
  private static final long serialVersionUID = -4956725966578230432L;
}
