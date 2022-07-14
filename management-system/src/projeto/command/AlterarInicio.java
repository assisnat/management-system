package projeto.command;

import projeto.Projeto;

public class AlterarInicio implements Operation {
    private Projeto projeto;

    public AlterarInicio(Projeto projeto) {
        this.projeto = projeto;
    }

    public boolean execute(){
        return projeto.alterarStatusInicial();
    }
}
