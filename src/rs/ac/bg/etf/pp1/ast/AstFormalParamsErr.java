// generated with ast extension for cup
// version 0.8
// 3/5/2021 21:13:59


package rs.ac.bg.etf.pp1.ast;

public class AstFormalParamsErr extends FormalParams {

    public AstFormalParamsErr () {
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
        buffer.append("AstFormalParamsErr(\n");

        buffer.append(tab);
        buffer.append(") [AstFormalParamsErr]");
        return buffer.toString();
    }
}
