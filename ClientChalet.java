/*
    ClientChalet.java

    Implémente un client qui doit
        -- Lire les inputs d'un client
        -- Les validers
        -- Les envoyers à contrôle chalet


*/
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class  ClientChalet implements Runnable{


    private BufferedReader bufferedReader;
    private PrintWriter printer;
    private ControleChalet ctChalet;
    private boolean Verifier = false;
    private String MotDePasse = "ABC123";

    public ClientChalet(BufferedReader bufferReader,PrintWriter printer,ControleChalet ctChalet){

        this.bufferedReader = bufferReader;
        this.printer = printer;
        this.ctChalet = ctChalet; // On lui donne accées au controleur e Chalet

    }



    private String[] CommandesPossibles = {"LOGIN","LOGOUT","TEMP","HUM","LUM","TMARGE","LMIN","FIN"};

    /// --- Servire ---
	/// Attent une reponse ecrite du client
	///
	public void run() 
	{
		
		boolean fini = false;
		String ligne = null;

		while(!fini)
		{
			try{
				
			ligne = bufferedReader.readLine();
			}
			catch(IOException e)
			{
				fini = true;
				System.out.println("Read Failed");
			}
				
				
			if(ligne != null || !ligne.equals(""))
					// Si la ligne n'est pas vide ou null
						// On envois la ligne a une methode qui s'occupera de la gestion des donners recus
							// retournera false si la connexion est terminer
			   fini = Traiter_Donners(ligne.split(" "));

			else{
				// Si la ligne est null, alors la connexion a eter couper
				fini = true;
			}


		}

}

	

	/// --- Traiter_Donners ---
	/// commandes: String[] -> Les commandes et ses parametres
	/// Resumer -> Recois une commande et reagis a celle-ci
	/// Retourne -> Boolean representant si la connexion est terminer (false si OUI)
	private boolean Traiter_Donners(String[] commandes)
	{
		boolean Valide = false;
		boolean fini = false;
		String Message = "NAK";


		for(String A : CommandesPossibles) /// Je m'assure que le premier paramètre de la commande
												// est t'un commande possible
		{
			if(Valide == false)
				Valide = A.equals(commandes[0].toUpperCase());

		}

		if(Valide && !Verifier){

			if(!commandes[0].toUpperCase().equals( "LOGIN") && !commandes[0].toUpperCase().equals("FIN"))
			{
				Message = "NAK NOT LOGGED IN";

			}
			else if(commandes[0].toUpperCase().equals("FIN"))
			{
				fini =  true;;
				Message = "AKN";
			}
			else if(commandes.length == 2)
			{
				
				Message = VerifierPassword(commandes[1]);
				
			}


		}
		else if(Valide && Verifier){

			switch(commandes[0].toUpperCase()){
			case "LOGOUT":
			    Verifier = false;
			break;
			case "TEMP":
			Message = getTemperature();
			break;
			case "HUM":
			Message = getHumidite();
			break;
			case "LUM":
			Message = getLuminosite();
			break;
			case "TMARGE":
			Message = setMargeTemp(commandes[1],commandes[2]);
			break;
			case "LMIN":
			Message = setLum(commandes[1]);
			break;
			case "FIN":
			fini = true;
			Message= "Goodbye";

			default:


			}


		}
		else{

		}

		printer.write(Message + "\n");
		printer.flush();
		return fini; // Retoure Temporaire

	}

	/// --- Verifier Mot de Passe ---
	    /// Password: String -> Mot de Passe reçu
	    /// Resumer -> Confirme si le mot de passe reçu est le bon.
	    /// Retourn -> String : Message de validation à envoyer
	private String VerifierPassword(String Password){

		if(!Password.equals(MotDePasse))
		{

			return "NAK Accès refusé";

		}
		else{
			Verifier = true;
			return "ACK Accès Accepté";

		}

	}


	/// --- get Temperature ---
    	/// Retourne -> string Contenant la Temperature du Chalet
	private String getTemperature() {return ctChalet.get_Temp();}

    /// --- get Humiditer ---
    	/// Retourne -> string Contenant l'humiditer du Chalet
	private String getHumidite() {return ctChalet.get_Hum();}

    /// --- get Luminositer ---
    	/// Retourne -> string Contenant la luminositer du Chalet
    private String getLuminosite() {return ctChalet.get_Lum();}

    /// --- set MargeTemp ---
        /// Min: String -> La temperature Minimum
        /// Max: String -> La temperature Maxinum
    private String setMargeTemp(String Min,String Max)
    {
            boolean Valide = ctChalet.setMarge(Min,Max);
            if(!Valide)
            {
                return "NAK";
            }

            return "ACK";
    }




     /// --- set MargeTemp ---
            /// Lum: String -> La luminositer Min avant d'allumer les lumières
    private String setLum(String Lum){
    
    if(ctChalet.setLum(Lum))
    return "ACK";
    else
     return "NAK";
     }



}
