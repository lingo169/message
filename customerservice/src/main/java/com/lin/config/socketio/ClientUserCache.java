//package com.lin.config.socketio;
//
//import com.corundumstudio.socketio.SocketIOClient;
//import com.lin.common.constant.RedisConstant;
//import org.redisson.api.RBucket;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class ClientUserCache {
//    private static Logger LOG = LoggerFactory.getLogger(ClientUserCache.class);
//    @Autowired
//    private RedissonClient redissonClient;
//
//    //本地缓存
//    private static Map<String, HashMap<UUID, SocketIOClient>> concurrentHashMap = new ConcurrentHashMap<>();
//
//
//    /**
//     * 存入redis缓存中，用于
//     *
//     * @param userId         用户ID
//     * @param socketIOClient 页面对应的通道连接信息
//     */
//    public void saveClient(String userId, SocketIOClient socketIOClient) {
//        LOG.info("hashCode:{}",socketIOClient.hashCode());
////        socketIOClient.set(userId,socketIOClient);
////        SocketIOClient sc=socketIOClient.get(userId);
////        LOG.info("hashCode:{}",sc.hashCode());
////        RBucket<SocketIOClient> rb = redissonClient.getBucket(RedisConstant.CUSTOMER_SOCKETIOCLIENT+userId);
////        rb.set(socketIOClient);
////        rb.expire(1, TimeUnit.HOURS);
////        HashMap<UUID, SocketIOClient> sessionIdClientCache = concurrentHashMap.get(userId);
////        if (sessionIdClientCache == null) {
////            sessionIdClientCache = new HashMap<>();
////        }
////        sessionIdClientCache.put(socketIOClient.getSessionId(), socketIOClient);
////        concurrentHashMap.put(userId, sessionIdClientCache);
//    }
//
//    /**
//     * 根据用户ID获取所有通道信息
//     *
//     * @param userId
//     * @return
//     */
//    public HashMap<UUID, SocketIOClient> getUserClient(String userId) {
//        return concurrentHashMap.get(userId);
//    }
//
//    /**
//     * 根据用户ID及页面sessionID删除页面链接信息
//     *
//     * @param userId
//     */
//    public void deleteSessionClient(String userId) {
//        RBucket<SocketIOClient> rb = redissonClient.getBucket(RedisConstant.CUSTOMER_SOCKETIOCLIENT+userId);
//        rb.delete();
//    }
//
//    public static void main(String[] args) {
//        Set<String> s=new HashSet<>();
//        s.add("aa");
//        s.add("aa");
//        Iterator<String> i=s.iterator();
//        while (i.hasNext()){
//            System.out.println(i.next());
//        }
//    }
//}