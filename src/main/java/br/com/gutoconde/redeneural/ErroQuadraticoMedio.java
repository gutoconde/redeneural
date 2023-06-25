package br.com.gutoconde.redeneural;

public class ErroQuadraticoMedio implements FuncaoDeCusto {
	
	@Override
	public Double calcular(Double[] saidas, Double[] saidasEperadas) {
		Double erro = 0.0;
		for(int i =0; i< saidas.length; i++) {
			erro += Math.pow(saidas[i] - saidasEperadas[i]  , 2.0) / 2.0;
		}
		return erro;
	}

}
