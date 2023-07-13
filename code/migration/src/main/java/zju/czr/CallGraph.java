package zju.czr;


public class CallGraph {
    public String fileName;
    public String functionName;
    public String[] paraName;

    CallGraph(String fileName, String functionName, String[] paraName){
        this.fileName = fileName;
        this.functionName = functionName;
        this.paraName = paraName;
    }
}