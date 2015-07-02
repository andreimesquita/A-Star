package astar_pathfinding;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
	{astar_pathfinding.NodoJUnitTest.class,
	 astar_pathfinding.GridManagerJUnitTest.class, 
	 astar_pathfinding.PathfindingJUnitTestCase.class,
	 astar_pathfinding.GridManagerControllerJUnitTest.class})
public class PathfindingJUnitTestSuit {}