package astar_pathfinding.view;

import astar_pathfinding.model.GridManager;
import astar_pathfinding.model.Nodo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * @author Ândrei
 */
public class GridJPanel extends JPanel implements Observer {

    private int nodeSize = 40; // Tamanho do Nodo em pixels
    private final GridManager model;

    public GridJPanel(GridManager model) {
	super(null);
	setPreferredSize(new Dimension(nodeSize * model.getWidth(), nodeSize * model.getHeight()));
	this.model = model;
    }

    @Override
    public void paint(Graphics g) {
	// Draw Background
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, getSize().width, getSize().height);

	try {
	    Nodo[][] grid = this.model.getGrid();
	    ArrayList<Nodo> pathfindingCopy, openListCopy, closedListCopy;
	    pathfindingCopy = model.getLastPathFound();
	    openListCopy = model.getOpenListCopy();
	    closedListCopy = model.getClosedListCopy();
	    for (int x = 0; x < grid.length; x++) {
		for (int y = 0; y < grid[0].length; y++) {
		    int real_x = (x * nodeSize),
			    real_y = (y * nodeSize);
		    if (pathfindingCopy != null) {
			if (!pathfindingCopy.isEmpty() && !model.getNodoA().equals(grid[x][y])
				&& !model.getNodoB().equals(grid[x][y])) {
			    if (pathfindingCopy.contains(grid[x][y])) {
				// Faz parte do caminho encontrado!
				g.setColor(Color.PINK);
				g.fillRect(real_x, real_y, nodeSize, nodeSize);
				paintDot(g, real_x, real_y);
				debugValues(g, grid[x][y], real_x, real_y);
				paintBoarder(g, grid[x][y], real_x, real_y);
				printRoadMap(grid[x][y]);
				continue;
			    }

			    if (openListCopy.contains(grid[x][y])) {
				g.setColor(Color.YELLOW.darker());
				g.fillRect(real_x, real_y, nodeSize, nodeSize);
				paintDot(g, real_x, real_y);
				debugValues(g, grid[x][y], real_x, real_y);
				paintBoarder(g, grid[x][y], real_x, real_y);
				continue;
			    }

			    if (closedListCopy.contains(grid[x][y])) {
				g.setColor(Color.YELLOW.darker().darker());
				g.fillRect(real_x, real_y, nodeSize, nodeSize);
				paintDot(g, real_x, real_y);
				debugValues(g, grid[x][y], real_x, real_y);
				paintBoarder(g, grid[x][y], real_x, real_y);
				continue;
			    }
			}
		    }

		    if (model.getNodoA() != null) {
			if (model.getNodoA().equals(grid[x][y])) {
			    g.setColor(Color.GREEN.darker());
			    g.fillRect(real_x, real_y, nodeSize, nodeSize);
			    paintDot(g, real_x, real_y);
			    debugValues(g, grid[x][y], real_x, real_y);
			    paintBoarder(g, grid[x][y], real_x, real_y);
			    continue;
			}
		    }

		    if (model.getNodoB() != null) {
			if (model.getNodoB().equals(grid[x][y])) {
			    g.setColor(Color.RED.darker());
			    g.fillRect(real_x, real_y, nodeSize, nodeSize);
			    paintDot(g, real_x, real_y);
			    debugValues(g, grid[x][y], real_x, real_y);
			    paintBoarder(g, grid[x][y], real_x, real_y);
			    continue;
			}
		    }

		    if (grid[x][y].isPassable()) {
			g.setColor(Color.BLUE.darker());
			g.fillRect(real_x, real_y, nodeSize, nodeSize);
			paintDot(g, real_x, real_y);
		    } else {
			g.setColor(Color.GRAY.darker());
			g.fillRect(real_x, real_y, nodeSize, nodeSize);
		    }

		    paintBoarder(g, grid[x][y], real_x, real_y);
		}
	    }
	} catch (NullPointerException npe) {
	    // Nada a fazer
	}
    }

    private void paintBoarder(Graphics g, Nodo nodo, int real_x, int real_y) {
	Nodo selected = model.getSelectedNode();
	if (selected != null) {
	    if (selected.equals(nodo)) {
		g.setColor(Color.YELLOW);
		g.drawRect(real_x + 1, real_y + 1, nodeSize - 2, nodeSize - 2);
		return;
	    } else {
		g.setColor(Color.BLACK);
	    }
	} else {
	    g.setColor(Color.BLACK);
	}
	g.drawRect(real_x, real_y, nodeSize, nodeSize);
    }

    private void printRoadMap(Nodo currentNode)
    {
	Nodo p = currentNode.parent;
	if (p == null) return;
	// TODO: Continuar...
    }
    
    private void debugValues(Graphics g, Nodo n, int real_x, int real_y) {
	if (model.isUIValuesEnabled()) {
	    g.setColor(Color.BLACK);
	    g.setFont(new Font(Font.DIALOG, Font.PLAIN, 8));
	    g.drawString(n.getF() + "", real_x + 2, real_y + 10);
	    g.drawString(n.g + "", real_x + 2, real_y + nodeSize - 4);
	    g.drawString(n.h + "", real_x + nodeSize - 15, real_y + nodeSize - 4);
	}
    }

    private void paintDot(Graphics g, int real_x, int real_y) {
	g.setColor(Color.BLACK);
	g.fillRect(real_x + (nodeSize / 2) - 2, real_y + (nodeSize / 2) - 2, 4, 4);
    }

    @Override
    public void update(Observable o, Object arg) {
	repaint();
    }

    public void setNodoSize(int nodoSize) {
	this.nodeSize = nodoSize;
    }

    public int getNodeSize() {
	return nodeSize;
    }

}
