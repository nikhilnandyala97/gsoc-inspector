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

package gov.nasa.jpf.inspector.utils;

import gov.nasa.jpf.jvm.ClassInfo;
import gov.nasa.jpf.jvm.FieldInfo;
import gov.nasa.jpf.jvm.JVM;

/**
 * Caches ClassInfos of primitive types and wrappers.
 * 
 * Note: Each instance of JVM have new ClassInfos for these types.
 * 
 * @author Alf
 */
public class ClassInfoCache {
  private final JVM vm; // VM to which cached values are related to.

  // Primitive types
  public final ClassInfo ci_boolean;
  public final ClassInfo ci_byte;
  public final ClassInfo ci_char;
  public final ClassInfo ci_short;
  public final ClassInfo ci_int;
  public final ClassInfo ci_long;
  public final ClassInfo ci_float;
  public final ClassInfo ci_double;

  // Wrappers
  public final ClassInfo ci_Boolean;
  public final ClassInfo ci_Byte;
  public final ClassInfo ci_Char;
  public final ClassInfo ci_Short;
  public final ClassInfo ci_Int;
  public final ClassInfo ci_Long;
  public final ClassInfo ci_Float;
  public final ClassInfo ci_Double;

  // Wrappers value fields (field which holds the wrapped primitive type)
  public final FieldInfo fi_val_Boolean;
  public final FieldInfo fi_val_Byte;
  public final FieldInfo fi_val_Char;
  public final FieldInfo fi_val_Short;
  public final FieldInfo fi_val_Int;
  public final FieldInfo fi_val_Long;
  public final FieldInfo fi_val_Float;
  public final FieldInfo fi_val_Double;

  public final ClassInfo ci_String;

  public ClassInfoCache (JVM vm) {
    assert (vm != null);

    this.vm = vm;

    ci_boolean = ClassInfo.getResolvedClassInfo("boolean");
    ci_byte = ClassInfo.getResolvedClassInfo("byte");
    ci_char = ClassInfo.getResolvedClassInfo("char");
    ci_short = ClassInfo.getResolvedClassInfo("short");
    ci_int = ClassInfo.getResolvedClassInfo("int");
    ci_long = ClassInfo.getResolvedClassInfo("long");
    ci_float = ClassInfo.getResolvedClassInfo("float");
    ci_double = ClassInfo.getResolvedClassInfo("double");

    assert (ci_boolean != null);
    assert (ci_byte != null);
    assert (ci_char != null);
    assert (ci_short != null);
    assert (ci_int != null);
    assert (ci_long != null);
    assert (ci_float != null);
    assert (ci_double != null);

    ci_Boolean = ClassInfo.getResolvedClassInfo("java.lang.Boolean");
    ci_Byte = ClassInfo.getResolvedClassInfo("java.lang.Byte");
    ci_Char = ClassInfo.getResolvedClassInfo("java.lang.Character");
    ci_Short = ClassInfo.getResolvedClassInfo("java.lang.Short");
    ci_Int = ClassInfo.getResolvedClassInfo("java.lang.Integer");
    ci_Long = ClassInfo.getResolvedClassInfo("java.lang.Long");
    ci_Float = ClassInfo.getResolvedClassInfo("java.lang.Float");
    ci_Double = ClassInfo.getResolvedClassInfo("java.lang.Double");

    assert (ci_Boolean != null);
    assert (ci_Byte != null);
    assert (ci_Char != null);
    assert (ci_Short != null);
    assert (ci_Int != null);
    assert (ci_Long != null);
    assert (ci_Float != null);
    assert (ci_Double != null);

    fi_val_Boolean = ci_Boolean.getInstanceField("value");
    fi_val_Byte = ci_Byte.getInstanceField("value");
    fi_val_Char = ci_Char.getInstanceField("value");
    fi_val_Short = ci_Short.getInstanceField("value");
    fi_val_Int = ci_Int.getInstanceField("value");
    fi_val_Long = ci_Long.getInstanceField("value");
    fi_val_Float = ci_Float.getInstanceField("value");
    fi_val_Double = ci_Double.getInstanceField("value");

    ci_String = ClassInfo.getResolvedClassInfo("java.lang.String");

    assert (ci_String != null);
  }

  public boolean isBoxedPrimitiveType (ClassInfo ci) {
    return (ci_Boolean.equals(ci) || ci_Byte.equals(ci) || ci_Char.equals(ci) || ci_Short.equals(ci) || ci_Int.equals(ci) || ci_Long.equals(ci)
        || ci_Float.equals(ci) || ci_Double.equals(ci));
  }

  /**
   * @return Gets true if cache is valid(holds proper instances of ClassInfos) for given instance of JVM.
   */
  public boolean cacheValid (JVM vm) {
    return vm == this.vm;
  }

  @Override
  public String toString () {
    return "ClassInfoCache [ci_boolean=" + ci_boolean + ", ci_byte=" + ci_byte + ", ci_char=" + ci_char + ", ci_short=" + ci_short + ", ci_int=" + ci_int
        + ", ci_long=" + ci_long + ", ci_float=" + ci_float + ", ci_double=" + ci_double + ", ci_Boolean=" + ci_Boolean + ", ci_Byte=" + ci_Byte + ", ci_Char="
        + ci_Char + ", ci_Short=" + ci_Short + ", ci_Int=" + ci_Int + ", ci_Long=" + ci_Long + ", ci_Float=" + ci_Float + ", ci_Double=" + ci_Double
        + ", ci_String=" + ci_String + "]";
  }

}