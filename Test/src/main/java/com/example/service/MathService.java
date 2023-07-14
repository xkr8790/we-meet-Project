package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class MathService {
    public double average(byte... nums){
        int sum=0;
        for(byte num : nums){
            sum += num;
        }
        return (double) sum/ nums.length;
    }
    public double average(int... nums){
        int sum=0;
        for(int num : nums){
            sum += num;
        }
        return (double) sum/ nums.length;
    }
    public double average(long... nums){
        int sum=0;
        for(long num : nums){
            sum += num;
        }
        return (double) sum/ nums.length;
    }
    public double average(float... nums){
        int sum=0;
        for(float num : nums){
            sum += num;
        }
        return (double) sum/ nums.length;
    }
    public double average(double... nums){
        int sum=0;
        for(double num : nums){
            sum += num;
        }
        return (double) sum/ nums.length;
    }

    public int countCollatz(int nums){
        int a=0;
        while( nums !=1 ){
            if(nums%2==0){
                nums /=2;
            }else{
               nums= nums*3+1;
            }
            a++;
        }
        return  a;
    }

    public boolean inPrime(int nums){

            if(nums/2 !=0){
                return true;
            }else{
                return false;
            }
    }



    public byte max(byte... nums){}
    public int max(int... nums){}
    public long max(long... nums){}
    public float max(float... nums){}
    public double max(double... nums){}
    public byte min(byte... nums){}
    public int min(int... nums){}
    public long min(long... nums){}
    public float min(float... nums){}
    public double min(double... nums){}
    public byte sum(byte... nums){}
    public int sum(int... nums){}
    public long sum(long... nums){}
    public float sum(float... nums){}
    public double sum(double... nums){}






}
