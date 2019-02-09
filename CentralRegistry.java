import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class CentralRegistry {
	private static  ArrayList<Airport> airports= new ArrayList<>();
	private static ArrayList<Flight> flights= new ArrayList<>();
	
	public static void addAirport(Airport x) {
		airports.add(x);
	}
	
	public static void addFlight(Flight x) {
		flights.add(x);
	}
	
	public static Airport getAirport(String cityName) {
		for(Airport anairport: airports)
			if(anairport.getCity().toLowerCase().equals(cityName.toLowerCase()))
				return anairport;
		return null;
		
	}
	
	public static ArrayList<Airport> getAirports(){
		return airports;
	}
	
	public static ArrayList<Flight> getFlights(){
		return flights;
	}
	
	public static String getDirectFlightsDetails(Airport a1, Airport a2) {
		ArrayList<Flight> dflights= CentralRegistry.CheckDirect(a1, a2);
		String text="DIRECT FLIGHTS DETAILS"+System.lineSeparator();
		int i=1;
		for (Flight aflight:dflights) {
			text+="["+Integer.toString(i)+"]"+aflight.toString();
			i+=1;
		}
		return text;
	}
	
	public static String getInDirectFlightsDetails(Airport a1, Airport a2) {
		ArrayList<Airport> airports= CentralRegistry.CheckInDirect(a1, a2);
		HashSet<Airport> airportset = new HashSet<>(airports);
		String text="INDIRECT FLIGHTS through"+System.lineSeparator();
		int i=1;
		for (Airport anairport:airportset) {
			text+="["+Integer.toString(i)+"]"+anairport.toString();
			i+=1;
		}
		return text;
	}
	
	public static Airport getLargestHub() {
		Airport maxa=airports.get(0);
		int maxcount=0;
		for(Airport anairport: airports) {
			String name= anairport.getName();
			int count=0;
			for (Flight aflight: flights) {
				if(name.equals(aflight.getAirportA().getName())||name.equals(aflight.getAirportB().getName()))
					count++;
				if(count>maxcount) {
					maxa=anairport;
					maxcount=count;
				}
			}
		}
		return maxa;
	}
	
	public static Flight getLongestFlight() {
		Flight maxf=flights.get(0);
		int maxlength=flights.get(0).getLength();
		for(Flight aflight: flights)
			if(aflight.getLength()>=maxlength) {
				maxf=aflight;
				maxlength=aflight.getLength();
			}
		return maxf;
	}
	
	public static ArrayList<Flight> CheckDirect(Airport a1, Airport a2) {
		ArrayList<Flight> directflights = new ArrayList<>();
		for(Flight aflight: flights){
			if((a1.getName().equals(aflight.getAirportA().getName())||a1.getName().equals(aflight.getAirportB().getName()))
					&& 
				(a2.getName().equals(aflight.getAirportA().getName())||a2.getName().equals(aflight.getAirportB().getName()))) {
				directflights.add(aflight);
			}
		}
		return directflights;
	}
	
	public static ArrayList<Airport> CheckInDirect(Airport a1, Airport a2) {
		ArrayList<Airport> indirectairports = new ArrayList<>();
		//for every flight, checks if a1 is either one of the two airports
		//and if the other airport in the particular flight isn't a2
		//if it isn't, it checks if the particular airport is also connected with a2
		//if it is, then a1 and a2 are indirectly connected via that airport
		
		for (Flight aflight: flights) {
			if (a1.getName().equals((aflight.getAirportA().getName()))) {
				String check=aflight.getAirportB().getName();
				if (!check.equals(a2.getName())){
					for (Flight anotherflight: flights)
						if (InDirectIf(anotherflight,a1,a2,check))
							indirectairports.add(aflight.getAirportB());
				}
					
			}
			
			
			if (a1.getName().equals((aflight.getAirportB().getName()))) {
				String check=aflight.getAirportA().getName();
				if (!check.equals(a2.getName())) {
					for (Flight anotherflight: flights)
						if (InDirectIf(anotherflight,a1,a2,check))
							indirectairports.add(aflight.getAirportA());
					}
			}
		}
		return indirectairports;
	}
	
	private static boolean InDirectIf(Flight anotherflight,Airport a1, Airport a2, String check) {
		
		//If statement checks: A2 is either Airport A or Airport B
		//While also making sure that A1 is neither Airport A or Airport B
		//so: if a2 -> check or check-> a2 returns True
		
		if ((anotherflight.getAirportA().getName().equals(a2.getName())
				||
				anotherflight.getAirportB().getName().equals(a2.getName()))
				&& 
				(!a1.getName().equals(anotherflight.getAirportA().getName()) && !a1.getName().equals(anotherflight.getAirportB().getName()))
				&&
				(anotherflight.getAirportA().getName().equals(check)
						||
						anotherflight.getAirportB().getName().equals(check))) {
			return true;
		}
		return false;
	}
	
	public static ArrayList<Airport> CheckCC(Airport a1, Airport a2){
		ArrayList<Airport> connections = new ArrayList<>();
		for(Airport airport: airports) {
			if (a1.isDirectlyConnectedTo(airport)!=null && a2.isDirectlyConnectedTo(airport)!=null)
				connections.add(airport);
		
			return connections;		
		}
		
		
		return connections;
	}
	
	public static HashSet<String> AssociatedCompanies(Airport a1) {
		ArrayList<String> temp=new ArrayList<>();
		for(Flight aflight: flights)
			if(a1.getName().equals(aflight.getAirportA().getName())
					||
			   a1.getName().equals(aflight.getAirportB().getName())){
				temp.add(aflight.getCompany());
			}
		HashSet<String> companies= new HashSet<>(temp);
		//use of HashSet to keep unique values
		return companies;
	}
	
}
