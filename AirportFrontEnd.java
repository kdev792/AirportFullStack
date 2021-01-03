import java.util.NoSuchElementException;
import java.util.Scanner;

public class AirportFrontEnd {

	private static final String WELCOME = "******Welcome to Worldwide Airplane Map. Please choose one of "
			+ "the commands associated with each option from the menu to continue!******\n";

	private static final String MENU = "MENU:\n" + "M: Print menu again.\n" +

			"L: Check if your desired location/airport is listed in our system.\n" +

			"R: Check if your desired route is listed in our system.\n" +

			"T: Get time cost between departure and destination\n" +

			"C: Get number of airlines in our system.\n" +

			"S: Get total number of Airports in our system.\n" +

			"H: Find lowest time cost between your desired loactions.\n" +

			"I: Find lowest time cost path/route between your desired locations.\n" +

			"O: Show lowest currency cost between your desired locations.\n" +

			"B: Show lowest currency cost path/route between your desired locations.\n" +

			"E: Print list of all Airports in our WorlWide Map.\n" +

			"D: List of airports in a specific continent.\n" +

			"A: Find the airport in your location.\n" +

			"F: Get the location of your airport.\n" +

			"Q: Quit from Worldwide Airplane App\n";

	private static final String THANKYOUMESSAGE = "*****Thank you for using the Worldwide Airplane Map. Have a wonderful day ahead*****";

	private static void frontEndApplication() {
		// get data from data wrangler and store over here
		DataReader infoToStore = new DataReader();
		infoToStore.storeAirportInfo();
		infoToStore.storeFlightPathInfo();
		AirportBackEnd mapByTime = infoToStore.mapByTime;
		AirportBackEnd mapByCost = infoToStore.mapByCost;
		// input
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your commad: ");
		String command = input.nextLine();
		command = command.trim().toLowerCase();
		while (!command.equals("q")) {
			// if command is M - printing M again
			if (command.equals("m")) {
				System.out.println(MENU);
			}

			// L - List contains desired airport
			if (command.equals("l")) {
				System.out.println("Please enter an airport you want to look up: ");
				String airportName = input.nextLine();
				try {
					mapByTime.containsAirport(airportName);
					System.out.println("Yes your desired airport is in our list!");
				} catch (NullPointerException e) {
					System.out.println("We could not find your desired airport!");
				}
				/**
				 * if(mapByTime.containsAirport(airportName) &&
				 * mapByCost.containsAirport(airportName)) { System.out.println("Yes your
				 * desired airport is in our list!"); }
				 **/

			}

			// R - List contains desired route
			if (command.equals("r")) {
				System.out.println("Enter origin Airport: ");
				String depAirport = input.nextLine();
				System.out.println("Enter destination Airprt: ");
				String desAirport = input.nextLine();
				try {
					if (mapByTime.containsPath(depAirport, desAirport)
							&& mapByCost.containsPath(depAirport, desAirport)) {
						System.out.println("Direct airline exists between your desired depature and destination!");
					} else {
						System.out.println(
								"Direct airline does not exist exists between your desired depature and destination!");
					}
				} catch (NullPointerException e) {
					System.out.println("Cannot find root between locations that do not exist!");
				}
			}

			// T - Time cost
			if (command.equals("t")) {
				System.out.println("Please enter a origin location: " + "");
				String origin = input.nextLine();
				System.out.println("Please enter a destination location: ");
				String destination = input.nextLine();

				int time;
				try {
					time = mapByTime.getWeight(origin, destination);
					System.out.println("Time cost of your desired route is: " + time + " mins");

				} catch (NoSuchElementException e) {
					System.out.println("No direct route available between " + origin + " and " + destination);
				} catch (IllegalArgumentException e) {
					System.out.println("Origin or Destination does not exist in our map!");
				}

			}

			// C - Airline Count
			if (command.equals("c")) {
				System.out.println("Total number of airlines in out Worldwide Map is " + mapByTime.getNumberOfPaths());
			}

			// S - Airport Count
			if (command.equals("s")) {
				System.out
						.println("Total number of Airports in our Worlwide Map is " + mapByTime.getNumberOfAirports());
			}

			// H - cheapest time cost
			if (command.equals("h")) {
				try {
					System.out.println("Please enter your desired departure airport: ");
					String origin = input.nextLine();
					System.out.println("Please enter your desired destination airport: ");
					String destination = input.nextLine();
					System.out.println("Cheapest time cost between desired locations is "
							+ mapByTime.cheapestFlight(origin, destination));
				} catch (NullPointerException e) {
					System.out.println("Sorry Airports entered do not exist in our map!");
				}
			}

			// I - cheapest time cost path
			if (command.equals("i")) {
				try {
					System.out.println("Please enter your desired departure airport: ");
					String origin = input.nextLine();
					System.out.println("Please enter your desired destination airport: ");
					String destination = input.nextLine();
					System.out.println("Cheapest time cost path between desired locations is "
							+ mapByTime.cheapestFlight(origin, destination));
				} catch (NullPointerException e) {
					System.out.println("Sorry Airports entered do not exist in our map!");
				}

			}

			// O - Cheapest currency cost
			if (command.equals("o")) {
				try {
					System.out.println("Please enter your desired departure airport: ");
					String origin = input.nextLine();
					System.out.println("Please enter your desired destination airport: ");
					String destination = input.nextLine();
					System.out.println("Cheapest currency cost path between desired locations is "
							+ mapByCost.cheapestFlight(origin, destination));
				} catch (NullPointerException e) {
					System.out.println("Sorry Airports entered do not exist in our map!");
				}
			}

			// B - Cheapest currency cost path
			if (command.equals("b")) {
				try {
					System.out.println("Please enter your desired departure airport: ");
					String origin = input.nextLine();
					System.out.println("Please enter your desired destination airport: ");
					String destination = input.nextLine();
					System.out.println("Cheapest currency cost path between desired locations is "
							+ mapByCost.cheapestFlight(origin, destination));
				} catch (NullPointerException e) {
					System.out.println("Sorry Airports entered do not exist in our map!");
				}
			}

			// D - Airports in a specific continent.
			if (command.equals("d")) {
				System.out.println("Enter a continent: ");
				String continent = input.nextLine();
				// System.out.println("List of continents in " + continent + " are: " +
				// mapByTime.listOfContinent(continent) );
				try {
					System.out.println(mapByCost.airportsOnContinent(continent));
				} catch (NoSuchElementException e) {
					System.out.println("Continent does not exist!");
				}
			}

			// E - Print list of all restaurants
			if (command.equals("e")) {
				System.out.println(mapByTime.printAllAirports());
			}

			// A - Airport in specified location
			if (command.equals("a")) {
				System.out.println("Enter your location: ");
				String location = input.nextLine();
				try {
					System.out.println("Airport in " + location + " is " + mapByTime.printAtLocation(location));
				} catch (IllegalArgumentException e) {
					System.out.println("Location does not exist in our map!");
				}
			}

			// F - Location of specified airport
			if (command.equals("f")) {
				System.out.println("Enter your airport: ");
				String airport = input.nextLine();
				try {
					System.out.println("Location of " + airport + " is " + mapByCost.printLocationFromAirport(airport));
				} catch (IllegalArgumentException e) {
					System.out.println("Location does not exist in our map!");
				}
			}

			// at the end ask for input again
			System.out.println("~~~~~~~~~~~~~~~~~~~");
			System.out.println("Enter another command, or enter \"Q\" to quit: ");
			command = input.nextLine().trim().toLowerCase();
		}

	}

	public static void main(String[] args) {
		System.out.println(WELCOME);
		System.out.println(MENU);
		frontEndApplication();
		System.out.println();
		System.out.println(THANKYOUMESSAGE);
	}

}