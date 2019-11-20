import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveLed;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;
import org.iot.raspberry.grovepi.devices.GroveLightSensor;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import java.io.IOException;
import java.lang.*;

class Chalet implements Runnable
{
	
	private Object Mutex = new Object();
	private GroveTemperatureAndHumiditySensor Dt;
	private GroveLightSensor CapteurLumiere;
	
	
	private GroveLed DelR;
	private GroveLed DelB;
	private GroveLed DelG;
	
	private double MaxTemp;
	private double MinTemp;
	private double Luminosité;
	
	final   int HUMTEMP = 3 ;
	final   int LUM = 0;
	final   int RED = 2;
	final   int BLU = 5;
	final   int GRE = 6;
	
	public Chalet(double maxTemp,double minTemp,double luminosité)
	{
		MaxTemp = maxTemp;
		MinTemp = minTemp;
		Luminosité = luminosité;
		
		
		try
		{
			//Capteur Temperature et Humidity
			GrovePi grovePi = new GrovePi4J();
			GroveTemperatureAndHumiditySensor dt
                = new GroveTemperatureAndHumiditySensor(grovePi, HUMTEMP,
                        GroveTemperatureAndHumiditySensor.Type.DHT11);
			Dt = dt;
			
            
            //Capteur Lumiere
			GroveLightSensor capteurLumiere = new GroveLightSensor(grovePi, LUM);
			
			CapteurLumiere = capteurLumiere;
			//Lumiere rouge
			
			DelR = new GroveLed(grovePi, RED);

			DelB = new GroveLed(grovePi, BLU);
			
			DelG = new GroveLed(grovePi, GRE);
		}
		catch(IOException x)
		{System.err.println("Probleme de capteur(Temperature,Humidity,Lumiere)");}
	}
	

	public void Chauffage(boolean on) throws IOException { DelR.set(on);}
	
	public void Climatisation(boolean on)  throws IOException {DelB.set(on);}
	
	public void Éclairage(boolean on)  throws IOException {DelG.set(on);}
	
	public boolean setLuminosité(double luminosité) { 
		if(luminosité >= 0 && luminosité <= 1000) 
		{
			Luminosité = luminosité;
			return true; 
			}
		else{
			return false;
		}
		}
	
	public boolean setMargeTemp(double Min,double Max) { 
		if(Min < Max && Min > -273.15/*Big brain reference*/)
		{MaxTemp = Max; 
		 MinTemp = Min; 
		return true; 
		}
		else{
			return false;
		}}
	
	public String to_string() throws IOException, InterruptedException{ 
		
		String Info = getTemperatureHumidite(1)+" °C | ";
		Info+= getTemperatureHumidite(2)+ " % | ";
		Info+= getLuminosite()+ " lx | \n";
		return Info;
	}
	
	
	public double getTempMax(){return MaxTemp;}
	public double getTempMin(){return MinTemp;}
	public double getLumMin(){ return Luminosité;}
	public synchronized double getTemperatureHumidite(int i) throws IOException , InterruptedException
	{ 
		Thread.sleep(852);
		if(i == 1)
		{
			return Dt.get().getTemperature();
		}
		else
		{
		return Dt.get().getHumidity();
		}
		
	}
	
	public double getLuminosite() throws IOException {return CapteurLumiere.get();}
	
	
	public void Update(){
		
		double temp;
		double hum;
		double lum;
		try
		{
			
				
				temp = this.getTemperatureHumidite(1);
			
				hum = this.getTemperatureHumidite(2);
				lum  = this.getLuminosite();
				
				//Clim
				if(temp > this.getTempMax())
				{
					this.Climatisation(true);
					//System.out.println("Clim on");
				}
				else
				{
					this.Climatisation(false);
					//System.out.println("Clim off");
				}
				//Chauffage
				if(temp < this.getTempMin())
				{
					this.Chauffage(true);
					//System.out.println("Chauf on");
				}
				else
				{
					this.Chauffage(false);
					//System.out.println("Chauf off");
				}
				//Lumiere
				if(lum < this.getLumMin())
				{
					this.Éclairage(true);	
					//System.out.println("Lum on");
				}
				else
				{
					this.Éclairage(false);
					//System.out.println("Lum off");
				}
			
		}
		catch(IOException x){}
		catch(InterruptedException xxx){}
		
	}
}


