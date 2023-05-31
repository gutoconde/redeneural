package br.com.gutoconde.redeneural;

import java.util.List;

public interface FuncaoDeCusto {

	Double calcularCustoTotal(List<Double[]> listaDesaidasEperadas, List<Double[]> listaDeSaidas);
	
	Double calcular(Double[] saidasEperadas, Double[] saidas);
	
}
