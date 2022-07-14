package projeto.command;

import projeto.Projeto;

public class AlterarFinal implements Operation {
    private Projeto projeto;

    public AlterarFinal(Projeto projeto) {
        this.projeto = projeto;
    }

    public boolean execute(){
        return projeto.alterarStatusFinal();
    }
}
