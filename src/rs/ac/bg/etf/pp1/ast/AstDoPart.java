// generated with ast extension for cup
// version 0.8
// 5/5/2021 14:23:59


package rs.ac.bg.etf.pp1.ast;

public class AstDoPart extends DoPartOfWhile {

    public AstDoPart () {
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
        buffer.append("AstDoPart(\n");

        buffer.append(tab);
        buffer.append(") [AstDoPart]");
        return buffer.toString();
    }
}
