package br.edu.ifnmg.poo.trabalhofinalpoo.entity;

import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProjectEntity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "turma")
public class Turma extends ProjectEntity {

    private String periodo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idDisciplina", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProfessor", nullable = false)
    private Professor professor;
    
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Matricula> matriculas = new HashSet<>();

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Aula> aulas = new HashSet<>();

    public Turma() {}

    public Turma(String periodo, Disciplina disciplina, Professor professor) {
        this.periodo = periodo;
        this.disciplina = disciplina;
        this.professor = professor;
    }

    
    public void addMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setTurma(this);
    }

    public void addAula(Aula aula) {
        this.aulas.add(aula);
        aula.setTurma(this);
    }
    
    @Override
    public String toString() {
        return "Turma{" + "id=" + getId() + 
               ", disciplina=" + disciplina.getNome() + 
               ", periodo='" + periodo + '\'' +
               ", professor=" + professor.getNome() + '}';
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }
    public Set<Matricula> getMatriculas() { return matriculas; }
    public void setMatriculas(Set<Matricula> matriculas) { this.matriculas = matriculas; }
    public Set<Aula> getAulas() { return aulas; }
    public void setAulas(Set<Aula> aulas) { this.aulas = aulas; }
    //</editor-fold>
}