package com.github.shardingsphere.paper.cornucopia.jdbctest.executor;

import com.github.shardingsphere.paper.cornucopia.jdbctest.cases.SysbenchBenchmark;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class BenchmarkExecutor implements Runnable {

    private final SysbenchBenchmark sysbenchBenchmark;

    private final ConcurrentLinkedQueue<Long> queue;
    private final ConcurrentLinkedQueue<Long> errorResponseTimeQueue;

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            long start = System.nanoTime();
            try {
                sysbenchBenchmark.execute();
                queue.add(System.nanoTime() - start);
            } catch (Exception e) {
                // System.err.println("Execute Error: " + e.getMessage());
                errorResponseTimeQueue.add(System.nanoTime() - start);
            }
        }
    }
}
