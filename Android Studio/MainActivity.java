package com.example.topiary.ev3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MAIn";
    private java.net.Socket socketToAP = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MyApplicatio app = new MyApplicatio();
//        Socket socket = app.getSocket();
//        socket.connect();
//
//         socket.on("Test", new Emitter.Listener() {
//
//           @Override
//            public void call(Object... args) {
//               System.out.println(args);
//                Toast.makeText(MainActivity.this, "well", Toast.LENGTH_LONG).show();
//           }
//       });
        makeThread();
        conn();
    }

    public void makeThread(){

        new Thread(){

            @Override
            public void run() {
               try{

                   socketToAP = new java.net.Socket("192.168.10.100", 3000);

               }catch(Exception e){
                   System.out.println(e);
               }
            }
        }.start();

    }


    public void conn() {
        try {
            MyApplicatio app = new MyApplicatio();
            final Socket socket = app.getSocket();

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    socket.emit("foo", "hi");
                }

            });

            socket.on("incoming", new Emitter.Listener() {

                @Override
                public void call(final Object... args) {
                    Log.d(TAG, args.toString());
                    JSONObject jsonObject = (JSONObject) args[0];
                    String x = null;
                    int y = 0;
                    try {
                        x = (String) jsonObject.get("test");
                        y = (int) jsonObject.get("time");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(jsonObject.toString());
                    System.out.println(x);
                    if(x.equals("forward")){
                        send2Server("F", y);
                    }else if(x.equals("backward")){
                        send2Server("B", y);
                    }else if(x.equals("right")){
                        send2Server("R", y);
                    }else if(x.equals("left")){
                        send2Server("L", y);
                    }
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d(TAG, "Disconnected");
                }
            });

            socket.connect();
        }catch (Exception e){

        }

    }

    public void send2Server(String x, int y){

        BufferedWriter bw = null;
        String data2send = x;
        int time = y * 1000;

        String timeString = Integer.toString(time);
        String sentence = data2send.concat(timeString);

        try{
            bw = new BufferedWriter(new OutputStreamWriter(socketToAP.getOutputStream()));
            bw.write(sentence);
            bw.newLine();
            bw.flush();
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

}
