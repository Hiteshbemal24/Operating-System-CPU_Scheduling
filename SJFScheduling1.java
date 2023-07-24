import java.util.Scanner;

public class SJFScheduling1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        int[] burst_time = new int[n];
        int[] arrival_time = new int[n];
        int[] waiting_time = new int[n];
        int[] turnaround_time = new int[n];
        int[] completion_time = new int[n];
        int[] process_id = new int[n];

        for (int i = 0; i < n; i++) {
            process_id[i] = i + 1;
            arrival_time[i] = 0; // Setting arrival time to 0
            System.out.print("Enter the burst time for process " + (i + 1) + ": ");
            burst_time[i] = sc.nextInt();
        }

        // Sorting processes based on burst time (SJF)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (burst_time[j] > burst_time[j + 1]) {
                    // Swapping arrival time
                    int temp = arrival_time[j];
                    arrival_time[j] = arrival_time[j + 1];
                    arrival_time[j + 1] = temp;

                    // Swapping burst time
                    temp = burst_time[j];
                    burst_time[j] = burst_time[j + 1];
                    burst_time[j + 1] = temp;

                    // Swapping process id
                    temp = process_id[j];
                    process_id[j] = process_id[j + 1];
                    process_id[j + 1] = temp;
                }
            }
        }

        // Calculating completion time for each process
        completion_time[0] = burst_time[0];
        for (int i = 1; i < n; i++) {
            completion_time[i] = completion_time[i - 1] + burst_time[i];
        }

        // Calculating turnaround time and waiting time for each process
        for (int i = 0; i < n; i++) {
            turnaround_time[i] = completion_time[i];
            waiting_time[i] = turnaround_time[i] - burst_time[i];
        }

        // Printing the results
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tCompletion Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            System.out.println(process_id[i] + "\t\t" + burst_time[i] + "\t\t" + waiting_time[i] + "\t\t" + completion_time[i] + "\t\t" + turnaround_time[i]);
        }

        // Calculating average turnaround time and average waiting time
        double avg_turnaround_time = 0;
        double avg_waiting_time = 0;
        for (int i = 0; i < n; i++) {
            avg_turnaround_time += turnaround_time[i];
            avg_waiting_time += waiting_time[i];
        }
        avg_turnaround_time /= n;
        avg_waiting_time /= n;

        // Calculating throughput
        double throughput = n / (double) completion_time[n - 1];

        System.out.println("\nAverage Turnaround Time: " + avg_turnaround_time);
        System.out.println("Average Waiting Time: " + avg_waiting_time);
        System.out.println("Throughput: " + throughput);

        sc.close();
    }
}
