package com.course_blogging.user_service;

public class trycatch {
    public static void main(String[] args) {
        try {
            int i=10/0;
            System.out.println("try is executing "+i);
        }/*catch (Exception e){
            System.out.println("catch block was running"+e);
        }*/finally {
            System.out.println("finally block was running");
        }
    }
}
