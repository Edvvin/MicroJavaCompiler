// generated with ast extension for cup
// version 0.8
// 3/5/2021 23:27:9


package rs.ac.bg.etf.pp1.ast;

public class AstNoFormalParams extends FormalParamList {

    public AstNoFormalParams () {
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
        buffer.append("AstNoFormalParams(\n");

        buffer.append(tab);
        buffer.append(") [AstNoFormalParams]");
        return buffer.toString();
    }
}
