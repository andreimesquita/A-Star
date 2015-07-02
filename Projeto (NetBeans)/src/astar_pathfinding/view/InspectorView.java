package astar_pathfinding.view;

import astar_pathfinding.controller.GridManagerController;
import astar_pathfinding.model.GridManager;
import astar_pathfinding.model.Nodo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Ândrei
 */
public class InspectorView extends JPanel implements Observer {

    private final GridManager gridManager;
    private JLabel lblX, lblX_value, lblY, lblY_value, lblEstado;
    private JButton btnEstadoDoNodo, btnDefinirPontoInicial, btnDefinirPontoFinal;
    private Nodo selectedNodoCopy;
    private JCheckBox cbmi, cbmv, cbrp;
    private InspectorNodoJPanel injpanel;

    public InspectorView(GridManager gridManager, GridManagerController controller) {
	super();
	setFocusable(false);
	setFocusCycleRoot(false);
	setPreferredSize(new Dimension(185, 528));
	this.gridManager = gridManager;
	setBackground(getBackground().darker());
	GridLayout gl = new GridLayout(8, 1, 0, 0);
	setLayout(gl);
	injpanel = new InspectorNodoJPanel();
	initUI();
	addUIElements();
	addListeners(controller);
    }

    private void addListeners(GridManagerController controller) {
	btnDefinirPontoFinal.addActionListener(controller.getSetPontoDestinoActionListener());
	btnDefinirPontoInicial.addActionListener(controller.getSetPontoOrigemActionListener());
	btnEstadoDoNodo.addActionListener(controller.getChangeNodeStateActionListener());
	cbmi.addItemListener(controller.getChangeEnableDiagonalMovementItemListener());
	cbmv.addItemListener(controller.getChangeEnableUIValuesItemListener());
	cbrp.addItemListener(controller.getChangeRoadPathItemListener());
    }

    private void initUI() {
	lblX = new JLabel("<html><b>X</b>:</html>");
	lblX.setHorizontalAlignment(JLabel.CENTER);
	lblY = new JLabel("<html><b>Y</b>:</html>");
	lblY.setHorizontalAlignment(JLabel.CENTER);
	lblEstado = new JLabel("<html><b>HABILITADO</b>:</html>");
	lblEstado.setHorizontalAlignment(JLabel.CENTER);
	lblX_value = new JLabel();
	lblX_value.setHorizontalAlignment(JLabel.CENTER);
	lblY_value = new JLabel();
	lblY_value.setHorizontalAlignment(JLabel.CENTER);
	btnEstadoDoNodo = new JButton();
	btnEstadoDoNodo.setToolTipText("<html>Atalho: <b>CTRL + Botão Direito do Mouse</b> no Nodo que deseja <b>habilitar/desabilitar</b>.</html>");
	btnEstadoDoNodo.setFont(new Font(Font.DIALOG, Font.PLAIN, btnEstadoDoNodo.getFont().getSize()));
	btnEstadoDoNodo.setBackground(btnEstadoDoNodo.getBackground().darker());
	btnEstadoDoNodo.setFocusable(false);
	btnEstadoDoNodo.setHorizontalAlignment(JLabel.CENTER);
	btnDefinirPontoInicial = new JButton("<html>Definir <b>Ponto Inicial</b></html>");
	btnDefinirPontoInicial.setFont(new Font(Font.DIALOG, Font.PLAIN, btnDefinirPontoInicial.getFont().getSize()));
	btnDefinirPontoInicial.setBackground(btnDefinirPontoInicial.getBackground().darker());
	btnDefinirPontoInicial.setFocusable(false);
	btnDefinirPontoInicial.setHorizontalAlignment(JLabel.CENTER);
	btnDefinirPontoFinal = new JButton("<html>Definir <b>Ponto Final</b></html>");
	btnDefinirPontoFinal.setFont(new Font(Font.DIALOG, Font.PLAIN, btnDefinirPontoFinal.getFont().getSize()));
	btnDefinirPontoFinal.setBackground(btnDefinirPontoFinal.getBackground().darker());
	btnDefinirPontoFinal.setFocusable(false);
	btnDefinirPontoFinal.setHorizontalAlignment(JLabel.CENTER);
	resetValues();
	// TODO
    }

    private void addUIElements() {
	JLabel lbl = new JLabel("<html><b>INSPETOR</b></html>");
	lbl.setHorizontalAlignment(JLabel.CENTER);
	add(lbl);
	this.injpanel.setPreferredSize(new Dimension(60,60));
	JPanel c = new JPanel();
	c.setBackground(c.getBackground().darker());
	c.setLayout(null);
	this.injpanel.setBounds((int) (injpanel.getPreferredSize().height) - (1 * injpanel.getPreferredSize().height / 100), 0, injpanel.getPreferredSize().width, injpanel.getPreferredSize().height);
	c.add(this.injpanel);
	add(c);
	JPanel painelNodoSelecionado = new JPanel(new GridLayout(2, 1, 0, 5));
	painelNodoSelecionado.setBackground(painelNodoSelecionado.getBackground().darker());
	JPanel sub1_painelNodoSelecionado = new JPanel(new GridLayout(1, 4, 5, 0));
	sub1_painelNodoSelecionado.setBackground(sub1_painelNodoSelecionado.getBackground().darker());
	sub1_painelNodoSelecionado.add(lblX);
	sub1_painelNodoSelecionado.add(lblX_value);
	sub1_painelNodoSelecionado.add(lblY);
	sub1_painelNodoSelecionado.add(lblY_value);
	JPanel sub2_painelNodoSelecionado = new JPanel(new GridLayout(1, 2, 5, 0));
	sub2_painelNodoSelecionado.setBackground(sub2_painelNodoSelecionado.getBackground().darker());
	sub2_painelNodoSelecionado.add(lblEstado);
	sub2_painelNodoSelecionado.add(btnEstadoDoNodo);
	painelNodoSelecionado.add(sub1_painelNodoSelecionado);
	painelNodoSelecionado.add(sub2_painelNodoSelecionado);
	add(painelNodoSelecionado);

	add(btnDefinirPontoInicial);
	add(btnDefinirPontoFinal);
	cbmi = new JCheckBox("<html>Movimento <b>Diagonal</b></html>");
	cbmi.setSelected(true);
	cbmi.setFont(new Font(Font.DIALOG, Font.PLAIN, cbmi.getFont().getSize()));
	cbmi.setFocusable(false);
	cbmi.setBackground(cbmi.getBackground().darker());
	
	cbmv = new JCheckBox("<html><b>G</b>, <b>H</b> e <b>F</b></html>");
	cbmv.setSelected(true);
	cbmv.setFont(new Font(Font.DIALOG, Font.PLAIN, cbmv.getFont().getSize()));
	cbmv.setFocusable(false);
	cbmv.setBackground(cbmv.getBackground().darker());

	cbrp = new JCheckBox("Road Map");
	cbrp.setToolTipText("<html>Apresenta uma seta na direção do nodo pai para cada nodo contido no caminho encontrado.<br/><b>Esta funcionalidade ainda não foi implementada.</b></html>");
	cbrp.setEnabled(false);
	cbrp.setSelected(false);
	cbrp.setFont(new Font(Font.DIALOG, Font.PLAIN, cbrp.getFont().getSize()));
	cbrp.setFocusable(false);
	cbrp.setBackground(cbrp.getBackground().darker());

	add(cbmi);
	add(cbmv);
	add(cbrp);
    }

    /**
     * Reseta os valores dinâmicos da View.
     */
    private void resetValues() {
	lblX_value.setText("-");
	lblX_value.setFont(new Font(Font.DIALOG, Font.BOLD, lblX_value.getFont().getSize()));
	lblY_value.setText("-");
	lblY_value.setFont(new Font(Font.DIALOG, Font.BOLD, lblY_value.getFont().getSize()));
	btnEstadoDoNodo.setText("-");
	btnDefinirPontoInicial.setEnabled(false);
	btnDefinirPontoFinal.setEnabled(false);
	btnEstadoDoNodo.setEnabled(false);
	btnEstadoDoNodo.setFont(new Font(Font.DIALOG, Font.BOLD, btnEstadoDoNodo.getFont().getSize()));
    }

    /**
     * Atualiza a View com as novas informações.
     */
    private void updateViewWithSelectedNode() {
	lblX_value.setText("" + (selectedNodoCopy.x));
	lblY_value.setText("" + (selectedNodoCopy.y));
	btnEstadoDoNodo.setEnabled(true);
	btnEstadoDoNodo.setText(!selectedNodoCopy.isPassable() ? "<html><b>SIM</b></html>" : "<html><b>NÃO</b></html>");
	Nodo nodoA = gridManager.getNodoA(), nodoB = gridManager.getNodoB();
	btnDefinirPontoInicial.setEnabled(nodoA != null ? (!nodoA.equals(selectedNodoCopy)) : true);
	btnDefinirPontoFinal.setEnabled(nodoB != null ? (!nodoB.equals(selectedNodoCopy)) : true);
    }

    @Override
    public void update(Observable o, Object arg) {
	Nodo novoNodoSelecionado = this.gridManager.getSelectedNode();
	selectedNodoCopy = novoNodoSelecionado;
	if (selectedNodoCopy != null) {
	    updateViewWithSelectedNode();
	    injpanel.updateValues();
	} else {
	    resetValues();
	    injpanel.resetValues();
	}
    }

    private class InspectorNodoJPanel extends JPanel {

	private boolean reset = true;
	
	private InspectorNodoJPanel() {}

	private void updateValues() {
	    reset = false;
	    repaint();
	}

	@Override
	public void paint(Graphics g) {
	    if (reset)
	    {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getSize().height, this.getSize().height);
		paintDot(g);
		paintBoarder(g);
		return;
	    }
	    
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, this.getSize().height, this.getSize().height);

	    Nodo selected = gridManager.getSelectedNode();
	    ArrayList<Nodo> pathfindingCopy = gridManager.getLastPathFound();

	    if (pathfindingCopy != null) {
		ArrayList<Nodo> openListCopy = gridManager.getOpenListCopy(),
			closedListCopy = gridManager.getClosedListCopy();
		if (!pathfindingCopy.isEmpty() && !gridManager.getNodoA().equals(selected)
			&& !gridManager.getNodoB().equals(selected)) {
		    if (pathfindingCopy.contains(selected)) {
			// Faz parte do caminho encontrado!
			g.setColor(Color.PINK);
			g.fillRect(0, 0, this.getSize().height, this.getSize().height);
			paintDot(g);
			debugValues(g, selected);
			paintBoarder(g);
			return;
		    }

		    if (openListCopy.contains(selected)) {
			g.setColor(Color.YELLOW.darker());
			g.fillRect(0, 0, this.getSize().height, this.getSize().height);
			paintDot(g);
			debugValues(g, selected);
			paintBoarder(g);
			return;
		    }

		    if (closedListCopy.contains(selected)) {
			g.setColor(Color.YELLOW.darker().darker());
			g.fillRect(0, 0, this.getSize().height, this.getSize().height);
			paintDot(g);
			debugValues(g, selected);
			paintBoarder(g);
			return;
		    }
		}
	    }

	    if (gridManager.getNodoA() != null) {
		if (gridManager.getNodoA().equals(selected)) {
		    g.setColor(Color.GREEN.darker());
		    g.fillRect(0, 0, this.getSize().height, this.getSize().height);
		    paintDot(g);
		    debugValues(g, selected);
		    paintBoarder(g);
		    return;
		}
	    }

	    if (gridManager.getNodoB() != null) {
		if (gridManager.getNodoB().equals(selected)) {
		    g.setColor(Color.RED.darker());
		    g.fillRect(0, 0, this.getSize().height, this.getSize().height);
		    paintDot(g);
		    debugValues(g, selected);
		    paintBoarder(g);
		    return;
		}
	    }

	    if (selected.isPassable()) {
		g.setColor(Color.BLUE.darker());
		g.fillRect(0, 0, this.getSize().height, this.getSize().height);
		paintDot(g);
	    } else {
		g.setColor(Color.GRAY.darker());
		g.fillRect(0, 0, this.getSize().height, this.getSize().height);
	    }

	    paintBoarder(g);
	}

	private void paintDot(Graphics g) {
	    g.setColor(Color.BLACK);
	    g.fillRect((this.getSize().height / 2) - 2, (this.getSize().height / 2) - 2, 4, 4);
	}

	private void debugValues(Graphics g, Nodo selected) {
	    if (gridManager.isUIValuesEnabled()) {
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		g.drawString(selected.getF() + "", 2, 12);
		g.drawString(selected.g + "", 2, this.getSize().height - 4);
		int rasd = 8 * String.valueOf(selected.h).length();
		g.drawString(selected.h + "", this.getSize().height - rasd, this.getSize().height - 4);
	    }
	}

	// F
	// 
	
	private void paintBoarder(Graphics g) {
	    g.setColor(Color.BLACK);
	    g.drawRect(0, 0, this.getSize().height - 1, this.getSize().height - 1);
	}

	private void resetValues() {
	    reset = true;
	    repaint();
	}

    }
}
