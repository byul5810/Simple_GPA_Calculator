package com.example.dkim5.gpa;

import java.io.Serializable;


public class Item implements Serializable
{
    private double credit;
    private double num;

    public Item()
    {

        credit=-1;
        num=-1;
    }


    public double getcredit()
    {
        return credit;
    }
    public void setCredit(double credit)
    {
        this.credit=credit;
    }

    public double getnum()
    {
        return num;
    }

    public void setnum(double num)
    {
        this.num=num;
    }


}
