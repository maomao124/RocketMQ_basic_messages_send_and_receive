package mao.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * Project name(项目名称)：RocketMQ_基本消息的发送与接收
 * Package(包名): mao.producer
 * Class(类名): SyncProducer
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/12/3
 * Time(创建时间)： 17:01
 * Version(版本): 1.0
 * Description(描述)： 生产者-同步消息
 */

public class SyncProducer
{
    public static void main(String[] args)
            throws MQClientException, MQBrokerException, RemotingException, InterruptedException
    {
        //生产者，参数为生产者组名
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("mao_group");
        //nameserver地址
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        //启动
        defaultMQProducer.start();
        //发送300条消息
        for (int i = 0; i < 300; i++)
        {
            //创建消息对象，参数分别是话题和消息体
            Message message = new Message("test_topic", ("hello" + i).getBytes(StandardCharsets.UTF_8));
            //发送消息，获取发送结果
            SendResult sendResult = defaultMQProducer.send(message);
            //直接打印发送结果
            System.out.println(sendResult);
            System.out.println();
        }
        //关闭
        defaultMQProducer.shutdown();

    }
}
