package usuario.colaborador;

import projeto.*;
import java.util.ArrayList;
import atividade.*;

public class Pesquisador extends Colaborador {
    public Pesquisador(String nome, String email) {
        super(nome, email);
    }

    public Pesquisador(String nome, String email, ArrayList<Atividade> publicacoes, ArrayList<Projeto> projetos) {
        super(nome, email, publicacoes, projetos);
    }
}
