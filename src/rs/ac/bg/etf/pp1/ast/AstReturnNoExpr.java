// generated with ast extension for cup
// version 0.8
// 17/2/2021 23:16:37


package rs.ac.bg.etf.pp1.ast;

public class AstReturnNoExpr extends Statement {

    public AstReturnNoExpr () {
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
        buffer.append("AstReturnNoExpr(\n");

        buffer.append(tab);
        buffer.append(") [AstReturnNoExpr]");
        return buffer.toString();
    }
}
