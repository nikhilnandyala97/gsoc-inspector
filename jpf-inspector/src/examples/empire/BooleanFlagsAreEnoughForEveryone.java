///
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
///

package empire;

public class BooleanFlagsAreEnoughForEveryone {
  static boolean flag;
  public static void main(String[] args) throws InterruptedException {
    Thread1 thread1 = new Thread1();
    Thread1 thread2 = new Thread1();
    thread1.start();
    thread2.start();
    thread1.join();
    thread2.join();
  }
  static class Thread1 extends Thread {
    @Override
    public void run() {
      ParallelWizard.criticalSection();
      /*
      while (flag != false) {
        ;
      }*/
      flag = true;
      ParallelWizard.criticalSection();
      flag = false;
    }
  }
}
