package unidade;

import java.util.ArrayList;

import projeto.*;
import usuario.colaborador.*;
import usuario.*;

public class UnidadeAcademica {
    private String nome;
    private Admin administrador;
    private ArrayList<Colaborador> colaboradores = new ArrayList<Colaborador>();
    private ArrayList<Projeto> projetos = new ArrayList<Projeto>();

    public UnidadeAcademica(String nome, Admin administrador) {
        this.nome = nome;
        this.administrador = administrador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(ArrayList<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public void addColaborador(Colaborador novoColaborador) {
        this.colaboradores.add(novoColaborador);
    }

    public void removeColaborador(Colaborador colaborador) {
        this.colaboradores.remove(colaborador);
    }

    public Colaborador procurarColaborador(String nome) {
        Colaborador colaborador;

        for (int i = 0; i < this.colaboradores.size(); i++) {
            colaborador = this.colaboradores.get(i);

            if (colaborador.getNome().equals(nome)) {
                return colaborador;
            }
        }

        return null;
    }

    public ArrayList<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(ArrayList<Projeto> projetos) {
        this.projetos = projetos;
    }

    public void addProjeto(Projeto novoProjeto) {
        if (!this.projetos.contains(novoProjeto)) {
            for (int i = 0; i < this.projetos.size(); i++) {
                if (this.projetos.get(i).getDataTermino().compareTo(novoProjeto.getDataTermino()) < 0) {
                    this.projetos.add(i, novoProjeto);
                    return;
                }
            }

            this.projetos.add(novoProjeto);
        }
    }

    public void removeProjeto(Projeto projeto) {
        if (this.projetos.contains(projeto)) {
            this.projetos.remove(projeto);
        }
    }

    public Projeto procurarProjeto(String titulo) {
        Projeto projeto;

        for (int i = 0; i < this.projetos.size(); i++) {
            projeto = this.projetos.get(i);

            if (projeto.getIdentificacao().equals(titulo)) {
                return projeto;
            }
        }

        return null;
    }

    public Admin getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Admin administrador) {
        this.administrador = administrador;
    }
}
