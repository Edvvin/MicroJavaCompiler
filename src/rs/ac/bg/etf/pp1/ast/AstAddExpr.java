// generated with ast extension for cup
// version 0.8
// 12/2/2021 20:48:22


package rs.ac.bg.etf.pp1.ast;

public class AstAddExpr extends SumExpr {

    private Term Term;
    private Addop Addop;
    private SumExpr SumExpr;

    public AstAddExpr (Term Term, Addop Addop, SumExpr SumExpr) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.Addop=Addop;
        if(Addop!=null) Addop.setParent(this);
        this.SumExpr=SumExpr;
        if(SumExpr!=null) SumExpr.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public Addop getAddop() {
        return Addop;
    }

    public void setAddop(Addop Addop) {
        this.Addop=Addop;
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
        if(Term!=null) Term.accept(visitor);
        if(Addop!=null) Addop.accept(visitor);
        if(SumExpr!=null) SumExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(Addop!=null) Addop.traverseTopDown(visitor);
        if(SumExpr!=null) SumExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(Addop!=null) Addop.traverseBottomUp(visitor);
        if(SumExpr!=null) SumExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstAddExpr(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Addop!=null)
            buffer.append(Addop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SumExpr!=null)
            buffer.append(SumExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstAddExpr]");
        return buffer.toString();
    }
}
