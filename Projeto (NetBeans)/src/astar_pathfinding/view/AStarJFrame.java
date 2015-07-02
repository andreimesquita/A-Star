package astar_pathfinding.view;

import astar_pathfinding.controller.GridManagerController;
import astar_pathfinding.controller.Pathfinding;
import astar_pathfinding.view.SobreJFrame;
import astar_pathfinding.model.Nodo;
import astar_pathfinding.view.GridJPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * @author Ândrei
 */
public class AStarJFrame extends JFrame {

    private SobreJFrame sobreJframe;

    public static void updateInfo(String message) {
	lblPresentation.setText(message);
    }

    private JMenuItem jmiItem1, jmiItem2, jmiItem3;
    private final GridJPanel gridJPanelview;
    private final static JLabel lblPresentation = new JLabel("...");
    private static GridManagerController controller;

    public AStarJFrame(GridJPanel gridJPanelview, GridManagerController controller) {
	super("A* Pathfinding - Aplicação Prática");
	this.gridJPanelview = gridJPanelview;
	AStarJFrame.controller = controller;
	try {
	    ImageIcon logo = new ImageIcon("AStar_Logo.png");
	    if (logo != null) {
		setIconImage(logo.getImage());
	    }
	} catch (Exception e) {
	}
	JPanel panelSouth = new JPanel();
	panelSouth.add(BorderLayout.CENTER, lblPresentation);
	add(BorderLayout.SOUTH, lblPresentation);
	initMenuBar();
	setListeners();
	sendEventsToController();
    }

    public static void updateTimeDuration() {
	long nanosseconds = (Pathfinding.END_TIME - Pathfinding.INIT_TIME);
	StringBuilder sb = new StringBuilder("<html>A <b>execução</b> do algoritmo A* durou ");
	long milissegundos = nanosseconds / 1000000;
	if (milissegundos > 0) {
	    sb.append("<b>" + milissegundos + " milissegundo(s)</b> e ");
	}
	sb.append("<b>" + (nanosseconds - milissegundos * 1000000) + " nanossegundos</b>.");
	ArrayList<Nodo> path = controller.getGrid().getLastPathFound();
	if (path != null) {
	    sb.append(" Foram dados <b>").append(path.size() - 1).append(" passos</b>");
	}
	sb.append("</html>");
	lblPresentation.setText(sb.toString());
    }

    private void setListeners() {
	gridJPanelview.addMouseListener(controller.getGridJPanelMouseListener());
	addKeyListener(controller.getWindowKeyListener());
    }

    private void sendEventsToController() {
	jmiItem1.addActionListener(controller.getDoCreateActionListener());
	jmiItem2.addActionListener(controller.getSalvarGridAtualActionListener());
	jmiItem3.addActionListener(controller.getCarregarGridSalvoAnteriormenteActionListener());
    }

    private void initMenuBar() {
	JMenuBar jmb = new JMenuBar();
	JMenu jmMenu1 = new JMenu("Arquivo");
	jmiItem1 = new JMenuItem("Resetar");
	JMenu jm_jmiMenu1 = new JMenu("Persistir");
	jmiItem2 = new JMenuItem("Salvar");
	jmiItem3 = new JMenuItem("Carregar");

	JMenuItem jmiSobre = new JMenuItem("Sobre");
	jmiSobre.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (sobreJframe == null) {
		    sobreJframe = new SobreJFrame();
		}
		sobreJframe.setLocationRelativeTo(AStarJFrame.this);
		sobreJframe.setVisible(true);
	    }
	});

	jmMenu1.add(jmiItem1);
	jm_jmiMenu1.add(jmiItem2);
	jm_jmiMenu1.add(jmiItem3);
	jmMenu1.add(jm_jmiMenu1);

	jmb.add(jmMenu1);
	jmb.add(jmiSobre);
	setJMenuBar(jmb);
    }

    public void ShowGUI() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Dimension dim = getPreferredSize();
	dim.width += 7;
	dim.height += 20;
	setPreferredSize(dim);
	add(BorderLayout.CENTER, gridJPanelview);
	pack();
	setResizable(false);
	setLocationRelativeTo(null);
	setVisible(true);
    }

}
