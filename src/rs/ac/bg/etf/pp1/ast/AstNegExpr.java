// generated with ast extension for cup
// version 0.8
// 11/5/2021 0:35:3


package rs.ac.bg.etf.pp1.ast;

public class AstNegExpr extends Expr1 {

    private SumExpr SumExpr;

    public AstNegExpr (SumExpr SumExpr) {
        this.SumExpr=SumExpr;
        if(SumExpr!=null) SumExpr.setParent(this);
    }

    public SumExpr getSumExpr() {
        return SumExpr;
    }

    public void setSumExpr(SumExpr SumExpr) {
        this.SumExpr=SumExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SumExpr!=null) SumExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SumExpr!=null) SumExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SumExpr!=null) SumExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstNegExpr(\n");

        if(SumExpr!=null)
            buffer.append(SumExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstNegExpr]");
        return buffer.toString();
    }
}
