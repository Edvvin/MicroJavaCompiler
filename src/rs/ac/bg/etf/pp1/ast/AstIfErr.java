// generated with ast extension for cup
// version 0.8
// 12/2/2021 17:45:15


package rs.ac.bg.etf.pp1.ast;

public class AstIfErr extends Statement {

    public AstIfErr () {
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
        buffer.append("AstIfErr(\n");

        buffer.append(tab);
        buffer.append(") [AstIfErr]");
        return buffer.toString();
    }
}
