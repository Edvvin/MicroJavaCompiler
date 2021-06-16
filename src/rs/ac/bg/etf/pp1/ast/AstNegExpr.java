// generated with ast extension for cup
// version 0.8
// 16/5/2021 15:11:18


package rs.ac.bg.etf.pp1.ast;

public class AstNegExpr extends Expr1 {

    private Minus Minus;
    private SumExpr SumExpr;

    public AstNegExpr (Minus Minus, SumExpr SumExpr) {
        this.Minus=Minus;
        if(Minus!=null) Minus.setParent(this);
        this.SumExpr=SumExpr;
        if(SumExpr!=null) SumExpr.setParent(this);
    }

    public Minus getMinus() {
        return Minus;
    }

    public void setMinus(Minus Minus) {
        this.Minus=Minus;
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
        if(Minus!=null) Minus.accept(visitor);
        if(SumExpr!=null) SumExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Minus!=null) Minus.traverseTopDown(visitor);
        if(SumExpr!=null) SumExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Minus!=null) Minus.traverseBottomUp(visitor);
        if(SumExpr!=null) SumExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstNegExpr(\n");

        if(Minus!=null)
            buffer.append(Minus.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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
