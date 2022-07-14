package usuario.colaborador;

import java.util.ArrayList;
import atividade.*;
import projeto.*;

public class Aluno extends Colaborador {
    private int tipo;
    String[] arrayTipo = {"Aluno de Graduação", "Aluno de Mestrado", "Aluno de Doutorado"};

    public Aluno(String nome, String email, int tipo) {
        this(nome, email, tipo, new ArrayList<Atividade>(), new ArrayList<Projeto>());
    }

    public Aluno(String nome, String email, int tipo, ArrayList<Atividade> atividades, ArrayList<Projeto> projetos) {
        super(nome, email, atividades, projetos);
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.arrayTipo[this.tipo];
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
