import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AirportPageFrame extends JFrame {
	
	private JPanel mainpanel;
	private Airport myairport;
	
	public AirportPageFrame(Airport anairport) {
		mainpanel=new JPanel();
		myairport=anairport;
		
		
		JPanel panel1= new JPanel();
		int fieldsize=10;
		JTextField name= new JTextField(myairport.getName(),fieldsize);
		JTextField id= new JTextField(myairport.getID(),fieldsize);
		JTextField city= new JTextField(myairport.getCity(),fieldsize);
		JTextField country= new JTextField(myairport.getCountry(),fieldsize);
		
		name.setEditable(false);
		id.setEditable(false);
		city.setEditable(false);
		country.setEditable(false);
		
		name.setBackground(Color.WHITE);
		id.setBackground(Color.WHITE);
		city.setBackground(Color.WHITE);
		country.setBackground(Color.WHITE);
		
		
		HashSet<String> comptemp= CentralRegistry.AssociatedCompanies(myairport);
		ArrayList<String> companies= new ArrayList<>(comptemp);
		Collections.sort(companies);
		
		String compstring="";
		for(String acompany: companies)
			compstring+=acompany+"\n";
		JTextArea comparea = new JTextArea(compstring);
		comparea.setPreferredSize(new Dimension(100,100));
		comparea.setEditable(false);
		comparea.setBackground(Color.WHITE);
		
		panel1.add(name);
		panel1.add(id);
		panel1.add(city);
		panel1.add(country);
		panel1.add(comparea);
		
		JPanel panel2=new JPanel();
		
		JTextField anothercityfield= new JTextField("City name: ",fieldsize);
		JButton FindFlights = new JButton("Find Flights");
		
		anothercityfield.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
				  anothercityfield.setText("");
			  }
			}); //when anothercityfield is initially clicked on, text area will be cleared
		
		
		panel2.add(anothercityfield);
		panel2.add(FindFlights);
		
		mainpanel.add(panel1, BorderLayout.PAGE_START);
		mainpanel.add(panel2, BorderLayout.CENTER);

		JPanel panel3 = new JPanel();
		FindFlights.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			String city = anothercityfield.getText();
			Airport airportx=CentralRegistry.getAirport(city);
			
			if (airportx!=null) {
				String directinfo=CentralRegistry.getDirectFlightsDetails(myairport,airportx);
				JTextArea directinfofield = new JTextArea(directinfo);
				directinfofield.setPreferredSize(new Dimension( 340, 340 ) );

				directinfofield.setEditable(false);
				directinfofield.setBackground(Color.WHITE);
				
				
				String indirectinfo=CentralRegistry.getInDirectFlightsDetails(myairport,airportx);
				JTextArea indirectinfofield = new JTextArea(indirectinfo);
				indirectinfofield.setPreferredSize(new Dimension( 340, 340 ) );

				indirectinfofield.setEditable(false);
				indirectinfofield.setBackground(Color.WHITE);
				panel3.add(directinfofield);
				panel3.add(indirectinfofield);
				
				
				mainpanel.add(panel3, BorderLayout.SOUTH);
				mainpanel.revalidate();
				
				
				try {
					File f = new File(myairport.getCity()+"To"+airportx.getCity()+".txt");
					FileWriter writer = new FileWriter(f);
					writer.write("CITY: "+myairport.getCity()+","+myairport.getCountry());
					writer.write(System.lineSeparator());
					writer.write("Airport: "+myairport.getName()+","+myairport.getID());
					writer.write(System.lineSeparator());
					writer.write(System.lineSeparator());
					writer.write("DESTINATION: "+airportx.getCity());
					writer.write(System.lineSeparator());
					writer.write(System.lineSeparator());
					
					writer.write(directinfo);
					if (directinfo.equals("DIRECT FLIGHTS DETAILS"+System.lineSeparator())) {
						writer.write(System.lineSeparator());
						writer.write("No direct flights");
					}
					writer.write(System.lineSeparator());
					writer.write(System.lineSeparator());
					writer.write(indirectinfo);
					if (indirectinfo.equals("INDIRECT FLIGHTS through"+System.lineSeparator())) {
						writer.write(System.lineSeparator());
						writer.write("No indirect flights");
					}
					writer.close();
					
					} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				
				
			}
			
			else
				JOptionPane.showMessageDialog(null,city+" does not have an airport");
			
		}
			}
		);
		
		
		JButton BackTS = new JButton("Back to Search Screen");
		BackTS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AirportPageFrame.this.dispose();
			}
			});
		panel3.add(BackTS);
		mainpanel.add(panel3);
		
		
		this.setContentPane(mainpanel);
		this.setVisible(true);
		this.setSize(500,500);
		this.setTitle("Find Airport");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
