package exceptions;

public class ForzarUndoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1605032043050057722L;

	public ForzarUndoException () {
		super("Fuerza hacer un undo.");
	}
}
