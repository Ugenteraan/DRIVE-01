package com.example.topiary.ev3;

import android.util.Log;

import io.socket.client.IO;
import io.socket.client.Socket;


public class MyApplicatio extends  MainActivity{


    Socket mSocket;
    public Socket getSocket(){
        if(mSocket==null){
            try{
                mSocket= IO.socket("http://192.168.10.102:8000");
            }catch (Exception e){
                e.printStackTrace();
                Log.d("error connecting","to server");
            }
        }

        return mSocket;
    }

}
