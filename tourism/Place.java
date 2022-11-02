package tourism;

import java.sql.*;

public class Place {
    private String cityName;
    private Residence residence;
    private Attraction attraction;

    public Place(String cityName, Connection Con) {
        this.cityName = cityName;
        this.residence = new Residence(Con); #new?
        this.attraction = new Attraction(Con);
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public String getCityName() {
        return cityName;
    }

    public Residence getResidence() {
        return residence;
    }
    
}
