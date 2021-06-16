// generated with ast extension for cup
// version 0.8
// 16/5/2021 15:11:18


package rs.ac.bg.etf.pp1.ast;

public class AstFormalParamL extends FormalParamList {

    private FormalParamDeclComma FormalParamDeclComma;
    private FormalParamList FormalParamList;

    public AstFormalParamL (FormalParamDeclComma FormalParamDeclComma, FormalParamList FormalParamList) {
        this.FormalParamDeclComma=FormalParamDeclComma;
        if(FormalParamDeclComma!=null) FormalParamDeclComma.setParent(this);
        this.FormalParamList=FormalParamList;
        if(FormalParamList!=null) FormalParamList.setParent(this);
    }

    public FormalParamDeclComma getFormalParamDeclComma() {
        return FormalParamDeclComma;
    }

    public void setFormalParamDeclComma(FormalParamDeclComma FormalParamDeclComma) {
        this.FormalParamDeclComma=FormalParamDeclComma;
    }

    public FormalParamList getFormalParamList() {
        return FormalParamList;
    }

    public void setFormalParamList(FormalParamList FormalParamList) {
        this.FormalParamList=FormalParamList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalParamDeclComma!=null) FormalParamDeclComma.accept(visitor);
        if(FormalParamList!=null) FormalParamList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParamDeclComma!=null) FormalParamDeclComma.traverseTopDown(visitor);
        if(FormalParamList!=null) FormalParamList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParamDeclComma!=null) FormalParamDeclComma.traverseBottomUp(visitor);
        if(FormalParamList!=null) FormalParamList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AstFormalParamL(\n");

        if(FormalParamDeclComma!=null)
            buffer.append(FormalParamDeclComma.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalParamList!=null)
            buffer.append(FormalParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AstFormalParamL]");
        return buffer.toString();
    }
}
