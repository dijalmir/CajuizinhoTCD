package br.edu.ifnmg.poo.trabalhofinalpoo.repository;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Disciplina;

public class DisciplinaRepository extends Repository<Disciplina> {

    @Override
    public String getJpqlFindAll() {
        return "SELECT a FROM Disciplina a";
    }

    @Override
    public String getJpqlFindById() {
        return "SELECT a FROM Disciplina a WHERE a.id = :id";
    }

    @Override
    public String getJpqlDeleteById() {
        return "DELETE FROM Disciplina a WHERE a.id = :id";
    }
}
