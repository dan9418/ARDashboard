package com.catchoom.test.database;
/**
 * Created by van on 3/13/17.
 * @author Vinayak Nesarikar
 * Encapsulating class that holds all the data for the TC100
 */

public class Tc100 {
    private boolean alarm;
    private int temp;

    /**
     * Constructor
     * @param alarm If the TC100 is in alarm mode
     * @param temp The temp of the TC100
     */
    public Tc100(boolean alarm, int temp){
        this.alarm = alarm;
        this.temp=temp;
    }

    /**
     * Gets if the TC100 is in alarm mode
     * @return if the TC100 is in alarm mode
     */
    public boolean getAlarm(){
        return alarm;
    }

    /**
     * Gets the TC100's temp
     * @return the TC100's temp
     */
    public int getTemp(){
        return temp;
    }
}
