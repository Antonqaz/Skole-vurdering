
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.*;
import java.util.List;

public class main {
    static List<School> schools;  //List of schools in area
    static List<TrafficCounter> counters; //List of traffic counters in area
    static Scanner input = new Scanner(System.in); //user input


    static boolean run = true;

    public static void main(String[] args) {
        getSchoolData();
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
                getCounterData();
                for (Pair pair : trafficScores(schools, counters)){
                    System.out.println(pair.getA() + "  " + pair.getB() + "\r\n");
                }
                break;

            case "update":
                getSchoolData();
                System.out.println("Data updated");
                break;

            case "exit": //Exit program
                run = false;
                break;

            default:
                System.out.println("Command not recognized");
        }
    }

    public static void getSchoolData(){
        schools = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("SkoleData.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                values[15] = values[15].replaceAll("[^\\.0123456789 ]", "");
                String co[] = values[15].split(" ");

                schools.add(new School(new Point2D.Double(Double.parseDouble(co[1]), Double.parseDouble(co[2])), values[7]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getCounterData(){
        counters = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("trafiktaelling.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                if (values.length < 35){
                    continue;
                }

                if (values[10] == ""){
                    continue;
                }

                if (values[34] == ""){
                    continue;
                }

                try {
                    Integer test = Integer.parseInt(values[10]);
                    if (test == 0){
                        continue;
                    }
                } catch (NumberFormatException e){
                    continue;
                }

                values[34] = values[34].replaceAll("[^\\.0123456789 ]", "");
                String co[] = values[34].split(" ");

                counters.add(new TrafficCounter(new Point2D.Double(
                        Double.parseDouble(co[1]), Double.parseDouble(co[2])),
                        Integer.parseInt(values[10])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Get the traffic of each school
    public static List<Pair<String, Double>> trafficScores(List<School> schools, List<TrafficCounter> counters){
        List<Pair<String, Double>> result = new ArrayList<>();
        for (School school : schools){
            result.add(new Pair(school.getName(), schoolScore(school, counters)));
        }
        Collections.sort(result, Comparator.comparing(p -> p.getB()));  //sort list
        return result;
    }

    //Calculate traffic score for a single school
    public static double schoolScore(School school, List<TrafficCounter> counters){
        double score = 0;
        double distance = 0;
        for (TrafficCounter counter : counters) {
            if (haversine(school.getCoordinate(), counter.getCoordinate()) < distance || distance == 0) {
                score = singleScore(school, counter);
                distance = haversine(school.getCoordinate(), counter.getCoordinate());
            }
        }
        return score;
    }

    //Calculate the traffic score for one traffic counter on a school
    public static double singleScore(School school, TrafficCounter counter){
        /*double distance = haversine(school.getCoordinate(), counter.getCoordinate());
        if (distance > 20){  //Return a score of 0 if distance is too great
            return 0;
        }*/
        return counter.getCount();
    }

    //formula for finding distance between two coordinates
    public static double haversine(Point2D first, Point2D second){
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
