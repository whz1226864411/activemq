package com.creat;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Administrator on 2017-12-24.
 */
public class Consumer {
    //默认用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        //实例化工厂
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(Consumer.USERNAME, Consumer.PASSWORD, Consumer.BROKERURL);
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个消息队列
            Destination destination = session.createQueue("hello");
            MessageConsumer messageConsumer = session.createConsumer(destination);
            while (true){
                TextMessage textMessage = (TextMessage) messageConsumer.receive(10000);
                if (textMessage != null){
                    System.out.println(textMessage.getText());
                }else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
