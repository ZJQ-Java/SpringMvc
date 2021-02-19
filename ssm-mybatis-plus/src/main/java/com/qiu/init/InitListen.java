package com.qiu.init;//package com.qiu.init;
//
//import com.qiu.filter.RequestFilter;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//@Component
////监听器可以使用component 或者 xml注入 即可生效
//public class AppInit implements ApplicationListener<ContextRefreshedEvent> {
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        //spring 启动的时候缓存城市和国家等信息
//        System.out.println(event.getApplicationContext().getDisplayName());
//        if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
//            System.out.println("init app");
//            RequestFilter.initLog();
//            //redisCache.setCacheIntegerMap("cityMap", cityMap);
//            //redisCache.setCacheIntegerMap("countryMap", countryMap);
//        }
//    }
//}
