package com.catchoom.test.database;

/**
 * Created by van on 3/13/17.
 * @author Vinayak Nesarikar
 * Encapsulating class that holds all the data for the magnum
 */

public class Magnum {
    private String reason4trip;
    private int maintenance;
    private int status;
    private int line1c;
    private int line2c;
    private int line3c;

    /**
     * Constructor
     * @param reason4trip Reason for a trip
     * @param maintenance If the system is in maintenance mode
     * @param status If the magnum is open or closed
     * @param line1c Line 1's current
     * @param line2c Line 2's current
     * @param line3c Line 3's current
     */

    public Magnum(String reason4trip, int maintenance, int status,  int line1c,  int line2c, int line3c){

        this.reason4trip=reason4trip;
        this.maintenance = maintenance;
        this.status =status;
        this.line1c=line1c;
        this.line2c=line2c;
        this.line3c=line3c;
    }

    /**
     * Gets the reason for trip
     * @return The string of the reason for the trip
     */
    public String getReason4trip(){
        return reason4trip;
    }

    /**
     * Gets if the Magnum is in maintenance mode
     * @return If the Magnum is maintenance mode
     */
    public int getMaintenace(){
        return maintenance;
    }
    /**
     * Gets if the Magnum is closed or open
     * @return If the Magnum is closed or open
     */
    public int getStatus(){
        return status;
    }
    /**
     * Gets the first line's current
     * @return Gets the first line's current
     */
    public int getLine1c(){
        return line1c;
    }
    /**
     * Gets the second line's current
     * @return Gets the second line's current
     */
    public int getLine2c(){
        return line2c;
    }
    /**
     * Gets the third line's current
     * @return Gets the third line's current
     */
    public int getLine3c(){
        return line3c;
    }
}
