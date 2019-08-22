package com.geely.devops.aoptest.complet;

import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.complet
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2019/8/23 0:45
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2019
 */
public class CompletTest {

    private  Map<String, List<String>> getInfo(StoreUser item){
        CompletableFuture<String> futureUp = CompletableFuture.supplyAsync(() -> {
            System.out.println("获取上级所在店铺" + item.getStoreUserCode());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "004";
        });
        CompletableFuture<List<String>> futureDown = CompletableFuture.supplyAsync(() -> {
            System.out.println("获取下级所在店铺" + item.getStoreUserCode());
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> list = new ArrayList<>();
            list.add("D");
            list.add("E");
            return list;
        });
        CompletableFuture<Map<String, List<String>>> futureResult = futureUp.thenCombine(futureDown, (storeCode, list) -> {
            System.out.println("拼接信息" + item.getStoreUserCode());
            Map<String, List<String>> mapResult = new HashMap<>();
            mapResult.put(storeCode, list);
            return mapResult;
        });
        System.out.println("最终价格为:" + futureResult.join()); //最终店铺信息
        return futureResult.join();
    }

    public static void main(String[] args) {
        StoreUser storeUser1 = new StoreUser("001", "A");
        StoreUser storeUser2 = new StoreUser("002", "B");
        StoreUser storeUser3 = new StoreUser("003", "C");
        List<StoreUser> users = Lists.newArrayList();
        List< CompletableFuture<Map<String, List<String>>>> list = new ArrayList<>();
        users.add(storeUser1);
        users.add(storeUser2);
        users.add(storeUser3);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletTest completTest =new CompletTest();
        long a  = System.currentTimeMillis();
        users.forEach(item -> {
            CompletableFuture<Map<String, List<String>>> future = CompletableFuture.supplyAsync(() -> {
              return   completTest.getInfo(item);
          },executorService);
            list.add(future);
        });

        long b  = System.currentTimeMillis();
        System.out.println("耗时："+ (b-a));
    }
}
