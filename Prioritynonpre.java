import java.util.Scanner;

public class Prioritynonpre {
    public static void main(String[] args) {
        int[] burstTime = new int[20];
        int[] arrivalTime = new int[10];
        int[] priority = new int[10];
        int[] startTime = new int[10];
        int[] completionTime = new int[10];
        int[] waitingTime = new int[10];
        int[] turnaroundTime = new int[10];
        int totalWaitingTime = 0, totalTurnaroundTime = 0;
        float averageWaitingTime, averageTurnaroundTime, throughput;
        String[] processName = new String[10];
        String temp;
        int tempValue;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter process name, arrival time, burst time, and priority for process " + (i + 1) + ": ");
            processName[i] = scanner.next();
            arrivalTime[i] = scanner.nextInt();
            burstTime[i] = scanner.nextInt();
            priority[i] = scanner.nextInt();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (priority[i] < priority[j]) {
                    tempValue = priority[i];
                    priority[i] = priority[j];
                    priority[j] = tempValue;

                    tempValue = arrivalTime[i];
                    arrivalTime[i] = arrivalTime[j];
                    arrivalTime[j] = tempValue;

                    tempValue = burstTime[i];
                    burstTime[i] = burstTime[j];
                    burstTime[j] = tempValue;

                    temp = processName[i];
                    processName[i] = processName[j];
                    processName[j] = temp;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (i == 0) {
                startTime[i] = arrivalTime[i];
            } else {
                startTime[i] = Math.max(arrivalTime[i], completionTime[i - 1]);
            }
            completionTime[i] = startTime[i] + burstTime[i];
            waitingTime[i] = startTime[i] - arrivalTime[i];
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];

            totalWaitingTime += waitingTime[i];
            totalTurnaroundTime += turnaroundTime[i];
        }

        averageWaitingTime = (float) totalWaitingTime / n;
        averageTurnaroundTime = (float) totalTurnaroundTime / n;
        throughput = 1 / averageTurnaroundTime;

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
