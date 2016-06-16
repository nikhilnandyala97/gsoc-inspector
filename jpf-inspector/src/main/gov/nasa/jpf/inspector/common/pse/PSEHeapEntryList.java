//
// Copyright (C) 2010 United States Government as represented by the
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

package gov.nasa.jpf.inspector.common.pse;

import gov.nasa.jpf.inspector.exceptions.JPFInspectorException;
import gov.nasa.jpf.inspector.server.expression.expressions.ExpressionStateHeapEntryList;
import gov.nasa.jpf.inspector.server.programstate.StateHeapEntryList;
import gov.nasa.jpf.inspector.server.programstate.StateNode;

import java.util.List;

/**
 * A list of objects on the heap, generated by the #heap[class-name] expression.
 * Linked classes:
 * - {@link StateHeapEntryList}
 * - {@link ExpressionStateHeapEntryList}
 */
public class PSEHeapEntryList extends ProgramStateEntry {

  private static final long serialVersionUID = -3669281817671708017L;

  private final List<PSEVariable> heapEntryList;

  public PSEHeapEntryList (StateNode sn, List<PSEVariable> heapEntryList) {
    super(sn);

    assert (heapEntryList != null);

    this.heapEntryList = heapEntryList;
  }

  /**
   * Gets the unmodifiable list with representation of filtered (by class name) heap entries.
   */
  public List<PSEVariable> getHeapList () {
    return heapEntryList;
  }

  @Override
  public <T> T visit (PSEVisitor<T> visitor) throws JPFInspectorException {
    return visitor.visitPSEHeapEntryList(this);
  }

}
