package anderson20021001.etapa1;

/**
 * Representa um usuário do sistema.
 * Classe usada para serialização/desserialização JSON e armazenamento em memória.
 */
public class Usuario {

    private String nome;
    private String email;
    private int idade;

    /**
     * Construtor padrão vazio necessário para frameworks (ex: Javalin).
     */
    public Usuario() {}

    /**
     * Construtor para facilitar criação manual do objeto.
     *
     * @param nome  nome do usuário
     * @param email email do usuário
     * @param idade idade do usuário
     */
    public Usuario(String nome, String email, int idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", idade=" + idade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return idade == usuario.idade &&
                nome.equals(usuario.nome) &&
                email.equals(usuario.email);
    }

    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + idade;
        return result;
    }
}
