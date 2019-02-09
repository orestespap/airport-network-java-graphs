import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FindAirportFrame extends JFrame {
	private JPanel panel;
	private JButton FindButton,VisualizeButton;
	private JTextField findfield;
	public FindAirportFrame() {
		
		panel = new JPanel();
		findfield=new JTextField("City name: ", 15);
		FindButton= new JButton("Find");
		VisualizeButton= new JButton("Visualize Network");
		
		panel.add(findfield);
		panel.add(FindButton);
		panel.add(VisualizeButton);

		
		
		findfield.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
			    findfield.setText(""); 
			  }
			});//when findfield is initially clicked on, text area will be cleared
				
		FindButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			String city = findfield.getText();
			Airport airportx=CentralRegistry.getAirport(city);
			
			if (airportx!=null)
				new AirportPageFrame(airportx);
			else
				JOptionPane.showMessageDialog(null,city+" does not have an airport");
			
		}
			}
		);
		
		VisualizeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			new VisFrame(CentralRegistry.getFlights());
		}
			}
		);
		
		this.setContentPane(panel);
		this.setVisible(true);
		this.setSize(300,400);
		this.setTitle("Find Airport");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
	}
}
