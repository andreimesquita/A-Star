package astar_pathfinding;

import astar_pathfinding.model.Nodo;
import astar_pathfinding.model.GridManager;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author Ândrei
 */
public class GridManagerJUnitTest {
    
    @Test (expected = NullPointerException.class)
    public void EarlyGetGridCallTest()
    {
	GridManager gm = new GridManager();
	gm.getGrid();
    }
    
    @Test (expected = NullPointerException.class)
    public void recreateGridTest_EarlyRecreateGridCall()
    {
	GridManager gm = new GridManager();
	gm.recreate();
    }
    
    @Test
    public void createGrid_Test()
    {
	int testWidth = 10, testHeight = 15;
	GridManager gm = new GridManager();
	gm.create(testWidth, testHeight);
	Nodo[][] gridCopy = gm.getGrid();
	assertEquals(testWidth, gridCopy.length);
	assertEquals(testHeight, gridCopy[0].length);
    }
    
    @Test
    public void createAndMakeGrid_Test()
    {
	int testWidth = 10, testHeight = 15;
	GridManager gm = new GridManager();
	gm.create(testWidth, testHeight);
	Nodo[][] gridCopy = gm.getGrid();
	assertEquals(testWidth, gridCopy.length);
	assertEquals(testHeight, gridCopy[0].length);
	for (Nodo[] nl : gridCopy){for (Nodo nc : nl){assertNotNull(nc);}}
	assertEquals(0, gridCopy[0][0].x);
	assertEquals(0, gridCopy[0][0].y);
	assertEquals(testWidth, gridCopy[testWidth - 1][testHeight - 1].x + 1);
	assertEquals(testHeight, gridCopy[testWidth - 1][testHeight - 1].y + 1);
    }
    
    @Test
    public void recreateGrid_Test()
    {
	int testWidth = 24, testHeight = 29;
	GridManager gm = new GridManager();
	gm.create(testWidth, testHeight);
	Nodo[][] gridCopy = gm.getGrid();
	assertEquals(testWidth, gridCopy.length);
	assertEquals(testHeight, gridCopy[0].length);
	for (Nodo[] nl : gridCopy){for (Nodo nc : nl){assertNotNull(nc);}}
	assertEquals(0, gridCopy[0][0].x);
	assertEquals(0, gridCopy[0][0].y);
	assertEquals(testWidth, gridCopy[testWidth - 1][testHeight - 1].x + 1);
	assertEquals(testHeight, gridCopy[testWidth - 1][testHeight - 1].y + 1);
	gm.recreate();
	assertEquals(testWidth, gridCopy.length);
	assertEquals(testHeight, gridCopy[0].length);
	for (Nodo[] nl : gridCopy){for (Nodo nc : nl){assertNotNull(nc);}}
	assertEquals(0, gridCopy[0][0].x);
	assertEquals(0, gridCopy[0][0].y);
	assertEquals(testWidth, gridCopy[testWidth - 1][testHeight - 1].x + 1);
	assertEquals(testHeight, gridCopy[testWidth - 1][testHeight - 1].y + 1);
    }
    
    @Test (expected = NullPointerException.class)
    public void getNeighborsFrom_InvalidValueTest()
    {
	int testWidth = 3, testHeight = 3;
	GridManager gm = new GridManager();
	gm.create(testWidth, testHeight);
	Nodo[][] gridCopy = gm.getGrid();
	Nodo nodoAtual = new Nodo(-1, 1);
	ArrayList<Nodo> neighbors = gm.getNeighborsFrom(nodoAtual, true);
    }
    
    @Test
    public void getNeighborsFrom_Test()
    {
	int testWidth = 3, testHeight = 3;
	GridManager gm = new GridManager();
	gm.create(testWidth, testHeight);
	Nodo[][] gridCopy = gm.getGrid();
	Nodo nodoAtual = new Nodo(1, 1);
	// 0,0 - 0,1 - 0,2
	// 1,0 -  X  - 1,2
	// 2,0 - 2,1 - 2,2
	int qntVizinhos = 8;
	ArrayList<Nodo> neighbors = gm.getNeighborsFrom(nodoAtual, true);
	assertNotNull(neighbors);
	assertEquals(qntVizinhos, neighbors.size());
	nodoAtual = new Nodo(0, 0);
	//  X  - 0,1 - ?
	// 1,0 - 1,1 - ?
	//  ?  - ?   - ?
	qntVizinhos = 3;
	neighbors = gm.getNeighborsFrom(nodoAtual, true);
	assertNotNull(neighbors);
	assertEquals(qntVizinhos, neighbors.size());
	nodoAtual = new Nodo(2, 2);
	//  ? -  ?  -  ?
	//  ? - 1,1 - 1,2
	//  ? - 1,2 - X
	qntVizinhos = 3;
	neighbors = gm.getNeighborsFrom(nodoAtual, true);
	assertNotNull(neighbors);
	assertEquals(qntVizinhos, neighbors.size());
	nodoAtual = new Nodo(0, 2);
	//  ? - 0,1 -  X
	//  ? - 1,1 - 1,2
	//  ? -  ?  -  ?
	qntVizinhos = 3;
	neighbors = gm.getNeighborsFrom(nodoAtual, true);
	assertNotNull(neighbors);
	assertEquals(qntVizinhos, neighbors.size());
	nodoAtual = new Nodo(2, 0);
	//  ?  -  ?  - ?
	// 1,0 - 1,1 - ?
	//  X  - 2,1 - ?
	qntVizinhos = 3;
	neighbors = gm.getNeighborsFrom(nodoAtual, true);
	assertNotNull(neighbors);
	assertEquals(qntVizinhos, neighbors.size());
	
	nodoAtual = new Nodo(1, 0);
	// 0,0 - 0,1 - ?
	//  X  - 1,1 - ?
	// 2,0 - 2,1 - ?
	qntVizinhos = 5;
	neighbors = gm.getNeighborsFrom(nodoAtual, true);
	assertNotNull(neighbors);
	assertEquals(qntVizinhos, neighbors.size());
    }
    
    @Test
    public void getNodoAtPosition_Test()
    {
	int testHeight, testWidth;
	testWidth = testHeight = 10;
	GridManager gm = new GridManager();
	gm.create(testWidth, testHeight);
	Nodo nodo = gm.getNodoAtPosition(0, 0);
	assertNotNull(nodo);
	assertEquals(0,nodo.x);
	assertEquals(0,nodo.y);
    }
    
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void getNodoAtPosition_InvalidValueTest()
    {
	int testHeight, testWidth;
	testWidth = testHeight = 10;
	GridManager gm = new GridManager();
	gm.create(testWidth, testHeight);
	gm.getNodoAtPosition(-1, 0);
    }
}