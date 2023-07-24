import java.util.Scanner;

public class SRTF {
    static class Process {
        int pid;
        int arrivalTime;
        int burstTime;
        int startTime;
        int completionTime;
        int turnaroundTime;
        int waitingTime;
        int responseTime;
    }

    public static void main(String[] args) {
        int x;
        Process[] p = new Process[100];
        float avgTurnaroundTime, avgWaitingTime, avgResponseTime, cpuUtilization, throughput;
        int totalTurnaroundTime = 0, totalWaitingTime = 0, totalResponseTime = 0, totalIdleTime = 0;
        int[] burstRemaining = new int[100];
        int[] isCompleted = new int[100];

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        x = scanner.nextInt();

        for (int i = 0; i < x; i++) {
            p[i] = new Process();
            System.out.print("Enter arrival time of process " + (i + 1) + ": ");
            p[i].arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time of process " + (i + 1) + ": ");
            p[i].burstTime = scanner.nextInt();
            p[i].pid = i + 1;
            burstRemaining[i] = p[i].burstTime;
            System.out.println();
        }

        int currentTime = 0;
        int completed = 0;
        int prev = 0;

        while (completed != x) {
            int idx = -1;
            int minBurst = 10000000;

            for (int i = 0; i < x; i++) {
                if (p[i].arrivalTime <= currentTime && isCompleted[i] == 0) {
                    if (burstRemaining[i] < minBurst) {
                        minBurst = burstRemaining[i];
                        idx = i;
                    }
                    if (burstRemaining[i] == minBurst) {
                        if (p[i].arrivalTime < p[idx].arrivalTime) {
                            minBurst = burstRemaining[i];
                            idx = i;
                        }
                    }
                }
            }

            if (idx != -1) {
                if (burstRemaining[idx] == p[idx].burstTime) {
                    p[idx].startTime = currentTime;
                    totalIdleTime += p[idx].startTime - prev;
                }
                burstRemaining[idx] -= 1;
                currentTime++;
                prev = currentTime;

                if (burstRemaining[idx] == 0) {
                    p[idx].completionTime = currentTime;
                    p[idx].turnaroundTime = p[idx].completionTime - p[idx].arrivalTime;
                    p[idx].waitingTime = p[idx].turnaroundTime - p[idx].burstTime;
                    p[idx].responseTime = p[idx].startTime - p[idx].arrivalTime;

                    totalTurnaroundTime += p[idx].turnaroundTime;
                    totalWaitingTime += p[idx].waitingTime;
                    totalResponseTime += p[idx].responseTime;

                    isCompleted[idx] = 1; 
                    completed++;
                }
            } else {
                currentTime++;
            }
        }

        int minArrivalTime = 10000000;
        int maxCompletionTime = -1;

        for (int i = 0; i < x; i++) {
            minArrivalTime = Math.min(minArrivalTime, p[i].arrivalTime);
            maxCompletionTime = Math.max(maxCompletionTime, p[i].completionTime);
        }

        avgTurnaroundTime = (float) totalTurnaroundTime / x;
        avgWaitingTime = (float) totalWaitingTime / x;
        avgResponseTime = (float) totalResponseTime / x;
        cpuUtilization = ((maxCompletionTime - totalIdleTime) / (float) maxCompletionTime) * 100;
        throughput = (float) x / (maxCompletionTime - minArrivalTime);

        System.out.println("\nProcess\tArrival Time\tBurst Time\tST\tCT\tTAT\tWT\tRT\n");

        for (int i = 0; i < x; i++) {
            System.out.println(p[i].pid + "\t\t" + p[i].arrivalTime + "\t\t" + p[i].burstTime + "\t" + p[i].startTime + "\t" + p[i].completionTime + "\t" + p[i].turnaroundTime + "\t" + p[i].waitingTime + "\t" + p[i].responseTime);
        }

        System.out.println("Average Turnaround Time = " + avgTurnaroundTime);
        System.out.println("Average Waiting Time = " + avgWaitingTime);
        System.out.println("Average Response Time = " + avgResponseTime);
        System.out.println("CPU Utilization = " + cpuUtilization + "%");
        System.out.println("Throughput = " + throughput + " process/unit time");
    }
}
