package com.hwj.tinymq;

public interface ConsumerTask extends Runnable {
    @Override
    void run();
}
