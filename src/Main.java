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
        System.out.println("Enter the number of computers to be simulated: ");
        int numComputers = scan.nextInt();
        System.out.println("Enter the probability of infecting another computer: ");
        double infectProbability = scan.nextDouble();
        System.out.println("Enter the number of computers to be repaired each day: ");
        int numRepairs = scan.nextInt();

        computer[] network = new computer[numComputers];
        for(int y = 0; y < numComputers; y++) {
            network[y] = new computer();
        }
        int numInfected = 1;
        network[0].infected = true;
        ArrayList<Integer> infectedComputers = new ArrayList<>();
        infectedComputers.add(0);
        int fixedToday;
        int day = 0;
        for(int i = 0; i < n; i++) {
            System.out.println("\n---------- SIMULATION " + i + " ----------");
            while(numInfected != 0) {
                for (int k = 0; k < numInfected; k++) {
                    for (int l = 0; l < 20; l++) {
                        if (!network[l].infected) {
                            if (Math.random() <= infectProbability) {
                                network[l].infected = true;
                                infectedComputers.add(l);
                                numInfected++;
                            }
                        }
                    }
                }
                fixedToday = 0;
                while (numInfected > 0 && fixedToday < numRepairs) {
                    int checkID = (int) (Math.random() * infectedComputers.size());
                    network[infectedComputers.get(checkID)].infected = false;
                    infectedComputers.remove(checkID);
                    numInfected--;
                    fixedToday++;
                }
                System.out.println("Day: "+day+"    Infected: "+numInfected+" Repaired: "+fixedToday);
                System.out.println("Infected Computers: "+infectedComputers);
                day++;
            }
            System.out.println("----- SIMULATION "+i+" RESULTS -----");
            System.out.println("Days: " + day);
        }
    }
}
