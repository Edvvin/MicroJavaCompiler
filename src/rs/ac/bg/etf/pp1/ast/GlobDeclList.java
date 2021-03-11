// generated with ast extension for cup
// version 0.8
// 12/2/2021 0:46:30


package rs.ac.bg.etf.pp1.ast;

public class GlobDeclList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private GlobDeclList GlobDeclList;
    private Decl Decl;

    public GlobDeclList (GlobDeclList GlobDeclList, Decl Decl) {
        this.GlobDeclList=GlobDeclList;
        if(GlobDeclList!=null) GlobDeclList.setParent(this);
        this.Decl=Decl;
        if(Decl!=null) Decl.setParent(this);
    }

    public GlobDeclList getGlobDeclList() {
        return GlobDeclList;
    }

    public void setGlobDeclList(GlobDeclList GlobDeclList) {
        this.GlobDeclList=GlobDeclList;
    }

    public Decl getDecl() {
        return Decl;
    }

    public void setDecl(Decl Decl) {
        this.Decl=Decl;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobDeclList!=null) GlobDeclList.accept(visitor);
        if(Decl!=null) Decl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobDeclList!=null) GlobDeclList.traverseTopDown(visitor);
        if(Decl!=null) Decl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobDeclList!=null) GlobDeclList.traverseBottomUp(visitor);
        if(Decl!=null) Decl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobDeclList(\n");

        if(GlobDeclList!=null)
            buffer.append(GlobDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Decl!=null)
            buffer.append(Decl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobDeclList]");
        return buffer.toString();
    }
}
