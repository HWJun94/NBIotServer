package com.hwj.tinymq;

import java.util.concurrent.Callable;

public interface ProductorFutureTask extends Callable<Integer> {
    @Override
    Integer call() throws Exception;
}
