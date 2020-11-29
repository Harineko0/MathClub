package jp.mincra.mathclub.objects;

import java.util.ArrayList;

public class testArrayList {

    public static void testArrayList(){
        Thread thread1 = new Thread();
        thread1.subject = "test";
        thread1.url = "https";
        Thread thread2 = new Thread();
        thread2.subject = "aaa";
        thread2.url = "1234";

        ArrayList threadList = new ArrayList<Thread>();
        threadList.add(thread1);
        threadList.add(thread2);

        display(threadList);
    }

    public static void display(ArrayList<Thread> threadArrayList) {
        for(Thread thread : threadArrayList){
            System.out.println("---------\n"+thread.subject+thread.url);
        }
    }
}
