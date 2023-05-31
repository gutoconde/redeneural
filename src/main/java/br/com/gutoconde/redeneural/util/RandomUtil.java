package br.com.gutoconde.redeneural.util;

import java.security.SecureRandom;

public class RandomUtil {
	
	private static SecureRandom secureRandom = new SecureRandom();
	
	public synchronized static  Double gerarNumeroAleatorio(double limiteInferior, double limiteSuperior) {
		return limiteInferior + secureRandom.nextDouble() * (limiteSuperior - limiteInferior);
	}
	
}
