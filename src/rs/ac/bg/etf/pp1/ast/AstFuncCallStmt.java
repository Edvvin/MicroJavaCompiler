// generated with ast extension for cup
// version 0.8
// 10/5/2021 0:46:5


package rs.ac.bg.etf.pp1.ast;

public class AstFuncCallStmt extends DesignatorStatement {

    private FuncDesig FuncDesig;
    private ActualPars ActualPars;

    public AstFuncCallStmt (FuncDesig FuncDesig, ActualPars ActualPars) {
        this.FuncDesig=FuncDesig;
        if(FuncDesig!=null) FuncDesig.setParent(this);
        this.ActualPars=ActualPars;
        if(ActualPars!=null) ActualPars.setParent(this);
    }

    public FuncDesig getFuncDesig() {
        return FuncDesig;
    }

    public void setFuncDesig(FuncDesig FuncDesig) {
        this.FuncDesig=FuncDesig;
    }

    public ActualPars getActualPars() {
        return ActualPars;
    }

    public void setActualPars(ActualPars ActualPars) {
        this.ActualPars=ActualPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FuncDesig!=null) FuncDesig.accept(visitor);
        if(ActualPars!=null) ActualPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncDesig!=null) FuncDesig.traverseTopDown(visitor);
        if(ActualPars!=null) ActualPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncDesig!=null) FuncDesig.traverseBottomUp(visitor);
        if(ActualPars!=null) ActualPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstFuncCallStmt(\n");

        if(FuncDesig!=null)
            buffer.append(FuncDesig.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualPars!=null)
            buffer.append(ActualPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstFuncCallStmt]");
        return buffer.toString();
    }
}
