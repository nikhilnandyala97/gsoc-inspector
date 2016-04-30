//
// Copyright (C) 2011 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
// 
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
// 
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//  

package gov.nasa.jpf.inspector.server.expression.expressions;

import gov.nasa.jpf.inspector.interfaces.JPFInspectorException;
import gov.nasa.jpf.inspector.server.programstate.StateReadableValueInterface;
import gov.nasa.jpf.inspector.server.programstate.StateValueElementInfoField;

/**
 * @author Alf
 * 
 */
public class ExpressionStateValueOuterClass extends ExpressionStateValue {

  private static final String TOKEN_HASH_OUTER_CLASS = "#outerClass";

  /**
   * @param child
   */
  public ExpressionStateValueOuterClass (ExpressionStateValue child) {
    super(child);
  }

  /*
   * @see gov.nasa.jpf.inspector.server.expression.expressions.ExpressionStateValue#getResultExpression(gov.nasa.jpf.inspector.server.programstate.
   * StateReadableValueInterface)
   */
  @Override
  public StateReadableValueInterface getResultExpression (StateReadableValueInterface srv) throws JPFInspectorException {

    StateReadableValueInterface srvi = StateValueElementInfoField.createOuterClass(srv);

    ExpressionStateValue child = getChild();
    if (child == null) {
      return srvi;
    } else {
      return child.getResultExpression(srvi);
    }
  }

  /* @see gov.nasa.jpf.inspector.server.expression.ExpressionNodeInterface#getNormalizedExpression() */
  @Override
  public String getNormalizedExpression () {
    // TOKEN_HASH_OUTER_CLASS : '#outerClass' ;
    // TOKEN_HASH_OUTER_CLASS WS? a=cmdStateExpressionValue[$expFactory]?

    return '.' + TOKEN_HASH_OUTER_CLASS + (child != null ? child.getNormalizedExpression() : "");
  }

}
