import java.util.Scanner;
import java.io.IOException;

import controlador.*;
import unidade.*;
import projeto.*;
import usuario.*;
import leitor.*;
import usuario.colaborador.*;

public class Manager {
    public static void clearconsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void waitEnter(Scanner reader) {
        System.out.print("\nAperte Enter para continuar");

        reader.nextLine();
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Controlador controlador = new Controlador();
        Leitor leitor = new Leitor();
        boolean loop = true;

        clearconsole();
        Admin administrador = controlador.cadastroAdmin(reader, leitor);

        clearconsole();
        UnidadeAcademica unidade = controlador.cadastroUnidade(reader, administrador, leitor);

        while(loop) {
            clearconsole();
            controlador.menuPrint(administrador);

            int option = leitor.optionReader(reader, "Digite a opção: ", 0, 10);

            clearconsole();
            switch (option) {
                case 1:
                    Colaborador novoColaborador = controlador.criarColaborador(reader, unidade, leitor);

                    if (novoColaborador != null) {
                        unidade.addColaborador(novoColaborador);
                    }
                    break;
                case 2:
                    Projeto novoProjeto = controlador.criarProjeto(reader, unidade, leitor);

                    if (novoProjeto != null) {
                        unidade.addProjeto(novoProjeto);
                    }
                    break;
                case 3:
                    controlador.alocarColaborador(reader, unidade, leitor);
                    break;
                case 4:
                    controlador.alterarStatus(reader, unidade, leitor);
                    break;
                case 5:
                    controlador.addAtividade(reader, unidade, leitor);
                    break;
                case 6:
                    Colaborador colaborador = controlador.buscarColaborador(reader, unidade, leitor);

                    if (colaborador != null) {
                        System.out.print(controlador.colaboradorRelatorio(colaborador));
                    }

                    break;
                case 7:
                    Projeto projeto = controlador.buscarProjeto(reader, unidade, leitor);

                    if (projeto != null) {
                        controlador.mostrarInformacoesProjeto(projeto);
                    }

                    break;
                case 8:
                    controlador.mostrarUnidade(unidade);

                    break;
                case 9:
                    controlador.mostrarColaboradores(unidade);

                    break;
                case 0:
                    loop = false;

                    break;
                default:
                    loop = false;
            }

            waitEnter(reader);
        }

        reader.close();
    }
}
