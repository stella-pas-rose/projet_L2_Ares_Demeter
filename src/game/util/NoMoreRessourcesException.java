package game.util;
/**
 * throw when you don't have ressources left
 */
public class NoMoreRessourcesException extends Exception {

	/**
	 * the exception
	 */
	public NoMoreRessourcesException() {
		super();
	}

	/**
	 * alternative version
	 * @param arg0 the arg
	 */
	public NoMoreRessourcesException(String arg0) {
		super(arg0);
	}


}
