package com.hwj.tinymq;

public interface ProductorTask extends Runnable {
    @Override
    void run();
}
