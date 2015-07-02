package astar_pathfinding;

import astar_pathfinding.controller.Pathfinding;
import astar_pathfinding.model.Nodo;
import astar_pathfinding.model.GridManager;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author Ândrei
 */
public class PathfindingJUnitTestCase 
{    
    @Test (expected = IllegalArgumentException.class)
    public void GetPath_IllegalArgumentFailTest() throws Exception
    {
	GridManager gm = new GridManager();
	Pathfinding path = new Pathfinding(gm);
	gm.setNodoA(new Nodo(0,0));
	gm.setNodoB(null);
	path.getPath();
	fail();
    }
    
    @Test (expected = NullPointerException.class)
    public void GetPath_GridManagerNotSetFailTest() throws Exception
    {
	GridManager gm = null;
	Pathfinding path = new Pathfinding(gm);
	gm.setNodoA(new Nodo(0,0));
	gm.setNodoB(new Nodo(0,1));
	path.getPath();
	fail();
    }

    @Test (expected = InternalError.class)
    public void GetPath_NotFountPathBetweenPointsFailTest() throws Exception
    {
	GridManager gm = new GridManager();
	Pathfinding path = new Pathfinding(gm);
	gm.create(4, 4);
	gm.getNodoAtPosition(2, 2).changePassable();
	gm.getNodoAtPosition(2, 3).changePassable();
	gm.getNodoAtPosition(3, 2).changePassable();
	gm.setNodoA(new Nodo(0,0));
	gm.setNodoB(new Nodo(3,3));
	path.getPath();
	fail();
    }
    
    @Test
    public void GetPath__DiagonalONTest()
    {
	GridManager gm = new GridManager();
	Pathfinding path = new Pathfinding(gm);
	path.setDiagonalMovementEnabled(true);
	gm.create(3, 3);
	Nodo start = new Nodo(0,0), end = new Nodo(2,2);
	gm.setNodoA(start);
	gm.setNodoB(end);
	ArrayList<Nodo> caminho = path.getPath();
	// ERRO
	// 0,0 p 2,2
	// 0,0 - 1,1 - 2,2
	assertEquals(3, caminho.size());
	assertEquals(new Nodo(0,0), caminho.get(0));
	assertEquals(new Nodo(1,1), caminho.get(1));
	assertEquals(new Nodo(2,2), caminho.get(2));
	
	gm.recreate();
	start.x = 2;
	start.y = 2;
	end.y = 0;
	end.x = 0;
	gm.setNodoA(start);
	gm.setNodoB(end);
	caminho = path.getPath();
	// 0,0 p 2,2
	// 0,0 - 1,1 - 2,2
	assertEquals(3, caminho.size());
	assertEquals(new Nodo(2,2), caminho.get(0));
	assertEquals(new Nodo(1,1), caminho.get(1));
	assertEquals(new Nodo(0,0), caminho.get(2));
	
	gm.recreate();
	start.x = 1;
	start.y = 2;
	end.x = 1;
	end.y = 0;
	gm.setNodoA(start);
	gm.setNodoB(end);
	caminho = path.getPath();
	// 0,0 p 2,2
	// 0,0 - 0,1 - 0,2
	//  E - 1,1 -  S
	// 2,0 - 2,1 - 2,2
	assertEquals(3, caminho.size());
	assertEquals(new Nodo(1,2), caminho.get(0));
	assertEquals(new Nodo(1,1), caminho.get(1));
	assertEquals(new Nodo(1,0), caminho.get(2));
	
	gm.recreate();
	start.x = 0;
	start.y = 0;
	end.x = 2;
	end.y = 2;
	gm.getNodoAtPosition(1,1).changePassable();
	gm.setNodoA(start);
	gm.setNodoB(end);
	caminho = path.getPath();
	// 0,0 p 2,2
	// 0,0 - 0,1 - 0,2
	//  E - 1,1 -  S
	// 2,0 - 2,1 - 2,2
	assertEquals(4, caminho.size());
	if (caminho.get(1).x == 0)
	{
	    assertEquals(new Nodo(0,0), caminho.get(0));
	    assertEquals(new Nodo(0,1), caminho.get(1));
	    assertEquals(new Nodo(1,2), caminho.get(2));
	    assertEquals(new Nodo(2,2), caminho.get(3));
	} else if (caminho.get(1).x == 1)
	{
	    assertEquals(new Nodo(0,0), caminho.get(0));
	    assertEquals(new Nodo(1,0), caminho.get(1));
	    assertEquals(new Nodo(2,1), caminho.get(2));
	    assertEquals(new Nodo(2,2), caminho.get(3));
	} else {
	    fail();
	}
	
	gm.recreate();
	start.x = 2;
	start.y = 0;
	end.x = 2;
	end.y = 2;
	gm.getNodoAtPosition(1,1).changePassable();
	gm.getNodoAtPosition(2,1).changePassable();
	gm.setNodoA(start);
	gm.setNodoB(end);
	caminho = path.getPath();
	// 0,0 p 2,2
	// 0,0 - 0,1 - 0,2
	// 1,0 - 1,1 - 1,2
	//  S  - 2,1 -  E
	assertEquals(5, caminho.size());
	assertEquals(new Nodo(2,0), caminho.get(0));
	assertEquals(new Nodo(1,0), caminho.get(1));
	assertEquals(new Nodo(0,1), caminho.get(2));
	assertEquals(new Nodo(1,2), caminho.get(3));
	assertEquals(new Nodo(2,2), caminho.get(4));
    }
    
    @Test
    public void GetPath__DiagonalOFFTest()
    {
	GridManager gm = new GridManager();
	Pathfinding path = new Pathfinding(gm);
	path.setDiagonalMovementEnabled(false);
	gm.create(3, 3);
	Nodo start = new Nodo(2,0), end = new Nodo(2,2);
	gm.getNodoAtPosition(1,1).changePassable();
	gm.getNodoAtPosition(2,1).changePassable();
	gm.setNodoA(start);
	gm.setNodoB(end);
	ArrayList<Nodo> caminho = path.getPath();
	// 0,0 p 2,2
	// 0,0 - 0,1 - 0,2
	// 1,0 - 1,1 - 1,2
	//  S  - 2,1 -  E
	assertEquals(7, caminho.size());
	assertEquals(new Nodo(2,0), caminho.get(0));
	assertEquals(new Nodo(1,0), caminho.get(1));
	assertEquals(new Nodo(0,0), caminho.get(2));
	assertEquals(new Nodo(0,1), caminho.get(3));
	assertEquals(new Nodo(0,2), caminho.get(4));
	assertEquals(new Nodo(1,2), caminho.get(5));
	assertEquals(new Nodo(2,2), caminho.get(6));
	
	gm.recreate();
	start.x = 0;
	start.y = 0;
	end.x = 2;
	end.y = 2;
	gm.getNodoAtPosition(1,1).changePassable();
	gm.setNodoA(start);
	gm.setNodoB(end);
	caminho = path.getPath();
	assertEquals(5, caminho.size());
    }
}