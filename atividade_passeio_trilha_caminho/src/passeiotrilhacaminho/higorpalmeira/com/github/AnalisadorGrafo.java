package passeiotrilhacaminho.higorpalmeira.com.github;

import java.util.List;

/**
 * Programa que analisa um grafo informado nos argumentos de execução.
 * Modo de uso: java AnalisadorGrafo "V1 V2 V3" "011 101 110".
 * 
 * V: são os vértices do grafo.
 * "011": exemplo de lista de adjacência.
 * 
 * @author higor
 * 
 * */
public class AnalisadorGrafo {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Erro: São necessários exatamente dois argumentos.");
			System.out.println("Uso: java AnalisadorGrafos \"V1 V2 V3\" \"011 101 110\"");
			return;
		}
		
		String[] strVertices = args[0].trim().split("\\s+");
		String[] strMatriz = args[1].trim().split("\\s+");
		
		if (strVertices.length != strMatriz.length) {
			System.out.println("Erro: O número de vértices não corresponde ao número de linhas da matriz de adjacência.");
			return;
		}
		
		int numVertices = strVertices.length;
		int[][] matrizAdjacencia = new int[numVertices][numVertices];
		
		try {
			
			for (int i=0; i<numVertices; i++) {
				
				if (strMatriz[i].length() != numVertices) {
					System.out.println("Erro: A matriz de adjacência deve ser quadrada.");
					return;
				}
				
				for (int j=0; j<numVertices; j++) {
					
					matrizAdjacencia[i][j] = Character.getNumericValue(strMatriz[i].charAt(j));
					
				}
				
			}
			
		} catch (Exception e) {
			
			System.out.println("Erro: Ocorreu um erro na matriz de adjacência.");
			e.printStackTrace();
			return;
			
		}
		
		Grafo grafo = new Grafo(strVertices, matrizAdjacencia);
		
		System.out.println("\n===\tAnálise do Grafo\t===\n");
		
		System.out.print("Vértices: ");
		for (String v : strVertices) {
			System.out.print(v + " ");
		}
		System.out.println("\n");
		
		System.out.println("\n===\tÉ Euleriano?\t===");
		System.out.println(grafo.verificarEuleriano());
		System.out.println();
		
		String verticeInicial = strVertices[0];
		System.out.println("\n===\tExemplo de Sequências (iniciando em " + verticeInicial + ")\t===\n");
		
		List<String> listaPasseio = grafo.encontrarPasseio(verticeInicial, numVertices + 2);
		System.out.println("Passeio: " + formatLista(listaPasseio));
		
		List<String> listaTrilha = grafo.encontrarTrilha(verticeInicial);
		System.out.println("Trilha: " + formatLista(listaTrilha));
		
		List<String> listaCaminho = grafo.encontrarCaminho(verticeInicial);
		System.out.println("Caminho: " + formatLista(listaCaminho));
		
		System.out.println("\n===\tFim da Análise\t===\n");

	}

	public static String formatLista(List<String> lista) {
		
		if (lista == null || lista.isEmpty()) {
			return "[ ]";
		}
		
		StringBuilder strBuilder = new StringBuilder();
		
		for (int i=0; i<lista.size(); i++) {
			
			strBuilder.append(lista.get(i));
			
			if (i < lista.size() - 1) {
				strBuilder.append(" -> ");
			}
			
		}
		
		return strBuilder.toString();
		
	}
	
}
