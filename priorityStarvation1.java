import java.util.*;

class Process2 {
    int id;
    int arrivalTime;
    int burstTime;
    int priority;
    int tempPriority;
    int startTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean completed;

    public Process2(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.tempPriority = priority;
        this.startTime = 0;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.completed = false;
    }
}

public class priorityStarvation1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        List<Process2> processes = new ArrayList<>();

        System.out.println("Enter Arrival, Burst, Priority");
        for (int i = 0; i < n; i++) {
            System.out.println("For process " + (i + 1) + ":");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();

            Process2 process = new Process2(i + 1, arrivalTime, burstTime, priority);
            processes.add(process);
        }

        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt((Process2 p) -> p.arrivalTime));

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            Process2 selectedProcess = null;
            int highestPriority = Integer.MAX_VALUE;

            for (Process2 process : processes) {
                if (process.arrivalTime <= currentTime && process.priority < highestPriority && !process.completed) {
                    highestPriority = process.priority;
                    selectedProcess = process;
                }
            }

            if (selectedProcess == null) {
                currentTime++;
                continue;
            }

            selectedProcess.startTime = currentTime;
            selectedProcess.completionTime = selectedProcess.startTime + selectedProcess.burstTime;
            currentTime += selectedProcess.burstTime;
            selectedProcess.turnaroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;

            selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.burstTime;
            selectedProcess.completed = true;
            completedProcesses++;

            // Aging: Increase the priority of waiting processes
            for (Process2 process : processes) {
                if (process.arrivalTime <= currentTime && process != selectedProcess) {
                    process.priority--;
                }
            }
        }

        float averageWaitingTime = 0f, averageTurnaroundTime = 0f;
        int totalBurstTime = 0;
        System.out.println("\nProcess\tArrival Time\tBurst Time\t\tPriority\tCompletion Time\tWaiting Time\tTurnaround Time");

        for (Process2 process : processes) {
            averageTurnaroundTime += process.turnaroundTime;
            averageWaitingTime += process.waitingTime;
            totalBurstTime += process.burstTime;

            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" +
                    process.tempPriority + "\t\t\t" + process.completionTime + "\t\t" + process.waitingTime +
                    "\t\t" + process.turnaroundTime);
        }

        float throughput = (float) n / totalBurstTime;
        System.out.println("Throughput: " + throughput);

        System.out.println("Average Waiting Time: " + averageWaitingTime / n);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime / n);

        scanner.close();
    }
}
