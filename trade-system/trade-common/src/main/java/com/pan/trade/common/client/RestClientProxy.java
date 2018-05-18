package com.pan.trade.common.client;

import com.pan.trade.common.tenum.TradeEnum;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Loren on 2018/5/18.
 */
public class RestClientProxy implements FactoryBean {

    private  static RestTemplate restTemplate = new RestTemplate();

    private Class serviceInterface;

    private TradeEnum.RestServerEnum serverEnum;



    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{serviceInterface},new ClientProxy());
    }

    public Class<?> getObjectType() {
        return serviceInterface;
    }

    public boolean isSingleton() {
        return true;
    }

    public Class getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public TradeEnum.RestServerEnum getServerEnum() {
        return serverEnum;
    }

    public void setServerEnum(TradeEnum.RestServerEnum serverEnum) {
        this.serverEnum = serverEnum;
    }

    private class ClientProxy implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return restTemplate.postForObject(serverEnum.getUrl()+serverEnum.getType()+"/"+method.getName(),args[0],method.getReturnType());
        }
    }
}
