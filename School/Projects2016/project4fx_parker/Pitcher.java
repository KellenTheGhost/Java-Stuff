package project4fx_parker;

public class Pitcher {
    private String name;
    private String position;
    private double strikeAverage;

    /**
     * create pitcher object
     * @param name - pitcher's name
     * @param strikeAverage - pitcher's average
     */
    public Pitcher(String name, double strikeAverage) {
        this.name = name;
        this.strikeAverage = strikeAverage;
    }

    /**
     * check if pitcher hits ball
     * @return - true if hits, false if ball
     */
    public boolean pitch() {
        return Math.random() <= strikeAverage;
    }

    /**
     * get pitcher's name
     * @return - pitcher's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * get pitcher's average
     * @return - pitcher's average
     */
    public double getStrikeAverage() {
        return strikeAverage;
    }
    
    /**
     * get pitcher's position
     * @return - pitcher's position
     */
    public String getPosition() {
        return position;
    }
    
    /**
     * sets pitcher's name
     * @param name - pitcher's name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * sets pitcher's average
     * @param strikeAverage - pitcher's average
     */
    public void setAverage(double strikeAverage) {
        this.strikeAverage = strikeAverage;
    }
    
    /**
     * sets pitcher's position
     * @param position - pitcher's position
     */
    public void setPosition(String position) {
        this.position = position;
    }    
}

