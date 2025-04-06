package servidor;

import java.util.Random;

public class ServicioPass {
	private RequisitosPass requisitosPass;	
	
	public ServicioPass(int numMinusculas, int numMayusculas, int numDigitos, int numCarEspeciales) {
		requisitosPass = new RequisitosPass();
		requisitosPass.setNumMinusculas(numMinusculas);
		requisitosPass.setNumMayusculas(numMayusculas);
		requisitosPass.setNumDigitos(numDigitos);
		requisitosPass.setNumCaractEspeciales(numCarEspeciales);
	}
	
	public String generaPass() {
		StringBuilder stringbuilder = new StringBuilder();
		
		stringbuilder.append(desorderString(getMinusculasString()).substring(0, requisitosPass.getNumMinusculas()));
		stringbuilder.append(desorderString(getMayusculasString()).substring(0, requisitosPass.getNumMayusculas()));
		stringbuilder.append(desorderString(getDigitosString()).substring(0, requisitosPass.getNumDigitos()));
		stringbuilder.append(desorderString(getCarEspecialesString()).substring(0,requisitosPass.getNumCaractEspeciales()));
						
		return desorderString(stringbuilder.toString());
	}
	
	public int longitudPass() {
		int longitud = 0;
		longitud += requisitosPass.getNumMinusculas();
		longitud += requisitosPass.getNumMayusculas();
		longitud += requisitosPass.getNumDigitos();
		longitud += requisitosPass.getNumCaractEspeciales();
		
		return longitud;
	}
	
	private String getMinusculasString() {		
		int a = 'a';
		int z = 'z';		
		String minusculasString = "";
		
		for (int i = a; i <= z; i++) {
			minusculasString += (char) i;
		}
		
		return minusculasString;
	}
	
	private String getMayusculasString() {		
		int a = 'A';
		int z = 'Z';
		
		String mayusculasString = "";
		
		for (int i = a; i <= z; i++) {
			mayusculasString += (char) i;
		}
		
		return mayusculasString;
	}
	
	private String getDigitosString() {		
		int a = '0';
		int z = '9';
		
		String digitosString = "";
		
		for (int i = a; i <= z; i++) {
			digitosString += (char) i;
		}
		
		return digitosString;
	}
	
	private String getCarEspecialesString() {
		return "!@#$%^&*()_-+=.:?";
	}
	
	private String desorderString(String string) {
		String desorderedString = "";
		Random rand = new Random();
		StringBuilder stringBuilder = new StringBuilder(string);
		
		while (stringBuilder.length() > 0) {
			int randomIndex = rand.nextInt(stringBuilder.length());
			desorderedString += stringBuilder.charAt(randomIndex);
			stringBuilder.deleteCharAt(randomIndex);
		}
		
		return desorderedString;
	}
}
