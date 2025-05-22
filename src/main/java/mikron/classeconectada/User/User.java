package mikron.classeconectada.User;

public abstract class User {

	private int id;

	private String Nome;

	private String CPF;

	private String Email;

	private String Senha;


	public User(int id, String nome) {
		this.id = id;
		Nome = nome;
	}

	public User(int id, String email, String senha, String CPF, String nome) {
		this.id = id;
		Email = email;
		Senha = senha;
		this.CPF = CPF;
		Nome = nome;
	}

	public void visualizacaoDePerfil() {
		System.out.println("Nome: " + Nome);
		System.out.println("CPF: " + CPF);
		System.out.println("Email: " + Email);
		System.out.println("Senha: " + Senha);
		System.out.println("ID: " + id);
	}

}
