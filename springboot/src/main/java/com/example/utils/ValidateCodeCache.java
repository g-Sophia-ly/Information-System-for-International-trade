package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidateCodeCache {

    private static final Logger log = LoggerFactory.getLogger(ValidateCodeCache.class);
    private static List<CodeCache> codeCache = new ArrayList<>();
    public static void setCache(String key, String code){
        CodeCache cache = new CodeCache();
        cache.setKey(key);
        cache.setCode(code);
        cache.setTimestamp(System.currentTimeMillis());
        codeCache.add(cache);
        log.info("当前的验证码缓存：{}",codeCache);
    }

    public static boolean validateCode(String key,String code){
        return codeCache.stream().anyMatch(cache -> cache.getKey().equals(key)&& cache.getCode().equalsIgnoreCase(code));
    }

    @Scheduled(fixedRate = 60000) //1分钟清理一次
    public void task(){
        log.info("============开始清理验证码缓存，验证码集合缓存长度:"+codeCache.size()+"===============");
        List<CodeCache> codeList = codeCache.stream().filter(cache ->{
            long timestamp =cache.getTimestamp();
            long duration =System.currentTimeMillis() - timestamp;
            return duration > 120000; //两分钟过期
        }).collect(Collectors.toList());
        codeCache.removeAll(codeList); //清除过期缓存
        if(codeCache.size()>1024){ //当缓存长度到达阈值，清空所有缓存
            codeCache.clear();
        }
        log.info("============清理验证码缓存结束，验证码集合缓存长度:"+codeCache.size()+"===============");
    }
}
