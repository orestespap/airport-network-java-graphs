
public class Flight {
	private Airport a1;
	private Airport a2;
	private int length;
	private String company;
	
	public Flight(Airport a1, Airport a2, int length, String company) {
		this.a1=a1;
		this.a2=a2;
		this.length=length;
		this.company=company;
	}
	
    public Airport getAirportA() {
    	return a1;
    }
    
    public Airport getAirportB() {
    	return a2;
    }

    public int getLength() {
    	return length;
    }
    
    public String getCompany() {
    	return company;
    }
    
    @Override
    public String toString() {
    	return "Flight operated by "+company+", duration "+length+" minutes.";
    }
}