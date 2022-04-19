import java.util.ArrayList;
import java.util.Scanner;

class computer {
    boolean infected;
    computer(){
        infected = false;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of times n this simulation should run: ");
        int n = scan.nextInt();
        System.out.println("Enter the number of computers in the network: ");
        int numComputers = scan.nextInt();
        System.out.println("Enter the probability of infecting another computer: ");
        double infectProbability = scan.nextDouble();
        System.out.println("Enter the number of computers to be repaired each day: ");
        int numRepairs = scan.nextInt();

        // Create an array of computer objects
        computer[] network = new computer[numComputers];
        for(int y = 0; y < numComputers; y++) {
            network[y] = new computer();
        }

        // Create an arrayList to keep track of the infected computers each day
        ArrayList<Integer> infectedComputers = new ArrayList<>();
        int day, days=0, numInfected, numInfectedToday, fixedToday;

        // Outermost loop runs n times (each iteration is one simulation)
        for(int i = 0; i < n; i++) {
            day = 0;
            numInfected = 1;
            network[0].infected = true;
            infectedComputers.add(0);
            //System.out.println("\n---------- SIMULATION " + i + " ----------");
            // While loop runs until there are no more infected computers (each iteration is one day of the simulation)
            while(numInfected != 0) {
                numInfectedToday = 0;
                // For each infected computer
                for (int k = 0; k < numInfected; k++) {
                    // Go through each computer
                    for (int l = 0; l < numComputers; l++) {
                        // If that computer isn't already infected, randomly decide if the computer gets infected with
                        // probability: infectProbability
                        if (!network[l].infected) {
                            if (Math.random() <= infectProbability) {
                                network[l].infected = true;
                                infectedComputers.add(l);
                                numInfectedToday++;
                            }
                        }
                    }
                }
                // Update the number of infected computers
                numInfected += numInfectedToday;
                fixedToday = 0;
                // Repairs random computers until there are none left or maximum number of repairs per day reached.
                while (numInfected > 0 && fixedToday < numRepairs) {
                    int checkID = (int) (Math.random() * infectedComputers.size());
                    network[infectedComputers.get(checkID)].infected = false;
                    infectedComputers.remove(checkID);
                    numInfected--;
                    fixedToday++;
                }
                //System.out.println("Day: "+day+"    Infected: "+numInfected+" Repaired: "+fixedToday);
                //System.out.println("Infected Computers: "+infectedComputers);
                day++;
                days++;
            }
            //System.out.println("----- SIMULATION "+i+" RESULTS -----");
            //System.out.println("Days: " + day);
        }
        System.out.println("\nAverage days until virus removed: " + days/n);
    }
}
