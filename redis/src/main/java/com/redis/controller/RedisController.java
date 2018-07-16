package com.redis.controller;

import com.redis.Util.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "redis")
public class RedisController {

//    @RequestMapping(value = "redis")
//    public String putString(@RequestParam String key, @RequestParam String value){
////        RedisForDB.getInstance().putData("userName","testRedisForJava");
////        return RedisForDB.getInstance().getDate("userName");
//        Jedis jedis = new Jedis("192.168.40.2",6379);
//        jedis.set(key,value);
//        return jedis.get(key);
//    }

    @Autowired
    RedisClient  redisClient;

    @RequestMapping(value = "key")
    public String putString(@RequestParam String key, @RequestParam String value){
//        RedisClient client = new RedisClient();
        String str= "no data";
        try{
            redisClient.set(key,value);
            str = redisClient.get(key);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return str;
    }

    @RequestMapping(value = "putList")
    public String putList(@RequestParam String key){
        List<String> list = new ArrayList<>();
        list.add("list01");
        list.add("list02");
        list.add("list03");
        list.add("list04");
        List<String> result = new ArrayList<String>();
        key ="LIST_"+key;
        System.out.println("key="+key);
        try{
            redisClient.putList(key,list);
            result = redisClient.getList(key);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        String resultStr = "no list";
        for (int i = 0; i <result.size() ; i++) {
            if (i==0){
                resultStr=result.get(0);
                continue;
            }
            resultStr = result+";"+result.get(i);
        }

        return resultStr;
    }

    @RequestMapping(value = "putMap")
    public String putMap(@RequestParam String key){
        String resultStr ="失败";
        Map<String,String> map = new HashMap<String,String>();
        map.put("name","xiaoming");
        map.put("age","10");
        key = "MAP_"+key;
        System.out.println("key="+key);
        try{
            redisClient.putMap(key,map);
            resultStr="成功";
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return resultStr;
    }
}
