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
		// Permite el inicio de una nueva conexión de socket cuando se cierra el anidado
		while (true) {
			// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
			// Cuando se sale del bloque se ejecutan los close de los recursos automáticamente
			try (Socket socket = serverSocket.accept()) {
				DataOutputStream mensajeSalida = new DataOutputStream(socket.getOutputStream());
				DataInputStream mensajeEntrada = new DataInputStream(socket.getInputStream());
				
				// Imprime la dirección y el puerto
				System.out.println("Cliente conectado desde" + socket.getLocalSocketAddress());				
				
				String nombre = "";
				int numMinusculas = 0;
				int numMayusculas = 0;
				int numDigitos = 0;
				int numCarEspeciales = 0;
				
				ServicioPass servicioPass;
				
				mensajeSalida.writeUTF("Hola, soy el Generador de Contraseñas.\n¿Cuál es tu nombre?");
				nombre = mensajeEntrada.readUTF();
				
				// Asigna un nombre genérico en caso de que el cliente no escriba caracteres
				if (nombre.isEmpty()) {
					nombre = "Cliente";
				}
				
				System.out.println("Nombre del cliente: " + nombre);
				
				mensajeSalida.writeUTF("Te doy la bienvenida " + nombre + ".\nVoy a solicitar algunos requisitos para generar la contraseña.\n¿Cuántas mínusculas debe incluir?");
				// Bienvenida y Minúsculas
				// Comprueba que se pueda hacer la conversión a números y sea un número válido. En caso negativo se cierra la conexión
				try {				        
			        numMinusculas = Integer.parseInt(mensajeEntrada.readUTF());				        
			        
			        if (numMinusculas > 0) {
			        	
			        } else {
			            mensajeSalida.writeUTF("La cantidad debe ser un número positivo. Inténtalo de nuevo.");			            
			            socket.close();
			            System.out.println("Error en introducción de datos. Sesión terminada con el cliente.");
			            continue;
			        }
			    } catch (Exception e) {				        
			        mensajeSalida.writeUTF("La cantidad debe ser un número válido. Inténtalo de nuevo.");
			        System.out.println("Error en la introducción de datos. La sesión con el cliente ha sido terminada.");
			        socket.close();
			        continue;
			    }
				
				// Mayúsculas
				// Comprueba que se pueda hacer la conversión a números y sea un número válido. En caso negativo se cierra la conexión
				mensajeSalida.writeUTF("¿Cuántas mayúsculas debe tener?");
				try {				        
					numMayusculas = Integer.parseInt(mensajeEntrada.readUTF());				        
			        
			        if (numMayusculas > 0) {
			        	
			        } else {
			            mensajeSalida.writeUTF("La cantidad debe ser un número positivo. Inténtalo de nuevo.");			            
			            socket.close();
			            System.out.println("Error en introducción de datos. Sesión terminada con el cliente.");
			            continue;
			        }
			    } catch (Exception e) {				        
			        mensajeSalida.writeUTF("La cantidad debe ser un número válido. Inténtalo de nuevo.");
			        System.out.println("Error en la introducción de datos. La sesión con el cliente ha sido terminada.");
			        socket.close();
			        continue;
			    }
				
				// Dígitos
				// Comprueba que se pueda hacer la conversión a números y sea un número válido. En caso negativo se cierra la conexión
				mensajeSalida.writeUTF("¿Cuántos dígitos te debe incluir?");
				try {				        
					numDigitos = Integer.parseInt(mensajeEntrada.readUTF());				        
			        
			        if (numDigitos > 0) {
			        	
			        } else {
			            mensajeSalida.writeUTF("La cantidad debe ser un número positivo. Inténtalo de nuevo.");			            
			            socket.close();
			            System.out.println("Error en introducción de datos. Sesión terminada con el cliente.");
			            continue;
			        }
			    } catch (Exception e) {				        
			        mensajeSalida.writeUTF("La cantidad debe ser un número válido. Inténtalo de nuevo.");
			        System.out.println("Error en la introducción de datos. La sesión con el cliente ha sido terminada.");
			        socket.close();
			        continue;
			    }
				
				// Caracteres especiales
				// Comprueba que se pueda hacer la conversión a números y sea un número válido. En caso negativo se cierra la conexión
				mensajeSalida.writeUTF("¿Cuántos caracteres especiales debe tener?");
				try {				        
					numCarEspeciales = Integer.parseInt(mensajeEntrada.readUTF());				        
			        
			        if (numCarEspeciales > 0) {
			        	
			        } else {
			            mensajeSalida.writeUTF("La cantidad debe ser un número positivo. Inténtalo de nuevo.");			            
			            socket.close();
			            System.out.println("Error en introducción de datos. Sesión terminada con el cliente.");
			            continue;
			        }
			    } catch (Exception e) {				        
			        mensajeSalida.writeUTF("La cantidad debe ser un número válido. Inténtalo de nuevo.");
			        System.out.println("Error en la introducción de datos. La sesión con el cliente ha sido terminada.");
			        socket.close();
			        continue;
			    }
				
				// Genera la contraseña
				servicioPass = new ServicioPass(numMinusculas, numMayusculas, numDigitos, numCarEspeciales);
				System.out.println(String.format("Los requisitos del cliente son los siguientes:\nPasswordReqs{minusculas=%d, mayusculas=%d, digitos=%d, caracteresEsoeciales=%d}", numMinusculas, numMayusculas, numDigitos, numCarEspeciales));
				
				// Confirma el envío de contraseña
				mensajeSalida.writeUTF("La longitud de la contraseña que se va a generar es de " + servicioPass.longitudPass() + " caracteres.\n¿Quieres generar una contraseña ahora? [sí/no]");
				System.out.println("Se ha enviado la longitud de la contraseña al cliente");
				
				String mensaje = mensajeEntrada.readUTF();
				
				// Envía de contraseña
				if (mensaje.equals("s") || mensaje.equals("sí") || mensaje.equals("si")) {
					mensajeSalida.writeUTF("La contraseña generada es: " + servicioPass.generaPass() + "\nGracias por utilizar el servicio. Hasta la próxima.");
					System.out.println("Se ha enviado la contraseña al cliente");
				} else {
					mensajeSalida.writeUTF("No se generará ninguna contraseña. Hasta la próxima.");
					System.out.println("El cliente no desea generar una contraseña");
				}
				
				// Cierra la conexión con el cliente
				System.out.println("Sesión terminada con el cliente.");
				socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}
