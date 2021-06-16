// generated with ast extension for cup
// version 0.8
// 16/5/2021 15:11:18


package rs.ac.bg.etf.pp1.ast;

public class AstCnstAsgnInt extends CnstAsgn {

    private String cnstName;
    private Integer value;

    public AstCnstAsgnInt (String cnstName, Integer value) {
        this.cnstName=cnstName;
        this.value=value;
    }

    public String getCnstName() {
        return cnstName;
    }

    public void setCnstName(String cnstName) {
        this.cnstName=cnstName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
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
        buffer.append("AstCnstAsgnInt(\n");

        buffer.append(" "+tab+cnstName);
        buffer.append("\n");

        buffer.append(" "+tab+value);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstCnstAsgnInt]");
        return buffer.toString();
    }
}
