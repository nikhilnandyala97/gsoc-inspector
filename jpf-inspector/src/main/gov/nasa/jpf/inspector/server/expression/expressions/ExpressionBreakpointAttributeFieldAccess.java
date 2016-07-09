//
// Copyright (C) 2016 Petr Hudeček
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package gov.nasa.jpf.inspector.server.expression.expressions;

import gov.nasa.jpf.inspector.exceptions.JPFInspectorException;
import gov.nasa.jpf.inspector.server.expression.AccessMode;
import gov.nasa.jpf.inspector.server.expression.ExpressionBooleanLeaf;
import gov.nasa.jpf.inspector.server.expression.InspectorState;
import gov.nasa.jpf.jvm.bytecode.JVMLocalVariableInstruction;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.VM;
import gov.nasa.jpf.vm.bytecode.StoreInstruction;

/**
 * Represent the family of local variable access hit conditions that hit when the local variable is about to be referenced.
 * This family contains the hit conditions "local_access", "local_read" and "local_write".
 */
public class ExpressionBreakpointAttributeFieldAccess extends ExpressionBooleanLeaf {
  private final AccessMode accessMode;
  private final String localName;

  public ExpressionBreakpointAttributeFieldAccess(AccessMode accessMode, String localName) {
    assert accessMode != null;
    assert localName != null;
    this.accessMode = accessMode;
    this.localName = localName;
  }

  @Override
  public boolean evaluateExpression(InspectorState state) throws JPFInspectorException {
    assert state != null;

    if (state.getListenerMethod() != InspectorState.ListenerMethod.LM_EXECUTE_INSTRUCTION) {
      return false;
    }

    throw new RuntimeException("Internal error - Unsupported access mode");
  }

  @Override
  public String getNormalizedExpression() {
    throw new RuntimeException("Internal error - Unsupported access mode");

  }
}