import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Movie {
    private final String title;
    private String genre;
    private int year;
    private int durationInMinutes;
    private ArrayList<String> hours;
    private ArrayList<SavedSeats> reservedSeats;
    private JButton[][] seats = new JButton[10][5];
    private HashMap<String, JButton[][]> seatsMap;
    private Set<String> keys;


    public Movie(String title, String genre, int year, int durationInMinutes, ArrayList<String> hours) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.durationInMinutes = durationInMinutes;
        this.hours = hours;
        seatsMap = new HashMap<>();
    }

    public void setSeatsMap(String hour, JButton[][] seats) {
        seatsMap.put(hour, seats);
        keys = seatsMap.keySet();
    }

    public HashMap<String, JButton[][]> getSeatsMap() {
        return seatsMap;
    }
    public String getTitle() {
        return title;
    }
    public ArrayList<String> getHours() {
        return hours;
    }
    public Set<String> getKeys() {
        return keys;
    }
    public ArrayList<SavedSeats> getReservedSeats() {
        return reservedSeats;
    }
    public JButton[][] getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return title;
    }

    public void getSeatsMap(String string, JButton[][] seats) {

    }
}

