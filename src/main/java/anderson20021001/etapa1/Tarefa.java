package anderson20021001.etapa1;

/**
 * Representa uma tarefa no sistema.
 * Classe usada para serialização/desserialização JSON e armazenamento em memória.
 */
public class Tarefa {

    private int id;
    private String descricao;
    private boolean concluida;

    /**
     * Construtor padrão vazio necessário para frameworks (ex: Javalin).
     */
    public Tarefa() {}

    /**
     * Construtor para facilitar criação manual do objeto.
     *
     * @param id        identificador da tarefa
     * @param descricao descrição da tarefa
     * @param concluida status da tarefa (concluída ou não)
     */
    public Tarefa(int id, String descricao, boolean concluida) {
        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", concluida=" + concluida +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tarefa)) return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id &&
                concluida == tarefa.concluida &&
                descricao.equals(tarefa.descricao);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (concluida ? 1 : 0);
        return result;
    }
}
