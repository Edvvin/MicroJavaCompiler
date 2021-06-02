// generated with ast extension for cup
// version 0.8
// 2/5/2021 19:48:2


package src.rs.ac.bg.etf.pp1.ast;

public class AstContinueStmt extends Statement {

    public AstContinueStmt () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstContinueStmt(\n");

        buffer.append(tab);
        buffer.append(") [AstContinueStmt]");
        return buffer.toString();
    }
}
