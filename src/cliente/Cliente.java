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
		socket = new Socket(HOST, PUERTO);
	}
	
	public void iniciarCliente() throws IOException {
		System.out.println("Se ha iniciado el cliente.");
		
		try (Scanner scanner = new Scanner(System.in)) {
			DataInputStream mensajeServidor = new DataInputStream(socket.getInputStream());
			DataOutputStream respuesta = new DataOutputStream(socket.getOutputStream());
			
			String inputConsola = "";
			
			while (true) {			
				String mensaje = mensajeServidor.readUTF();
				System.out.println(mensaje);
				inputConsola = scanner.nextLine();
				respuesta.writeUTF(inputConsola);
			}
		} catch (SocketException e) {
			System.out.println("El servidor ha cerrado la conexión.");
		}		
		
	}
}
