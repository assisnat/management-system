package atividade;

import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import usuario.colaborador.*;

public class Atividade {
    private String identificacao;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private ArrayList<Colaborador> participantes = new ArrayList<Colaborador>();
    private ArrayList<Atividade> atividades = new ArrayList<Atividade>();

    public Atividade(String identificacao, String descricao, LocalDate dataInicio, LocalDate dataTermino, Colaborador participantes) {
        this.identificacao = identificacao;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;        

        this.participantes.add(participantes);
    }

    public String getIdentificacao() {
        return this.identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return this.dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public ArrayList<Colaborador> getParticipantes() {
        return this.participantes;
    }

    public void setParticipantes(ArrayList<Colaborador> participantes) {
        this.participantes = participantes;
    }

    public String stringParticipantes() {
        StringBuilder bld = new StringBuilder("(");

        for (int i = 0; i < this.participantes.size(); i++) {
            bld.append(this.participantes.get(i).getNome());

            if (i == (this.participantes.size() - 1)) {
                bld.append(")");
            } else {
                bld.append(", ");
            }
        }

        return bld.toString();
    }

    public void addParticipante(Colaborador participante) {
        if (!this.participantes.contains(participante)) {
            this.participantes.add(participante);
        }
    }

    public void removeParticipante(Colaborador participante) {
        if (this.participantes.contains(participante)) {
            this.participantes.remove(participante);
        }
    }

    public ArrayList<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(ArrayList<Atividade> atividades) {
        this.atividades = atividades;
    }

    public void addAtividade(Atividade Atividade) {
        if (!this.atividades.contains(Atividade)) {
            this.atividades.add(Atividade);
        }
    }

    public void removeAtividade(Atividade Atividade) {
        if (this.atividades.contains(Atividade)) {
            this.atividades.remove(Atividade);
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "Nome do projeto: " + this.identificacao + "\n"
             + "Descrição: " + this.descricao + "\n"
             + "Data de Ínicio: " + this.dataInicio.format(formatter) + "\n"
             + "Data de Termino: " + this.dataTermino.format(formatter) + "\n"             
             + "Profissionais participantes: " + stringParticipantes();
    }

    public String toStringProjeto() {
        return null;
    }
}
