package usuario;

public class Admin extends Usuario {
    private String login;
    private String senha;

    public Admin(String nome, String email, String login, String senha) {
        super(nome, email);
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean checkLogin(String login, String senha) {
        return (login.equals(this.login) && senha.equals(this.senha)); 
    }
}
