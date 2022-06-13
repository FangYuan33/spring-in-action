package flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {

    /**
     * 转换器 使得从 channel 进去的文本都变大写的
     */
    @Bean
    @Profile(value = "local")
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return String::toUpperCase;
    }

    /**
     * ServiceActivator 表示接受来自fileWriterChannel的消息，并交给
     * FileWritingMessageHandler来处理，它会将消息写入一个特定的目录
     * 而文件的名字则是通过消息的头信息 file_name 指定的
     */
    @Bean
    @Profile(value = "local")
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/Users/wangyilong/GitHub"));

        // 不期望有返回值
        handler.setExpectReply(false);

        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }
}
