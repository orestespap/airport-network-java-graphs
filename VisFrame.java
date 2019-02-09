import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class VisFrame extends JFrame{
	private JPanel panel;
	public VisFrame(ArrayList<Flight> flights) {
		panel = new JPanel();
		UndirectedSparseGraph usg = new UndirectedSparseGraph();
		int i=0;
		for (Flight aflight: flights) {
			usg.addVertex(aflight.getAirportA().getCity());
			usg.addVertex(aflight.getAirportB().getCity());
			usg.addEdge(Integer.toString(i),aflight.getAirportA().getCity(), aflight.getAirportB().getCity());
			i+=1;
		}
	
		Layout<String, String> layout = new CircleLayout(usg);
		layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<String,String> vv = new BasicVisualizationServer<String,String>(layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		
		String diameterText="Diameter : " + DistanceStatistics.diameter(usg); 
		//Diameter: shortest longest path, the shortest distance between the two most distant nodes
		JTextField diameterfield=new JTextField(diameterText, 40);
		diameterfield.setEditable(false);
		
		panel.add(vv);
		panel.add(diameterfield);
		
		this.setContentPane(panel);
		this.setVisible(true);
		this.pack();
		this.setSize(300,400);
		this.setTitle("Network visualization");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	}

}
