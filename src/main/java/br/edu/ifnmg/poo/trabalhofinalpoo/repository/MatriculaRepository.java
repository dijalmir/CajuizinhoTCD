package br.edu.ifnmg.poo.trabalhofinalpoo.repository;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Matricula;

public class MatriculaRepository extends Repository<Matricula> {

    @Override
    public String getJpqlFindAll() {
        return "SELECT m FROM Matricula m";
    }

    @Override
    public String getJpqlFindById() {
        return "SELECT m FROM Matricula m WHERE m.id = :id";
    }

    @Override
    public String getJpqlDeleteById() {
        return "DELETE FROM Matricula m WHERE m.id = :id";
    }

}
