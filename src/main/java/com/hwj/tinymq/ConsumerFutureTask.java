package com.hwj.tinymq;

import java.util.concurrent.Callable;

public interface ConsumerFutureTask extends Callable<Integer>{
    @Override
    Integer call() throws Exception;
}
