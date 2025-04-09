package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente {
	private final String HOST = "localhost";
	private final int PUERTO = 4321;
	private Socket socket;
	
	public Cliente() throws IOException {
		// socket = new Socket(HOST, PUERTO);
	}
	
	public void interactuar() throws IOException {
		Scanner scanner = new Scanner(System.in);
		
		// Reintentar conexión en caso de que el servidor no esté disponible
		while (true) {
			try {
				socket = new Socket(HOST, PUERTO);
				System.out.println("Se ha iniciado la conexión con el servidor. Espere por favor...");
				break;
			} catch (IOException e) {
				System.out.println("No se pudo conectar al servidor. ¿Reintentar? [sí/no]");
				scanner = new Scanner(System.in);
				String answer = scanner.nextLine();
				
				// No se intenta conectar de nuevo en caso de que la respuesta sea distinta de "s", "sí" o "sí"
				if (!answer.equalsIgnoreCase("s") && !answer.equalsIgnoreCase("sí") && !answer.equalsIgnoreCase("si")) {
					System.out.println("Saliendo...");
					scanner.close();
					System.exit(0);
				}
				
			}
			
		}
		
		try {
			DataInputStream mensajeServidor = new DataInputStream(socket.getInputStream());
			DataOutputStream respuesta = new DataOutputStream(socket.getOutputStream());
			
			String inputConsola = "";
			String mensaje = "";
			
			// Lee y responde en bucle
			while (true) {			
				mensaje = mensajeServidor.readUTF();
				
				System.out.println(mensaje);
				
				// Se cierra la conexión después del último mensaje del servicio
				if (mensaje.contains("La contraseña generada es: ") || mensaje.contains("No se generará ninguna contraseña. Hasta la próxima.")) {
					System.out.println("Conexión terminada.");
					socket.close();
					break;
				}				
				
				inputConsola = scanner.nextLine();
				respuesta.writeUTF(inputConsola);
			}
		} catch (SocketException e) {
			System.out.println("La conexión ha finalizado.");
		} finally {
			scanner.close();
		}		
		
	}
}
