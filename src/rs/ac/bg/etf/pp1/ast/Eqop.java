// generated with ast extension for cup
// version 0.8
// 5/2/2021 23:11:30


package rs.ac.bg.etf.pp1.ast;

public class Eqop extends Relop {

    public Eqop () {
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
        buffer.append("Eqop(\n");

        buffer.append(tab);
        buffer.append(") [Eqop]");
        return buffer.toString();
    }
}
