// generated with ast extension for cup
// version 0.8
// 16/2/2021 18:27:51


package rs.ac.bg.etf.pp1.ast;

public class AstActualParamsL extends ActualParamList {

    private Expr Expr;
    private ActualParamList ActualParamList;

    public AstActualParamsL (Expr Expr, ActualParamList ActualParamList) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.ActualParamList=ActualParamList;
        if(ActualParamList!=null) ActualParamList.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
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
        if(Expr!=null) Expr.accept(visitor);
        if(ActualParamList!=null) ActualParamList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(ActualParamList!=null) ActualParamList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(ActualParamList!=null) ActualParamList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstActualParamsL(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualParamList!=null)
            buffer.append(ActualParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstActualParamsL]");
        return buffer.toString();
    }
}
