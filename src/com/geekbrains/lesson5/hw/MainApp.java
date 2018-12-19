package com.geekbrains.lesson5.hw;

import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {

        createMassSingleThread();
        createMassTwoThreads();

/*
        oneThread();
        twoThreads();
*/


    }

    public static void oneThread() {
        final int SIZE = 10_000_000;
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("time: " + (endTime - startTime));
    }

    public static void twoThreads() {
        final int SIZE = 10_000_000;
        final int HALF_SIZE = SIZE / 2;

        float[] arr = new float[SIZE];
        float[] a1 = new float[HALF_SIZE];
        float[] a2 = new float[HALF_SIZE];

        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, a2, 0, HALF_SIZE);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF_SIZE; i++) {
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF_SIZE; i++) {
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + (HALF_SIZE + i) / 5) * Math.cos(0.2f + (HALF_SIZE + i) / 5) * Math.cos(0.4f + (HALF_SIZE + i) / 2));
                }
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, HALF_SIZE);
        System.arraycopy(a2, 0, arr, HALF_SIZE, HALF_SIZE);

        long endTime = System.currentTimeMillis();

        System.out.println("Two thread time: " + (endTime - startTime));
    }

    static void createMassSingleThread() {
        final int size = 30_000_000;
        final int h = size / 2;
        float[] arr = new float[size];
        for (float value : arr) {
            value = 1;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long finishTime = System.currentTimeMillis();

        System.out.println("work time: " + (finishTime - startTime));
    }

    static void createMassTwoThreads() {
        final int size = 30_000_000;
        final int quarta = size / 4;
        float[] arr = new float[size];
        for (float value : arr) {
            value = 1;
        }

        long startTime = System.currentTimeMillis();
        float[] a1 = new float[quarta];
        float[] a2 = new float[quarta];
        float[] a3 = new float[quarta];
        float[] a4 = new float[quarta];
        System.arraycopy(arr, 0, a1, 0, quarta);
        System.arraycopy(arr, quarta, a2, 0, quarta);
        System.arraycopy(arr, 2*quarta, a3, 0, quarta);
        System.arraycopy(arr, 3*quarta, a4, 0, quarta);
        long copy1FinishTime = System.currentTimeMillis();


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = (float) (Math.sin(0.2f + (quarta + i)/5)*Math.cos(0.2f + (quarta + i)/5)*Math.cos(0.4f + (quarta + i)/2));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float) (Math.sin(0.2f + (quarta + i)/5)*Math.cos(0.2f + (quarta + i)/5)*Math.cos(0.4f + (quarta + i)/2));
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a3.length; i++) {
                    a3[i] = (float) (Math.sin(0.2f + (quarta + i)/5)*Math.cos(0.2f + (quarta + i)/5)*Math.cos(0.4f + (quarta + i)/2));
                }
            }
        });
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a4.length; i++) {
                    a4[i] = (float) (Math.sin(0.2f + (quarta + i)/5)*Math.cos(0.2f + (quarta + i)/5)*Math.cos(0.4f + (quarta + i)/2));
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long copy2StartTime = System.currentTimeMillis();
        System.arraycopy(a1, 0, arr, 0, quarta);
        System.arraycopy(a2, 0,arr, quarta, quarta);
        System.arraycopy(a3, 0,arr, 2*quarta, quarta);
        System.arraycopy(a4, 0,arr, 3*quarta, quarta);

        long finishTime = System.currentTimeMillis();

        System.out.println("4 threads - work time: " + (finishTime - startTime));
        System.out.println("4 threads - copy1 time: " + (copy1FinishTime - startTime));
        System.out.println("4 threads - copy2 time: " + (finishTime - copy2StartTime));
        System.out.println("4 threads - calc time: " + (copy2StartTime - copy1FinishTime));


    }
}
