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
package gov.nasa.jpf.inspector.interfaces;

import gov.nasa.jpf.JPF;
import gov.nasa.jpf.inspector.server.expression.InspectorState;
import gov.nasa.jpf.inspector.server.jpf.JPFInspector;
import gov.nasa.jpf.inspector.server.jpf.StopHolder;

/**
 * This enumeration is used by callback handling classes.
 *
 * Note that the server itself doesn't keep track of its state. Thus, these are not "states" as much as they are
 * message that the Inspector's state changed. This enum is used just to inform the client.
 */
public enum InspectorStatusChange {
  /**
   * JPF becomes started at the completion of the method {@link JPFInspector#bindWithJPF(JPF)}.
   */
  JPF_STARTED,
  /**
   * JPF becomes running whenever it resumes execution after leaving the method {@link StopHolder#stopExecution(InspectorState)}.
   * Notably, the state JPF is in between its start and the first time it is stopped is JPF_STARTED, not JPF_RUNNING.
   */
  JPF_RUNNING,
  /**
   * JPF becomes stopped immediately befpre the wait() call inside the method {@link StopHolder#stopExecution(InspectorState)}.
   */
  JPF_STOPPED,
  /**
   * JPF becomes terminating when the method {@link StopHolder#notifyClientTerminating()} is run. This method is
   * called when JPF wants to terminate or when the Search object does.
   */
  JPF_TERMINATING
}
