import java.util.ArrayList;
import java.util.Scanner;

class computer {
    // In order to determine which computers were infected after a simulation, use 2 booleans to track the current
    // infection status and the past infection status
    boolean isInfected;
    boolean wasInfectedOnce;

    // Constructor. Default state uninfected
    computer(){
        isInfected = false;
        wasInfectedOnce = false;
    }

    // Marks a computer as infected
    public void infect() {
        isInfected = true;
        wasInfectedOnce = true;
    }

    // Marks a computer as uninfected (DOES NOT overwrite previous infection status)
    public void repair() {
        isInfected = false;
    }

    // Resets a computer to uninfected (DOES overwrite previous infection status)
    public void reset() {
        isInfected = false;
        wasInfectedOnce = false;
    }
}

public class Main {
    public static void main(String[] args) {
        // Get simulation constants from user
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
        int numTotalDays=0, numInfected, numInfectedToday, numFixedToday, numUniqueInfected, numTotalInfected = 0,
                totalUniqueInfected = 0;

        // Outermost loop runs n times (each iteration is one simulation)
        for(int i = 0; i < n; i++) {
            // At the start of each simulation, reset counters and computer network to have 1 infected
            numInfected = 1;
            numUniqueInfected = 0;
            for(computer comp : network) {
                comp.reset();
            }
            network[0].infect();
            infectedComputers.add(0);
            // While loop runs until there are no more infected computers (each iteration is one day of the simulation)
            while(numInfected != 0) {
                numInfectedToday = 0;
                // For each infected computer
                for (int k = 0; k < numInfected; k++) {
                    // Go through each computer
                    for (int l = 0; l < numComputers; l++) {
                        // If that computer isn't already infected, randomly decide if the computer gets infected with
                        // probability: infectProbability
                        if (!network[l].isInfected) {
                            if (Math.random() < infectProbability) {
                                network[l].infect();
                                infectedComputers.add(l);
                                numInfectedToday++;
                            }
                        }
                    }
                }
                // Update the number of infected computers
                numInfected += numInfectedToday;
                numFixedToday = 0;
                // Repair random computers until there are none left or maximum number of repairs per day reached.
                while (numInfected > 0 && numFixedToday < numRepairs) {
                    int checkID = (int) (Math.random() * infectedComputers.size());
                    network[infectedComputers.get(checkID)].repair();
                    infectedComputers.remove(checkID);
                    numInfected--;
                    numFixedToday++;
                }
                // Increase day counter at end of each day
                numTotalDays++;
            }
            // After each simulation check if every computer was infected once and add the unique infected to the total
            for (computer comp:network) {
                if(comp.wasInfectedOnce) {
                    numUniqueInfected++;
                }
            }
            if(numUniqueInfected == numComputers) {
                numTotalInfected++;
            }
            totalUniqueInfected += numUniqueInfected;
        }
        // Display results after every simulation has run
        System.out.println("\nAverage days until virus removed: " + (double)numTotalDays/n);
        System.out.println("Probability every computer gets infected once: " + (double)numTotalInfected/n);
        System.out.println("Expected number of infected computers: " + (double)totalUniqueInfected/n);
    }
}
