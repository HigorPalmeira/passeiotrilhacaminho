package passeiotrilhacaminho.higorpalmeira.com.github;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que estrutura o grafo e métodos de interação no grafo. 
 * 
 * listaVertices lista com todos os vértices do grafo.
 * matrizAdjacencia matriz com a adjacência dos vértices.
 * numVertices número total de vértices no grafo.
 * 
 * @author higor
 * */
public class Grafo {
	
	private String[] listaVertices;
	private int[][] matrizAdjacencia;
	private int numVertices;
	
	public Grafo(String[] listaVertices, int[][] matrizAdjacencia) {
		
		this.listaVertices = listaVertices;
		this.matrizAdjacencia = matrizAdjacencia;
		this.numVertices = listaVertices.length;
		
	}
	
	public int getGrau(int idxVertice) {
		
		int grau = 0;
		
		for (int i=0; i<this.numVertices; i++) {
			
			grau += this.matrizAdjacencia[idxVertice][i];
			
		}
		
		if (this.matrizAdjacencia[idxVertice][idxVertice] == 1) grau++;
		
		return grau;
		
	}
	
	private void dfsToConectividade(int idxVertice, boolean[] listaVerticesVisitados) {
		
		listaVerticesVisitados[idxVertice] = true;
		
		for (int i=0; i<this.numVertices; i++) {
			
			if (this.matrizAdjacencia[idxVertice][i] > 0 && !listaVerticesVisitados[i]) {
				dfsToConectividade(i, listaVerticesVisitados);
			}
			
		}
		
	}
	
	public boolean isConexo() {
		
		if (this.numVertices == 0) return true;
		
		int verticeInicial = -1;
		
		for (int i=0; i<this.numVertices; i++) {
			
			if (getGrau(i) > 0) {
				verticeInicial = i;
				break;
			}
			
		}
		
		if (verticeInicial == -1) return this.numVertices <= 1;
		
		boolean[] listaVerticesVisitados = new boolean[this.numVertices];

		dfsToConectividade(verticeInicial, listaVerticesVisitados);
		
		for (int i=0; i<this.numVertices; i++) {
			
			if (getGrau(i) > 0 && !listaVerticesVisitados[i]) return false;
			
		}
		
		return true;
		
	}
	
	public String verificarEuleriano() {
		
		if (!isConexo()) {
			return "Não é Euleriano, porque não é conexo.";
		}
		
		int quantGrausImpar = 0;
		
		for (int i=0; i<this.numVertices; i++) {
			
			if (getGrau(i) % 2 != 0) quantGrausImpar++;
			
		}
		
		if (quantGrausImpar == 0) {
			return "É um grafo Euleriano.";
		} else if (quantGrausImpar == 2) {
			return "É um grafo semi-euleriano, possui uma trilha Euleriana.";
		} else {
			return "Não é Euleriano, porque possui " + quantGrausImpar + " vértices de grau ímpar.";
		}
		
	}
	
	public List<String> encontrarPasseio(String verticeInicial, int maxComprimento) {
		
		List<Integer> listaIndicesPasseio = new ArrayList<>();
		int idxInicial = getIndiceVertice(verticeInicial);
		
		if (idxInicial != -1) {
			dfsPasseio(idxInicial, listaIndicesPasseio, maxComprimento);
		}
		
		return indicesToVertices(listaIndicesPasseio);
		
	}
	
	private void dfsPasseio(int idxVertice, List<Integer> listaPasseioAtual, int maxComprimento) {
		
		listaPasseioAtual.add(idxVertice);
		
		if (listaPasseioAtual.size() >= maxComprimento) return;
		
		for (int v=0; v<this.numVertices; v++) {
			
			if (this.matrizAdjacencia[idxVertice][v] > 0) {
				dfsPasseio(v, listaPasseioAtual, maxComprimento);
				return; // pra não morrer aqui
			}
			
		}
		
	}
	
	public List<String> encontrarTrilha(String verticeInicial) {
		
		List<Integer> listaIndicesTrilha = new ArrayList<>();
		
		int[][] matrizAux = new int[this.numVertices][this.numVertices];
		
		for (int i=0; i<this.numVertices; i++) {
			
			matrizAux[i] = this.matrizAdjacencia[i].clone();
			
		}
		
		int idxInicial = getIndiceVertice(verticeInicial);
		
		if (idxInicial != -1) {
			dfsTrilha(idxInicial, listaIndicesTrilha, matrizAux);
		}
		
		return indicesToVertices(listaIndicesTrilha);
		
	}
	
	private void dfsTrilha(int idxVertice, List<Integer> listaTrilhaAtual, int[][] matriz) {
		
		listaTrilhaAtual.add(idxVertice);
		
		for (int v=0; v<this.numVertices; v++) {
			
			if (matriz[idxVertice][v] > 0) {
				matriz[idxVertice][v]--;
				matriz[v][idxVertice]--;
				dfsTrilha(v, listaTrilhaAtual, matriz);
				return;
			}
			
		}
		
	}
	
	public List<String> encontrarCaminho(String verticeInicial) {
		
		List<Integer> listaIndiceCaminho = new ArrayList<>();
		boolean[] listaVerticesVisitados = new boolean[this.numVertices];
		int idxVerticeInicial = getIndiceVertice(verticeInicial);
		
		if (idxVerticeInicial != -1) {
			dfsCaminho(idxVerticeInicial, listaIndiceCaminho, listaVerticesVisitados);
		}
		
		return indicesToVertices(listaIndiceCaminho);
		
	}
	
	private void dfsCaminho(int idxVertice, List<Integer> listaCaminhoAtual, boolean[] listaVerticesVisitados) {
		
		listaCaminhoAtual.add(idxVertice);
		listaVerticesVisitados[idxVertice] = true;
		
		for (int v=0; v<this.numVertices; v++) {
			
			if  (this.matrizAdjacencia[idxVertice][v] > 0 && !listaVerticesVisitados[v]) {
				dfsCaminho(v, listaCaminhoAtual, listaVerticesVisitados);
				return;
			}
			
		}
		
	}
	
	private List<String> indicesToVertices(List<Integer> listaIndices) {
		
		List<String> listaVerticesStr = new ArrayList<>();
		
		for (int indice : listaIndices) {
			listaVerticesStr.add(this.listaVertices[indice]);
		}
		
		return listaVerticesStr;
		
	}
	
	private int getIndiceVertice(String vertice) {
		
		for (int i=0; i<this.numVertices; i++) {
			
			if (this.listaVertices[i].equals(vertice)) {
				return i;
			}
			
		}
		
		return -1;
		
	}

}
