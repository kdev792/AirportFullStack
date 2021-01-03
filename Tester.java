// --== CS400 File Header Information ==--
// Name: Sihan Ren
// Email: sren33@wisc.edu
// Team: MC
// Role: Test Engineer 1
// TA: Harit
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/**
 * The class uses Junit 5 to make the test of each method in each class
 */

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tester {
	private DataReader data;

	@BeforeEach
	public void loadData() {
		data = new DataReader();
		data.storeAirportInfo();
		data.storeFlightPathInfo();
	}

	/**
	 * The method is to check whether the airport name, ID, location and continent
	 * information stored in the object is correct
	 */
	@Test
	public void testAirportObject() {
		// create several airport objects with its name, ID, location and continent
		Airport test1 = new Airport("Zhoushuizi", 1234, "Dalian", "Asia");
		Airport test2 = new Airport("Daxing", 2345, "Beijing", "Asia");
		Airport test3 = new Airport("Taoxian", 4567, "Shenyang", "Asia");
		Airport test4 = new Airport("MSN", 88948, "Madison", "North America");

		if (!test1.getAirportName().equals("Zhoushuizi") || test1.getAirportID() != 1234
				|| !test1.getLocation().equals("Dalian") || !test1.getContinent().equals("Asia")) {
			fail("The contents in test1 object is not correct.");
		}
		if (!test2.getAirportName().equals("Daxing") || test2.getAirportID() != 2345
				|| !test2.getLocation().equals("Beijing") || !test2.getContinent().equals("Asia")) {
			fail("The contents in test2 object is not correct.");
		}
		if (!test3.getAirportName().equals("Taoxian") || test3.getAirportID() != 4567
				|| !test3.getLocation().equals("Shenyang") || !test3.getContinent().equals("Asia")) {
			fail("The contents in test3 object is not correct.");
		}
		if (!test4.getAirportName().equals("MSN") || test4.getAirportID() != 88948
				|| !test4.getLocation().equals("Madison") || !test4.getContinent().equals("North America")) {
			fail("The contents in test4 object is not correct.");
		}
	}

	/**
	 * This method is to check whether the airport information from the
	 * "AirportInfo.csv" file is stored in the graph and transmitted to the back end
	 */
	@Test
	public void testAirportInfoRead() {
		if (!data.mapByCost.containsAirport("Singapore Changi")) {
			fail("There is no such airport name in the csv file");
		}
		if (!data.mapByCost.containsAirport("London Heathrow Airport")) {
			fail("There is no such airport name in the csv file");
		}
		if (!data.mapByCost.containsAirport("Sao Paulo-Guarrulhos International Airport")) {
			fail("There is no such airport name in the csv file");
		}
	}

	/**
	 * This method is to check whether the flight information from the
	 * "FlightInfo.csv" file is stored in the graph and transmitted to the back end
	 */
	@Test
	public void testFlightInfoRead() {
		if (!data.mapByCost.containsPath("Singapore Changi", "O'Hare International Airport")) {
			fail("There is no such airline in the csv file");
		}
		if (!data.mapByCost.containsPath("Dubai International Airport", "Frankfurt Airport")) {
			fail("There is no such airline in the csv file");
		}
		if (!data.mapByCost.containsPath("Shanghai Pudong International Airport", "Denver International Airport")) {
			fail("There is no such airline in the csv file");
		}
	}

	/**
	 * This method test two airport insert method, including inserting an object or
	 * just input the names and locations
	 */
	@Test
	public void testInsertAirport() {
		if (data.mapByCost.containsPath("Cape Town International Airport", "Bole International Airport") == false) {
			fail("The ailine is not contained");
		}
		// check the first insert airport method, inserting an airport object
		Airport test1 = new Airport("Daxing", 2345, "Beijing", "Asia");
		if (data.mapByCost.insertAirport(test1) == false) {
			fail("insert method unsuccessful.");
		}
		// check the second insert airport method, directly inserting the airport name,
		// location and
		// continent
		if (data.mapByCost.insertAirport("MSN", "Madison, WI", "North America") == false) {
			fail("insert method unsuccessful.");
		}
		// check if the insert method return false when inserting an object that has
		// already inserted
		if (data.mapByCost.insertAirport("Madrid Barajas Airport", "Barajas, Madrid, Spain", "Europe") == true) {
			fail("result returned unsuccessfully");
		}
		// check if the nullPointerException catches when inserting a null airport
		try {
			data.mapByCost.insertAirport(null);
			fail("NullPointerException should be caught");
		} catch (Exception e) {
			if (!e.getMessage().contains("Cannot add null vertex"))
				fail("Wrong message got when catching an exception");
		}
	}

	/**
	 * This method checks whether the remove method is correct
	 */
	@Test
	public void testRemoveAirport() {
		// remove an airport that already in the map
		data.mapByCost.insertAirport("BOS", "Boston", "North America");
		if (data.mapByCost.removeAirport("BOS") == false) {
			fail("The remove method is not correct.");
		}
		// remove an airport with invalid name
		try {
			data.mapByCost.removeAirport("wehfuoxnhio");
		} catch (IllegalArgumentException e1) {
			if (!e1.getMessage().contains("This is not an airport"))
				fail("Wrong message got when catching an exception");
		}
		// remove a null airport
		try {
			data.mapByCost.removeAirport(null);
		} catch (NullPointerException e2) {
			if (!e2.getMessage().contains("Need a valid input"))
				fail("Wrong message got when catching an exception");
		}
	}

	/**
	 * This method test insertAirline method. Since the project uses two different
	 * maps, we first test the insertion of mapByCost and then mapByTime
	 */
	@Test
	public void testInsertAirline() {
		// insert an airline in mapByCost and mapByTime
		if (data.mapByCost.insertPath("O'Hare International Airport", "Beijing Capital International Airport",
				1400) == false) {
			fail("insertAirline method failed.");
		}
		if (data.mapByTime.insertPath("O'Hare International Airport", "Dubai International Airport", 3600) == false) {
			fail("insertAirline method failed.");
		}
		// insert an airline with null name and invalid name of start and destination in
		// mapByCost and
		// mapByTime
		try {
			data.mapByCost.insertPath(null, null, 0);
		} catch (NullPointerException e1) {
			if (!e1.getMessage().contains("Neither airport can be null"))
				fail("Wrong message got when catching an exception");
		}
		try {
			data.mapByCost.insertPath("whiidd", "fofoowr", -2500);
		} catch (IllegalArgumentException e2) {
			if (!e2.getMessage().contains("This is not an airport"))
				fail("Wrong message got when catching an exception");
		}
		try {
			data.mapByTime.insertPath(null, null, 0);
		} catch (NullPointerException e3) {
			if (!e3.getMessage().contains("Neither airport can be null"))
				fail("Wrong message got when catching an exception");
		}
		try {
			data.mapByTime.insertPath("wlcmm", "q3liocm", -2900);
		} catch (IllegalArgumentException e4) {
			if (!e4.getMessage().contains("This is not an airport"))
				fail("Wrong message got when catching an exception");
		}
	}

	/**
	 * The method test the remove method of the Airline, still divided into
	 * mapByCost and mapByTime
	 */
	@Test
	public void testremovePath() {
		// remove an airline that already existed in the two maps
		data.mapByCost.insertPath("O'Hare International Airport", "Beijing Capital International Airport", 1400);
		data.mapByTime.insertPath("O'Hare International Airport", "Dubai International Airport", 3600);
		if (data.mapByCost.removePath("O'Hare International Airport",
				"Beijing Capital International Airport") == false) {
			fail("Remove Airline method failed.");
		}
		if (data.mapByTime.removePath("O'Hare International Airport", "Dubai International Airport") == false) {
			fail("Remove Airline method failed.");
		}
		// remove a null airline and an airline with invalid airport name in the two
		// maps
		try {
			data.mapByCost.removePath(null, null);
		} catch (NullPointerException e1) {
			if (!e1.getMessage().contains("Neither airport can be null"))
				fail("Wrong message got when catching an exception");
		}
		try {
			data.mapByTime.removePath(null, null);
		} catch (NullPointerException e2) {
			if (!e2.getMessage().contains("Neither airport can be null"))
				fail("Wrong message got when catching an exception");
		}
		try {
			data.mapByCost.removePath("vhoovh", "whefio");
		} catch (IllegalArgumentException e3) {
			if (!e3.getMessage().contains("This is not an airport"))
				fail("Wrong message got when catching an exception");
		}
		try {
			data.mapByTime.removePath("wvhiorn", "vowpv");
		} catch (IllegalArgumentException e4) {
			if (!e4.getMessage().contains("This is not an airport"))
				fail("Wrong message got when catching an exception");
		}
	}

	/**
	 * The test test whether we can get the correct cost between the origin and
	 * destination we input Check both the time and cost map
	 */
	@Test
	public void testgetWeight() {
		if (data.mapByCost.getWeight("Beijing Capital International Airport",
				"Los Angeles International Airport") != 1379) {
			fail("The method is not correct");
		}
		if (data.mapByTime.getWeight("Beijing Capital International Airport",
				"Jorge Chavez International Airport") != 1680) {
			fail("The method is not correct");
		}
		try {
			data.mapByCost.getWeight("ehjk", "shci");
		} catch (IllegalArgumentException e1) {
			if (!e1.getMessage().contains("There is no path between these two airports"))
				fail("Wrong message got when catching an exception");
		}
	}

	/**
	 * The test test whether the total number of airport and airline is correct.
	 * Check both the time and cost map
	 */
	@Test
	public void testGetAirportCount() {
		if (data.mapByCost.getNumberOfAirports() != 22 || data.mapByTime.getNumberOfAirports() != 22) {
			fail("The getAirportCount method is not correct");
		}
		if (data.mapByCost.getNumberOfPaths() != 66 || data.mapByTime.getNumberOfPaths() != 66) {
			fail("The getAirlineCount method is not correct");
		}
	}

	/**
	 * The method test whether the lowest cost of the flight follows the Dijkstra
	 * Short Path algorithm Test for both Cost and Time map
	 */
	@Test
	public void testcheapestFlight() {
		// check the shortest path of existed airlines
		// System.out.println(data.mapByCost.printCheapestFlightPath("Singapore Changi",
		// "Bole
		// International Airport"));
		if (data.mapByCost.cheapestFlight("Singapore Changi", "Bole International Airport") != 3099) {
			fail("The method is not correct");
		}
		if (data.mapByTime.cheapestFlight("Heathrow Airport", "Frankfurt Airport") != 1945) {
			fail("The method is not correct");
		}
		if (data.mapByCost.cheapestFlight("Shanghai Pudong International Airport", "London Heathrow Airport") != 1129) {
			fail("The method is not correct");
		}
		if (data.mapByTime.cheapestFlight("El Dorado International Airport", "Charles de Gaulle Airport") != 1790) {
			fail("The method is not correct");
		}
		// check if the method throw exception when the airport name is not contained
		try {
			data.mapByCost.cheapestFlight("wejkh", null);
		} catch (NullPointerException e) {
			if (!e.getMessage().contains("Neither airport can be null"))
				fail("Wrong message got when catching an exception");
		}
		// check if the path that is not existed in the graph throws the exception
		try {
			data.mapByTime.cheapestFlight("Sao Paulo-Guarrulhos International Airport", "Madrid Barajas Airport");
		} catch (NoSuchElementException e1) {
			if (!e1.getMessage().contains("There is no such element in the graph"))
				fail("Wrong message got when catching an exception");
		}
	}

	/**
	 * The method test whether the path printed from start vertex to the end vertex
	 * follows the Dijkstra Algorithm of the shortest path, also check both two maps
	 */
	@Test
	public void testLowestCostPath() {
		if (!data.mapByCost.printCheapestFlightPath("Singapore Changi", "Bole International Airport")
				.equals("--------------------------------\n" + "Start\n" + "Singapore Changi\n"
						+ "Shanghai Pudong International Airport\n" + "Cape Town International Airport\n"
						+ "Bole International Airport\n" + "Finish\n" + "---------------------------------\n")) {
			fail("The method is not correct");
		}
		if (!data.mapByTime.printCheapestFlightPath("Heathrow Airport", "Frankfurt Airport")
				.equals("--------------------------------\n" + "Start\n" + "Heathrow Airport\n"
						+ "Sao Paulo-Guarrulhos International Airport\n" + "Dubai International Airport\n"
						+ "Frankfurt Airport\n" + "Finish\n" + "---------------------------------\n")) {

		}
	}

	/**
	 * The method test if the list of continent print the corresponding airports in
	 * the input continent
	 * 
	 */
	@Test
	public void testListOfContinent() {
		if (!data.mapByCost.printAllAirportsAndContinents("Asia").equals("---------------------------------\n"
				+ "Asia:\n"
				+ "[Singapore Changi, Beijing Capital International Airport, Dubai International Airport, Tokyo Haneda Airport, Shanghai Pudong International Airport]\n"
				+ "---------------------------------")) {
			fail("The method is not correct");
		}
		if (!data.mapByTime.printAllAirportsAndContinents("Europe").equals("---------------------------------\n"
				+ "Europe:\n"
				+ "[London Heathrow Airport, Amsterdam Airport Schiphol, Frankfurt Airport, Madrid Barajas Airport, Istanbul Airport, Heathrow Airport, Charles de Gaulle Airport]\n"
				+ "---------------------------------")) {
			fail("The method is not correct");
		}
	}

	/**
	 * The method test if the list of airports print the correct list of airports
	 */
	@Test
	public void testListofAirports() {
		if (!data.mapByCost.printAllAirports().equals("---------------------------------\n"
				+ "Continent: South America\n"
				+ "Airport: [Sao Paulo-Guarrulhos International Airport, El Dorado International Airport, Jorge Chavez International Airport]\n"
				+ "Continent: Asia\n"
				+ "Airport: [Singapore Changi, Beijing Capital International Airport, Dubai International Airport, Tokyo Haneda Airport, Shanghai Pudong International Airport]\n"
				+ "Continent: Europe\n"
				+ "Airport: [London Heathrow Airport, Amsterdam Airport Schiphol, Frankfurt Airport, Madrid Barajas Airport, Istanbul Airport, Heathrow Airport, Charles de Gaulle Airport]\n"
				+ "Continent: Africa\n" + "Airport: [Bole International Airport, Cape Town International Airport]\n"
				+ "Continent: North America\n"
				+ "Airport: [O'Hare International Airport, John F. Kennedy International Airport, Dallas/Fort Worth International Airport, Los Angeles International Airport, Denver International Airport]\n"
				+ "---------------------------------")) {
			fail("The method is not correct");
		}
	}

	/**
	 * The method test airportFromLocation is correct with the location input
	 */
	@Test
	public void testAirportFromLocation() {
		if (!data.mapByCost.printAtLocation("Changi-East Region-Singapore").equals("Singapore Changi")) {
			fail("The method is not correct");
		}
		if (!data.mapByCost.printAtLocation("Queens-New York-United States")
				.equals("John F. Kennedy International Airport")) {
			fail("The method is not correct");
		}
		try {
			data.mapByCost.printAtLocation("wweih");
		} catch (IllegalArgumentException e) {
			if (!e.getMessage().contains("This location is not on our map"))
				fail("Wrong message got when catching an exception");
		}
	}

	/**
	 * The method test if the location of the input name of airport print correctly
	 */
	@Test
	public void testGetLocationFromName() {
		if (!data.mapByCost.printLocationFromAirport("Charles de Gaulle Airport").equals("Paris-France")) {
			fail("The method is not correct");
		}
		if (!data.mapByCost.printLocationFromAirport("Jorge Chavez International Airport").equals("Lima-Peru")) {
			fail("The method is not correct");
		}
		try {
			data.mapByCost.printLocationFromAirport("hifh");
		} catch (IllegalArgumentException e) {
			if (!e.getMessage().contains("This airport is not on our map"))
				fail("Wrong message got when catching an exception");
		}
	}

}