package com.games.gamingmessenger;



public class Listing {

    static Listing list;

    public static Listing getInstance()
    {
        if(list==null)
        {
            list=new Listing();
        }
        return list;
    }

}
