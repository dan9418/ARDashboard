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
    public void getSwitchgearInfo(){
        Client myClient = new Client(dstAddress,dstPort);

            try {
                String val = myClient.execute("Get all component names, locations, and powers").get();
                Log.d("MyApp", val);
            } catch(Exception e){
            }


    }
    public void getTC100Info(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("From name Get Alarm and temperature").get();
            Log.d("MyApp", val);
        } catch(Exception e){
        }
    }
    public void getPXM8000Info(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("From name Get 3phase current and voltage").get();
            Log.d("MyApp", val);
        } catch(Exception e){
        }
    }
    public void getMeterInfo(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("From name Get 3phase current and voltage").get();
            Log.d("MyApp", val);
        } catch(Exception e){
        }
    }
    public void getMagnumInfo(String name){
        Client myClient = new Client(dstAddress,dstPort);

        try {
            String val = myClient.execute("From name Get cause for trip,maintenance mode,status,3 phase current").get();
            Log.d("MyApp", val);
        } catch(Exception e){
        }
    }
}
