import java.util.ArrayList;

public class Airport {
	
	private String name;
	private String id;
	private String city;
	private String country;
	
	public Airport(String name, String id, String city, String country) {
		this.name=name;
		this.id=id;
		this.city=city;
		this.country=country;
		
	}
	public String getName() {
		return name;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getID() {
		return id;
	}
	
	public String getCountry() {
		return country;
	}
	
	
	public ArrayList<Flight> isDirectlyConnectedTo(Airport a2) {
		return CentralRegistry.CheckDirect(this,a2);
	}
	
	public ArrayList<Airport> isInDirectlyConnectedTo(Airport a2) {
		return CentralRegistry.CheckInDirect(this,a2);
		
	}
	
	public ArrayList<Airport> getCommonConnections(Airport a2){
		return CentralRegistry.CheckCC(this,a2);
	}
	
	public void printCompanies() {
		CentralRegistry.AssociatedCompanies(this);
	}
	
	public String toString() {
		return city+", "+id;
	}
}