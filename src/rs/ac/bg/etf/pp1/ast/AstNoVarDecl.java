// generated with ast extension for cup
// version 0.8
// 11/5/2021 0:35:3


package rs.ac.bg.etf.pp1.ast;

public class AstNoVarDecl extends VarDeclList {

    public AstNoVarDecl () {
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
        buffer.append("AstNoVarDecl(\n");

        buffer.append(tab);
        buffer.append(") [AstNoVarDecl]");
        return buffer.toString();
    }
}
