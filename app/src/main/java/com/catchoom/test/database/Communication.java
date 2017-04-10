package com.catchoom.test.database;

import android.util.Log;

/**
 * Created by van on 3/13/17.
 * @author  Vinayak Nesarikar
 * This class handles the formating of the data sent back from the server
 */

public class Communication {
    //store the given IP address and port number
    private String dstAddress;
    private int dstPort;
    private SwitchGearInfo switchgear;

    /**
     * This Constructor establishes a connection the application and the server. Then it grabs the names of all the components in the switch gear.
     * @param addr The switch gear's server IP address
     * @param port The switch gear's server port number
     */
    public Communication(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
        //Tries to connect to the server
        Client myClient = new Client(dstAddress, dstPort);
        //Tries to grab the names of the components in the switch gear
        try {
                String val = myClient.execute("SELECT * FROM NAME;").get();
                Log.d("MyApp", val);
                String[] components = val.split(",");
                switchgear = new SwitchGearInfo(components);
        } catch (Exception e){
            Log.e("MyApp", "Unable to establish communication with database.");
        }

    }

    /**
     * Given the component you are looking at its id is sent here and used to determine which component's data the app needs to overlay.
     * @param id The component's id
     * @return A formatted string
     */
    public StringBuilder getInfo(String id){
        StringBuilder display = new StringBuilder("");
        //If the component is the overall switch gear send back a formatted string with the names of the components in the switch gear and the data for all the TC100s in the switch gear.
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
                     String val = myClient.execute("SELECT * FROM TC100 WHERE NAME = '" + tcnames[i] + "';").get();
                     String[] info = val.split(",");
                     if (info[1].equals("1")) {
                         display.append(tcnames[i] + " : Alarm\n");
                     } else {
                         display.append(tcnames[i] + " : " + info[2] + " degrees\n");
                     }

                 } catch (Exception e) {
                     Log.e("MyApp", "Unable to get data for TC100");
                     return new StringBuilder("Unable to get data for TC100");
                 }
             }
                return display;
         } else {
             return new StringBuilder("");
         }
        }else {
            //Tries to find the names corresponding to an id and then class the method associated with its component.
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
                    return new StringBuilder("Component not implemented in application.");
                }
            } else {
                return new StringBuilder("Invalid Name");
            }
        }
    }

    /**
     * Grabs the data for a specific TC100. If the alarm is on sends back the alarm is on else sends back temperature.
     * @param name The name of the specific component
     * @return A formatted string
     */
    private StringBuilder getTC100Info(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("SELECT * FROM TC100 WHERE NAME = '"+name+"';").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Tc100 tc= new Tc100(Boolean.valueOf(vals[1]), Integer.valueOf(vals[2]));
            if (tc.getAlarm()){
                return new StringBuilder(name+" : Alarm");
            }else{
                return new StringBuilder(name+" : "+tc.getTemp()+" degrees");
            }

        } catch(Exception e){
            Log.e("MyApp", "Unable to get data for TC100");
            return new StringBuilder("Unable to get data for TC100");
        }
    }

    /**
     * Grabs the data for a specific PXM8000.
     * @param name The name of the specific component
     * @return A formatted string
     */

    private StringBuilder getPXM8000Info(String name){
        Client myClient = new Client(dstAddress,dstPort);
        try {
            String val = myClient.execute("SELECT * FROM PXM8000 WHERE NAME = '"+name+"';").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Pxm8000 pxm= new Pxm8000(Integer.valueOf(vals[1]),Integer.valueOf(vals[2]),Integer.valueOf(vals[3]),Integer.valueOf(vals[4]), Integer.valueOf(vals[5]), Integer.valueOf(vals[6]));
            return  new StringBuilder(name+"\nLine 1 current : "+pxm.line1c+"A\nLine 1 voltage : "+pxm.line1v+"V\n" +
                    "VLine 2 current : "+pxm.line2c+
                    "A\nLine 2 voltage : "+pxm.line2v+"V\nLine 3 current : "+pxm.line3c+"A\nLine 3 voltage : "+pxm.line3v+"V");
        } catch(Exception e) {
            Log.e("MyApp", "Unable to get data for PXM8000");
            return new StringBuilder("Unable to get data for PXM8000");
        }
    }
    /**
     * Grabs the data for a specific Meter.
     * @param name The name of the specific component
     * @return A formatted string
     */
    private StringBuilder getMeterInfo(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("SELECT * FROM METER WHERE NAME = '"+name+"';").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Meter meters= new Meter(Integer.valueOf(vals[1]),Integer.valueOf(vals[2]),Integer.valueOf(vals[3]),Integer.valueOf(vals[4]), Integer.valueOf(vals[5]), Integer.valueOf(vals[6]));
            return  new StringBuilder(name+"\nLine 1 current : "+meters.line1c+" A\nLine 1 voltage : "+meters.line1v+" V\n" +
                    "Line 2 current : "+meters.line2c+
                    "A\nLine 2 voltage : "+meters.line2v+"V\nLine 3 current : "+meters.line3c+"A\nLine 3 voltage : "+meters.line3v +"V");
        } catch(Exception e){
            Log.e("MyApp", "Unable to get data for Meter");
            return new StringBuilder("Unable to get data for Meter");
        }
    }
    /**
     * Grabs the data for a specific Magnum.
     * @param name The name of the specific component
     * @return A formatted string
     */
    private StringBuilder getMagnumInfo(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("SELECT * FROM MAGNUM WHERE NAME = '"+name+"';").get();
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
            return magnumbuilder.append("\nLine 1 current : "+mag.getLine1c()+ "A\nLine 2 current : "+mag.getLine2c()+"A\nLine 3 current : "+mag.getLine3c()+"A");
        } catch(Exception e){
            Log.e("MyApp", "Unable to get data for Magnum");
            return new StringBuilder("Unable to get data for Magnum");
        }
    }
}
