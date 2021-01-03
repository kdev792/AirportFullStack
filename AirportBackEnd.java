import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

public class AirportBackEnd {

	/*
	 * All private variables
	 */

	// Graph object that enables us to use Djikstra's shortest algorithm
	private CS400Graph map;

	// 3 HashMap objects that will contain the names of the airports, their
	// locations, and their identifiying ID's eventually
	private Map<String, Airport> airportNames = new HashMap<>();
	private Map<String, Airport> airportLocation = new HashMap<>();
	private Map<String, Airport> airportID = new HashMap<>();

	// HashMap object with a LinkedList parameter that stores the type variables for
	// this program: the continents
	private Map<String, List<String>> continents = new HashMap<String, List<String>>();

	// Random type variable to help produce random integers
	private Random random = new Random();

	// We have been consistently using 23 as a set number and will do so for this
	// project, keeping it consistent across all projects and roles
	private Integer index = 23;

	/**
	 * Constructor for the FinalBackEnd class that initializes the map variable with
	 * the CS400Graph()
	 */
	public AirportBackEnd() {
		this.map = new CS400Graph();
	}

	/**
	 * If only an airport type parameter is provided, this inserts a new airport
	 * into the map
	 * 
	 * @param airport Type airport to be inserted into the map
	 * @return true if it is inserted, false if not
	 */
	public boolean insertAirport(Airport airport) {

		// inserts a vertex with the airport variable as a parameter
		return this.map.insertVertex(airport);
	}

	/**
	 * If the name, location, and continent is provided/needed then this
	 * insertAirport method is used to insert a new Airport onto the map
	 * 
	 * @param name:      name of the airport
	 * @param location:  location in the world of the airport
	 * @param continent: continent that the airport is in (type variable used)
	 * @return true if it is inserted, false if not
	 */
	public boolean insertAirport(String name, String location, String continent) {

		// if any of the inputs are null then an exception is thrown
		if (name == null || location == null || continent == null) {
			throw new NullPointerException("Must have all three inputs to add to map");
		}

		// if the airport is already on the map then the method returns false
		if (airportNames.containsKey(name)) {
			return false;
		}

		// as long as the index is in the HashMap of airportIDs, it will be randomized
		// and set as the new index. It gives us a new random ID that is available
		while (airportID.containsKey(index)) {
			index = random.nextInt(1000);
		}

		// Airport type object that's used as a holder, with the ID as the index
		// randomized variable. Will be added to the HashMap
		Airport pointer = new Airport(name, index, location, continent);

		// adds the Airport type object to the CS400Graph type variable map
		if (map.insertVertex(pointer)) {

			// Adds to the HashMap where we can filter by name
			airportNames.put(name, pointer);

			// Adds to the HashMap where we can filter by location
			airportLocation.put(location, pointer);

			// Adds to the HashMap where we can filter by ID
			airportID.put(index.toString(), pointer);

			// Checks to see if the continents HashMap has the continent argument, if it
			// does not then it is added to the continents HashMap with a temporary List
			// type variable
			if (continents.containsKey(continent) == false) {
				List<String> hold = new ArrayList<String>();
				continents.put(continent, hold);

			}

			// the name variable is added to the continents
			continents.get(continent).add(name);

			return true;
		}

		// returns false if the vertex cannot be added
		return false;
	}

	/**
	 * removes airport from the map along with edges
	 * 
	 * @param name: the name of the airport to be removed
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean removeAirport(String name) {

		// if the input is null an exception is thrown
		if (name == null) {
			throw new NullPointerException("Need a valid input");
		}

		// if the airport is not on the map then an exception is thrown
		if (airportNames.containsKey(name) == false) {
			throw new IllegalArgumentException("This is not an airport");
		}

		// gets an airport type variable from the name given
		Airport pointer = airportNames.get(name);

		// if the removeVertex function returns true then this loop is entered
		if (this.map.removeVertex(pointer)) {

			// the airport is removed from all iterations of the local HashMap
			continents.get(getAirport(name).getContinent()).remove(name);
			airportNames.remove(name);
			airportLocation.remove(pointer.getLocation(), pointer);
			airportID.remove(pointer.getAirportID(), pointer);
			return true;
		}

		else {
			return false;
		}
	}

	/**
	 * Inserts a path from one airport to another with a weight
	 * 
	 * @param start:  the starting airport for the path
	 * @param end:    the ending airport for the path
	 * @param weight: the weight of the edge between the airports
	 * @return true if the path was inserted, false if not
	 */
	public boolean insertPath(String start, String end, int weight) {

		// If either starting or ending airports are null then an exception is thrown
		if (start == null || end == null) {
			throw new NullPointerException("Neither airport can be null");
		}

		// if either the start or the end airports are not in the HashMap with the
		// airport names then an exception is thrown
		if (airportNames.containsKey(start) == false) {
			throw new IllegalArgumentException("This is not an airport");
		}
		if (airportNames.containsKey(end) == false) {
			throw new IllegalArgumentException("This is not an airport");
		}

		// If the weight of a path is either negative or 0, then an exception is thrown
		if (weight <= 0) {
			throw new IllegalArgumentException("The cost of the trip must be greater than 0");
		}

		// returns true if the path has been inserted, false if not
		return this.map.insertEdge(airportNames.get(start), airportNames.get(end), weight);
	}

	/**
	 * Removes a path from one airport to another
	 * 
	 * @param start: the starting airport of the path
	 * @param end:   the ending airport of the path
	 * @return true if the path was removed, false if not
	 */
	public boolean removePath(String start, String end) {

		// If either starting or ending airports are null then an exception is thrown
		if (start == null || end == null) {
			throw new NullPointerException("Neither airport can be null");
		}

		// if either the start or the end airports are not in the HashMap with the
		// airport names then an exception is thrown
		if (airportNames.containsKey(start) == false) {
			throw new IllegalArgumentException("This is not an airport");
		}
		if (airportNames.containsKey(end) == false) {
			throw new IllegalArgumentException("This is not an airport");
		}

		// returns true if the path has been removed, false if not
		return this.map.removeEdge(airportNames.get(start), airportNames.get(end));
	}

	/**
	 * checks if the provided name is present within the airportNames HashMap
	 * 
	 * @param name: the name of the airport to be checked
	 * @return true if it is contained in the HashMap, false if not
	 */
	public boolean containsAirport(String name) {
		return this.map.containsVertex(airportNames.get(name));
	}

	/**
	 * Checks if there is a path between the starting and ending airports provided
	 * as arguments
	 * 
	 * @param start: the starting airport for the path
	 * @param end:   the ending airport for the path
	 * @return true if there is a path, false if not
	 */
	public boolean containsPath(String start, String end) {
		return this.map.containsEdge(airportNames.get(start), airportNames.get(end));
	}

	/**
	 * gets the weight of a path between airports
	 * 
	 * @param start: the starting airport
	 * @param end:   the ending airport
	 * @return the weight as an int type variable
	 */
	public int getWeight(String start, String end) {

		if (airportNames.containsKey(start) == false || airportNames.containsKey(end) == false) {
			throw new IllegalArgumentException("There is no path between these two airports");
		}

		// if the map does not have this path then an exception is thrown
		if (containsPath(start, end) == false) {
			throw new IllegalArgumentException("There is no path between these two airports");
		}

		return this.map.getWeight(airportNames.get(start), airportNames.get(end));

	}

	/**
	 * gets the number of paths (edges) on the map from airport to airport
	 * 
	 * @return the int value for the number of edges
	 */
	public int getNumberOfPaths() {
		return this.map.getEdgeCount();
	}

	/**
	 * gets the number of airports (vertices) on the map
	 * 
	 * @return the int value for the number of airports
	 */
	public int getNumberOfAirports() {
		return this.map.getVertexCount();
	}

	/**
	 * 
	 * @return true if the map is empty, false if not
	 */
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	/**
	 * 
	 * @param name: the name of the Airport we are trying to get
	 * @return returns the Airport type variable based on its name
	 */
	public Airport getAirport(String name) {
		return airportNames.get(name);
	}

	/**
	 * Finds the cheapest flight (shortest path) between the starting and ending
	 * airport provided. Utilizes Djikstra's algorithm from last project
	 * 
	 * @param start: the name of the starting airport
	 * @param end:   the name of the ending airport
	 * @return the int value for the cheapest path from airport to airport
	 */
	public int cheapestFlight(String start, String end) {

		// If either starting or ending airports are null then an exception is thrown
		if (start == null || end == null) {
			throw new NullPointerException("Neither airport can be null");
		}

		// needs to be on the map to work
		if (airportNames.containsKey(start) == false || airportNames.containsKey(end) == false) {
			throw new IllegalArgumentException("There is no path between these two airports");
		}

		return map.getPathCost(airportNames.get(start), airportNames.get(end));

	}

	/**
	 * Prints out the path of the cheapest flight from airport to airport
	 * 
	 * @param start: the starting airport of the path
	 * @param end:   the ending airport of the path
	 * @return the String representation of the cheapest path
	 */
	public String printCheapestFlightPath(String start, String end) {

		// String variable to be returned later
		String toReturn = "";

		// List type variable that holds the airports and the shortest paths between
		// them
		List<Airport> flights = map.shortestPath(airportNames.get(start), airportNames.get(end));

		// Prints out the shortest path from start to end, uses an enhanced for loop to
		// iterate through the List type variable created above
		toReturn += "--------------------------------\n";
		toReturn += "Start\n";
		for (Airport hold : flights) {
			toReturn += hold.getAirportName() + "\n";
		}
		toReturn += "Finish\n";
		toReturn += "---------------------------------\n";

		return toReturn;
	}

	/**
	 * Prints the airport at a certain location
	 * 
	 * @param location: the location from which we are going to print the airport
	 *                  name
	 * @return a String that we can print out containing the airport at the given
	 *         location
	 */
	public String printAtLocation(String location) {

		// String type variable to be returned and later printed if necessary
		String toReturn;

		// The airport type variable that we will use to find the airport at the
		// location
		Airport airportReturn;

		// if the location input is not given properly
		if (location == null) {
			throw new NullPointerException("There must be a valid location name");
		}

		// if the provided location is not on the map of locations
		if (airportLocation.containsKey(location) == false) {
			throw new IllegalArgumentException("This location is not on our map");
		}

		else {
			airportReturn = airportLocation.get(location);
			toReturn = airportReturn.getAirportName();
		}

		return toReturn;

	}

	/**
	 * Prints the name of the location that contains the given airport
	 * 
	 * @param airport: the name of the airport from which we will find the location
	 * @return a String that we can print out containing the location which has the
	 *         given airport
	 */
	public String printLocationFromAirport(String airport) {
		// String type variable to be returned and later printed if necessary
		String toReturn;

		// The airport type variable that we will use to find the name of the location
		// with the airport
		Airport airportReturn;

		// if the airport input is not given properly
		if (airport == null) {
			throw new NullPointerException("There must be a valid airport name");
		}

		// if the provided location is not on the map of locations
		if (airportNames.containsKey(airport) == false) {
			throw new IllegalArgumentException("This airport is not on our map");
		}

		else {
			airportReturn = airportNames.get(airport);
			toReturn = airportReturn.getLocation();
		}

		return toReturn;

	}

	/**
	 * Prints all the airports in the given continent
	 * 
	 * @param continent: the continent we are finding the airports in
	 * @return the String of all airports on said continent
	 */
	public String airportsOnContinent(String continent) {

		// String variable to be returned later
		String toReturn = "";

		if (continents.containsKey(continent) == false) {
			throw new NoSuchElementException("This continent is not on our map");
		}

		// will be used to track the number of airports in the continent
		int tracker = 0;

		List<Airport> airports = airportNames.values().stream().filter(c -> c.getContinent().equals("South America"))
				.collect(Collectors.toList());
		for (Airport temp : airports) {
			toReturn += temp.getAirportName() + "\n";
			tracker++;
		}

		toReturn += "There are " + tracker + " airports on the continent " + continent + "\n";

		return toReturn;
	}

	/**
	 * Prints all the airports in the given continent
	 * 
	 * @param continent: the continent we are finding the airports in
	 * @return the String of all airports on said continent
	 */
	public String printAllAirportsAndContinents(String continent) {

		// String variable to be returned later
		String toReturn = "";

		// If the map is not empty then we proceed
		if (!(continents.isEmpty())) {
			toReturn += "---------------------------------\n";

			// if there are no airports in the specified continent
			if (continents.get(continent).isEmpty()) {
				toReturn += "There are no airports in " + continent + "\n";
			}

			// otherwise, it adds the names of the airports on the continent and adds to the
			// String to be returned
			else {
				toReturn += continent + ":\n";
				toReturn += continents.get(continent).toString() + "\n";
			}

			toReturn += "---------------------------------";

		}

		// if map IS empty we send this message
		else {
			toReturn += "---------------------------------\n";
			toReturn += "There is nothing on the map\n";
			toReturn += "---------------------------------";

		}

		return toReturn;
	}

	/**
	 * Prints all airports
	 * 
	 * @return String of all airports
	 */
	public String printAllAirports() {

		// String variable to be returned later
		String toReturn = "";

		// enhanced for loop to iterate through continents HashMap and add airports with
		// their corresponding continents to the String that will be returned
		toReturn += "---------------------------------\n";
		for (String pointer : continents.keySet()) {
			String continent = pointer;
			List airports = continents.get(continent);

			toReturn += "Continent: " + continent + "\n";
			toReturn += "Airport: " + airports + "\n";
		}
		toReturn += "---------------------------------";

		return toReturn;
	}

	/**
	 * Just contains some tests for me to make sure that my functionality is working
	 * as it should be
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// DataRead object that will get the data from the csv files we created and
		// store them into the tester variable
		DataReader tester = new DataReader();
		tester.storeAirportInfo();
		tester.storeFlightPathInfo();

		// checks the lambda and streams method where we print all the airports on a
		// continent
		System.out.println(tester.mapByTime.airportsOnContinent("South America"));

		// A FinalBackEnd variable to make sure that the methods work with some pseudo
		// data
		AirportBackEnd tester2 = new AirportBackEnd();

		// Random airport insertions to make sure that some methods work
		tester2.insertAirport("Midway Airport", "Chicago", "North America");
		tester2.insertAirport("A Airport", "Melbourne", "Australia");
		tester2.insertAirport("B Airport", "Japan", "Asia");
		tester2.insertAirport("C Airport", "Tanzania", "Africa");
		tester2.insertAirport("D Airport", "Peru", "South America");
		tester2.insertAirport("E Airport", "Toronto", "North America");
		tester2.insertAirport("F Airport", "Russia", "Asia");

		// Print statements to check these tests
		System.out.println(tester2.printAtLocation("Melbourne"));
		System.out.println(tester2.printLocationFromAirport("C Airport"));

		// Random path insertions to make sure other methods work
		tester2.insertPath("A Airport", "F Airport", 5);
		tester2.insertPath("Midway Airport", "C Airport", 12);
		tester2.insertPath("B Airport", "D Airport", 3);

		// Print statements to check these tests
		System.out.println(tester2.cheapestFlight("A Airport", "F Airport"));
		System.out.println(tester2.cheapestFlight("Midway Airport", "C Airport"));
		System.out.println(tester2.cheapestFlight("B Airport", "D Airport"));
		// System.out.println(tester2.withinBudget("Melbourne", 10));

		// System.out.print(tester.mapByCost.withinBudget("Paris-France", 1000));

	}

}
