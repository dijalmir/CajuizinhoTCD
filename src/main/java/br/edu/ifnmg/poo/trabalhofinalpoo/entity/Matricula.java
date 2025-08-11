package br.edu.ifnmg.poo.trabalhofinalpoo.entity;

import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProjectEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matricula")
public class Matricula extends ProjectEntity {

    private LocalDate dataMatricula;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idDiscente", nullable = false)
    private Discente discente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTurma", nullable = false)
    private Turma turma;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    public Matricula() {}

    public Matricula(Discente discente, Turma turma) {
        this.discente = discente;
        this.turma = turma;
        this.dataMatricula = LocalDate.now();
        this.status = "Cursando";
    }

    public void addAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
        avaliacao.setMatricula(this);
    }

    @Override
    public String toString() {
        return "Matricula{" + "id=" + getId() +
                ", status='" + status + '\'' +
                ", discente=" + discente.getNome() +
                ", turma=" + turma.getDisciplina().getNome() + " (" + turma.getPeriodo() + ")" +
                '}';
    }

    //<editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Discente getDiscente() { return discente; }
    public void setDiscente(Discente discente) { this.discente = discente; }
    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<Avaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }
    //</editor-fold>
}