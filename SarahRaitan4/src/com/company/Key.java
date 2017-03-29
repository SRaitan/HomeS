package com.company;

/**
 * Created by hackeru on 3/20/2017.
 */
public interface Key {
    Object getKey();
}
//region listener
/*
class EncDecAction{

    static EncDecActionListener listener;

    public static void setListener(EncDecActionListener listener) {EncDecAction.listener = listener;}

    public void started (){
        if(listener != null)
            listener.started();
    }
    public void finished (){
        if(listener != null)
            listener.finished();
    }

    void action( int key, boolean encrypt){
        started();
        //everything else in the middle.. encryption, decryption etc.....
        finished();

    }

    interface EncDecActionListener{
        void started();
        void finished();
    }
}
*/
//endregion
