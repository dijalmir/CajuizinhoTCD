package br.edu.ifnmg.poo.trabalhofinalpoo.repository;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Avaliacao;

public class AvaliacaoRepository extends Repository<Avaliacao> {

    @Override
    public String getJpqlFindAll() {
        return "SELECT a FROM Avaliacao a";
    }

    @Override
    public String getJpqlFindById() {
        return "SELECT a FROM Avaliacao a WHERE a.id = :id";
    }

    @Override
    public String getJpqlDeleteById() {
        return "DELETE FROM Avaliacao a WHERE a.id = :id";
    }
}
