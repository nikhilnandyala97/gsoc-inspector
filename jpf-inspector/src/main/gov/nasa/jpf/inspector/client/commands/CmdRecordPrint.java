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

package gov.nasa.jpf.inspector.client.commands;

import gov.nasa.jpf.inspector.client.ClientCommand;
import gov.nasa.jpf.inspector.client.CommandRecorder;
import gov.nasa.jpf.inspector.client.JPFInspectorClient;
import gov.nasa.jpf.inspector.interfaces.JPFInspectorBackEndInterface;

import java.io.PrintStream;

/**
 * @author Alf
 * 
 */
public class CmdRecordPrint extends ClientCommand {

  /*
   * (non-Javadoc)
   * 
   * @see gov.nasa.jpf.inspector.client.ClientCommandInterface#executeCommands(gov.nasa.jpf.inspector.client.JPFInspectorClient,
   * gov.nasa.jpf.inspector.interfaces.JPFInspectorBackEndInterface, java.io.PrintStream)
   */
  @Override
  public void executeCommands (JPFInspectorClient client, JPFInspectorBackEndInterface inspector, PrintStream outStream) {
    CommandRecorder rec = client.getCommandRecorder();
    String recordedCmds = rec.getRecordedEvents();
    outStream.print(recordedCmds);
    rec.addComment(recordedCmds);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.nasa.jpf.inspector.client.ClientCommandInterface#getNormalizedCommand()
   */
  @Override
  public String getNormalizedCommand () {
    return "record print";
  }

}
