package astar_pathfinding.controller;

import astar_pathfinding.model.GridManager;
import astar_pathfinding.view.GridJPanel;
import astar_pathfinding.view.InspectorView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 * @author Ândrei
 */
public class Start {

    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(
		    UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
	}
	EventQueue.invokeLater(
		new Runnable() {
		    @Override
		    public void run() {
			// MODEL
			GridManager grid = new GridManager();
			grid.create();
			Pathfinding model = new Pathfinding(grid);
			// VIEW
			GridJPanel view = new GridJPanel(grid);
			grid.addObserver(view);
			// CONTROLLER
			GridManagerController controller = new GridManagerController(grid, view);
			// MANAGER
			AStarJFrame mf = new AStarJFrame(view, controller);
			InspectorView inspectorView = new InspectorView(grid, controller);
			grid.addObserver(inspectorView);
			mf.add(BorderLayout.EAST, inspectorView);
			mf.setPreferredSize(
				new Dimension(
					(view.getPreferredSize().width + inspectorView.getPreferredSize().width),
					grid.getHeight() * view.getNodeSize() + 45
				)
			);
			mf.ShowGUI();
		    }
		}
	);
    }
}
