import com.orpheus.game.GameOperationLogicUtility;

/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */

public class TestThread {

    /**
     *@param args
     */
    public static void main(String[] args) {
        
        new TestThread().test();
    }
    
    private void test(){
        new Foo().start();
        new Foo().start();
        String a;
        System.out.print("o");
    }

    public class Foo extends Thread{

        public void run() {
            GameOperationLogicUtility.getInstance();
        }
        
        
        
    }
    
}

