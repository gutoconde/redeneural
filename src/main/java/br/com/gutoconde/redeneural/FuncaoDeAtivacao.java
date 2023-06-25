package br.com.gutoconde.redeneural;

public interface FuncaoDeAtivacao {
	
	Double calcular(double entrada);
	
	Double calcularDerivada(double entrada);
	
	Double calcular(double entrada, Double entradas[]);
	
	Double calcularDerivada(double entrada, Double entradas[]);
	
	Double calcularFatorDeAjusteDoDelta(double entrada);

}
