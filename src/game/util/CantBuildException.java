package game.util;	

public class CantBuildException extends Exception {

	/**
	 * A exception when you cant't build on a tile  due to different reason
	 */
	public CantBuildException() {
		super();
	}

	/**
	 * the same with a text
	 * @param arg0 the arg
	 */
    public CantBuildException(String arg0) {
		super(arg0);
	}


}
