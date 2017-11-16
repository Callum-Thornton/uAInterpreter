/*
 *  ========================================================================
 *  uA Interpreter
 *  ========================================================================
 *  
 *  This file is part of ua Interpreter.
 *  
 *  ua Interpreter is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or (at
 *  your option) any later version.
 *  
 *  ua Interpreter is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with ua Interpreter.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  (C) Copyright 2017, Gabor Kecskemeti (g.kecskemeti@ljmu.ac.uk)
 */
package uk.ac.ljmu.fet.cs.comp.calccomp.visitors;

import uk.ac.ljmu.fet.cs.comp.calccomp.CalcHelperStructures;
import uk.ac.ljmu.fet.cs.comp.calccomp.interfaces.CalcVisitor;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.AdditionStatement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.AlterScope;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.AssignStatement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.CalcIntNumber;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.DivisionStatement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.FunctionCallStatement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.FunctionDeclarationStatement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.MultiplyStatement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.PrintStatement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.Statement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.SubtractionStatement;
import uk.ac.ljmu.fet.cs.comp.calccomp.tokens.VariableRef;

public class CalcSyntaxCheck implements CalcVisitor {

	@Override
	public void visit(CalcIntNumber e) {
		// do nothing
	}

	@Override
	public void visit(AlterScope e) {
		// do nothing
	}

	@Override
	public void visit(VariableRef e) {
		// do nothing
	}

	private void ensureTarget(Statement e) {
		if (e.target == null) {
			e.throwError("No target specified for the statement");
		}
	}

	private void ensureLeft(Statement e) {
		if (e.left == null) {
			e.throwError("No left subexpression specified for the statement");
		}
	}

	private void ensureRight(Statement e) {
		if (e.right == null) {
			e.throwError("No right subexpression specified for the statement");
		}
	}

	private void checkMultiParStatement(Statement e, boolean t, boolean l, boolean r) {
		if (t)
			ensureTarget(e);
		if (l)
			ensureLeft(e);
		if (r)
			ensureRight(e);
		e.propagate(this);
	}

	private void checkMultiParStatement(Statement e) {
		checkMultiParStatement(e, true, true, true);
	}

	@Override
	public void visit(AdditionStatement e) {
		checkMultiParStatement(e);
	}

	@Override
	public void visit(DivisionStatement e) {
		checkMultiParStatement(e);
	}

	@Override
	public void visit(FunctionCallStatement e) {
		checkMultiParStatement(e);
		if (e.left instanceof VariableRef) {
			((VariableRef) e.left).functionName = true;
		} else {
			e.throwError("A function call does not reference to a function");
		}
	}

	@Override
	public void visit(MultiplyStatement e) {
		checkMultiParStatement(e);
	}

	@Override
	public void visit(SubtractionStatement e) {
		checkMultiParStatement(e);
	}

	@Override
	public void visit(PrintStatement e) {
		checkMultiParStatement(e, true, false, false);
	}

	@Override
	public void visit(FunctionDeclarationStatement e) {
		checkMultiParStatement(e, true, true, false);
		e.target.functionName = true;
		CalcHelperStructures.allFunctions.add(e);
		e.generateCanonicalName();
	}

	@Override
	public void visit(AssignStatement e) {
		checkMultiParStatement(e, true, true, false);
	}
}
