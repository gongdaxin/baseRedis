package com.redis.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@Component("redisClient")
public class RedisClient {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 普通键值对存入redis
     * @param key
     * @param value
     * @throws Exception
     */
    public void set(String key, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * 取出普通键值对
     * @param key
     * @return
     * @throws Exception
     */
    public String get(String key) throws Exception  {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * 存入list
     * @param key
     * @param list
     */
    public void putList(String key,List<String> list){

        if (list==null||list.size()==0)
        {
            System.out.println("list is null");
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            for (int i=0;i<list.size();i++){
                jedis.lpush(key, list.get(i));
            }

        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * 获取redis所存的list
     * @param key
     * @return
     */
    public List<String> getList(String key){

        Jedis jedis = null;
        List<String> list = null;
        try {
            jedis = jedisPool.getResource();
            list =jedis.lrange(key,0,-1);
        } finally {
            //返还到连接池
            jedis.close();
        }
        return list;
    }

    /**
     * 往redis中存map
     * @param key
     * @param map
     */
    public void putMap(String key,Map<String,String> map){
        if (map==null||map.size()==0){
            System.out.println("map is null");
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            jedis.hmset(key,map);
        } finally {
            //返还到连接池
            jedis.close();
        }

    }

    /**
     * 从redis中获取map
     * @param key
     *
     */
    public void getMap(String key){

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

//            Map<String,String> map = jedis.hmget(key);
        } finally {
            //返还到连接池
            jedis.close();
        }

    }
}
