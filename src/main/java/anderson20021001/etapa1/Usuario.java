// Declara o pacote onde a classe está localizada
package anderson20021001.etapa1;

// Classe que representa um objeto Usuario
public class Usuario {

    // Atributos privados que representam as propriedades do usuário
    private String nome;
    private String email;
    private int idade;

    // Construtor vazio (necessário para frameworks como Javalin fazerem a desserialização de JSON)
    public Usuario() {
    }

    // Construtor com parâmetros para facilitar a criação do objeto manualmente
    public Usuario(String nome, String email, int idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    // Getter (leitura) do nome
    public String getNome() {
        return nome;
    }

    // Setter (modificação) do nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter do email
    public String getEmail() {
        return email;
    }

    // Setter do email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter da idade
    public int getIdade() {
        return idade;
    }

    // Setter da idade
    public void setIdade(int idade) {
        this.idade = idade;
    }
}
