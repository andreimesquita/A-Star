package astar_pathfinding.model;

import java.io.Serializable;

/**
 * Representa um Nodo de uma estrutura de grafos, onde sua posição é determinada pelas variáveis X e Y. Esta classe contém a lógica necessária para empregar o algoritmo A* Pathfinding.
 * @author Ândrei
 */
public class Nodo implements Serializable {

    /**
     * g = Custo de movimento do nodo inicial ao atual
     */
    public int g;
    /**
     * h = Estimativa de custo entre o nodo atual e o final
     */
    public int h;
    private boolean isPassable;
    // Nodo 'pai' do nodo atual
    public Nodo parent;
    // Posição do Nodo no plano 2D
    public int x, y;

    public Nodo(int _x, int _y) {
	isPassable = true;
	x = _x;
	y = _y;
    }

    public int getF() {
	return (g + h);
    }

    public boolean isPassable() {
	return (this.isPassable);
    }

    /**
     * Calcula o valor da Heurística através do seguinte cálculo:
     * <i>h = Math.max(Math.abs(x - end.x),Math.abs(y - end.y))</i>
     * @param end
     */
    public void calcularValorDeH(Nodo end)
    {
	// Método de Manhattan
	h = (Math.abs(x - end.x) + Math.abs(y - end.y)) * 10;
    }
    /**
     * Atualiza o valor de G.
     * @param maiorPesoDiagonal
     */
    public void setValorDeG(boolean maiorPesoDiagonal)
    {
	if (maiorPesoDiagonal)
	{
	    this.g = parent.g + (parent.x != x && parent.y != y ? 14 : 10);
	} else {
	    this.g = parent.g + 10;
	}
    }

    // GUI Controlls

    public void changePassable() {
	this.isPassable = !this.isPassable;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Nodo) {
	    Nodo other = (Nodo) obj;
	    if (other.x == this.x && other.y == this.y) {
		return true;
	    }
	}
	return false;
    }
}
