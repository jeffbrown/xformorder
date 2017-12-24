package org.xformorder

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.grails.datastore.gorm.transactions.transform.TransactionalTransform
import org.grails.datastore.mapping.core.Ordered

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class SayHelloTransformation extends AbstractASTTransformation implements Ordered {

    int getOrder() {
        TransactionalTransform.POSITION + 100
    }

    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        MethodNode methodNode = (MethodNode) nodes[1]

        if(!methodNode.isAbstract()) {
            BlockStatement newCode = new BlockStatement()
            def thisVariableExpression = new VariableExpression('this')
            def printlnArgumentListExpression = new ArgumentListExpression(new ConstantExpression('Hello World!'))
            def callPrintlnExpression = new MethodCallExpression(thisVariableExpression, 'println', printlnArgumentListExpression)
            newCode.addStatement(new ExpressionStatement(callPrintlnExpression))

            newCode.addStatement methodNode.code

            methodNode.code = newCode
        }
    }
}
