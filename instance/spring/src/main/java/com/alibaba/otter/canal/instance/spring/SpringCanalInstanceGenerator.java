package com.alibaba.otter.canal.instance.spring;

import com.alibaba.otter.canal.instance.core.CanalInstance;
import com.alibaba.otter.canal.instance.core.CanalInstanceGenerator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author zebin.xuzb @ 2012-7-12
 * @version 1.0.0
 */
public class SpringCanalInstanceGenerator implements CanalInstanceGenerator, BeanFactoryAware {

    private String defaultName = "instance";
    private BeanFactory beanFactory;

    public CanalInstance generate(String destination) {
        String beanName = destination;
        //首先判断beanFactory是否包含以destination为id的bean
        if (!beanFactory.containsBean(beanName)) {
            //如果没有，设置要获取的bean的id为"instance"。
            beanName = defaultName;
        }
        //以默认的bean的id值"instance"来获取CanalInstance实例
        return (CanalInstance) beanFactory.getBean(beanName);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
