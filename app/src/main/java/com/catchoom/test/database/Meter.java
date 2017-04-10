package com.catchoom.test.database;
/**
 * Created by van on 3/13/17.
 * @author Vinayak Nesarikar
 * Encapsulating class that holds all the data for the meter
 */


public class Meter {
    int line1c;
    int line1v;
    int line2c;
    int line2v;
    int line3c;
    int line3v;

    /**
     * Constructor
     * @param line1c Line 1's current
     * @param line1v Line 1's voltage
     * @param line2c Line 2's current
     * @param line2v Line 2's voltage
     * @param line3c Line 3's current
     * @param line3v Line 3's voltage
     */
    public Meter(int line1c, int line1v, int line2c, int line2v, int line3c, int line3v){
        this.line1c=line1c;
        this.line1v=line1v;
        this.line2c=line2c;
        this.line2v=line2v;
        this.line3c=line3c;
        this.line3v=line3v;
    }
    /**
     * Gets the first line's current
     * @return Gets the first line's current
     */
    public int getLine1c(){
        return line1c;
    }
    /**
     * Gets the third line's voltage
     * @return Gets the third line's voltage
     */
    public int getLine1v(){
        return line1v;
    }
    /**
     * Gets the second line's current
     * @return Gets the second line's current
     */
    public int getLine2c(){
        return line2c;
    }
    /**
     * Gets the third line's voltage
     * @return Gets the third line's voltage
     */
    public int getLine2v(){
        return line2v;
    }
    /**
     * Gets the third line's current
     * @return Gets the third line's current
     */
    public int getLine3c(){
        return line3c;
    }
    /**
     * Gets the third line's voltage
     * @return Gets the third line's voltage
     */
    public int getLine3v(){
        return line3v;
    }
}
