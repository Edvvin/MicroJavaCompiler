// generated with ast extension for cup
// version 0.8
// 10/5/2021 18:33:52


package rs.ac.bg.etf.pp1.ast;

public class AstCnstAsgnSemiErr extends CnstAsgnList {

    public AstCnstAsgnSemiErr () {
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
        buffer.append("AstCnstAsgnSemiErr(\n");

        buffer.append(tab);
        buffer.append(") [AstCnstAsgnSemiErr]");
        return buffer.toString();
    }
}
