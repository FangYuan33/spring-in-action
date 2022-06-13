package flow;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * MessagingGateway告诉Spring在运行时生成该接口的实现
 * <p>
 * defaultRequestChannel: 接口方法调用时所返回的消息要发送给指定的通道
 */
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {

    /**
     * @param fileName Header注解表明给fileName的值在在消息头中
     * @param data     写入的文本数据
     */
    void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String data);
}
