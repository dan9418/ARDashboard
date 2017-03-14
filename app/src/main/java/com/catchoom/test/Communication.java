package com.catchoom.test;

import android.util.Log;

/**
 * Created by van on 3/13/17.
 */

public class Communication {
    String dstAddress;
    int dstPort;

    Communication(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
    }
    public SwitchGearInfo getSwitchgearInfo(){
        Client myClient = new Client(dstAddress,dstPort);

            try {
                String val = myClient.execute("Get all component names,number of components, locations, and powers").get();
                Log.d("MyApp", val);
                String[] vals = val.split(",");
                String[] components = new String[Integer.valueOf(vals[1])];
                int[] locations = new int[Integer.valueOf(vals[1])];
                int[] powers = new int[Integer.valueOf(vals[1])];
                int i, j;
                for (i = 2; i < Integer.valueOf(vals[1])+2 ;i++) {
                    components[i - 2] = vals[i];
                }
                for (j = i; j < Integer.valueOf(vals[1])+i ;j++){
                    locations[j-i] = Integer.valueOf(vals[j]);
                }
                for (int t=j; t < Integer.valueOf(vals[1])+j ;t++){
                    powers[t-j] = Integer.valueOf(vals[t]);
                }
                SwitchGearInfo switchgear = new SwitchGearInfo(vals[0], components, locations, powers);
                return switchgear;
            } catch(Exception e){
            }
        return null;

    }
    public Tc100 getTC100Info(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("From name Get Alarm and temperature").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Tc100 tc= new Tc100(Boolean.valueOf(vals[0]), Integer.valueOf(vals[1]));
            return tc;
        } catch(Exception e){
        }
        return null;
    }
    public Pxm8000 getPXM8000Info(String name){
        Client myClient = new Client(dstAddress,dstPort);
        try {
            String val = myClient.execute("From name Get 3phase current and voltage").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Pxm8000 pxm= new Pxm8000(Integer.valueOf(vals[0]),Integer.valueOf(vals[1]),Integer.valueOf(vals[2]),Integer.valueOf(vals[3]), Integer.valueOf(vals[4]), Integer.valueOf(vals[5]));
            return pxm;
        } catch(Exception e){
        }
        return null;
    }
    public Meter getMeterInfo(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("From name Get 3phase current and voltage").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Meter meters= new Meter(Integer.valueOf(vals[0]),Integer.valueOf(vals[1]),Integer.valueOf(vals[2]),Integer.valueOf(vals[3]), Integer.valueOf(vals[4]), Integer.valueOf(vals[5]));
            return meters;
        } catch(Exception e){
        }
        return null;
    }
    public Magnum getMagnumInfo(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("From name Get cause for trip,maintenance mode,status,3 phase current").get();
            Log.d("MyApp", val);
            String[] vals = val.split(",");
            Magnum mag = new Magnum(vals[0], Boolean.valueOf(vals[1]), Boolean.valueOf(vals[2]), Integer.valueOf(vals[3]), Integer.valueOf(vals[4]), Integer.valueOf(vals[5]));
            return mag;
        } catch(Exception e){
        }
        return null;
    }
}
