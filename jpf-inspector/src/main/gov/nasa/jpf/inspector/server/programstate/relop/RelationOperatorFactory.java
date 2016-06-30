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

package gov.nasa.jpf.inspector.server.programstate.relop;

/**
 * Contains an instance of each of the six boolean operators and provides them publicly.
 */
public final class RelationOperatorFactory {
  private RelOpEqual relopEqual;
  private RelOpNotEqual relopNotEqual;
  private RelOpLess relopLess;
  private RelOpLessEqual relopLessEqual;
  private RelOpGreater relopGreater;
  private RelOpGreaterEqual relopGreaterEqual;

  public RelationOperatorFactory () {
    relopEqual = new RelOpEqual();
    relopNotEqual = new RelOpNotEqual();
    relopLess = new RelOpLess();
    relopLessEqual = new RelOpLessEqual();
    relopGreater = new RelOpGreater();
    relopGreaterEqual = new RelOpGreaterEqual();
  }

  public RelationOperator getRelOpEqual () {
    return relopEqual;
  }

  public RelationOperator getRelOpNotEqual () {
    return relopNotEqual;
  }

  public RelationOperator getRelOpLessThan () {
    return relopLess;
  }

  public RelationOperator getRelOpLessEqual () {
    return relopLessEqual;
  }

  public RelationOperator getRelOpGreaterThan () {
    return relopGreater;
  }

  public RelationOperator getRelOpGreaterEqual () {
    return relopGreaterEqual;
  }

}
