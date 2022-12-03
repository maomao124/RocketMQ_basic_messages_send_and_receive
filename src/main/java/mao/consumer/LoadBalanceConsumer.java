package mao.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Project name(项目名称)：RocketMQ_基本消息的发送与接收
 * Package(包名): mao.consumer
 * Class(类名): LoadBalanceConsumer
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/12/3
 * Time(创建时间)： 19:10
 * Version(版本): 1.0
 * Description(描述)： 消费者-负载均衡模式
 */

public class LoadBalanceConsumer
{
    public static void main(String[] args) throws MQClientException
    {
        //创建消费者
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("mao_group");
        //设置nameserver地址
        defaultMQPushConsumer.setNamesrvAddr("127.0.0.1:9876");
        //订阅topic
        defaultMQPushConsumer.subscribe("test_topic", "*");
        //设置消费模式-负载均衡
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //注册监听器，处理消息
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently()
        {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext)
            {
                //打印
                System.out.println("线程" + Thread.currentThread().getName() + "消费消息：" + list);
                //返回成功的状态
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        defaultMQPushConsumer.start();
    }
}
