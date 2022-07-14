package usuario.colaborador;

import projeto.*;
import atividade.*;
import java.util.ArrayList;

public class Professor extends Colaborador {

    public Professor(String nome, String email) {
        this(nome, email, new ArrayList<Atividade>(), new ArrayList<Projeto>());
    }

    public Professor(String nome, String email, ArrayList<Atividade> publicacoes, ArrayList<Projeto> projetos) {
        super(nome, email, publicacoes, projetos);
    }
    
}
