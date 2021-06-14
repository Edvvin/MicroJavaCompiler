package rs.ac.bg.etf.pp1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.etf.pp1.MJCompiler;

public class MicroJava {
	
	private static void compile(String src, String dst, Compiler compiler) {
		List<CompilerError> errors = compiler.compile(src, dst);
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

	public static void main(String[] args) {
		ArrayList<String> srcs = new ArrayList<>(), dsts = new ArrayList<>();
		MJCompiler compiler = new MJCompiler();
		int state = 0;
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("o")) {
				state = 1;
			}
			else {
				if(state == 0) {
					srcs.add(args[i]);
				}
				else {
					dsts.add(args[i]);
				}
			}
		}
		
		if(srcs.size()!=dsts.size()) {
			System.out.println("The number of sources does not match the number of destinations");
			return;
		}
		
		for(int i = 0; i < srcs.size(); i++) {
			System.out.println("######################### NEXT FILE ##############################");
			File testSrc = new File(srcs.get(i));
			File testDst = new File(dsts.get(i));
			if(testSrc.isFile()) {
				compile(srcs.get(i), dsts.get(i), compiler);
			}
			else {
				System.out.println("Error src: " + srcs.get(i) + " isn't a file!");
			}
		}
		
	}

}
