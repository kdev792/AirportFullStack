
// --== CS400 File Header Information ==--
// Name: <Robert Schultz>
// Email: <rlschultz5@wisc.edu>
// Team: <MC>
// Role: <Data Wrangler>
// TA: <Harit>
// Lecturer: <Gary Dahl>
// Notes to Grader: <optional extra notes>
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This class initiates two Dijkstra's Shortest Path hash tables from two csv
 * files containing Airport (vertex) object information and flightPath (edge)
 * information. One tree is based on time travel, while the other is based on
 * coast of travel.
 *
 */
public class DataReader {
	public AirportBackEnd mapByTime = new AirportBackEnd();
	public AirportBackEnd mapByCost = new AirportBackEnd();

	/**
	 * Read data from the CSV file called AirportInfo.csv
	 *
	 * @return an arrayList that stores contents of each row in the file
	 */
	public ArrayList readAirportInfo() {
		ArrayList<String[]> airportList = new ArrayList<String[]>();
		try {
			FileReader fileReader = new FileReader("AirportInfo.csv");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = "";
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				String[] contents = line.split(",");
				airportList.add(contents);
			}
		} catch (Exception e) {
			System.out.println("Error reading from AirportInfo.csv");
		}
		return airportList;
	}

	/**
	 * Create Airport objects with data from AirportInfo.csv and add to initial map
	 */
	public void storeAirportInfo() {
		ArrayList<String[]> airportList = readAirportInfo();
		String airportName = "";
		String airportLocation = "";
		String airportContinent = "";
		for (int i = 0; i < airportList.size(); i++) {
			airportName = ((String[]) airportList.get(i))[0].trim();
			airportLocation = ((String[]) airportList.get(i))[1].trim();
			airportContinent = ((String[]) airportList.get(i))[2].trim();
			mapByTime.insertAirport(airportName, airportLocation, airportContinent);
			mapByCost.insertAirport(airportName, airportLocation, airportContinent);
		}
	}

	/**
	 * Read data from FlightPaths.csv
	 *
	 * @return an arrayList that stores contents of each row in the file
	 */
	public ArrayList<String[]> readFlightPathInfo() {
		ArrayList<String[]> flightPathList = new ArrayList<String[]>();
		try {
			FileReader reader = new FileReader("FlightInfo.csv");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = "";
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				String[] contents = line.split(",");
				flightPathList.add(contents);
			}
		} catch (Exception e) {
			System.out.println("Error reading from FlightPaths.csv");
		}
		return flightPathList;
	}

	/**
	 * Insert flightPath edges into both initial maps
	 */
	public void storeFlightPathInfo() {
		ArrayList<String[]> flightPathList = readFlightPathInfo();
		String origin = "";
		String destination = "";
		int timeCost = 0;
		int currencyCost = 0;
		for (int i = 0; i < flightPathList.size(); i++) {
			origin = ((String[]) flightPathList.get(i))[0].trim();
			destination = ((String[]) flightPathList.get(i))[1].trim();
			timeCost = Integer.parseInt(((String[]) flightPathList.get(i))[2].trim());
			currencyCost = Integer.parseInt(((String[]) flightPathList.get(i))[3].trim());
			mapByTime.insertPath(origin, destination, timeCost);
			mapByCost.insertPath(origin, destination, currencyCost);
		}
	}
}