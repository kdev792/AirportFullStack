public class Airport {
	private String airportName; // name of airport
	private Integer airportID; // airport’s ID
	private String location; // location of airport (can be city, state, country, region, etc.)
	private String continent; // continent of airport

	public Airport(String airportName, Integer airportID, String location, String continent) {
		this.airportName = airportName;
		this.airportID = airportID;
		this.location = location;
		this.continent = continent;
	}

	public String getAirportName() {
		return this.airportName;
	}

	public Integer getAirportID() {
		return this.airportID;
	}

	public String getLocation() {
		return this.location;
	}

	public String getContinent() {
		return this.continent;
	}
}