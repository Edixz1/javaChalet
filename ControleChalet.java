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


	public Chalet chalet

	public ControleChalet(Chalet chalet ) {
		this.chalet = chalet;



	}


	public void Update(){

		double temp;
		double hum;
		double lum;
		try
		{


			temp = chalet.getTemperatureHumidite(1);

			hum = chalet.getTemperatureHumidite(2);
			lum  = chalet.getLuminosite();

			//Clim
			if(temp > chalet.getTempMax())
			{
				chalet.Climatisation(true);
				//System.out.println("Clim on");
			}
			else
			{
				chalet.Climatisation(false);
				//System.out.println("Clim off");
			}
			//Chauffage
			if(temp < chalet.getTempMin())
			{
				chalet.Chauffage(true);
				//System.out.println("Chauf on");
			}
			else
			{
				chalet.Chauffage(false);
				//System.out.println("Chauf off");
			}
			//Lumiere
			if(lum < chalet.getLumMin())
			{
				chalet.Éclairage(true);
				//System.out.println("Lum on");
			}
			else
			{
				chalet.Éclairage(false);
				//System.out.println("Lum off");
			}

		}
		catch(IOException x){}
		catch(InterruptedException xxx){}

	}



	public String get_Temp() => ( "Temp : "+chalet.getTemperatureHumidite(1)+" *C");
	public String get_Hum() => ("Hum : "+chalet.getTemperatureHumidite(0)+" %");
	public String get_Lum() => ("Lum : "+chalet.getLuminosite()+" lx");



	public boolean setMarge(String Min, String Max){
			if(tryParseInt(Min) && tryParseInt(Max) && (Min < Max))
			{
				return chalet.setMargeTemp((double)Integer.parseInt(Min),(double)Integer.parseInt(Max));
			}

				return false;
		}



	public boolean setLum(String Lum){

		if(tryParseInt(Lum))
		{
		return chalet.setLuminosité((double)Interger.parseInt(Lum));
		}

		return false
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
