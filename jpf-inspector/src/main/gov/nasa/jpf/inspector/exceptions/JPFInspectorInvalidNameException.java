//
// Copyright (C) 2010-2011 Pavel Jančík
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

package gov.nasa.jpf.inspector.exceptions;

/**
 * @author Alf
 * 
 */
public class JPFInspectorInvalidNameException extends JPFInspectorException {

  private static final long serialVersionUID = -3334686833820140601L;

  public JPFInspectorInvalidNameException (String varName) {
    super("No entry (field, local variable, parameter) with the name \"" + varName + "\" exists (maybe we are inside a native method).");
  }

}
