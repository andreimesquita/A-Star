package astar_pathfinding.model;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;
/**
 * @author Ândrei
 */
public class GridManager extends Observable {

    private Nodo[][] grid;
    private ArrayList<Nodo> lastPathFoundCopy, openListCopy, closedListCopy;
    private Nodo nodoInicial, nodoFinal, selectedNode;
    private int width, height;
    private boolean isUIValuesEnabled = true, isRoadPathEnabled = false, isDiagonalCostEnabled = true;
    
    public GridManager() {}

    public void create() {
	this.width = 16;
	this.height = 14;
	nodoInicial = null;
	nodoFinal = null;
	grid = new Nodo[width][height];
	bake();
    }

    public void setNodoA(Nodo a) {
	this.nodoInicial = a;
    }

    public void setNodoB(Nodo b) {
	this.nodoFinal = b;
    }

    public Nodo getNodoA() {
	return nodoInicial;
    }

    public Nodo getNodoB() {
	return nodoFinal;
    }

    /**
     * Ainda é necessário para a execução correta dos testes unitários.
     *
     * @param width
     * @param height
     * @throws IllegalArgumentException
     * @deprecated Este método ainda não foi implementado com a UI. Use o método <i>create()</i>.
     */
    public void create(int width, int height) throws IllegalArgumentException {
	if (width == 0 || height == 0) {
	    throw new IllegalArgumentException("<html><b>ERROR</b>: Width and Height cannot be zero!</html>");
	}
	this.width = width;
	this.height = height;
	grid = new Nodo[width][height];
	bake();
    }

    private void bake() {
	lastPathFoundCopy = new ArrayList<>();
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		grid[x][y] = new Nodo(x, y);
	    }
	}
	notifyChangesToView();
    }

    /**
     * Notifica a GUI de que as informações foram modificadas.
     */
    public void notifyChangesToView() {
	setChanged();
	notifyObservers();
    }

    /**
     * @throws NullPointerException
     */
    public void recreate() throws NullPointerException {
	if (grid == null) {
	    throw new NullPointerException("<html><b>ERROR</b>: Can't recreate a not created grid!</html>");
	}
	bake();
    }

    /**
     * @param nodo
     * @param diagonal_movement_enabled
     * @return
     * @throws NullPointerException
     */
    public ArrayList<Nodo> getNeighborsFrom(Nodo nodo, boolean diagonal_movement_enabled) throws NullPointerException {
	if (nodo.x < 0 || nodo.y < 0 || nodo.x > width || nodo.y > height) {
	    throw new NullPointerException("<html><b>ERROR</b>: Can't find neighbors from this Node because It isn't in the grid!</html>");
	}
	ArrayList<Nodo> foundNeighbors = new ArrayList<>();
	for (int x = -1; x < 2; x++) {
	    for (int y = -1; y < 2; y++) {
		if (x == 0 && y == 0) {
		    continue;
		}

		int realX = nodo.x + x, realY = nodo.y + y;
		if (realX < 0 || realY < 0) {
		    continue;
		}
		if (realX >= width || realY >= height) {
		    continue;
		}

		if (!grid[realX][realY].isPassable()) {
		    continue;
		}
		if (!diagonal_movement_enabled) {
		    if (x == 0 || y == 0)
		    {
			foundNeighbors.add(grid[realX][realY]);
		    }
		} else {
		    foundNeighbors.add(grid[realX][realY]);
		}

	    }
	}
	return foundNeighbors;
    }

    // UI
    /**
     * Este método retona um clone do array bidimensional.
     *
     * @return
     * @throws NullPointerException
     */
    public Nodo[][] getGrid() throws NullPointerException {
	return (Nodo[][]) grid.clone();
    }

    /**
     * @param x
     * @param y
     * @return
     * @throws ArrayIndexOutOfBoundsException Não foi possível encontrar o Nodo na posição determinada pois ela é menor do que zero (0) ou maior do que o tamanho do array bidimensional.
     * @throws NullPointerException
     */
    public Nodo getNodoAtPosition(int x, int y) throws ArrayIndexOutOfBoundsException, NullPointerException {
	return (grid[x][y]);
    }

    public int getWidth() {
	return width;
    }

    ;
    public int getHeight() {
	return height;
    }

    ;
    
    public void setLastPathFound(ArrayList<Nodo> path) {
	this.lastPathFoundCopy = path;
	notifyChangesToView();
    }

    public ArrayList<Nodo> getLastPathFound() {
	return this.lastPathFoundCopy;
    }

    public void setOpenListCopy(ArrayList<Nodo> openListCopy) {
	this.openListCopy = openListCopy;
    }

    public ArrayList<Nodo> getOpenListCopy() {
	return openListCopy;
    }

    public void setClosedListCopy(ArrayList<Nodo> closedListCopy) {
	this.closedListCopy = closedListCopy;
    }

    public ArrayList<Nodo> getClosedListCopy() {
	return closedListCopy;
    }

    public Nodo getSelectedNode() {
	return this.selectedNode;
    }

    public void setSelectedNode(Nodo selected) {
	this.selectedNode = selected;
    }

    public boolean isUIValuesEnabled() {
	return isUIValuesEnabled;
    }

    public void changeUIValuesEnabled() {
	this.isUIValuesEnabled = !this.isUIValuesEnabled;
    }

    public boolean isIsRoadPathEnabled() {
	return isRoadPathEnabled;
    }

    public void changeRoadPathEnabled()
    {
	isRoadPathEnabled = !isRoadPathEnabled;
    }
    /**
     * @param fileName
     * @throws FileNotFoundException 
     */
    public void saveGrid(String fileName) throws FileNotFoundException, IOException, URISyntaxException
    {
	File f = new File(fileName + ".xml");
	XStream xstream = new XStream();
	FileOutputStream fos = new FileOutputStream(f);
	SavePOJO savePojo = new SavePOJO();
	savePojo.setClosedListCopy(closedListCopy);
	savePojo.setGrid(grid);
	savePojo.setLastPathFoundCopy(lastPathFoundCopy);
	savePojo.setNodoFinal(nodoFinal);
	savePojo.setNodoInicial(nodoInicial);
	savePojo.setOpenListCopy(openListCopy);
	savePojo.setSelectedNode(selectedNode);
	xstream.alias("save", SavePOJO.class);
	xstream.alias("grid", Nodo[][].class);
	xstream.alias("nodo", Nodo.class);
	xstream.toXML(savePojo, fos);
	fos.close();
    }

    public void carregarGrid(String fileName) throws MalformedURLException, URISyntaxException, NullPointerException {
	File f = new File(fileName + ".xml");
	if (!f.exists()) throw new NullPointerException("<html><b>ERRO</b>: Não existe uma grid salva!</html>");
	XStream xstream = new XStream();
	xstream.alias("save", SavePOJO.class);
	xstream.alias("grid", Nodo[][].class);
	xstream.alias("nodo", Nodo.class);
	SavePOJO savePojo = (SavePOJO) xstream.fromXML(f);
	closedListCopy = savePojo.getClosedListCopy();
	grid = savePojo.getGrid();
	lastPathFoundCopy = savePojo.getLastPathFoundCopy();
	nodoFinal = savePojo.getNodoFinal();
	nodoInicial = savePojo.getNodoInicial();
	openListCopy = savePojo.getOpenListCopy();
	selectedNode = savePojo.getSelectedNode();
    }

    public boolean isIsDiagonalCostEnabled() {
	return isDiagonalCostEnabled;
    }

    public void setIsDiagonalCostEnabled(boolean isDiagonalCostEnabled) {
	this.isDiagonalCostEnabled = isDiagonalCostEnabled;
    }
}