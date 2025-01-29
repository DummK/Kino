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
    private HashMap<String, ArrayList<SavedSeats>> reservedSeatsMap;
    private Set<String> keys;

    public Movie(String title, String genre, int year, int durationInMinutes, ArrayList<String> hours) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.durationInMinutes = durationInMinutes;
        this.hours = hours;
        reservedSeatsMap = new HashMap<>();
    }

    public void setReservedSeatsMap(String hour, ArrayList<SavedSeats> reservedSeats) {
        reservedSeatsMap.put(hour, reservedSeats);
        keys = reservedSeatsMap.keySet();
    }

    public HashMap<String, ArrayList<SavedSeats>> getReservedSeatsMap() {
        return reservedSeatsMap;
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

    @Override
    public String toString() {
        return title;
    }
}

