package mikron.classeconectada.User;

public class Supervisor extends User {

	private int id;

	private String Nome;

	private String CPF;

	private String Senha;

	private String Email;

	public Supervisor(int id, String senha, String CPF, String nome, String email) {
		super(id, senha, CPF, nome, email);
	}


	public int getId() {
		return id;
	}

	public String getNome() {
		return Nome;
	}

	public String getCPF() {
		return CPF;
	}

	public String getSenha() {
		return Senha;
	}

	public String getEmail() {
		return Email;
	}
}
