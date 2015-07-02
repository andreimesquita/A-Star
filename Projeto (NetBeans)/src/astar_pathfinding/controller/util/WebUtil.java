package astar_pathfinding.controller.util;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JOptionPane;
/**
 * @author Ândrei
 */
public class WebUtil 
{
    public static void AbrirNoNavegadorAPaginaWeb(Component view, String url) {
	// Recupera o nome do sistema operacional
	String osName = System.getProperty("os.name");
	String browser = null;
	try {
	    if (osName.startsWith("Mac OS")) {
		Class fileMgr = Class.forName("com.apple.eio.FileManager");
		Method openURL = fileMgr.getDeclaredMethod("openURL",
			new Class[]{String.class});
		openURL.invoke(null, new Object[]{url});
	    } else if (osName.startsWith("Windows")) {
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
	    } else { // Unix or Linux
		String[] browsers = {
		    "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"
		};
		for (int count = 0; count < browsers.length && browser == null; count++) {
		    if (Runtime.getRuntime().exec(
			    new String[]{"which", browsers[count]}).waitFor() == 0) {
			browser = browsers[count];
		    }
		}
		JOptionPane.showMessageDialog(null, browser);
		if (browser == null) {
		    JOptionPane.showMessageDialog(null, "Navegador não encontrado!");
		} else {
		    Runtime.getRuntime().exec(new String[]{browser, url});
		}
	    }
	} catch (HeadlessException | IOException | ClassNotFoundException | IllegalAccessException | 
		IllegalArgumentException | InterruptedException | NoSuchMethodException | 
		SecurityException | InvocationTargetException ex) {
	    if (view != null)
	    {
		JOptionPane.showMessageDialog(view,
		"<html>Não foi possível abrir o seu navegador Web. Por favor, acesse o link manualmente:<br/>" + url + "</html>",
		"Erro Interno", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
}