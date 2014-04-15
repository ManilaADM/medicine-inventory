package exceptions;

public class InsufficientCountException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -865686544471958900L;

	public InsufficientCountException()
	{
		
	}
	
	public InsufficientCountException(String message)
	{
		super(message);
	}
}
