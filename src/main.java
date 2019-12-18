//HUSK NOTER

import java.lang.Math;
import java.awt.*;
import java.util.*;
import java.util.List;

public class main {
    static School[] schools;  //List of schools in area
    static TrafficCounter[] counters; //List of traffic counters in area
    static Scanner input = new Scanner(System.in); //user input


    static boolean run = true;

    public static void main(String[] args) {
        while(run){ //run till exited
            command();
        }

    }
    public static void command() {
        //List commands
        System.out.println("Copenhagen schools available metrics:\r\n" +
                "traffic\n" +
                "exit");

        switch (input.nextLine()) {
            case "traffic": //Print every school and their respective traffic score

                System.out.println("Schools and their traffic score:");
                for (Pair pair : trafficScores(schools, counters)){
                    System.out.println(pair.getA() + "  " + pair.getB() + "\r\n");
                }
                break;

            case "exit": //Exit program
                run = false;
                break;

            default:
                System.out.println("Command not recognized");
        }
    }


    //Get the traffic of each school
    public static List<Pair<String, Double>> trafficScores(School[] schools, TrafficCounter[] counters){
        List<Pair<String, Double>> result = new ArrayList<>();
        for (School school : schools){
            result.add(new Pair(school.getName(), schoolScore(school, counters)));
        }
        Collections.sort(result, Comparator.comparing(p -> p.getB()));  //sort list
        return result;
    }

    //Calculate traffic score for a single school
    public static double schoolScore(School school, TrafficCounter[] counters){
        double score = 0;
        for (TrafficCounter counter : counters) {
            score += singleScore(school, counter);
        }
        return score;
    }

    //Calculate the traffic score for one traffic counter on a school
    public static double singleScore(School school, TrafficCounter counter){
        double distance = haversine(school.getCoordinate(), counter.getCoordinate());
        if (distance > 20){  //Return a score of 0 if distance is too great
            return 0;
        }
        return counter.getCount() *
                (Math.pow(0.5, distance)); // traffic score decreases as it gets farther away
    }

    //formula for finding distance between two coordinates
    public static double haversine(Point first, Point second){
        double lat_1 = Math.toRadians(first.getX());
        double lat_2 = Math.toRadians(second.getX());
        double delta_lat = Math.toRadians(second.getX() - first.getX());
        double delta_lon = Math.toRadians(second.getY() - first.getY());
        double R = 6371; //radius of the earth

        double a = Math.pow(Math.sin(delta_lat/2), 2) +
                Math.cos(lat_1) * Math.cos(lat_2) * Math.pow(Math.sin(delta_lon), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }
}
