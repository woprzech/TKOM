package tkom.module.semcheck;

import tkom.structures.ex.*;
import tkom.structures.model.*;
import tkom.structures.model.data.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static tkom.structures.model.data.Node.Type.*;

/**
 * Created by wprzecho on 11.06.16.
 */
public class SemCheck {
    private Map<String, FunctionEx> definedFunctions = new HashMap<>();
    private Program program;

    public List<FunctionEx> check(final Program program) throws Exception {
        definedFunctions.clear();
        this.program = program;

        scanDefinedFunctions();
        checkMain();


        final List<FunctionEx> functions = new LinkedList<>();
        for (final Function function : program.getFunctions()) {
            functions.add(checkFunction(function));
        }
        return functions;
    }

    private void checkMain() throws Exception {
        if (!definedFunctions.containsKey("main")) {
            throw new Exception("No \"main\" function defined");
        }
        if (definedFunctions.get("main").getScope().getVariables().size() != 0) {
            throw new Exception("\"main\" function should not have parameters");
        }
    }

    private void scanDefinedFunctions() throws Exception {
        for (final Function function : program.getFunctions()) {
            scanDefinedFunction(function);
        }
    }

    private void scanDefinedFunction(final Function function) throws Exception {
        if (definedFunctions.containsKey(function.getName())) {
            throw new Exception("Redefined function: " + function.getName());
        }
        final FunctionEx functionEx = new FunctionEx();
        functionEx.setName(function.name);
        for (final String parameter : function.getParameters()) {
            if (!functionEx.getScope().addVariable(parameter)) {
                throw new Exception(new StringBuilder()
                        .append("Dupicated parameter : ")
                        .append(parameter)
                        .append("in function")
                        .append(function.getName()).toString());
            }
        }
        definedFunctions.put(function.getName(), functionEx);
    }

    private FunctionEx checkFunction(final Function function) throws Exception {
        final FunctionEx functionEx = definedFunctions.get(function.getName());
        functionEx.getInstructions().add(checkBlock(functionEx.getScope(), function.getStatementBlock()));
        return functionEx;

    }

    private Block checkBlock(final Scope scope, final StatementBlock statementBlock) throws Exception {
        final Block block = new Block();
        block.getScope().setParentScope(scope);
        for (final Node instruction : statementBlock.getInstructions()) {
            switch (instruction.getType()) {
                case Assignment:
                    final AssingStatement assingStatement = (AssingStatement) instruction;
                    block.addInstruction(checkAssignment(block.getScope(), assingStatement));
                    break;
                case ReturnStatement:
                    final ReturnStatement returnStatement = (ReturnStatement) instruction;
                    block.addInstruction(checkReturnStatement(block.getScope(), returnStatement));
                    break;
                case Call:
                    final FunCall call = (FunCall) instruction;
                    block.addInstruction(checkFunCall(block.getScope(), call));
                    break;
                case IfStatement:
                    final IfStatement ifStatement = (IfStatement) instruction;
                    block.addInstruction(checkIfStatement(block.getScope(), ifStatement));
                    break;
                case WhileStatement:
                    final WhileStatement whileStatement = (WhileStatement) instruction;
                    block.addInstruction(checkWhileStatement(block.getScope(), whileStatement));
                    break;
            }
        }
        return block;
    }

    private Instruction checkWhileStatement(final Scope scope, final WhileStatement whileStatement) throws Exception {
        final While whileEx = new While();
        whileEx.setCondition(checkCondition(scope, whileStatement.getCondition()));
        whileEx.setBlock(checkBlock(scope, whileStatement.getStatementBlock()));
        return whileEx;
    }

    private Instruction checkAssignment(final Scope scope, final AssingStatement assingStatement) throws Exception {
        final Assignment assignment = new Assignment();
        final String variableName = assingStatement.variable.name;
        if (!scope.hasVariable(variableName)) {
            scope.addVariable(variableName);
        }
        assignment.setName(variableName);
        assignment.setValue(checkAssignable(scope, assingStatement.value));
        return assignment;
    }

    private Assignable checkAssignable(final Scope scope, final Node assignable) throws Exception {
        if (Call.equals(assignable.getType())) {
            return checkFunCall(scope, (FunCall) assignable);
        } else if (Expression.equals(assignable.getType())) {
            return checkExpression(scope, (tkom.structures.model.Expression) assignable);
        } else if (Variable.equals(assignable.getType())) {
            return checkVariable(scope, (Variable) assignable);
        } else {
            throw new Exception("Invalid assignable value");
        }
    }

    private ExpressionEx checkExpression(final Scope scope, final Expression expr) throws Exception {
        final ExpressionEx expression = new ExpressionEx();
        expression.setOperations(expr.getOperations());
        for (final Node operand : expr.getOperands()) {
            if (operand.getType() == Literal) {
                expression.addOperand(checkLiteral(scope, (Literal) operand));
            } else if (operand.getType() == Expression) {
                expression.addOperand(checkExpression(scope, (Expression) operand));
            } else if (operand.getType() == Variable) {
                expression.addOperand(checkVariable(scope, (tkom.structures.model.Variable) operand));
            }
        }
        return expression;
    }

    private LiteralEx checkLiteral(final Scope scope, final Literal operand) {
        final LiteralEx literal = new LiteralEx();
        literal.setValue(operand.getValue());
        return literal;
    }

    private VariableEx checkVariable(final Scope scope, final Variable operand) throws Exception {
        final VariableEx variable = new VariableEx();
        if (!scope.hasVariable(operand.getName())) {
            throw new Exception("Usage of undefined variable: " + operand.getName());
        }
        variable.setName(operand.getName());
        return variable;
    }

    private Call checkFunCall(final Scope scope, final FunCall assignable) throws Exception {
        if (!definedFunctions.containsKey(assignable.getName()) && !StdLib.printFun.equals(assignable.getName())) {
            throw new Exception("Undefined function: " + assignable.getName());
        }
        final FunctionEx function = definedFunctions.get(assignable.getName());
        if ((StdLib.printFun.equals(assignable.getName()) && assignable.arguments.size() > 1)) {
            throw new Exception("Invalid arguments count for function " + StdLib.printFun
                    + ". Expected : 1" + " but found " + assignable.arguments.size());
        } else if ((!StdLib.printFun.equals(assignable.getName()) && function.getScope().getVariables().size() != assignable.arguments.size())) {
            throw new Exception("Invalid arguments count for function " + assignable.getName()
                    + ". Expected " + function.getScope().getVariables().size()
                    + " but found " + assignable.arguments.size());
        }
        final Call funCall = new Call();
        funCall.setName(assignable.getName());
        for (final Node argument : assignable.getArguments()) {
            funCall.addArgument(checkAssignable(scope, argument));
        }
        return funCall;
    }

    private Instruction checkReturnStatement(final Scope scope, final ReturnStatement returnStatement) throws Exception {
        final Return returnSt = new Return();
        returnSt.setValue(checkAssignable(scope, returnStatement.getReturnValue()));
        return returnSt;
    }

    private Instruction checkIfStatement(final Scope scope, final IfStatement ifStatement) throws Exception {
        final If ifSt = new If();
        ifSt.setCondition(checkCondition(scope, ifStatement.getCondition()));
        ifSt.setTrueBlock(checkBlock(scope, ifStatement.getTrueBlock()));
        if (ifStatement.hasElseBlock()) {
            ifSt.setElseBlock(checkBlock(scope, ifStatement.getElseBlock()));
        }
        return ifSt;
    }

    private ConditionEx checkCondition(final Scope scope, final Condition condition) throws Exception {
        final ConditionEx cond = new ConditionEx();
        cond.setIsNegated(condition.isNegated());
        cond.setOperation(condition.getOperation());
        for (final Node operand : condition.getOperands()) {
            if (Condition.equals(operand.getType())) {
                cond.addOperand(checkCondition(scope, (Condition) operand));
            } else if (Variable.equals(operand.getType())) {
                cond.addOperand(checkVariable(scope, (Variable) operand));
            } else if (Literal.equals(operand.getType())) {
                cond.addOperand(checkLiteral(scope, (Literal) operand));
            }
        }
        return cond;
    }

}
