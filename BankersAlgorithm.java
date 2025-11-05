import java.util.*;

public class BankersAlgorithm {
    // Available resources vector - represents currently available resources of each type
    private int[] available;
    // Maximum demand matrix - maximum resources each process may request
    private int[][] max;
    // Allocation matrix - resources currently allocated to each process
    private int[][] allocation;
    // Need matrix - remaining resources needed by each process (max - allocation)
    private int[][] need;
    // Number of processes in the system
    private int numProcesses;
    // Number of resource types in the system
    private int numResources;

    // Constructor to initialize the Banker's Algorithm with available resources and maximum demands
    public BankersAlgorithm(int[] available, int[][] max) {
        this.available = available.clone();
        this.max = max;
        this.numProcesses = max.length;
        this.numResources = available.length;
        this.allocation = new int[numProcesses][numResources];
        this.need = new int[numProcesses][numResources];
        
        // Calculate initial need matrix (need = max - allocation)
        for (int i = 0; i < numProcesses; i++) {
            need[i] = max[i].clone(); // since allocation starts as zeros
        }
    }

    // Method to handle resource request from a specific process
    public boolean requestResources(int process, int[] request) {
        // Step 1: Check if request exceeds the process's declared maximum need
        for (int i = 0; i < numResources; i++) {
            if (request[i] > need[process][i]) {
                System.out.println("Error: Process has exceeded its maximum claim.");
                return false;
            }
        }
        
        // Step 2: Check if request exceeds currently available resources
        for (int i = 0; i < numResources; i++) {
            if (request[i] > available[i]) {
                System.out.println("Resources not available. Process must wait.");
                return false;
            }
        }
        
        // Step 3: Tentatively allocate
        for (int i = 0; i < numResources; i++) {
            available[i] -= request[i];
            allocation[process][i] += request[i];
            need[process][i] -= request[i];
        }
        
        // Step 4: Check for safe state
        List<Integer> safeSequence = getSafeSequence();
        if (safeSequence == null) {
            // Rollback
            for (int i = 0; i < numResources; i++) {
                available[i] += request[i];
                allocation[process][i] -= request[i];
                need[process][i] += request[i];
            }
            System.out.println("Request denied. System would enter unsafe state.");
            return false;
        }

        System.out.println("Request granted. System remains in safe state.");
        System.out.println("Safe sequence: " + safeSequence);
        return true;
    }

    // Method to check if the system is in a safe state and return the safe sequence if possible
    private List<Integer> getSafeSequence() {
        boolean[] finish = new boolean[numProcesses];
        int[] work = available.clone();
        List<Integer> safeSequence = new ArrayList<>();

        int count = 0;
        while (count < numProcesses) {
            boolean found = false;
            for (int p = 0; p < numProcesses; p++) {
                if (!finish[p]) {
                    boolean canProceed = true;
                    for (int r = 0; r < numResources; r++) {
                        if (need[p][r] > work[r]) {
                            canProceed = false;
                            break;
                        }
                    }
                    if (canProceed) {
                        for (int r = 0; r < numResources; r++) {
                            work[r] += allocation[p][r];
                        }
                        finish[p] = true;
                        safeSequence.add(p);
                        found = true;
                        count++;
                    }
                }
            }
            if (!found) return null; // unsafe state
        }
        return safeSequence; // safe sequence found
    }

    // For testing
    public static void main(String[] args) {
        int[] available = {3, 3, 2};
        int[][] max = {
            {7, 5, 3},
            {3, 2, 2},
            {9, 0, 2},
            {2, 2, 2},
            {4, 3, 3}
        };
        BankersAlgorithm ba = new BankersAlgorithm(available, max);

        // Example requests
        System.out.println("\n--- Request 1 ---");
        ba.requestResources(1, new int[]{1, 0, 2}); // should be granted if safe

        System.out.println("\n--- Request 2 ---");
        ba.requestResources(4, new int[]{3, 3, 0}); // might be denied if unsafe
    }
}
