package mao.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * Project name(项目名称)：RocketMQ_基本消息的发送与接收
 * Package(包名): mao.producer
 * Class(类名): AsyncProducer
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/12/3
 * Time(创建时间)： 17:21
 * Version(版本): 1.0
 * Description(描述)： 生产者-异步消息
 */

public class AsyncProducer
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
        //设置当异步消息发送失败时，消息重试的时间
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        //发送300条异步消息
        for (int i = 300; i < 600; i++)
        {
            //创建消息对象，参数分别是主题和消息体
            Message message = new Message("test_topic", ("hello" + i).getBytes(StandardCharsets.UTF_8));
            //发送消息，获取发送结果
            defaultMQProducer.send(message, new SendCallback()
            {
                /**
                 * 成功
                 *
                 * @param sendResult 发送结果
                 */
                @Override
                public void onSuccess(SendResult sendResult)
                {
                    //直接打印发送结果
                    System.out.println(sendResult);
                    System.out.println();
                }

                /**
                 * 异常
                 *
                 * @param throwable throwable
                 */
                @Override
                public void onException(Throwable throwable)
                {
                    //打印异常
                    throwable.printStackTrace();
                }
            });

        }
    }
}
