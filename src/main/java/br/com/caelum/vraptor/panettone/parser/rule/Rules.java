package br.com.caelum.vraptor.panettone.parser.rule;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

public enum Rules {

	// be careful... currently, the order matters... :/
	// in particular, comment should be executed before print_variable
	COMMENT(new CommentRule(), true),
	INJECT_DECLARATION(new InjectDeclarationRule(), true),
	VARIABLE_DECLARATION(new VariableDeclarationRule(), true),
	REUSABLE_VARIABLE(new ReusableVariableRule(), true),
	EXPRESSION(new ExpressionRule(), true),
	PRINT_VARIABLE(new PrintVariableRule(), true),

	SCRIPTLET_PRINT(new ScriptletPrintRule(), true),
	
	HTML(new HTMLRule(), false),
	SCRIPTLET(new ScriptletRule(), false);
	
	private Rule rule;
	private boolean execute;

	Rules(Rule rule, boolean execute) {
		this.rule = rule;
		this.execute = execute;
	}
	
	public Rule getRule() {
		return rule;
	}
	
	public static List<Rules> rulesToExecute() {
		return Stream.of(values())
					.filter(Rules::shouldExecute)
					.collect(toList());
	}

	private boolean shouldExecute() {
		return execute;
	}

	public static Rule byName(String ruleName) {
		for (Rules rule : Rules.values()) {
			if (rule.name().equals(ruleName))
				return rule.getRule();
		}
		
		throw new RuntimeException("rule doesnt exist: " + ruleName);
		
	}
	
	public static String htmlRuleName() {
		return "HTML";
	}
	
	public static String scriptletRuleName() {
		return "SCRIPTLET";
	}
	
}
