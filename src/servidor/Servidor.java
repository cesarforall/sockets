package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private final int PUERTO = 4321;
	private ServerSocket serverSocket;
	
	public Servidor() throws IOException {
		// Inicia el socket en localhost (127.0.0.1)
		serverSocket = new ServerSocket(PUERTO, 50, InetAddress.getLoopbackAddress());
	}
	
	public void start() throws IOException {
		System.out.println("Se ha iniciado el servidor.");
		System.out.println("Esperando la conexión del cliente...");
		try {
			while (true) {				
				Socket socket = serverSocket.accept();
				System.out.println("Cliente conectado desde" + socket.getLocalSocketAddress());				
				
				String nombre = "";
				int numMinusculas = 0;
				int numMayusculas = 0;
				int numDigitos = 0;
				int numCarEspeciales = 0;
				
				ServicioPass servicioPass;
				
				DataOutputStream mensajeSalida = new DataOutputStream(socket.getOutputStream());
				DataInputStream mensajeEntrada = new DataInputStream(socket.getInputStream());
				
				mensajeSalida.writeUTF("Hola, soy un servidor.\n¿Cuál es tu nombre?");
				nombre = mensajeEntrada.readUTF();
				System.out.println("Nombre del cliente: " + nombre);
				
				mensajeSalida.writeUTF("Te doy la bienvenida " + nombre + ".\nVoy a solicitar algunos requisitos para generar la contraseña.\n¿Cuántas mínusculas debe incluir?");
				numMinusculas = Integer.parseInt(mensajeEntrada.readUTF());
				
				mensajeSalida.writeUTF("¿Cuántas mayúsculas debe tener?");
				numMayusculas = Integer.parseInt(mensajeEntrada.readUTF());
				
				mensajeSalida.writeUTF("¿Cuántos dígitos te debe incluir?");
				numDigitos = Integer.parseInt(mensajeEntrada.readUTF());
				
				mensajeSalida.writeUTF("¿Cuántos caracteres especiales debe tener?");
				numCarEspeciales = Integer.parseInt(mensajeEntrada.readUTF());
				
				servicioPass = new ServicioPass(numMinusculas, numMayusculas, numDigitos, numCarEspeciales);
				System.out.println(String.format("Los requisitos del cliente son los siguientes:\nPasswordReqs{minusculas=%d, mayusculas=%d, digitos=%d, caracteresEsoeciales=%d}", numMinusculas, numMayusculas, numDigitos, numCarEspeciales));
				
				mensajeSalida.writeUTF("La longitud de la contraseña que se va a generar es de " + servicioPass.longitudPass() + " caracteres.\n¿Quieres generar una contraseña ahora? [sí/no]");
				System.out.println("Se ha enviado la longitud de la contraseña al cliente");
				
				if (mensajeEntrada.readUTF().equals("sí")) {
					mensajeSalida.writeUTF("La contraseña generada es: " + servicioPass.generaPass());
					System.out.println("Se ha enviado la contraseña al cliente");
				} else {
					mensajeSalida.writeUTF("No se generará ninguna contraseña. Hasta la próxima.");
					System.out.println("El cliente no desea generar una contraseña");
				}
				
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			serverSocket.close();
		}	
	}
}
