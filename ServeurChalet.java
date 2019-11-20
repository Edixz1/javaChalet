/*
	ServeurChalet
	
	Implémente un Serveur qui dois:
		--Recevoirs des commandes d'un Client 
		-- Les Validers
		-- Les redirigers au Controleur Chalet
		-- Répondre aux demandes du client
		-- Creer un thread pour le client


*/
import java.net.ServerSocket;
import java.net.Socket;


public class Runnable ServeurChalet{
	
	public static final int PORT = 1942;
	
	private ServerSocket socServeur;
	private Socket socClient;
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	// Boolean représentant si l'a connexion a écris le bon mot de passe
	private boolean Verifier = false;
	
	
	private String[5] CommandesPossibles = new String() {"LOGIN","LOGOUT","TEMP","HUM","LUM","TMIN","TMAX","LMIN","FIN"};
	
	
	
	///--- Start ---
	///   Demarage des Services du Serveur et Attente d'une connexion au client 
	///
	public void Start(){
		
		try{
		socServeur = new ServerSocket(PORT);
		System.out.println("Serveur Online. En attente d'une connexion");
		
		// Attente de la connexion d'un client
		socClient = socServeur.accept();
		System.out.println("Client Connecte");
		
		// Obtention des flux d'entrer et de sorties 
		reader = new BufferedReader(
				new InputStreamReader(socClient.getInputStream()));
		writer = new PrintWriter(
				new OutputStreamWriter(socClient.getOutputStream()));
		}
		catch (IOException ioe)
		{
			System.out.println("Connexion a echouer ");
		}
		
	
	}
	
	
	/// --- Servire ---
	/// Attent une reponse ecrite du client
	///
	private void Servir()
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
		String Message;
		foreach(String A : CommandesPossibles)
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
				return false;
			}
			else if(commandes.length() != 2)
			{
				Message = "NAK";
				
			}
			else{
				VerifierPassword(commandes[1]);
			}
			return true;
		
		}
		else if(Valide && !Verifier){
			
			switch(commandes[0].toUpperCase()){
			case "LOGOUT":
			
			
			break;
			case "TEMP":
			
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
			return false;
			break;
			default:
			
			
			}
			
			
		}
		else{
			 
		}
		
		writer.write(Message);
		writer.flush();
		
	}
	
	/// --- Verifier Mot de Passe ---
	/// commandes: String -> Mot de Passe reçu
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
	private String getTemperature(){
		
		
		
	}
}