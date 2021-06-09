package rs.ac.bg.etf.pp1;

import java.io.File;

import rs.ac.bg.etf.pp1.MJCompiler;

public class MicroJava {


	public static void main(String[] args) {
		String src;
		
		if(args.length >= 1) {
			src = args[0];
		}
		else {
			src = "test/program.mj";
		}
		
		MJCompiler compiler = new MJCompiler();
		
		compiler.compile(src, "test/program.obj");
			
	}

}
