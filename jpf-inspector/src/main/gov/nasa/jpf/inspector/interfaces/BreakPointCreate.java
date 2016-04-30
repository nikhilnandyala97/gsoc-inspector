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

package gov.nasa.jpf.inspector.interfaces;

import java.io.Serializable;

/**
 * Class the stores data needed to create or modify breakpoint.
 */
public interface BreakPointCreate extends Serializable {

  public static final Integer DEFAULT_LOWER_BOUND = 0; // Default value of the lower bound of the hit counter
  public static final Integer DEFAULT_UPPER_BOUND = Integer.MAX_VALUE;

  /**
   * Used as undefined value of the breakpoint ID. This value specify that you
   * creates new breakpoint and Inspector assign ID alone.
   * The JPF never returns breakpoint with this ID.
   */
  public static final int BP_ID_NOT_DEFINED = -1;

  /**
   * Get unique BP identifier
   */
  public int getBPID ();

  /**
   * @return String representation of the expression used to create/update the breakpoint.
   *         Note: This expression is parsed by the server part.
   */
  public String getBPExpression ();

  public String getName ();

  public BreakPointStates getState ();

  /**
   * Specify lower bound for breakpoint's hitCounter.
   * The breakpoint will only if the {@link BreakPointStatus#getHitCounter()} will be equal or higher then specified number.
   * <br>Note: The Upper bound condition must hold too to hit the breakpoint
   * 
   * @return Lower bound of hitCounter to hit the breakpoint.
   */
  public Integer bpHitCountLowerBound ();

  /**
   * Specify upper bound for breakpoint's hitCounter.
   * The breakpoint will only if the {@link BreakPointStatus#getHitCounter()} will be equal or lower then specified number.
   * <br>Note: The lower bound condition must hold too to hit the breakpoint
   * 
   * @return Upper bound of hitCounter to hit the breakpoint.
   */
  public Integer bpHitCountUpperBound ();

}
