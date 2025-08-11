package br.edu.ifnmg.poo.trabalhofinalpoo.entity;

import jakarta.persistence.*;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProjectEntity;

@Entity
@Table(name = "avaliacao")
public class Avaliacao extends ProjectEntity {

    private String descricao; // Ex: "Prova 1", "Trabalho Final"
    private Double nota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idMatricula", nullable = false)
    private Matricula matricula;

    // Getters, Setters e Construtores...
    public Avaliacao() {}

    public Avaliacao(String descricao, Double nota, Matricula matricula) {
        this.descricao = descricao;
        this.nota = nota;
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + getId() +
                ", descricao='" + descricao + '\'' +
                ", nota=" + nota +
                '}';
    }

    //<editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }
    public Matricula getMatricula() { return matricula; }
    public void setMatricula(Matricula matricula) { this.matricula = matricula; }
    //</editor-fold>
}