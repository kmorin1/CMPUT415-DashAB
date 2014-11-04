package dash;

import java.io.FileNotFoundException;
import SymTab.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.DOTTreeGenerator;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class DashAB_Test {
	public static void main(String[] args) throws RecognitionException {
		/*if (args.length != 2) {
			System.err.print("Insufficient arguments: ");
			System.err.println(Arrays.toString(args));
			System.exit(1);
		}*/
		String inputfile = null;
		ANTLRFileStream input = null;
		try {
			inputfile = "test.ds";
			//String inputfile = args[0];
			input = new ANTLRFileStream(inputfile);
		} catch (IOException e) {
			System.err.print("Invalid program filename: ");
			//System.err.println(args[0]);
			System.exit(1);
		}

		SymbolTable symtab = new SymbolTable();
		SyntaxLexer lexer = new SyntaxLexer(input);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SyntaxParser parser = new SyntaxParser(tokenStream);
		SyntaxParser.program_return entry = parser.program();
		CommonTree ast = (CommonTree)entry.getTree();
		DOTTreeGenerator gen = new DOTTreeGenerator();
		StringTemplate st = gen.toDOT(ast);
		//System.out.println(st);
			
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		nodes.setTokenStream(tokenStream);
		TypeExpand te = new TypeExpand(nodes, symtab, inputfile);
		TypeExpand.program_return teret = te.program();
		ast = (CommonTree) teret.getTree();
		st = gen.toDOT(ast);
		//System.out.println(st);
			
		nodes = new CommonTreeNodeStream(ast);
		nodes.setTokenStream(tokenStream);
		TypeTranslate tt = new TypeTranslate(nodes, symtab, inputfile);
		TypeTranslate.program_return ttret = tt.program();
		ast = (CommonTree) ttret.getTree();
			
		st = gen.toDOT(ast);
		System.out.println(st);

		String templateFile = "llvm.stg";

		FileReader template;
		try {
		    template = new FileReader(templateFile);

		    StringTemplateGroup stg = new StringTemplateGroup(template);

		    nodes = new CommonTreeNodeStream(ast);
		    nodes.setTokenStream(tokenStream);

		    LLVMTemplater templater = new LLVMTemplater(nodes, symtab);
		    templater.setTemplateLib(stg);
		    System.out.println(templater.program().getTemplate().toString());				
		} catch (FileNotFoundException e) {
		    System.out.print("The template file is missing:");
		    System.out.println(templateFile);
		}
	}
	
}
