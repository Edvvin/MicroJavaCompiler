// generated with ast extension for cup
// version 0.8
// 10/5/2021 18:33:52


package rs.ac.bg.etf.pp1.ast;

public class AstCnstAsgnBool extends CnstAsgn {

    private String cnstName;
    private String value;

    public AstCnstAsgnBool (String cnstName, String value) {
        this.cnstName=cnstName;
        this.value=value;
    }

    public String getCnstName() {
        return cnstName;
    }

    public void setCnstName(String cnstName) {
        this.cnstName=cnstName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value=value;
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
        buffer.append("AstCnstAsgnBool(\n");

        buffer.append(" "+tab+cnstName);
        buffer.append("\n");

        buffer.append(" "+tab+value);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstCnstAsgnBool]");
        return buffer.toString();
    }
}
