package rs.ac.bg.etf.pp1;

import java.io.File;
import java.util.List;

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
		
		List<CompilerError> errors = compiler.compile(src, "test/program.obj");
		System.out.println("Compiler Errors: ");
		if(errors != null) {
			for(CompilerError e : errors) {
				System.out.println(e);
			}
		}
		else {
			System.out.println("No errors");
		}
	}

}
