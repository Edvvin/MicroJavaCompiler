package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class MJStatic {
	public static final int Bool = 5;
	public static final Struct boolType = new Struct(MJStatic.Bool);

	enum RelOpType {
		EQ, NE, G, GE, L, LE ;
	}
	
	public static String relOpToString(RelOpType type) {
		switch(type) {
			case EQ:
				return "==";
			case NE:
				return "!=";
			case G:
				return ">";
			case GE:
				return ">=";
			case L:
				return "<";
			case LE:
				return "<=";
		}
		return "ERROR";
	}
}
