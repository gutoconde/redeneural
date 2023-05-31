package br.com.gutoconde.redeneural;

public class RedeNeuralException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RedeNeuralException() {
		super();
	}

	public RedeNeuralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RedeNeuralException(String message, Throwable cause) {
		super(message, cause);
	}

	public RedeNeuralException(String message) {
		super(message);		
	}

	public RedeNeuralException(Throwable cause) {
		super(cause);
	}
}
