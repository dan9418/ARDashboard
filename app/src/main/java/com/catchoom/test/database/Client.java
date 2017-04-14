package com.catchoom.test.database;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class is the asynchronous client that connects to the server through a websocket
 * @author Vinayak Nesarikar
 */
public class Client extends AsyncTask<String, String, String> {

    private String dstAddress;
    private String response = "";
    private int dstPort;

    /**
     * Stores the servers IP address and Port
     * @param addr IP address
     * @param port Port number
     */
    Client(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
    }

    /**
     * Invoked on a background thread. Preforms the data query to the RPI server.
     * @param query The database query
     * @return Database query string
     */
    @Override
    protected String doInBackground(String...  query) {

        Socket socket = new Socket();

        try {
            //connects to serve through a sockets
            socket.connect(new InetSocketAddress(dstAddress, dstPort),3000);
            if(socket!=null) {
                byte[] vals = new byte[1024];
                PrintWriter outToServer = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
                outToServer.print(query[0]);
                outToServer.flush();

                InputStream inputStream = socket.getInputStream();
                int nums = 0;
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //continuously reads in data until a space is read in
                while ((nums = in.read()) != 32) {
                    response = response + Character.toString((char) nums);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;

    }
    //closes thread
    @Override
    protected void onPostExecute(String result) {
        this.cancel(true);
    }

}