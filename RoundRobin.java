import java.util.Scanner;

public class RoundRobin {
    public static void main(String[] args) {
        int count, j, n, time, remain, flag = 0, timeQuantum;
        int waitTime = 0, turnaroundTime = 0;
        int[] arrivalTime = new int[10];
        int[] burstTime = new int[10];
        int[] remainingTime = new int[10];
        // int[] startTime = new int[10];
        int[] completionTime = new int[10];
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Total Process: ");
        n = scanner.nextInt();
        remain = n;

        for (count = 0; count < n; count++) {
            System.out.print("Enter Arrival Time and Burst Time for Process " + (count + 1) + ": ");
            arrivalTime[count] = scanner.nextInt();
            burstTime[count] = scanner.nextInt();
            remainingTime[count] = burstTime[count];
        }

        System.out.print("Enter Time Quantum: ");
        timeQuantum = scanner.nextInt();

        System.out.println("\nProcess\t Arrival Time\t Burst Time \t Completion Time\t Turnaround Time\t Waiting Time\n");

        for (time = 0, count = 0; remain != 0; ) {
            if (remainingTime[count] <= timeQuantum && remainingTime[count] > 0) {
                time += remainingTime[count];
                remainingTime[count] = 0;
                flag = 1;
            } else if (remainingTime[count] > 0) {
                remainingTime[count] -= timeQuantum;
                time += timeQuantum;
            }

            if (remainingTime[count] == 0 && flag == 1) {
                remain--;
                completionTime[count] = time;
                // startTime[count] = completionTime[count] - burstTime[count];
                System.out.println( + (count + 1) + "\t\t" + arrivalTime[count] + "\t\t " + burstTime[count] + "\t\t " + completionTime[count] + "\t\t " + (completionTime[count] - arrivalTime[count]) + "\t\t\t" + (completionTime[count] - arrivalTime[count] - burstTime[count]));
                waitTime += completionTime[count] - arrivalTime[count] - burstTime[count];
                turnaroundTime += completionTime[count] - arrivalTime[count];
                flag = 0;
            }

            if (count == n - 1) {
                count = 0;
            } else if (arrivalTime[count + 1] <= time) {
                count++;
            } else {
                count = 0;
            }
        }

        float averageWaitingTime = (float) waitTime / n;
        float averageTurnaroundTime = (float) turnaroundTime / n;

        System.out.println("\nAverage Waiting Time = " + averageWaitingTime);
        System.out.println("Average Turnaround Time = " + averageTurnaroundTime);
    }
}
