package com.catchoom.test;

/**
 * Created by van on 3/13/17.
 */

public class Tc100 {
    boolean alarm;
    int temp;
    public Tc100(boolean alarm, int temp){
        this.alarm = alarm;
        this.temp=temp;
    }
    public boolean getAlarm(){
        return alarm;
    }
    public int getTemp(){
        return temp;
    }
}
