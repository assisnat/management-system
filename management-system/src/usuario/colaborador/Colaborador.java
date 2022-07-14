package usuario.colaborador;

import usuario.*;
import projeto.*;
import atividade.*;
import java.util.ArrayList;

public class Colaborador extends Usuario {
    private ArrayList<Atividade> atividades;
    private ArrayList<Projeto> projetos;

    public Colaborador(String nome, String email) {
        this(nome, email, new ArrayList<Atividade>(), new ArrayList<Projeto>());
    }

    public Colaborador(String nome, String email, ArrayList<Atividade> atividades, ArrayList<Projeto> projetos) {
        super(nome, email);
        this.atividades = atividades;
        this.projetos = projetos;
    }

    public ArrayList<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(ArrayList<Atividade> atividades) {
        this.atividades = atividades;
    }

    public void addAtividade(Atividade atividade) {
            this.atividades.add(atividade);
    }


    public ArrayList<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(ArrayList<Projeto> projetos) {
        this.projetos = projetos;
    }

    public void addProjeto(Projeto projeto) {
        if (!this.projetos.contains(projeto)) {
            for (int i = 0; i < this.projetos.size(); i++) {
                if (this.projetos.get(i).getDataTermino().compareTo(projeto.getDataTermino()) < 0) {
                    this.projetos.add(i, projeto);
                    return;
                }
            }

            this.projetos.add(projeto);
        }
    }

    public void removeProjeto(Projeto projeto) {
        if (this.projetos.contains(projeto)) {
            this.projetos.remove(projeto);
        }
    }

    public ArrayList<Projeto> getProjetosByStatus(String status) {
        ArrayList<Projeto> projetosResult = new ArrayList<Projeto>();

        for (int i = 0; i < this.projetos.size(); i++) {
            if (this.projetos.get(i).getStatus().equals(status)) {
                projetosResult.add(this.projetos.get(i));
            }
        }

        return projetosResult;
    }
}
