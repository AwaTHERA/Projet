import java.util.Scanner;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Main {
	
	
	public static int getPageSize;
	// Cretation d'une instance de la classe Main
	private static Main Main = new Main();
	private Main() {
	}
	public static Main getInstance() {
		return Main;
	}
	String DBPath = "/home/stankovic/Projet_BDDA_THERA_STANKOVIC/Projet_BDDA_THERA_STANKOVIC/DB" ;
	static int pageSize =4096;
	
	//Compteur du frame
			int frameCount = 2;
			
	public static void main(String[] args) throws IOException {
		//BufferManager buffer = new BufferManager ();
		
		int x = 0;
		System.out.println("ARG 1"+args[0]);
		DBManager.init();
		Scanner scanner = new Scanner (System.in);
		while (true) {
			System.out.println ("Coucou, que veux-tu faire?");
			String commande = scanner.nextLine();
			if (commande.equals("EXIT")|| commande.equals("exit")) {
				DBManager.finish();
				System.out.println("au revoir!");
				break;
		    }
			else {
				DBManager.getInstance().processCommand(commande);
			}

		}

		scanner.close();
		}
	public int getFrameCount() {
		return frameCount;
	}
	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}
	public static Main getMain() {
		return Main;
	}
	public static void setMain(Main main) {
		Main.Main = main;
	}
	public String getDBPath() {
		return DBPath;
	}
	public void setDBPath(String dBPath) {
		DBPath = dBPath;
	}
	public static int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
		
	}
