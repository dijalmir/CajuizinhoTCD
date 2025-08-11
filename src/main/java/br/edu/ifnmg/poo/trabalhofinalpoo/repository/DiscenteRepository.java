package br.edu.ifnmg.poo.trabalhofinalpoo.repository;
import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Discente;

public class DiscenteRepository extends Repository<Discente> {

    @Override
    public String getJpqlFindAll() {
        return "SELECT a FROM Discente a";
    }

    @Override
    public String getJpqlFindById() {
        return "SELECT a FROM Discente a WHERE a.id = :id";
    }

    @Override
    public String getJpqlDeleteById() {
        return "DELETE FROM Discente a WHERE a.id = :id";
    }
}
