package br.com.gutoconde.redeneural;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FuncaoSigmoidTest {

	@ParameterizedTest
	@CsvSource({
		"1.0, 0.7310585786300049", "1.5, 0.8175744761936437", 
		"0.5, 0.6224593312018546", "0.0, 0.5",
		"10.0, 0.9999546021312976", "100.0, 1.0"}
	)
	public void testarResultados(Double entrada, Double resultadoEsperado) {
		FuncaoSigmoid func = new FuncaoSigmoid();
		assertEquals(resultadoEsperado, func.calcular(entrada));
		
	}
	
	@ParameterizedTest
	@CsvSource({
		"1.0, 0.19661193324148186", "1.5, 0.14914645207033286", 
		"0.5, 0.2350037122015945", "0.0, 0.25",
		"10.0, 4.5395807735907655E-05", "100.0, 0.0"}
	)
	public void testarDerivada(Double entrada, Double resultadoEsperado) {
		FuncaoSigmoid func = new FuncaoSigmoid();
		assertEquals(resultadoEsperado, func.calcularDerivada(entrada));
		
	}
	
}
