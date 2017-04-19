package project4fx_parker;

public class Batter {
    private String name;
    private String position;
    private double average;

    /**
     * create batter object
     * @param name - batter's name
     * @param average - batter's average
     */
    public Batter(String name, double average) {
        this.name = name;
        this.average = average;
    }

    /**
     * check if batter hits ball
     * @return - true if hits, false if ball
     */
    public boolean hit() {
        return Math.random() <= average;
    }

    /**
     * get batter's name
     * @return - batter's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * get batter's average
     * @return - batter's average
     */
    public double getAverage() {
        return average;
    }
    
    /**
     * get batter's position
     * @return - batter's position
     */
    public String getPosition() {
        return position;
    }
    
    /**
     * sets batter's name
     * @param name - batter's name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * sets batter's average
     * @param average - batter's average
     */
    public void setAverage(double average) {
        this.average = average;
    }
    
    /**
     * sets batter's position
     * @param position - batter's position
     */
    public void setPosition(String position) {
        this.position = position;
    }
}
