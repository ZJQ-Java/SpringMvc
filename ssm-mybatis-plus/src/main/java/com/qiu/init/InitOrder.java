package com.qiu.init;//package com.qiu.init;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.ServletContextAware;
//
//import javax.servlet.ServletContext;
//
//@Component
//public class InitOrder implements ApplicationContextAware, ServletContextAware,
//        InitializingBean, ApplicationListener<ContextRefreshedEvent> {
//
//    protected Logger logger = LogManager.getLogger();
//
//    @Override
//    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
//        logger.info( "1 => StartupListener.setApplicationContext" );
//    }
//
//    @Override
//    public void setServletContext(ServletContext context) {
//        logger.info( "2 => StartupListener.setServletContext" );
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        logger.info( "3 => StartupListener.afterPropertiesSet" );
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent evt) {
//        logger.info( "4.1 => MyApplicationListener.onApplicationEvent" );
//        if (evt.getApplicationContext().getParent() == null ) {
//            logger.info( "4.2 => MyApplicationListener.onApplicationEvent" );
//        }
//    }
//
//}
