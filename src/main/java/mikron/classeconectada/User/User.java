package mikron.classeconectada.User;

public abstract class User {

	private int id;

	private String Nome;

	private String CPF;

	private String Senha;

	private String Email;

	public User(int id, String senha, String CPF, String nome, String email) {
		this.id = id;
		Senha = senha;
		this.CPF = CPF;
		Nome = nome;
		Email = email;
	}

	public void visualizacaoDePerfil() {
		System.out.println("Nome: " + Nome);
		System.out.println("CPF: " + CPF);
		System.out.println("Email: " + Email);
		System.out.println("Senha: " + Senha);
		System.out.println("ID: " + id);
	}

}
