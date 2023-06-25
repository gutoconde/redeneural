package br.com.gutoconde.redeneural;

public class EntropiaCruzada implements FuncaoDeCusto {

	@Override
	public Double calcular(Double[] saidas, Double[] saidasEperadas) {
		assert saidas.length == saidasEperadas.length;
		double somatorio = 0.0;
		for(int i = 0; i < saidas.length; i++) {
			somatorio += saidasEperadas[i] * Math.log(saidas[i]);
		}
		return -somatorio;
	}

}
