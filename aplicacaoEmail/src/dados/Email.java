package dados;

public class Email {
	Usuario remetente;
	Usuario destinatario;
	String corpo;
	String data;
	String hora;
	int id;
	
	public Email() {
	}
	public Email(Usuario remetente, Usuario destinatario, String corpo, String data, String hora, int id) {
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.corpo = corpo;
		this.data = data;
		this.hora = hora;
		this.id = id;
	}
	public Usuario getRemetente() {
		return remetente;
	}
	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}
	public Usuario getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
		
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUnidadeData() {
		char []dia = data.toCharArray();
		int unidade = Character.getNumericValue(dia[9]);
		return unidade;
	}
	public int getUnidadeId() {
		int unidade = id%10;
		return unidade;
	}
		
	private String decodifica() {
		char []corpoEmail = corpo.toCharArray();
		int cifra = getUnidadeData() + getUnidadeId();
		String corpoNovo = "";
		for(int i = 0; i <corpo.length();i++) {
			if(corpoEmail[i]>=65 && corpoEmail[i]<=90) {
				char c = (char) (corpoEmail[i] - cifra);
				if(c < 65) {
					corpoNovo += (char) (corpoEmail[i] + (26 - cifra));
				}else {
					corpoNovo += (char) (corpoEmail[i] - cifra);
				}
			}else if(corpoEmail[i] >= 97 && corpoEmail[i] <= 122) {
				char c = (char) (corpoEmail[i] - cifra);
				if(c < 97) {
					corpoNovo += (char) (corpoEmail[i] + (26 - cifra));
				}else {
					corpoNovo += (char) (corpoEmail[i] - cifra);
				}
				
			}else {
				corpoNovo += (char) (corpoEmail[i]);
			}
		}
		
		return corpoNovo;
		
	}
	
	public boolean equals(Object o) {
		if(o instanceof Email) {
			Email e =(Email) o;
			if(this.id == e.getId()) {
				return true;
			}
		}
			return false;
	}

	
	public String toString() {
		return "ID: "+id+" Remetente: "+remetente+ " Destinatario: "+destinatario+" Data: "+data+" Hora: "+hora+"\n\n"+decodifica()+"\n";
	}

}
