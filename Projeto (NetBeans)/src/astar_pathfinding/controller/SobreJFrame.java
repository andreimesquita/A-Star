package astar_pathfinding.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Ândrei
 */
public class SobreJFrame extends JDialog
{
    String desenvolvedor = "Ândrei Schuch";
    String licenca = "Livre para destribuição.";
    
    public SobreJFrame()
    {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setUndecorated(true);
	addMouseListener(new MouseListener() {
	    @Override
	    public void mouseClicked(MouseEvent e) {}
	    @Override
	    public void mousePressed(MouseEvent e) {}
	    @Override
	    public void mouseReleased(MouseEvent e) 
	    {
		SobreJFrame.this.dispose();
	    }
	    @Override
	    public void mouseEntered(MouseEvent e) {}
	    @Override
	    public void mouseExited(MouseEvent e) {}
	});
	JPanel panel = new JPanel();
	panel.setBackground(Color.GRAY.darker());
	panel.setPreferredSize(new Dimension(310, 210));
	panel.setLayout(new BorderLayout(50, 6));
	JPanel internalPanel = new JPanel();
	internalPanel.setBackground(Color.GRAY.darker());
	internalPanel.setLayout(new GridLayout(3, 1, 5, 0));
	StringBuilder sb = new StringBuilder("<html><b>Desenvolvedor</b>: ");
	sb.append(desenvolvedor);
	sb.append("</html>");
	JLabel lblDesenvolvedor = new JLabel(sb.toString());
	lblDesenvolvedor.setHorizontalAlignment(SwingConstants.CENTER);
	internalPanel.add(lblDesenvolvedor);
	sb = new StringBuilder("<html><b>Licença</b>: ");
	sb.append(licenca);
	sb.append("</html>");
	JLabel lblLicenca = new JLabel(sb.toString());
	lblLicenca.setHorizontalAlignment(SwingConstants.CENTER);
	ImageIcon logo = new ImageIcon("AStar_Logo.png");
	JLabel logoLabel = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(212, 156, 50)));
	logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
	panel.add(BorderLayout.CENTER, logoLabel);
	JLabel lblEmail = new JLabel("<html><b>Email</b>: andreirs@outlook.com</html>");
	lblEmail.setHorizontalTextPosition(SwingConstants.CENTER);
	lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
	internalPanel.add(lblEmail);
	internalPanel.add(lblLicenca);
	panel.add(BorderLayout.SOUTH, internalPanel);
	add(panel);
	setAlwaysOnTop(true);
	pack();
    }

}