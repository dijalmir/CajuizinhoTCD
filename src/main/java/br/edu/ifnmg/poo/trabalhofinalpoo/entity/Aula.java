package br.edu.ifnmg.poo.trabalhofinalpoo.entity;

import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProjectEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aula")
public class Aula extends ProjectEntity {

    @Column(columnDefinition = "TEXT")
    private String conteudo;

    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTurma", nullable = false)
    private Turma turma;

    public Aula() {}

    public Aula(String conteudo, LocalDateTime dataHora, Turma turma) {
        this.conteudo = conteudo;
        this.dataHora = dataHora;
        this.turma = turma;
    }

    @Override
    public String toString() {
        return "Aula{" + "id=" + getId() + ", dataHora=" + dataHora +
                ", conteudo='" + conteudo + '\'' + '}';
    }

    //<editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
    //</editor-fold>
}