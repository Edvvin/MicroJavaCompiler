// generated with ast extension for cup
// version 0.8
// 16/5/2021 15:11:18


package rs.ac.bg.etf.pp1.ast;

public class AstTerExpr extends Expr {

    private Expr1 Expr1;
    private TerQstmk TerQstmk;
    private Expr1 Expr11;
    private TerColon TerColon;
    private Expr1 Expr12;

    public AstTerExpr (Expr1 Expr1, TerQstmk TerQstmk, Expr1 Expr11, TerColon TerColon, Expr1 Expr12) {
        this.Expr1=Expr1;
        if(Expr1!=null) Expr1.setParent(this);
        this.TerQstmk=TerQstmk;
        if(TerQstmk!=null) TerQstmk.setParent(this);
        this.Expr11=Expr11;
        if(Expr11!=null) Expr11.setParent(this);
        this.TerColon=TerColon;
        if(TerColon!=null) TerColon.setParent(this);
        this.Expr12=Expr12;
        if(Expr12!=null) Expr12.setParent(this);
    }

    public Expr1 getExpr1() {
        return Expr1;
    }

    public void setExpr1(Expr1 Expr1) {
        this.Expr1=Expr1;
    }

    public TerQstmk getTerQstmk() {
        return TerQstmk;
    }

    public void setTerQstmk(TerQstmk TerQstmk) {
        this.TerQstmk=TerQstmk;
    }

    public Expr1 getExpr11() {
        return Expr11;
    }

    public void setExpr11(Expr1 Expr11) {
        this.Expr11=Expr11;
    }

    public TerColon getTerColon() {
        return TerColon;
    }

    public void setTerColon(TerColon TerColon) {
        this.TerColon=TerColon;
    }

    public Expr1 getExpr12() {
        return Expr12;
    }

    public void setExpr12(Expr1 Expr12) {
        this.Expr12=Expr12;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr1!=null) Expr1.accept(visitor);
        if(TerQstmk!=null) TerQstmk.accept(visitor);
        if(Expr11!=null) Expr11.accept(visitor);
        if(TerColon!=null) TerColon.accept(visitor);
        if(Expr12!=null) Expr12.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr1!=null) Expr1.traverseTopDown(visitor);
        if(TerQstmk!=null) TerQstmk.traverseTopDown(visitor);
        if(Expr11!=null) Expr11.traverseTopDown(visitor);
        if(TerColon!=null) TerColon.traverseTopDown(visitor);
        if(Expr12!=null) Expr12.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr1!=null) Expr1.traverseBottomUp(visitor);
        if(TerQstmk!=null) TerQstmk.traverseBottomUp(visitor);
        if(Expr11!=null) Expr11.traverseBottomUp(visitor);
        if(TerColon!=null) TerColon.traverseBottomUp(visitor);
        if(Expr12!=null) Expr12.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstTerExpr(\n");

        if(Expr1!=null)
            buffer.append(Expr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TerQstmk!=null)
            buffer.append(TerQstmk.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr11!=null)
            buffer.append(Expr11.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TerColon!=null)
            buffer.append(TerColon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr12!=null)
            buffer.append(Expr12.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstTerExpr]");
        return buffer.toString();
    }
}
