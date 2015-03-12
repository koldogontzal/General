package except;

public class NotRegistroException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1028446028916179217L;

	public NotRegistroException() {
		super();
	}
	
	public NotRegistroException(String err) {
		super(err);
	}

}
