/*
    ClientChalet.java

    Implémente un client qui doit
        -- Lire les inputs d'un client
        -- Les validers
        -- Les envoyers à contrôle chalet


*/


public class  ClientChalet implements Runnable{


    private BufferedReader bufferedReader;
    private PrintWriter printer;
    private Controlechalet ctChalet;

    public ClientChalet(BufferedReader bufferReader,PrintWriter printer,ControleChalet ctChalet){

        this.bufferedReader = bufferReader;
        this.printer = printer;
        this.ctChalet = ctChalet; // On lui donne accées au controleur e Chalet

    }



    private String[] CommandesPossibles = {"LOGIN","LOGOUT","TEMP","HUM","LUM","TMIN","TMAX","LMIN","FIN"};

    /// --- Servire ---
	/// Attent une reponse ecrite du client
	///
	private void run()
	{
		boolean fini = false;
		String ligne = null;

		while(!fini)
		{
			ligne = reader.readLine();

			if(ligne != null || !ligne.equals("")
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
		boolean Continuer = true;
		String Message;


		foreach(String A : CommandesPossibles) /// Je m'assure que le premier paramètre de la commande
												// est t'un commande possible
		{
			if(Valide == false)
				Valide = A.equals(commandes[0].toUpperCase());

		}

		if(Valide && Verifier){

			if(commandes[0].toUpperCase() != "LOGIN" && commandes[0].toUpperCase() != "FIN")
			{
				Message = "NAK NOT LOGGED IN";

			}
			else if(commandes[0].toUpperCase() == "FIN")
			{
				Continuer =  false;
			}
			else if(commandes.length() != 2)
			{
				Message = "NAK";

			}
			else{
				VerifierPassword(commandes[1]);
			}


		}
		else if(Valide && !Verifier){

			switch(commandes[0].toUpperCase()){
			case "LOGOUT":

			break;
			case "TEMP":
			Message = getTemperature();
			break;
			case "HUM":
			break;
			case "LUM":
			break;
			case "TMIN":
			break;
			case "TMAX":
			break;
			case "LMIN":
			break;
			case "FIN"
			Continuer = false;

			default:


			}


		}
		else{

		}

		writer.write(Message);
		writer.flush();
		return Continuer; // Retoure Temporaire

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
			return "ACK"

		}

	}


	/// --- get Temperature ---
    	/// Retourne -> string Contenant la Temperature du Chalet
	private String getTemperature() => ctChalet.get_Temp();

    /// --- get Humiditer ---
    	/// Retourne -> string Contenant l'humiditer du Chalet
	private String getHumidite() => ctChalet.get_Hum();

    /// --- get Luminositer ---
    	/// Retourne -> string Contenant la luminositer du Chalet
    private String getLuminosite() => ctChalet.get_Lum();

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
    private String setLum(String Lum) => ctChalet.setLum(Lum) ? "ACK" : "NAK";



}