
import java.io.*;
import java.nio.ByteBuffer;

public class DBParams {
	static String DBPath;
	public int pageSize;
	public static int frameCount;
	private static DBParams DBParams = new DBParams();
	private DBParams() {
		
	}
	//J'ai crée une instance de DBParams pour plus tard si on devra utiliser une methode de cette classe
	public static DBParams getInstance() {
		return DBParams;
	}
}

