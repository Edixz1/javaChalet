/*
	ServeurChalet
	
	Implémente un Serveur qui dois:
		-Recevoirs des commandes d'un Client-
		-- Les Validers
		-- Les redirigers au Controleur Chalet

		-


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



public class ServeurChalet{

	public static void main(String args[]) {
		double maxTemp = 25;
		double minTemp = 15;
		double eclairage = 250;

		// Tester les lumières
			/*try{
				Ch1.Chauffage(true);
				Ch1.Climatisation(true);
			}catch(IOException e){}
			*/
		Chalet chalet = new Chalet(maxTemp,minTemp,eclairage); // Crée le Chalet

		ControleChalet controleChalet = new ControleChalet(Chalet);

		ClientChalet Client;

		try {
			ServeurChalet Serveur = new ServeurChalet();
			Client = Serveur.Start(controleChalet);

			Thread A = new Thread(Client);

			A.start();

			while(A.isAlive())
			{
				controleChalet.Update();
			}

		}
		catch(IOException ioe)
		{
			System.out.println("Connexion a échoué");
		}

	}

	public static final int PORT = 1942;
	
	private ServerSocket socServeur;
	private Socket socClient;
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	// Boolean représentant si l'a connexion a écris le bon mot de passe
	private boolean Verifier = false;
	
	

	
	
	
	///--- Start ---
	///   Demarage des Services du Serveur et Attente d'une connexion au client 
	///
	public ClientChalet Start(ControleChalet ctChalet) throws IOException{
		

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

		return new ClientChalet(reader,writer,ctChalet);
		
	
	}
	
	

}