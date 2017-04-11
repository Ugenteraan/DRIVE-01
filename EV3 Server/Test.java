import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Test {

	public static void main(String[] args) {
		try{
			ServerSocket server = new ServerSocket(3000);
			System.out.println("Started");
			Socket s = server.accept();
			System.out.println("Connected");
			//LCD.drawString("Connected", 0, 1);
			
			RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
			RegulatedMotor m2 = new EV3LargeRegulatedMotor(MotorPort.C);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String data = null;
			int time = 0;
			String data1 = null;
			
			while ((data = br.readLine()) != null){
				
				data1 = data.substring(0, 1);
				time = Integer.parseInt(data.substring(1));
				
				if(data1.equals("F")){
					m.forward();
					m2.forward();
					Delay.msDelay(time);
					m2.stop();
					m.stop();
				}
				else if(data1.equals("B")){
					
					m.backward();
					m2.backward();
					Delay.msDelay(time);
					m2.stop();
					m.stop();
					
				}
				else if(data1.equals("R")){
				
					m.forward();
					m2.backward();
					Delay.msDelay(time);
					m.stop();
					m2.stop();
				}	
				else if(data1.equals("L")){
					m.backward();
					m2.forward();
					Delay.msDelay(time);
					m2.stop();
					m.stop();
					
				}
							
				System.out.println(data);
			}
			
			System.out.println("Ended");
			Delay.msDelay(4000);
			
			//LCD.drawString(br.readLine(), 0, 1);
			
			//Delay.msDelay(2000);
			
			
			
		}catch(Exception e){
			
		}
	}

}
