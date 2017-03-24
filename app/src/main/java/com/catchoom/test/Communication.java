package com.catchoom.test;

import android.util.Log;
import android.util.StringBuilderPrinter;

import java.util.concurrent.ExecutionException;

/**
 * Created by van on 3/13/17.
 * assumed naming convention tag_id ie. TC100_A1
 */

public class Communication {
    String dstAddress;
    int dstPort;
    SwitchGearInfo switchgear;
    Communication(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
        Client myClient = new Client(dstAddress,dstPort);
        try {
            String val = myClient.execute("SELECT * FROM NAME;").get();
            Log.d("MyApp", val);
            String[] components = val.split(",");
            switchgear = new SwitchGearInfo(components);
        } catch(Exception e){
        }
    }
    public StringBuilder getInfo(String id){
        StringBuilder display = new StringBuilder("");
        if(id.equalsIgnoreCase("Switchgear")){
            String [] components = switchgear.getComponents();
            for(int i = 0; i<components.length; i++){
                display.append(components[i]+"\n");
            }
            String [] tcnames = switchgear.findNames("TC100");
            if(tcnames != null){
             for(int i=1; i<tcnames.length; i++ ) {
                 Client myClient = new Client(dstAddress, dstPort);
                 try {
                     String val = myClient.execute("SELECT * FROM TC100 WHERE NAME = '"+ tcnames[i]+"';").get();
                     String[] info = val.split(",");
                     if(info[1].equals("1")){
                         display.append(tcnames[i]+" : Alarm\n");
                     } else {
                         display.append(tcnames[i]+" : "+info[1]+"\n");
                     }
                 } catch (Exception e) {
                    return new StringBuilder("");
                 }
             }
         } else {
             return new StringBuilder("");
         }
        }else {
            String name = switchgear.findName(id);
            if (name != null) {
                String[] tags = name.split("_");
                if (tags[0].equalsIgnoreCase("TC100")) {
                  return getTC100Info(name);
                } else if (tags[0].equalsIgnoreCase("PXM8000")) {
                  return getPXM8000Info(name);
                } else if (tags[0].equalsIgnoreCase("Meter")) {
                    return getMeterInfo(name);
                } else if (tags[0].equalsIgnoreCase("Magnum")) {
                    return getMagnumInfo(name);
                } else {
                    return new StringBuilder("");
                }
            } else {
                return new StringBuilder("");
            }
        }
        return new StringBuilder("");
    }
    public StringBuilder getTC100Info(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("SELECT * FROM TC100 WHERE NAME = '"+name+"';").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Tc100 tc= new Tc100(Boolean.valueOf(vals[1]), Integer.valueOf(vals[2]));
            if (tc.getAlarm()){
                return new StringBuilder(name+" : Alarm");
            }else{
                return new StringBuilder(name+" : "+tc.getTemp());
            }

        } catch(Exception e){
            return new StringBuilder("");
        }
    }
    public StringBuilder getPXM8000Info(String name){
        Client myClient = new Client(dstAddress,dstPort);
        try {
            String val = myClient.execute("SELECT * FROM PXM8000 WHERE NAME = '"+name+"';").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Pxm8000 pxm= new Pxm8000(Integer.valueOf(vals[1]),Integer.valueOf(vals[2]),Integer.valueOf(vals[3]),Integer.valueOf(vals[4]), Integer.valueOf(vals[5]), Integer.valueOf(vals[6]));
            return  new StringBuilder(name+"\nLine 1 current : "+pxm.line1c+"\nLine 1 voltage : "+pxm.line1v+"\n" +
                    "Line 2 current : "+pxm.line2c+
                    "\nLine 2 voltage : "+pxm.line2v+"\nLine 3 current"+pxm.line3c+"\nLine 3 voltage"+pxm.line3v);
        } catch(Exception e) {
            return new StringBuilder("");
        }
    }
    public StringBuilder getMeterInfo(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("SELECT * FROM METER WHERE NAME = '"+name+"';").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Meter meters= new Meter(Integer.valueOf(vals[1]),Integer.valueOf(vals[2]),Integer.valueOf(vals[3]),Integer.valueOf(vals[4]), Integer.valueOf(vals[5]), Integer.valueOf(vals[6]));
            return  new StringBuilder(name+"\nLine 1 current : "+meters.line1c+"\nLine 1 voltage : "+meters.line1v+"\n" +
                    "Line 2 current : "+meters.line2c+
                    "\nLine 2 voltage : "+meters.line2v+"\nLine 3 current"+meters.line3c+"\nLine 3 voltage"+meters.line3v);
        } catch(Exception e){
        }
        return null;
    }
    public StringBuilder getMagnumInfo(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("SELECT * FROM MAGNUM WHERE NAME = '"+name+"';").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Magnum mag = new Magnum(vals[1], Integer.valueOf(vals[2]), Integer.valueOf(vals[3]), Integer.valueOf(vals[4]), Integer.valueOf(vals[5]), Integer.valueOf(vals[6]));
            StringBuilder magnumbuilder = new StringBuilder(name);
            if(!mag.getReason4trip().equals("")){
                magnumbuilder.append("\nReason for trip : "+mag.getReason4trip());
            }
            if(mag.getMaintenace() == 1){
                magnumbuilder.append("\nMaintenance mode : On");
            } else {
                magnumbuilder.append("\nMaintenance mode : Off");
            }
            if(mag.getStatus() == 1){
                magnumbuilder.append("\nStatus : Open");
            } else {
                magnumbuilder.append("\nStatus : Close");
            }
            magnumbuilder.append("\nLine 1 current : "+mag.getLine1c()+ "\nLine 2 current : "+mag.getLine2c()+"\nLine 3 current"+mag.getLine3c());
        } catch(Exception e){
        }
        return null;
    }
}
