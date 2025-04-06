package servidor;


public class ServicioPass {
	private RequisitosPass requisitosPass;
	private String minusculas;
	private String mayusculas;
	private String digitos;
	private String carEspeciales;
	
	public ServicioPass() {
		
	}
	
	public String generaPass() {
		return "";
	}
	
	public int longitudPass() {
		return 0;
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
	
}
