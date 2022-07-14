package projeto.command;

public class Invoker {
    public boolean executeOperation(Operation operation){
        return operation.execute();		
    }
}
