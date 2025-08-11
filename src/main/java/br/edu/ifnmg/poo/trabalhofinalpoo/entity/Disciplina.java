package br.edu.ifnmg.poo.trabalhofinalpoo.entity;

import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProjectEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "disciplina")
public class Disciplina extends ProjectEntity {

    @Column(length = 150, nullable = false, unique = true)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String ementa;

    public Disciplina() {}

    public Disciplina(String nome, String ementa) {
        this.nome = nome;
        this.ementa = ementa;
    }

    @Override
    public String toString() {
        return "Disciplina{" + "id=" + getId() + ", nome='" + nome + '\'' + '}';
    }

    //<editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmenta() { return ementa; }
    public void setEmenta(String ementa) { this.ementa = ementa; }
    //</editor-fold>
}