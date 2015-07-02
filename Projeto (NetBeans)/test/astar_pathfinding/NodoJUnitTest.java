package astar_pathfinding;

import astar_pathfinding.controller.Pathfinding;
import astar_pathfinding.model.Nodo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodoJUnitTest {
    
    private Nodo nodo;

    @Before
    public void setUp() {
	nodo = new Nodo(0, 0);
    }
    
    @Test
    public void calculateValuesTest()
    {
	// First Test
	int heuristic_value = Pathfinding.HEURISTIC_VALUE;
	Nodo otherNodo = new Nodo(0, 1);
	otherNodo.h = 0;
	otherNodo.g = 0;
	nodo.parent = otherNodo;
	nodo.setValorDeG(true);
	nodo.calcularValorDeH(otherNodo);
	int expectedG = 10, expectedH = Pathfinding.HEURISTIC_VALUE, expectedF = expectedG + expectedH;
	assertEquals(expectedG, nodo.g);
	assertEquals(expectedH, nodo.h);
	assertEquals(expectedF, nodo.getF());
	// Reverse
	nodo = new Nodo(0, 1);
	otherNodo = new Nodo(0, 0);
	otherNodo.h = 0;
	otherNodo.g = 0;
	nodo.parent = otherNodo;
	nodo.setValorDeG(true);
	nodo.calcularValorDeH(otherNodo);
	assertEquals(expectedG, nodo.g);
	assertEquals(expectedH, nodo.h);
	assertEquals(expectedF, nodo.getF());
	// Second Test
	otherNodo = new Nodo(0, 0);
	otherNodo.h = 20;
	otherNodo.g = 0;
	nodo = new Nodo(2, 1);
	nodo.parent = otherNodo;
	nodo.setValorDeG(true);
	nodo.calcularValorDeH(otherNodo);
	expectedG = 14;
	expectedH = 30;
	expectedF = expectedG + expectedH;
	assertEquals(expectedG, nodo.g);
	assertEquals(expectedH, nodo.h);
	assertEquals(expectedF, nodo.getF());
	// Reverse
	otherNodo = new Nodo(1, 2);
	otherNodo.h = 20;
	otherNodo.g = 0;
	nodo = new Nodo(0, 0);
	
	nodo.parent = otherNodo;
	nodo.setValorDeG(true);
	nodo.calcularValorDeH(otherNodo);
	expectedG = 14;
	expectedH = 30;
	expectedF = expectedG + expectedH;
	assertEquals(expectedG, nodo.g);
	assertEquals(expectedH, nodo.h);
	assertEquals(expectedF, nodo.getF());
	// Third Test
	nodo = new Nodo(4, 4);
	heuristic_value = Pathfinding.HEURISTIC_VALUE;
	otherNodo = new Nodo(2, 2);
	otherNodo.h = 50;
	otherNodo.g = 20;
	nodo.parent = otherNodo;
	nodo.setValorDeG(true);
	nodo.calcularValorDeH(otherNodo);
	expectedG = 34;
	expectedH = 40;
	expectedF = expectedG + expectedH;
	assertEquals(expectedG, nodo.g);
	assertEquals(expectedH, nodo.h);
	assertEquals(expectedF, nodo.getF());
    }
    
    @Test
    public void changePassableTest()
    {
	assertTrue(nodo.isPassable());
	nodo.changePassable();
	assertFalse(nodo.isPassable());
	nodo.changePassable();
	assertTrue(nodo.isPassable());
    }
    
}
