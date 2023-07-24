import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Process {
    public static final int BT = 0;
    public static int CT;
    public final int AT = 0;
    String name;
    int arrivalTime;
    int burstTime;
    int age = 0;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class SJFStarvation {
    static ArrayList<Process> processes = new ArrayList<>();
    static ArrayList<Process> temp = new ArrayList<>();
    static int currentTime = 0;
    static int totalWaitingTime = 0;

    public static void calculate(String name, int at, int bt) {
        int st = currentTime;
        int ct = st + bt;
        currentTime = currentTime + bt;
        int tat = ct - at;
        int wt = ct - bt - at; // Fix: Calculate waiting time correctly
        totalWaitingTime += wt; // Update total waiting time
        System.out.println(name + "\t\t" + at + "\t\t" + bt + "\t\t" + st + "\t\t\t" + ct + "\t\t\t" + tat + "\t\t\t" + wt);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Boolean flag = false;

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter process Arrival & Burst time :\n");
        for (int i = 0; i < n; i++) {
            System.out.println("For Process " + (i + 1) + ":");
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();

            processes.add(new Process("P" + (i + 1), arrivalTime, burstTime));
        }

        System.out.println("PID\t\t" + "Arrival\t\t" + "Burst\t\t" + "Starting\t\t" + "Completion\t\t" + "TurnAround\t\t" + "Waiting");
        // calculate 1st process
        calculate(processes.get(0).name, processes.get(0).arrivalTime, processes.get(0).burstTime);
        processes.remove(0);

        while (!processes.isEmpty() || !temp.isEmpty()) {
            // add processes which have arrived into the temporary list
            if (!processes.isEmpty()) {
                for (int i = 0; i < processes.size(); i++) {
                    if (processes.get(i).arrivalTime <= currentTime) {
                        temp.add(processes.get(i));
                        processes.remove(processes.get(i));
                    }
                }
            }

            Collections.sort(temp, (p1, p2) -> {
                if (p1.burstTime < p2.burstTime)
                    return -1;
                else if (p1.burstTime > p2.burstTime)
                    return 1;
                else
                    return 0;
            });

            if (!temp.isEmpty()) {
                if (temp.get(0).age == 0) {
                    flag = true;
                    calculate(temp.get(0).name, temp.get(0).arrivalTime, temp.get(0).burstTime);
                    temp.remove(0);
                } else {
                    flag = false;
                }
            }

            if (!flag) {
                calculate(temp.get(0).name, temp.get(0).arrivalTime, temp.get(0).burstTime);
                for (Process p : temp) {
                    p.age -= 1;
                }
                temp.remove(0);
                flag = false;
            }
        }

        double throughput = (double) (currentTime) / n;
        double averageWaitingTime = (double) totalWaitingTime / n;
        System.out.println("Throughput: " + throughput);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
}
