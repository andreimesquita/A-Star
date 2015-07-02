package astar_pathfinding.controller;

import astar_pathfinding.model.Nodo;
import java.util.ArrayList;

/**
 * @author Ândrei
 */
public class PathfindingPool 
{
    private ArrayList<Nodo> path;
    int count;

    public PathfindingPool() {}

    public void setPath(ArrayList<Nodo> path) {
	this.path = path;
	
    }
    
    public ArrayList<Nodo> getNextStep() throws IllegalAccessException
    {
	if (count == path.size() - 1) throw new IllegalAccessException();
	path.subList(0, count);
	count++;
	
	return null;
    }
}