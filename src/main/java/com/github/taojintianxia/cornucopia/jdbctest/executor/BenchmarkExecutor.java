package com.github.taojintianxia.cornucopia.jdbctest.executor;

import com.github.taojintianxia.cornucopia.jdbctest.cases.SysbenchBenchmark;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class BenchmarkExecutor implements Runnable {

    private final SysbenchBenchmark sysbenchBenchmark;

    private final ConcurrentLinkedQueue<Long> queue;

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            long start = System.nanoTime();
            try {
                sysbenchBenchmark.execute();
            }catch (Exception e){
                e.printStackTrace();
            }
            queue.add(System.nanoTime() - start);
        }
    }
}
