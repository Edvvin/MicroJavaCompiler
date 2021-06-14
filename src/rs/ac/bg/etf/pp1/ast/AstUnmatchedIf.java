// generated with ast extension for cup
// version 0.8
// 14/5/2021 19:43:29


package rs.ac.bg.etf.pp1.ast;

public class AstUnmatchedIf extends Statement {

    private IfPart IfPart;
    private Statement Statement;

    public AstUnmatchedIf (IfPart IfPart, Statement Statement) {
        this.IfPart=IfPart;
        if(IfPart!=null) IfPart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public IfPart getIfPart() {
        return IfPart;
    }

    public void setIfPart(IfPart IfPart) {
        this.IfPart=IfPart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfPart!=null) IfPart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfPart!=null) IfPart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfPart!=null) IfPart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstUnmatchedIf(\n");

        if(IfPart!=null)
            buffer.append(IfPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstUnmatchedIf]");
        return buffer.toString();
    }
}
