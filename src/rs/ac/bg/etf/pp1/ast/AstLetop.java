// generated with ast extension for cup
// version 0.8
// 2/5/2021 19:48:2


package src.rs.ac.bg.etf.pp1.ast;

public class AstLetop extends Relop {

    public AstLetop () {
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
        buffer.append("AstLetop(\n");

        buffer.append(tab);
        buffer.append(") [AstLetop]");
        return buffer.toString();
    }
}
