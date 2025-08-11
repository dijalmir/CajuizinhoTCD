package br.edu.ifnmg.poo.trabalhofinalpoo.repository;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Professor;

public class ProfessorRepository extends Repository<Professor> {

    @Override
    public String getJpqlFindAll() {
        return "SELECT p FROM Professor p";
    }

    @Override
    public String getJpqlFindById() {
        return "SELECT p FROM Professor p WHERE p.id = :id";
    }

    @Override
    public String getJpqlDeleteById() {
        return "DELETE FROM Professor p WHERE p.id = :id";
    }
}
