// generated with ast extension for cup
// version 0.8
// 15/5/2021 0:40:51


package rs.ac.bg.etf.pp1.ast;

public class AstCnstAsgnChar extends CnstAsgn {

    private String cnstName;
    private Character value;

    public AstCnstAsgnChar (String cnstName, Character value) {
        this.cnstName=cnstName;
        this.value=value;
    }

    public String getCnstName() {
        return cnstName;
    }

    public void setCnstName(String cnstName) {
        this.cnstName=cnstName;
    }

    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
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
        buffer.append("AstCnstAsgnChar(\n");

        buffer.append(" "+tab+cnstName);
        buffer.append("\n");

        buffer.append(" "+tab+value);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstCnstAsgnChar]");
        return buffer.toString();
    }
}
