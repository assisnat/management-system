package projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import atividade.*;
import usuario.colaborador.*;

public class Projeto {
    private String identificacao;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private int status = 0;
    private ArrayList<String> allStatus = new ArrayList<String>(Arrays.asList("Em processo de criação", "Iniciado", "Em andamento", "Concluído"));
    private ArrayList<Colaborador> participantes = new ArrayList<Colaborador>();
    private ArrayList<Atividade> atividades = new ArrayList<Atividade>();

    public Projeto(String identificacao, String descricao, LocalDate dataInicio, LocalDate dataTermino, Colaborador professor) {
        this.identificacao = identificacao;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;        

        this.participantes.add(professor);
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

    public String getStatus() {
        return this.allStatus.get(this.status);
    }

    public void setStatus(String status) {
        this.status = allStatus.indexOf(status);
    }

    public boolean alterarStatusInicial() {
        if (this.status == 0) {
            boolean alunoCheck = true;

            for (int i = 0; i < this.participantes.size(); i++) {
                if (this.participantes.get(i) instanceof Aluno) {
                    Aluno aluno = (Aluno)this.participantes.get(i);

                    if (aluno.getTipo().equals("Aluno de Graduação") && aluno.getProjetosByStatus("Em andamento").size() == 2) {
                        alunoCheck = false;
                    }
                }
            }

            if (alunoCheck) {
                this.status = 1;

                return true;
            }
        }

        return false;
    }

    public boolean alterarStatusFinal() {
        if (this.status == 1 && !this.atividades.isEmpty()) {
            this.status = 2;

            return true;
        }

        return false;
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

    public void addPublicacao(Atividade publicacao) {
        if (!this.atividades.contains(publicacao)) {
            this.atividades.add(publicacao);
        }
    }

    public void removeAtividade(Atividade publicacao) {
        if (this.atividades.contains(publicacao)) {
            this.atividades.remove(publicacao);
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "Nome do projeto: " + this.identificacao + "\n"
             + "Descrição: " + this.descricao + "\n"
             + "Data de Ínicio: " + this.dataInicio.format(formatter) + "\n"
             + "Data de Termino: " + this.dataTermino.format(formatter) + "\n"             
             + "Status: " + this.allStatus.get(this.status) + "\n"
             + "Participantes: " + stringParticipantes();
    }
}
