package br.edu.ifnmg.poo.trabalhofinalpoo.entity;

import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProjectEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "discente")
public class Discente
        extends ProjectEntity
        implements Serializable {

    @Column(length = 14, nullable = false, unique = true)
    private String cpf;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(name = "nascimento", columnDefinition = "DATE")
    private LocalDate nascimento;

    //<editor-fold defaultstate="collapsed" desc="Construtores">
    public Discente() {
    }

    public Discente(String nome, String cpf , LocalDate nascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.nascimento = nascimento;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "Discente{" +
               "id=" + getId() +
               ", nome='" + nome + '\'' + 
               ", cpf='" + cpf + '\'' +  
               ", nascimento=" + nascimento +
               '}';
    }

    //<editor-fold defaultstate="collapsed" desc="Equals/HashCode">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.getId());
        return hash;
    }
    //</editor-fold>
}