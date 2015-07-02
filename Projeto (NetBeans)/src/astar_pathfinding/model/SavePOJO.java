package astar_pathfinding.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ândrei
 */
@XStreamAlias("save")
public class SavePOJO implements Serializable
{
    private Nodo[][] grid;
    private ArrayList<Nodo> lastPathFoundCopy;
    private ArrayList<Nodo> openListCopy;
    private ArrayList<Nodo> closedListCopy;
    private Nodo nodoInicial;
    private Nodo nodoFinal;
    private Nodo selectedNode;
    
    public void setGrid(Nodo[][] grid)
    {
	this.grid = grid;
    }

    public Nodo[][] getGrid() {
	return grid;
    }

    public void setClosedListCopy(ArrayList<Nodo> closedListCopy) {
	this.closedListCopy = closedListCopy;
    }

    public void setLastPathFoundCopy(ArrayList<Nodo> lastPathFoundCopy) {
	this.lastPathFoundCopy = lastPathFoundCopy;
    }

    public void setNodoFinal(Nodo nodoFinal) {
	this.nodoFinal = nodoFinal;
    }

    public void setNodoInicial(Nodo nodoInicial) {
	this.nodoInicial = nodoInicial;
    }

    public void setOpenListCopy(ArrayList<Nodo> openListCopy) {
	this.openListCopy = openListCopy;
    }

    public void setSelectedNode(Nodo selectedNode) {
	this.selectedNode = selectedNode;
    }

    public ArrayList<Nodo> getClosedListCopy() {
	return closedListCopy;
    }

    public ArrayList<Nodo> getLastPathFoundCopy() {
	return lastPathFoundCopy;
    }

    public Nodo getNodoFinal() {
	return nodoFinal;
    }

    public Nodo getNodoInicial() {
	return nodoInicial;
    }

    public ArrayList<Nodo> getOpenListCopy() {
	return openListCopy;
    }

    public Nodo getSelectedNode() {
	return selectedNode;
    }

}