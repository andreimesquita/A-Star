package astar_pathfinding;

import astar_pathfinding.controller.GridManagerController;
import astar_pathfinding.model.GridManager;
import astar_pathfinding.model.Nodo;
import astar_pathfinding.controller.Pathfinding;
import astar_pathfinding.view.GridJPanel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 * @author Ândrei
 */
public class GridManagerControllerJUnitTest {
    
    @Test
    public void getGridJPanelMouseListener_FailTest() throws ArrayIndexOutOfBoundsException
    {
	GridManager gm = new GridManager();
	gm.create(10, 10);
	Pathfinding path = new Pathfinding(gm);
	GridJPanel view = new GridJPanel(gm);
	int width = 10 * view.getNodeSize(), height = 10 * view.getNodeSize();
	GridManagerController controller = new GridManagerController(gm, view);
	// Simula o clique do botão esquerdo do mouse sobre o Nodo C,L 0,11
	Nodo[][] grid_InitialCopy = gm.getGrid();
	for (Nodo[] nn : grid_InitialCopy)
	{
	    for (Nodo n : nn)
	    {
		assertTrue(n.isPassable());
	    }
	}
	MouseEvent e = 
	    new MouseEvent(view, MouseEvent.MOUSE_PRESSED, System.nanoTime(), 0, 0, (height + view.getNodeSize()), 0, 0, 1, false, MouseEvent.BUTTON1);
	controller.getGridJPanelMouseListener().mousePressed(e);
	Nodo[][] grid_FinalCopy = gm.getGrid();
	for (Nodo[] nn : grid_FinalCopy)
	{
	    for (Nodo n : nn)
	    {
		assertTrue(n.isPassable());
	    }
	}
    }
    
    @Test
    public void getGridJPanelMouseListener_Test()
    {
	GridManager gm = new GridManager();
	gm.create(10, 10);
	Pathfinding path = new Pathfinding(gm);
	GridJPanel view = new GridJPanel(gm);
	GridManagerController controller = new GridManagerController(gm, view);
	// Simula o clique do botão esquerdo do mouse sobre o Nodo C,L 0,0
	Nodo[][] grid_InitialCopy = gm.getGrid();
	assertTrue(grid_InitialCopy[0][0].isPassable());
	MouseEvent e = 
	    new MouseEvent(view, MouseEvent.MOUSE_PRESSED, System.nanoTime(), KeyEvent.CTRL_DOWN_MASK, 0, 0, 0, 0, 1, false, MouseEvent.BUTTON1);
	controller.getGridJPanelMouseListener().mousePressed(e);
	Nodo[][] grid_FinalCopy = gm.getGrid();
	assertFalse(grid_FinalCopy[0][0].isPassable());
	// Simula o clique do botão direito do mouse sobre o Nodo C,L 0,0
	gm.recreate();
	grid_InitialCopy = gm.getGrid();
	assertTrue(grid_InitialCopy[0][0].isPassable());
	e = new MouseEvent(view, MouseEvent.MOUSE_PRESSED, System.nanoTime(), 0, 0, 0, 0, 0, 1, false, MouseEvent.BUTTON3);
	controller.getGridJPanelMouseListener().mousePressed(e);
	grid_FinalCopy = gm.getGrid();
	assertTrue(grid_FinalCopy[0][0].isPassable());
	// Simula o clique do botão esquerdo do mouse sobre o Nodo 1,0
	gm.recreate();
	grid_InitialCopy = gm.getGrid();
	assertTrue(grid_InitialCopy[1][0].isPassable());
	e = new MouseEvent(view, MouseEvent.MOUSE_PRESSED, System.nanoTime(), KeyEvent.CTRL_DOWN_MASK, view.getNodeSize(), 0, 0, 0, 1, false, MouseEvent.BUTTON1);
	controller.getGridJPanelMouseListener().mousePressed(e);
	grid_FinalCopy = gm.getGrid();
	assertFalse(grid_FinalCopy[1][0].isPassable());
	// Simula o clique do botão esquerdo do mouse sobre o Nodo 0,1
	gm.recreate();
	grid_InitialCopy = gm.getGrid();
	assertTrue(grid_InitialCopy[0][1].isPassable());
	e = new MouseEvent(view, MouseEvent.MOUSE_PRESSED, System.nanoTime(), KeyEvent.CTRL_DOWN_MASK, 0, view.getNodeSize(), 0, 0, 1, false, MouseEvent.BUTTON1);
	controller.getGridJPanelMouseListener().mousePressed(e);
	grid_FinalCopy = gm.getGrid();
	assertFalse(grid_FinalCopy[0][1].isPassable());
    }

}