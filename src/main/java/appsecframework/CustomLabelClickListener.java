package appsecframework;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CustomLabelClickListener implements MouseListener{
	
	String URI;
	public CustomLabelClickListener(String URI) {
		this.URI = URI;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			Desktop.getDesktop().browse(new URI(URI));
		} catch (IOException | URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
