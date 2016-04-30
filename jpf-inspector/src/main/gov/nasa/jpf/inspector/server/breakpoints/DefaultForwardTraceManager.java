package gov.nasa.jpf.inspector.server.breakpoints;

import gov.nasa.jpf.inspector.interfaces.ChoiceGeneratorsInterface;
import gov.nasa.jpf.inspector.server.jpf.JPFInspector;
import gov.nasa.jpf.jvm.ChoiceGenerator;
import gov.nasa.jpf.jvm.JVM;
import gov.nasa.jpf.jvm.Transition;
import gov.nasa.jpf.jvm.bytecode.Instruction;
import gov.nasa.jpf.search.Search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class holds default forward trace. It means state which were backtracked in backward stepping commands. This trace helps to reach place where backtracking
 * started.
 * 
 * It holds stack with state of CG and stateID. In forward steps it checks whether "correct/expected" are reached, if not user is notified.
 * 
 * @author Alf
 * 
 */
public class DefaultForwardTraceManager {
  private static final boolean DEBUG = false;

  private final JPFInspector inspector;

  /**
   * Holds "copy" of state of single GC
   * 
   * Note: Probably not works for CG<T> where T does not override default equals method (CG<Invocations>)
   */
  static private class CGStateMemento {
    private final String id; // CG ID
    private final Instruction ins; // Instruction CG is related to
    private final int threadID;

    private final Object selectedChoice;
    private final Object[] choices;
    private final boolean choicesSorted;

    public CGStateMemento (ChoiceGenerator<?> cg) {
      assert cg != null;

      id = cg.getId();
      ins = cg.getInsn();
      threadID = cg.getThreadInfo().getId();

      selectedChoice = cg.getNextChoice();

      // Obtain all possible Choices
      int processedChoices = cg.getProcessedNumberOfChoices();
      cg.reset();
      choices = new Object[cg.getTotalNumberOfChoices()];

      for (int i = 0; i < choices.length; i++) {
        cg.advance();
        choices[i] = cg.getNextChoice();
      }

      cg.reset();
      cg.advance(processedChoices);

      // CG state restored properly
      assert (cg.getNextChoice() != null ? cg.getNextChoice().equals(selectedChoice) : selectedChoice == null);
      // determinize choice order (to handle randomized CG)g
      if (Comparable.class.isAssignableFrom(cg.getChoiceType())) {
        choicesSorted = true;
        Arrays.sort(choices);
      } else {
        choicesSorted = false;
      }
    }

    public int getDefaultChoice (ChoiceGenerator<?> cg) {
      int cgOrigProcessedChoices = cg.getProcessedNumberOfChoices();
      Object cgOrigSelectedChoice = cg.getNextChoice();

      int result = ChoiceGeneratorsInterface.NO_DEFAULT_CHOICE;
      if (selectedChoice == null) {
        // Cannot use equals
        if (cgOrigSelectedChoice == null) {
          return cg.getProcessedNumberOfChoices();
        }

        cg.reset();
        for (int i = 0; i < cg.getTotalNumberOfChoices(); i++) {
          cg.advance();
          Object choice = cg.getNextChoice();
          if (choice == null) {
            result = i;
            break;
          }
        }
      } else {
        // TODO - ThreadInfo - pres ThreadCreate - not working - new instances + TI does not have equals
        if (selectedChoice.equals(cgOrigSelectedChoice)) {
          return cg.getProcessedNumberOfChoices();
        }

        cg.reset();
        for (int i = 0; i < cg.getTotalNumberOfChoices(); i++) {
          cg.advance();
          Object choice = cg.getNextChoice();
          if (selectedChoice.equals(choice)) {
            result = i + 1;
            break;
          }
        }

      }

      // Restore CG state
      cg.reset();
      cg.advance(cgOrigProcessedChoices);
      assert (cg.getNextChoice() != null ? cg.getNextChoice().equals(cgOrigSelectedChoice) : cgOrigSelectedChoice == null);

      return result;

    }

    @Override
    public int hashCode () {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(choices);
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((ins == null) ? 0 : ins.hashCode());
      result = prime * result + ((selectedChoice == null) ? 0 : selectedChoice.hashCode());
      result = prime * result + threadID;
      return result;
    }

    @Override
    public boolean equals (Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      CGStateMemento other = (CGStateMemento) obj;
      if (id == null) {
        if (other.id != null)
          return false;
      } else if (!id.equals(other.id))
        return false;

      if (ins == null) {
        if (other.ins != null)
          return false;
      } else if (!ins.equals(other.ins))
        return false;
      if (threadID != other.threadID)
        return false;

      if (selectedChoice == null) {
        if (other.selectedChoice != null)
          return false;
      } else if (!selectedChoice.equals(other.selectedChoice))
        return false;

      if (choices == null) {
        return other.choices == null;
      } else if (other.choices == null) {
        return false;
      }

      if (choices.length != other.choices.length) {
        return false;
      }

      if (choicesSorted && other.choicesSorted) {
        return Arrays.equals(choices, other.choices);
      } else {
        // this.choices has counterpart in other.choices
        for (int i = 0; i < choices.length; i++) {
          int j;
          for (j = 0; j < other.choices.length; j++) {
            if (choices[i] == null) {
              if (other.choices[j] == null) {
                break;
              }
            } else if (choices[i].equals(other.choices[j])) {
              break;
            }
          } // other.choices cycles
          if (j == other.choices.length) {
            return false;
          }
        }

        // other.choices has counterpart in this.choices
        for (int i = 0; i < other.choices.length; i++) {
          int j;
          for (j = 0; j < choices.length; j++) {
            if (other.choices[i] == null) {
              if (choices[j] == null) {
                break;
              }
            } else if (other.choices[i].equals(choices[j])) {
              break;
            }
          } // other.choices cycles
          if (j == choices.length) {
            return false;
          }
        }
      }
      // Passed all tests
      return true;
    }

    @Override
    public String toString () {
      return "[threadID=" + threadID + ", id=" + id + ", ins=" + ins + (ins != null ? '(' + ins.getFilePos() + ')' : "") + ", selectedChoice=" + selectedChoice
          + ", choices=" + Arrays.toString(choices) + "]";
    }

  }

  /**
   * Holds state of all CG and identification of reached state
   */
  private class TransitionMemento {
    private final int stateID; // State where transition terminates
    private final Map<String, CGStateMemento> cgs; // CG which where used to start the transition
    private final Instruction lastInst;

    public TransitionMemento (Transition tr) {
      assert tr != null;

      stateID = tr.getStateId();
      lastInst = tr.getLastStep().getInstruction();

      cgs = new HashMap<String, DefaultForwardTraceManager.CGStateMemento>();
      ChoiceGenerator<?> cg = tr.getChoiceGenerator();
      while (cg != null) {
        cgs.put(cg.getId(), new CGStateMemento(cg));
        cg = cg.getCascadedParent();
      }
    }

    @Override
    public int hashCode () {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((lastInst == null) ? 0 : lastInst.hashCode());
      result = prime * result + stateID;
      for (DefaultForwardTraceManager.CGStateMemento value : cgs.values()) {
        result += value.hashCode();
      }
      return result;
    }

    @Override
    public boolean equals (Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      TransitionMemento other = (TransitionMemento) obj;
      if (stateID != other.stateID)
        return false;

      if (lastInst == null) {
        if (other.lastInst != null)
          return false;
      } else if (!lastInst.equals(other.lastInst))
        return false;

      if (cgs == null) {
        if (other.cgs != null)
          return false;
      } else {
        if (other.cgs == null) {
          return false;
        } else {
          // check content of maps
          if (cgs.size() != other.cgs.size()) {
            return false;
          }

          for (Entry<String, CGStateMemento> entry : cgs.entrySet()) {
            CGStateMemento otherCGStateMemento = other.cgs.get(entry.getKey());
            CGStateMemento myCGStateMemento = entry.getValue();
            if (myCGStateMemento == null) {
              if (otherCGStateMemento != null) {
                return false;
              }
            } else {
              if (!myCGStateMemento.equals(otherCGStateMemento)) {
                return false;
              }
            }
          }
        }
      }
      return true;
    }

    @Override
    public String toString () {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getSimpleName() + "[stateID=" + stateID + ", lastInst=" + lastInst
          + (lastInst != null ? '(' + lastInst.getFilePos() + ')' : "") + "\n");
      for (CGStateMemento cgMem : cgs.values()) {
        sb.append('\t');
        sb.append(cgMem.toString());
        sb.append('\n');
      }
      return sb.toString();
    }

    public int getDefaultChoice (ChoiceGenerator<?> cg) {
      String cgID = cg.getId();

      CGStateMemento dftCGM = cgs.get(cgID); // DefaultForwardTravce CGM
      if (dftCGM == null) {
        // Invalid CG - different ID
        destroyTrace(false);
        return ChoiceGeneratorsInterface.NO_DEFAULT_CHOICE;
      }

      return dftCGM.getDefaultChoice(cg);
    }
  }

  private final LinkedList<TransitionMemento> forwardTrace = new LinkedList<TransitionMemento>();

  public DefaultForwardTraceManager (JPFInspector inspector) {
    this.inspector = inspector;
  }

  /**
   * Adds new transition into start of the default trace.
   * 
   * @param tr
   *        Transition which will be backtracked (by backward single stepping)
   * 
   *        // Has to be called after forward method (to be transition completely "initialized")
   */
  public void extendTrace (Transition tr) {
    if (DEBUG) {
      inspector.getDebugPrintStream().println(this.getClass().getSimpleName() + ".extendTrace(tr=" + tr + ");");
    }
    forwardTrace.push(new TransitionMemento(tr));
  }

  /**
   * Gets choice associated with given {@link ChoiceGenerator}
   * 
   * @param cg
   *        ChoiceGenerator whose previous state you are interested in.
   * @return Index of choice which should by used to follow default forward trace or {@link ChoiceGeneratorsInterface#NO_DEFAULT_CHOICE}
   * 
   *         Note: Destroy default forward trace if "invalid CG is provided" + reports info to user.
   */
  public int getDefaultChoice (ChoiceGenerator<?> cg) {
    if (DEBUG) {
      inspector.getDebugPrintStream().println(this.getClass().getSimpleName() + ".getDefaultChoice(cg=" + cg + ");");
    }

    assert (cg != null);

    if (forwardTrace.isEmpty()) {
      return ChoiceGeneratorsInterface.NO_DEFAULT_CHOICE;
    }

    TransitionMemento trMem = forwardTrace.peek();
    return trMem.getDefaultChoice(cg);
  }

  public void forwardStep (Search search) {
    if (DEBUG) {
      inspector.getDebugPrintStream().println(this.getClass().getSimpleName() + ".checkForwardStep();");
    }
    Transition tr = search.getTransition();

    if (forwardTrace.isEmpty() == false) {
      TransitionMemento dftTrMem = forwardTrace.poll();

      TransitionMemento trMem = new TransitionMemento(tr);

      if (dftTrMem.equals(trMem) == false) {
        destroyTrace(false);
      }
      // There is path in default forward trace -> force forward step
      JVM vm = search.getVM();
      vm.forceState();
    }
  }

  // User select different CG value then the default one
  public void destroyTrace (boolean silent) {
    if (DEBUG) {
      inspector.getDebugPrintStream().println(this.getClass().getSimpleName() + ".destroyTrace();");
    }
    forwardTrace.clear();
  }

  @Override
  public String toString () {
    StringBuilder sb = new StringBuilder();
    sb.append("DefaultForwardTrace\n");
    Iterator<TransitionMemento> it = forwardTrace.iterator();
    while (it.hasNext()) {
      TransitionMemento trMem = it.next();
      sb.append(trMem.toString());
    }

    return sb.toString();

  }

}
