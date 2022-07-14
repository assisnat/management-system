package controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import atividade.*;
import unidade.*;
import projeto.*;
import projeto.command.*;
import usuario.*;
import leitor.*;
import usuario.colaborador.*;

public class Controlador {
    public void menuPrint(Admin admin) {
        System.out.println("Bem vindo " + admin.getNome() + "!\n");

        System.out.println("Escolha uma das opções abaixo:\n\n"
                         + "Digite (1) para adicionar um Colaborador\n"
                         + "Digite (2) para criar um Projeto\n"
                         + "Digite (3) para alocar um colaborador a um Projeto\n"
                         + "Digite (4) para alterar o status de um Projeto\n"
                         + "Digite (5) para adicionar uma Atividade\n"
                         + "Digite (6) para fazer a consulta de um Colaborador\n"
                         + "Digite (7) para fazer a consulta de um Projeto\n"
                         + "Digite (8) para gerar o relatório da Unidade\n"
                         + "Digite (9) para listar todos os Colaboradores\n"
                         + "Digite (0) para Sair!\n");
    }

    public Admin cadastroAdmin(Scanner reader, Leitor leitor) {
        System.out.println("Bem vindo admin!\n");

        String nome = leitor.stringReader(reader, "Digite o seu nome: ");
        String email = leitor.regexValidatorReader(reader, "Digite o seu e-mail: ", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        String login = leitor.stringReader(reader, "Digite o seu login: ");
        String senha = leitor.stringReader(reader, "Digite a sua senha: ");

        return new Admin(nome, email, login, senha);
    }

    public UnidadeAcademica cadastroUnidade(Scanner reader, Admin admin, Leitor leitor) {
        System.out.println("Bem vindo " + admin.getNome() + "!\n");

        String nome = leitor.stringReader(reader, "Digite o nome da Unidade Acadêmica: ");

        return new UnidadeAcademica(nome, admin);
    }

    public void mostrarUnidade(UnidadeAcademica unidade) {
        System.out.println("Relatório de atividades da Unidade Acadêmica:\n");

        System.out.println("Número de colaboradores: " + unidade.getColaboradores().size());
        System.out.println("Número de projeto em elaboração: " + this.getProjetosByStatus("Em elaboração", unidade));
        System.out.println("Número de projeto em andamento: " + this.getProjetosByStatus("Em andamento", unidade));
        System.out.println("Número de projeto concluídos: " + this.getProjetosByStatus("Concluído", unidade));
        System.out.println("Número total de projeto: " + unidade.getProjetos().size());
        System.out.println("Número total de atividades: " + this.getAllAtividades(unidade).size());
    }

    public Colaborador criarColaborador(Scanner reader, UnidadeAcademica unidade, Leitor leitor) {
        System.out.println("Criação de Colaborador!\n");

        String nome = leitor.stringReader(reader, "Digite o nome do Colaborador: ");

        if (unidade.procurarColaborador(nome) == null) {
            String email = leitor.regexValidatorReader(reader, "Digite o e-mail do Colaborador: ", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            System.out.println("Escolha um tipo de Colaborador:\n");

            System.out.println("Digite (1) para o tipo Aluno de Graduação\n"
                             + "Digite (2) para o tipo Aluno de Mestrado\n"
                             + "Digite (3) para o tipo Aluno de Doutorado\n"
                             + "Digite (4) para o tipo Professor\n"
                             + "Digite (5) para o tipo Pesquisador\n"
                             + "Digite (6) para o tipo Técnico\n");

            int tipo = leitor.optionReader(reader, "Colaborador tipo: ", 1, 7);

            Colaborador novoColaborador;

            if (tipo == 4) {
                novoColaborador = new Professor(nome, email);
            } else if (tipo == 5) {
                novoColaborador = new Pesquisador(nome, email);
            } else if (tipo == 6) {
                novoColaborador = new Tecnico(nome, email); 
            } else {
                novoColaborador = new Aluno(nome, email, tipo - 1);
            }

            System.out.println("\nColaborador Criado!");

            return novoColaborador;
        } else {
            System.out.println("\nJá existe um colaborador com este nome!");

            return null;
        }
    }

    public Colaborador buscarColaborador(Scanner reader, UnidadeAcademica unidade, Leitor leitor) {
        System.out.println("Consulta de Colaborador!\n");

        String nome = leitor.stringReader(reader, "Digite o nome do Colaborador: ");

        Colaborador colaborador = unidade.procurarColaborador(nome);

        if (colaborador != null) {
            return colaborador;
        } else {
            System.out.println("\nNão existe um colaborador com este nome!");

            return null;
        }
    }

    public void alocarColaborador(Scanner reader, UnidadeAcademica unidade, Leitor leitor) {
        System.out.println("Alocação de Colaborador!\n");

        String titulo = leitor.stringReader(reader, "Digite o título do projeto: ");

        Projeto projeto = unidade.procurarProjeto(titulo);

        if (projeto != null) {
            if (projeto.getStatus().equals("Em processo de criação")) {
                String nome = leitor.stringReader(reader, "Digite o nome do Colaborador: ");

                Colaborador colaborador = unidade.procurarColaborador(nome);

                if (colaborador != null) {
                    addColaborador(projeto, colaborador);
                } else {
                    System.out.println("\nNão existe um colaborador com este nome!");
                }
            } else {
                System.out.println("\nO projeto não está 'Em elaboração' (A alocação deve ser permitida apenas quando o projeto estiver 'Em elaboração')");
            }
        } else {
            System.out.println("\nNão existe um projeto com este título!");
        }
    }

    /* Colaborador Funções */

    public void addColaborador(Projeto projeto, Colaborador colaborador) {
        projeto.addParticipante(colaborador);
        colaborador.addProjeto(projeto);

        System.out.println("\nColaborador alocado!");
    }

    public void mostrarColaboradores(UnidadeAcademica unidade) {
        System.out.println("Lista de Colaboradores:\n");

        ArrayList<Colaborador> colaboradores = unidade.getColaboradores();

        if (!colaboradores.isEmpty()) {
            for (int i = 0; i < colaboradores.size(); i++) {
                Colaborador colaborador = colaboradores.get(i);

                String tipo = "Colaborador";

                if (colaborador instanceof Aluno) {
                    Aluno aluno = (Aluno)colaborador;

                    tipo = aluno.getTipo();
                } else if (colaborador instanceof Pesquisador) {
                    tipo = "Pesquisador";
                } else if (colaborador instanceof Professor) {
                    tipo = "Professor";
                }
                
                System.out.println("    [" + (i + 1) + "] Nome: " + colaborador.getNome() + ", E-mail: " + colaborador.getEmail() + ", Tipo de colaborador: " + tipo);
            }
        } else {
            System.out.println("Sem colaboradores!");
        }
    }

    public Projeto buscarProjeto(Scanner reader, UnidadeAcademica unidade, Leitor leitor) {
        System.out.println("Consulta de Projeto!\n");

        String titulo = leitor.stringReader(reader, "Digite o título do Projeto: ");

        Projeto projeto = unidade.procurarProjeto(titulo);

        if (projeto != null) {
            return projeto;
        } else {
            System.out.println("\nNão existe um projeto com este título!");

            return null;
        }
    }

    public Projeto criarProjeto(Scanner reader, UnidadeAcademica unidade, Leitor leitor) {
        System.out.println("Criação de Projeto!\n");

        String identificacao = leitor.stringReader(reader, "Digite o título do Projeto: ");

        if (unidade.procurarProjeto(identificacao) == null) {
            LocalDate dataInicio = leitor.dateReader(reader, "Digite a Data de Início do Projeto (Formato: dd/MM/yyyy): ");
            LocalDate dataTermino = leitor.dateReader(reader, "Digite a Data de Termino do Projeto (Formato: dd/MM/yyyy): ");
            String descricao = leitor.stringReader(reader, "Digite a descrição do Projeto: ");
            boolean professorCreate = true;
            Colaborador professor = null;

            while (professorCreate) {
                String nomeProfessor = leitor.stringReader(reader, "Informe o nome do professor (Você deve alocar ao menos um professor ao novo projeto): ");

                Colaborador professorBusca = unidade.procurarColaborador(nomeProfessor);

                if (professorBusca != null) {
                    if (professorBusca instanceof Professor) {
                        boolean addOption = leitor.stringBoolReader(reader, "\nJá existe um professor cadastrado com esse nome, deseja adicioná-lo?");

                        if (addOption) {
                            professor = professorBusca;

                            professorCreate = false;
                        } else {
                            System.out.println("");
                        }
                    } else {
                        System.out.print("\nJá existe um colaborador com este nome (informe outro nome)\n\n");
                    }
                } else {
                    String emailProfessor = leitor.regexValidatorReader(reader, "Informe o e-mail do professor: ", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

                    professor = new Professor(nomeProfessor, emailProfessor);

                    unidade.addColaborador(professor);

                    professorCreate = false;
                }
            }

            Projeto novoProjeto = new Projeto(identificacao, descricao, dataInicio, dataTermino, professor);

            professor.addProjeto(novoProjeto);

            System.out.println("\nProjeto Criado!");

            return novoProjeto;
        } else {
            System.out.println("\nJá existe um projeto com este título!");

            return null;
        }
    }

    public void alterarStatus(Scanner reader, UnidadeAcademica unidade, Leitor leitor) {
        Invoker invokerOperation = new Invoker();

        System.out.println("Alterador de status!\n");

        String titulo = leitor.stringReader(reader, "Digite o título do Projeto: ");

        Projeto projeto = unidade.procurarProjeto(titulo);

        if (projeto != null) {
            if (projeto.getStatus().equals("Em processo de criação")) {
                boolean statusOption = leitor.stringBoolReader(reader, "\nVocê deseja alterar o status 'Em processo de criação' para 'Em andamento'?");

                if (statusOption) {
                    if (invokerOperation.executeOperation(new AlterarInicio(projeto))) {
                        System.out.println("\nStatus alterado com sucesso!");
                    } else {
                        System.out.println("\nStatus não alterado!\n\nOs seguintes 'alunos de gradução' estão em dois projetos com o status 'Em andamento': ");
                        ArrayList<Colaborador> colaboradores = projeto.getParticipantes();
                        ArrayList<Aluno> alunosRemover = new ArrayList<Aluno>();

                        for (int i = 0; i < colaboradores.size(); i++) {
                            if (colaboradores.get(i) instanceof Aluno) {
                                Aluno aluno = (Aluno)colaboradores.get(i);
            
                                if (aluno.getTipo().equals("Aluno de Graduação") && aluno.getProjetosByStatus("Em andamento").size() == 2) {
                                    System.out.println("    [" + (i + 1) + "] Nome: " + aluno.getNome() + ", E-mail: " + aluno.getEmail());
                                    alunosRemover.add(aluno);
                                }
                            }
                        }

                        boolean deleteOption = leitor.stringBoolReader(reader, "\nDeseja remover estes alunos do projeto?");

                        if (deleteOption) {
                            for (int i = 0; i < alunosRemover.size(); i++) {
                                alunosRemover.get(i).removeProjeto(projeto);
                                projeto.removeParticipante(alunosRemover.get(i));
                            }

                            System.out.println("\nTente alterar novamente!");
                        }
                    }
                } else {
                    System.out.println("\nStatus não alterado!");
                }
            } else if (projeto.getStatus().equals("Em andamento")) {
                boolean statusOption = leitor.stringBoolReader(reader, "\nVocê deseja alterar o status 'Em andamento' para 'Concluído'?");

                if (statusOption) {
                    if (invokerOperation.executeOperation(new AlterarFinal(projeto))) {
                        System.out.println("\nStatus alterado com sucesso!");
                    } else {
                        System.out.println("\nStatus não alterado! (Não existem atividades associadas ao projeto)");
                    }
                }
            } else {
                System.out.println("\nO projeto já foi concluído!");
            }
        } else {
            System.out.println("\nNão existe um projeto com este título!");
        }
    }

    /* Projeto Funções */

    public int getProjetosByStatus(String status, UnidadeAcademica unidade) {
        int counter = 0;

        ArrayList<Projeto> projetos = unidade.getProjetos();

        for (int i = 0; i < projetos.size(); i++) {
            if (projetos.get(i).getStatus().equals(status)) {
                counter++;
            }
        }

        return counter;
    }

    public void mostrarInformacoesProjeto(Projeto projeto) {
        System.out.print("\n" + projeto);

        ArrayList<Atividade> Atividades = projeto.getAtividades();

        if (!Atividades.isEmpty()) {
            System.out.println("\n\nProdução Acadêmica:");

            for (int i = 0; i < Atividades.size(); i++) {
                System.out.println("\n    [" + (i + 1) + "] " + Atividades.get(i).toString().replace("\n", "\n    "));
            }
        } else {
            System.out.println("");
        }
    }

    public void addAtividade(Scanner reader, UnidadeAcademica unidade, Leitor leitor) {
        if (!unidade.getColaboradores().isEmpty()) {
            Projeto projeto = null;

            System.out.println("Adicionar uma Atividade:\n");

            String identificacao1 = leitor.stringReader(reader, "Digite o título da atividade: ");
            String descricao1 = leitor.stringReader(reader, "Digite a descrição da atividade: ");
            LocalDate dataInicio1 = leitor.dateReader(reader, "Digite a Data de Início da atividade (Formato: dd/MM/yyyy): ");
            LocalDate dataTermino1 = leitor.dateReader(reader, "Digite a Data de Termino da atividade (Formato: dd/MM/yyyy): ");

            boolean optProjeto = leitor.stringBoolReader(reader, "A publicação tem algum projeto associado?");

            if (optProjeto) {
                boolean loop = true;

                while (loop) {
                    String projetoTitulo = leitor.stringReader(reader, "Digite o título do Projeto (Para cancelar digite 'sair'): ");

                    if (!projetoTitulo.equalsIgnoreCase("sair")) {
                        projeto = unidade.procurarProjeto(projetoTitulo);

                        if (projeto != null) {
                            if (projeto.getStatus().equals("Em andamento")) {
                                loop = false;
                                
                                System.out.println("\nProjeto associado!");
                            } else {
                                System.out.println("\nO Projeto não está 'Em andamento'!\n");
                            }
                        } else {
                            System.out.println("\nProjeto não encontrado!\n");
                        }
                    } else {
                        loop = false;

                        projeto = null;
                    }
                }
            }

            System.out.println("\nSeleção dos autores da Publicação: \n"
                             + "\nColaboradores:");

            ArrayList<Colaborador> colaboradores = unidade.getColaboradores();

            for (int i = 0; i < colaboradores.size(); i++) {
                System.out.println("    [" + (i + 1) + "] Nome: " + colaboradores.get(i).getNome() + ", E-mail: " + colaboradores.get(i).getEmail());
            }

            System.out.println("");
            String list = leitor.stringReader(reader, "Digite o número de no mínimo um colaborador para associá-lo a Publicação (Formato: 1, 2, 3, ...): ");

            String[] addColaboradores = list.replaceAll("\\s+","").split(",");

            ArrayList<Colaborador> autores = new ArrayList<Colaborador>();

            for (int i = 0; i < addColaboradores.length; i++) {
                if (leitor.regexValidator(addColaboradores[i], "^([1-9][0-9]{0,8})$")) {
                    int position = Integer.parseInt(addColaboradores[i]) - 1;

                    if (position >= 0 && position < colaboradores.size()) {
                        Colaborador colaborador = colaboradores.get(position);

                        autores.add(colaborador);
                    }
                }
            }

            Atividade novaAtividade;
            
            if (!autores.isEmpty()) {
                if (projeto == null) {
                    novaAtividade = new Atividade(identificacao1, descricao1, dataInicio1, dataTermino1);

                    for (int i = 0; i < autores.size(); i++) {
                        autores.get(i).addAtividade(novaAtividade);
                    }
                } else {
                    novaAtividade = new Atividade(identificacao1, descricao1, dataInicio1, dataTermino1, projeto);

                    for (int i = 0; i < autores.size(); i++) {
                        autores.get(i).addAtividade(novaAtividade);
                    }

                    projeto.addAtividade(novaAtividade);
                }

                System.out.println("\nPublicação Adicionada!");
            } else {
                System.out.println("\nPublicação não adicionada! (Sem autores)");
            }
        } else {
            System.out.println("Adicione no mínimo um colaborador para adicionar uma Publicação!");
        }
    }

    /* Publicação Funções */

    public ArrayList<Atividade> getAllAtividades(UnidadeAcademica unidade) {
        ArrayList<Colaborador> colaboradores = unidade.getColaboradores();
        ArrayList<Atividade> AtividadesResultado = new ArrayList<Atividade>();

        for (int i = 0; i < colaboradores.size(); i++) {
            ArrayList<Atividade> AtividadesColaborador = colaboradores.get(i).getAtividades();

            for (int j = 0; j < AtividadesColaborador.size(); j++) {
                if (!AtividadesResultado.contains(AtividadesColaborador.get(j))) {
                    AtividadesResultado.add(AtividadesColaborador.get(j));
                }
            }
        }

        return AtividadesResultado;
    }
}
