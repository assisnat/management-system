package usuario.colaborador;

import projeto.*;
import atividade.*;
import java.util.ArrayList;

public class Tecnico extends Colaborador {

    public Tecnico(String nome, String email) {
        this(nome, email, new ArrayList<Atividade>(), new ArrayList<Projeto>());
    }

    public Tecnico(String nome, String email, ArrayList<Atividade> publicacoes, ArrayList<Projeto> projetos) {
        super(nome, email, publicacoes, projetos);
    }
    
}
