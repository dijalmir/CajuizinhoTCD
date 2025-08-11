package br.edu.ifnmg.poo.trabalhofinalpoo.repository;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Turma;

public class TurmaRepository extends Repository<Turma> {

    @Override
    public String getJpqlFindAll() {
        return "SELECT t FROM Turma t";
    }

    @Override
    public String getJpqlFindById() {
        return "SELECT t FROM Turma t WHERE t.id = :id";
    }

    @Override
    public String getJpqlDeleteById() {
        return "DELETE FROM Turma t WHERE t.id = :id";
    }
}
