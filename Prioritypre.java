import java.util.Scanner;

public class Prioritypre {
    public static void main(String[] args) {
        int[] burstTime = new int[20];
        int[] arrivalTime = new int[10];
        int[] priority = new int[10];
        int[] startTime = new int[10];
        int[] completionTime = new int[10];
        int[] remainingTime = new int[10];
        int[] waitingTime = new int[10];
        int[] turnaroundTime = new int[10];
        int totalWaitingTime = 0, totalTurnaroundTime = 0;
        float averageWaitingTime, averageTurnaroundTime, throughput;
        String[] processName = new String[10];
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter process name, arrival time, burst time, and priority for process " + (i + 1) + ": ");
            processName[i] = scanner.next();
            arrivalTime[i] = scanner.nextInt();
            burstTime[i] = scanner.nextInt();
            priority[i] = scanner.nextInt();
            remainingTime[i] = burstTime[i];
        }

        int currentTime = 0;
        boolean allProcessesComplete = false;
        int completedProcesses = 0;

        while (!allProcessesComplete) {
            int highestPriorityIndex = -1;
            int highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && remainingTime[i] > 0 && priority[i] < highestPriority) {
                    highestPriority = priority[i];
                    highestPriorityIndex = i;
                }
            }

            if (highestPriorityIndex == -1) {
                currentTime++;
                continue;
            }

            if (remainingTime[highestPriorityIndex] == burstTime[highestPriorityIndex]) {
                startTime[highestPriorityIndex] = currentTime;
            }

            remainingTime[highestPriorityIndex]--;
            currentTime++;

            if (remainingTime[highestPriorityIndex] == 0) {
                completionTime[highestPriorityIndex] = currentTime;
                waitingTime[highestPriorityIndex] = completionTime[highestPriorityIndex] - arrivalTime[highestPriorityIndex] - burstTime[highestPriorityIndex];
                turnaroundTime[highestPriorityIndex] = completionTime[highestPriorityIndex] - arrivalTime[highestPriorityIndex];

                totalWaitingTime += waitingTime[highestPriorityIndex];
                totalTurnaroundTime += turnaroundTime[highestPriorityIndex];

                completedProcesses++;
            }

            // Check if all processes have completed
            if (completedProcesses == n) {
                allProcessesComplete = true;
            }
        }

        averageWaitingTime = (float) totalWaitingTime / n;
        averageTurnaroundTime = (float) totalTurnaroundTime / n;
        throughput = (float) n / currentTime;

        System.out.println("\nPname\tArrival Time\tBurst Time\tPriority\tStart Time\tCompletion Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            System.out.println(processName[i] + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" + priority[i] + "\t\t" + startTime[i] + "\t\t" + completionTime[i] + "\t\t" + waitingTime[i] + "\t\t" + turnaroundTime[i]);
        }

        System.out.println("\nAverage waiting time is: " + averageWaitingTime);
        System.out.println("Average turnaround time is: " + averageTurnaroundTime);
        System.out.println("Throughput is: " + throughput);

        scanner.close();
    }
}
