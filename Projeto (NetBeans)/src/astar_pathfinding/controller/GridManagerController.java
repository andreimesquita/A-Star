package astar_pathfinding.controller;

import astar_pathfinding.model.GridManager;
import astar_pathfinding.model.Nodo;
import astar_pathfinding.view.GridJPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author Ândrei
 */
public class GridManagerController {

    /**
     * MODEL
     */
    private Pathfinding pathfinding;
    private final GridManager gridModel;
    /**
     * VIEW
     */
    private final GridJPanel view;
    private int mouseX_virtual, mouseY_virtual;

    /**
     * Construtor
     * @param gridModel
     * @param view
     */
    public GridManagerController(GridManager gridModel, GridJPanel view) {
	this.view = view;
	this.gridModel = gridModel;
	this.pathfinding = new Pathfinding(gridModel);
    }

    /**
     * Retorna o MouseListener para o GridJPanel.
     *
     * @return MouseListener para o GridJPanel.
     */
    public MouseListener getGridJPanelMouseListener() {
	return (new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		mouseX_virtual = ((int) e.getX() / view.getNodeSize());
		mouseY_virtual = ((int) e.getY() / view.getNodeSize());
		if (e.getButton() == MouseEvent.BUTTON1) {
		    if (e.isControlDown()) {
			try {
			    gridModel.setClosedListCopy(null);
			    gridModel.setOpenListCopy(null);
			    gridModel.setLastPathFound(null);
			    Nodo nodo = gridModel.getNodoAtPosition(mouseX_virtual, mouseY_virtual);
			    nodo.changePassable();
			    gridModel.notifyChangesToView();
			} catch (ArrayIndexOutOfBoundsException | NullPointerException aiobe) {
			    aiobe.printStackTrace();
			}
		    } else {
			gridModel.setSelectedNode(null);
			// Set the selected Node to show info in the Inspector
			try {
			    Nodo nodo = gridModel.getNodoAtPosition(mouseX_virtual, mouseY_virtual);
			    gridModel.setSelectedNode(nodo);
			    gridModel.notifyChangesToView();
			} catch (ArrayIndexOutOfBoundsException | NullPointerException aiobe) {
			    // Do nothing
			}
		    }
		} else {
		    // TODO: Apenas mostrar informações sobre o nodo selecionado
		}
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
	    }
	});
    }

    public ActionListener getDoPathfindingActionListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    gridModel.setLastPathFound(null);
		    gridModel.setOpenListCopy(null);
		    gridModel.setClosedListCopy(null);
		    ArrayList<Nodo> lastPath = pathfinding.getPath();
		    gridModel.setLastPathFound(lastPath);
		    AStarJFrame.updateTimeDuration();
		} catch (IllegalArgumentException ex) {
		    AStarJFrame.updateInfo(ex.getMessage());
		} catch (InternalError | NullPointerException ie) {
		    AStarJFrame.updateInfo("<html><b>ERRO</b>: " + ie.getMessage() + "</html>");
		}
	    }
	};
    }

    public ActionListener getDoCreateActionListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		gridModel.setLastPathFound(null);
		gridModel.setOpenListCopy(null);
		gridModel.setClosedListCopy(null);
		gridModel.create();
	    }
	};
    }

    public ActionListener getSetPontoOrigemActionListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    gridModel.setLastPathFound(null);
		    gridModel.setOpenListCopy(null);
		    gridModel.setClosedListCopy(null);
		    gridModel.setNodoA(gridModel.getNodoAtPosition(mouseX_virtual, mouseY_virtual));
		    if (gridModel.getNodoB() != null) {
			gridModel.setLastPathFound(pathfinding.getPath());
			AStarJFrame.updateTimeDuration();
		    }
		    gridModel.notifyChangesToView();
		} catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
		    // Nada a fazer
		}
	    }
	};
    }

    public ActionListener getSetPontoDestinoActionListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    gridModel.setLastPathFound(null);
		    gridModel.setOpenListCopy(null);
		    gridModel.setClosedListCopy(null);
		    Nodo b = gridModel.getNodoAtPosition(mouseX_virtual, mouseY_virtual);
		    if (b == null) {
			AStarJFrame.updateInfo("<html><b>ERRO</b>: Não existe um nodo na posição [" + mouseX_virtual + "," + mouseY_virtual + "].\nO tamaho máximo da grid é de [" + gridModel.getWidth() + "," + gridModel.getHeight() + "]</html>");
			return;
		    }
		    gridModel.setNodoB(b);
		    if (gridModel.getNodoA() != null) {
			gridModel.setLastPathFound(pathfinding.getPath());
			AStarJFrame.updateTimeDuration();
		    }
		    gridModel.notifyChangesToView();
		} catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
		    // Nada a fazer
		}
	    }
	};
    }

    public ActionListener getChangeNodeStateActionListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		gridModel.setLastPathFound(null);
		gridModel.setOpenListCopy(null);
		gridModel.setClosedListCopy(null);
		Nodo nodo = gridModel.getNodoAtPosition(mouseX_virtual, mouseY_virtual);
		nodo.changePassable();
		if (gridModel.getNodoA() != null && gridModel.getNodoB() != null) {
		    gridModel.setLastPathFound(pathfinding.getPath());
		    AStarJFrame.updateTimeDuration();
		}
		gridModel.notifyChangesToView();
	    }
	};
    }

    public ItemListener getChangeEnableDiagonalMovementItemListener() {
	return new ItemListener() {
	    @Override
	    public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
		    pathfinding.setDiagonalMovementEnabled(true);
		} else {
		    pathfinding.setDiagonalMovementEnabled(false);
		}
		if (gridModel.getNodoA() != null && gridModel.getNodoB() != null) {
		    gridModel.setLastPathFound(pathfinding.getPath());
		    AStarJFrame.updateTimeDuration();
		    gridModel.notifyChangesToView();
		}
	    }
	};
    }

    public KeyListener getWindowKeyListener() {
	return new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent e) {
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		Nodo selectedNode = gridModel.getSelectedNode();
		if (selectedNode == null) {
		    return;
		}
		int nodeX = selectedNode.x;
		int nodeY = selectedNode.y;
		try {
		    Nodo nodo;
		    switch (e.getKeyCode()) {
			case (KeyEvent.VK_LEFT):
			    nodeX -= 1;
			    break;
			case (KeyEvent.VK_RIGHT):
			    nodeX += 1;
			    break;
			case (KeyEvent.VK_UP):
			    nodeY -= 1;
			    break;
			case (KeyEvent.VK_DOWN):
			    nodeY += 1;
			    break;
			case (KeyEvent.VK_H):
			    gridModel.setLastPathFound(null);
			    gridModel.setOpenListCopy(null);
			    gridModel.setClosedListCopy(null);
			    nodo = gridModel.getSelectedNode();
			    if (nodo != null) {
				nodo.changePassable();
				if (gridModel.getNodoA() != null && gridModel.getNodoB() != null) {
				    gridModel.setLastPathFound(pathfinding.getPath());
				    AStarJFrame.updateTimeDuration();
				}
				gridModel.notifyChangesToView();
			    }
			    return;
			case (KeyEvent.VK_1):
			    gridModel.setLastPathFound(null);
			    gridModel.setOpenListCopy(null);
			    gridModel.setClosedListCopy(null);
			    nodo = gridModel.getSelectedNode();
			    if (nodo != null) {
				gridModel.setNodoA(nodo);
				if (gridModel.getNodoA() != null && gridModel.getNodoB() != null) {
				    gridModel.setLastPathFound(pathfinding.getPath());
				    AStarJFrame.updateTimeDuration();
				}
				gridModel.notifyChangesToView();
			    }
			    return;
			case (KeyEvent.VK_2):
			    gridModel.setLastPathFound(null);
			    gridModel.setOpenListCopy(null);
			    gridModel.setClosedListCopy(null);
			    nodo = gridModel.getSelectedNode();
			    if (nodo != null) {
				gridModel.setNodoB(nodo);
				if (gridModel.getNodoA() != null && gridModel.getNodoB() != null) {
				    gridModel.setLastPathFound(pathfinding.getPath());
				    AStarJFrame.updateTimeDuration();
				}
				gridModel.notifyChangesToView();
			    }
			    return;
		    }
		    selectedNode = gridModel.getNodoAtPosition(nodeX, nodeY);
		    gridModel.setSelectedNode(selectedNode);
		    updateViewBeforeChangeSomething();
		} catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {

		}
	    }
	};
    }

    public ItemListener getChangeEnableUIValuesItemListener() {
	return new ItemListener() {
	    @Override
	    public void itemStateChanged(ItemEvent e) {
		gridModel.changeUIValuesEnabled();
		updateViewBeforeChangeSomething();
	    }
	};
    }

    public ItemListener getChangeRoadPathItemListener() {
	return new ItemListener() {
	    @Override
	    public void itemStateChanged(ItemEvent e) {
		gridModel.changeRoadPathEnabled();
		updateViewBeforeChangeSomething();
	    }
	};
    }

    public ActionListener getSalvarGridAtualActionListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    gridModel.saveGrid("savedGrid");
		    AStarJFrame.updateInfo("<html><b>SUCESSO</b>: A <i>grid</i> foi salva com sucesso!</html>");
		} catch (Exception exs) {
		    StringBuilder sb = new StringBuilder("<html><b>ERRO</b>: ");
		    sb.append(exs.getMessage());
		    sb.append("</html>");
		    AStarJFrame.updateInfo(sb.toString());
		}
	    }
	};
    }

    public ActionListener getCarregarGridSalvoAnteriormenteActionListener() {
	return new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    gridModel.carregarGrid("savedGrid");
		    AStarJFrame.updateInfo("<html><b>SUCESSO</b>: A <i>grid</i> salva foi carregada com sucesso!</html>");
		    updateViewBeforeChangeSomething();
		} catch (Exception ioe) {
		    AStarJFrame.updateInfo(ioe.getMessage());
		}
	    }
	};
    }

    public GridManager getGrid() {
	return gridModel;
    }

    private void updateViewBeforeChangeSomething() {
	try {
	    if (gridModel.getNodoA() != null && gridModel.getNodoB() != null) {
		gridModel.setLastPathFound(pathfinding.getPath());
		AStarJFrame.updateTimeDuration();
	    }
	} catch (NullPointerException | IllegalArgumentException | InternalError ex) {
	    AStarJFrame.updateInfo(ex.getMessage());
	}
	gridModel.notifyChangesToView();
    }

}
