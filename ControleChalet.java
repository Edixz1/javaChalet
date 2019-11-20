import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveLed;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;
import org.iot.raspberry.grovepi.devices.GroveLightSensor;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import java.io.IOException;
import java.lang.Thread;
import java.util.Scanner;
class ControleChalet 
{
	public static void main(String args[])
	{
		
			double maxTemp = 25;
			double minTemp = 15;
			double éclairage = 250;
			
			
			/// Créer le Chalet 
			Chalet Ch1 = new Chalet(maxTemp, minTemp, éclairage);
			
			
			
			
		// Tester les lumières 
		/*try{
			Ch1.Chauffage(true);
			Ch1.Climatisation(true);
		}catch(IOException e){}
		*/
			
			
			
			thC.start();
			
			while(thC.isAlive())
			{
				Ch1.Update();
				
			}
	
		
	}
	
	
	public static boolean tryParseInt(String Valeur)
	{
		try{
			Integer.parseInt(Valeur);
			return true;
		} catch(NumberFormatException e)
		{
			return false;
		}
	}
	
	
	
	
}
//lorsquon java : java ControleChalet 2> /dev
