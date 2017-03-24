package com.catchoom.test;

/**
 * Created by van on 3/13/17.
 */

public class Magnum {
    String reason4trip;
    int maintenance;
    int status;
    int line1c;
    int line2c;
    int line3c;
    public Magnum(String reason4trip, int maintenance, int status,  int line1c,  int line2c, int line3c){

        this.reason4trip=reason4trip;
        this.maintenance = maintenance;
        this.status =status;
        this.line1c=line1c;
        this.line2c=line2c;
        this.line3c=line3c;
    }
    public String getReason4trip(){
        return reason4trip;
    }
    public int getMaintenace(){
        return maintenance;
    }
    public int getStatus(){
        return status;
    }
    public int getLine1c(){
        return line1c;
    }
    public int getLine2c(){
        return line2c;
    }
    public int getLine3c(){
        return line3c;
    }
}
