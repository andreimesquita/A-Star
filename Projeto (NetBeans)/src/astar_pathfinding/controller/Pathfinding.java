package astar_pathfinding.controller;

import astar_pathfinding.model.GridManager;
import astar_pathfinding.model.Nodo;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Ândrei
 */
public class Pathfinding 
{
    /**
     * TODO Esta variável deve ser inicializada.
     */
    private GridManager gridManager;
    private ArrayList<Nodo> openList = new ArrayList<>(), closedList = new ArrayList<>();
    // Não pode ser nulo e nem igual a 0 pois é multiplicado pelo resultado de G durante a execução
    // do algoritmo.
    public static final int HEURISTIC_VALUE = 10;
    /**
     * Variáveis que mantém o tempo em nanosegundos a fim de apresentar esta informação na GUI.
     */
    public static long INIT_TIME, END_TIME;
    private boolean diagonal_movement_enabled = true;

    /**
     * @param grid
     * @throws NullPointerException
     */
    public Pathfinding(GridManager grid) throws NullPointerException {
	if (HEURISTIC_VALUE <= 0) {
	    throw new NullPointerException("HEURISTIC_VALUE can't be 0 or less!");
	}
	this.gridManager = grid;
    }

    /**
     * Executa o algoritmo <b>A* pathfinding</b> a fim de encontrar o caminho de menor custo entre os pontos A (<i>start</i>) e B (<i>end</i>). O cálculo é realizado com base nos valores
     * <b>G</b>,<b>H</b> e <b>F (G + H)</b>, respectivamente.
     * @param start
     * @param end
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws InternalError
     */
    public ArrayList<Nodo> getPath() throws NullPointerException, IllegalArgumentException, InternalError {
	Nodo start = gridManager.getNodoA(), end = gridManager.getNodoB();
	if (gridManager == null) {
	    throw new NullPointerException("GridManager can't be null!");
	}
	if (start == null || end == null) {
	    throw new IllegalArgumentException("Não foi possível realizar esta ação pois os\npontos A e B não foram definidos!");
	}

	INIT_TIME = System.nanoTime();
	openList.clear();
	closedList.clear();
	start.parent = null;
	end.parent = null;
	start.g = 0;
	start.calcularValorDeH(end);

	//INICIO DO A*
	openList.add(start);
	while (!openList.isEmpty()) {
	    Nodo currentNode = GetNodeWithLowestFCostValue();

	    if (currentNode.equals(end)) {
		END_TIME = System.nanoTime();
		ArrayList<Nodo> result = ReconstructPath(currentNode);
		this.gridManager.setClosedListCopy(closedList);
		this.gridManager.setOpenListCopy(openList);
		return result;
	    }

	    openList.remove(currentNode);
	    closedList.add(currentNode);

	    ArrayList<Nodo> neighbors = gridManager.getNeighborsFrom(currentNode, diagonal_movement_enabled);
	    for (Nodo neighbor : neighbors) {
		if (closedList.contains(neighbor)) {
		    continue;
		}

		if (!openList.contains(neighbor)) {
		    openList.add(neighbor);
		    neighbor.parent = currentNode;
		    neighbor.calcularValorDeH(end);
		    neighbor.setValorDeG();
		} else if (currentNode.g < neighbor.parent.g) {
		    neighbor.parent = currentNode;
		    neighbor.setValorDeG();
		}
	    }
	}
        END_TIME = System.nanoTime();
	this.gridManager.setClosedListCopy(closedList);
	this.gridManager.setOpenListCopy(openList);
	throw new InternalError("Não foi encontrado um caminho entre os pontos A e B.");
    }

    /**
     * Retorna o caminho no formato de um ArrayList corretamente organizado, do nodo inicial ao final. Para a execução correta deste método, é necessário que a cada execução do algoritmo A*
     * pathfinding o parametro <b>parent</b> de cada Nodo seja anulado (parent = null). Caso contrário, este método poderá retornar um caminho incorreto.
     *
     * @param end Nodo final que contém a referecia do parâmetro <b>parent</b> ao caminho percorrido desde o ponto inicial (A).
     * @return Caminho a ser percorrido do ponto A a B, organizado na forma de um ArrayList.
     */
    private ArrayList<Nodo> ReconstructPath(Nodo end) {
	ArrayList<Nodo> resultList = new ArrayList<>();
	while (end != null) {
	    resultList.add(end);
	    end = end.parent;
	}
	Collections.reverse(resultList);
	return resultList;
    }

    /**
     * Retorna o Nodo com menor valor de atributo F.
     *
     * @param openList
     * @return
     */
    private Nodo GetNodeWithLowestFCostValue() {
	Nodo lowestFCostNode = null;
	for (Nodo actual : openList) {
	    if (lowestFCostNode == null) {
		lowestFCostNode = actual;
		continue;
	    }

	    if (lowestFCostNode.getF() == actual.getF()) {
		if (actual.h < lowestFCostNode.h) {
		    lowestFCostNode = actual;
		} else if (actual.g < lowestFCostNode.g)
		{
		    lowestFCostNode = actual;
		}
	    } else {
		if (actual.getF() < lowestFCostNode.getF()) {
		    lowestFCostNode = actual;
		}
	    }
	}
	return lowestFCostNode;
    }

    public void setDiagonalMovementEnabled(boolean diagonal_movement_enabled) {
	this.diagonal_movement_enabled = diagonal_movement_enabled;
    }

    public boolean isDiagonalMovementEnabled() {
	return diagonal_movement_enabled;
    }
}
