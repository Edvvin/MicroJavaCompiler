// generated with ast extension for cup
// version 0.8
// 15/2/2021 22:6:37


package rs.ac.bg.etf.pp1.ast;

public class AstNoActualParam extends ActualPars {

    public AstNoActualParam () {
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
        buffer.append("AstNoActualParam(\n");

        buffer.append(tab);
        buffer.append(") [AstNoActualParam]");
        return buffer.toString();
    }
}
