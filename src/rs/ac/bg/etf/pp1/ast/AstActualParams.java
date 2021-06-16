// generated with ast extension for cup
// version 0.8
// 16/5/2021 15:11:18


package rs.ac.bg.etf.pp1.ast;

public class AstActualParams extends ActualPars {

    private ActualParsStart ActualParsStart;
    private ActualParamList ActualParamList;

    public AstActualParams (ActualParsStart ActualParsStart, ActualParamList ActualParamList) {
        this.ActualParsStart=ActualParsStart;
        if(ActualParsStart!=null) ActualParsStart.setParent(this);
        this.ActualParamList=ActualParamList;
        if(ActualParamList!=null) ActualParamList.setParent(this);
    }

    public ActualParsStart getActualParsStart() {
        return ActualParsStart;
    }

    public void setActualParsStart(ActualParsStart ActualParsStart) {
        this.ActualParsStart=ActualParsStart;
    }

    public ActualParamList getActualParamList() {
        return ActualParamList;
    }

    public void setActualParamList(ActualParamList ActualParamList) {
        this.ActualParamList=ActualParamList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActualParsStart!=null) ActualParsStart.accept(visitor);
        if(ActualParamList!=null) ActualParamList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParsStart!=null) ActualParsStart.traverseTopDown(visitor);
        if(ActualParamList!=null) ActualParamList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParsStart!=null) ActualParsStart.traverseBottomUp(visitor);
        if(ActualParamList!=null) ActualParamList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstActualParams(\n");

        if(ActualParsStart!=null)
            buffer.append(ActualParsStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualParamList!=null)
            buffer.append(ActualParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstActualParams]");
        return buffer.toString();
    }
}
