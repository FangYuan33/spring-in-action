package tacos.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// @JmxEndpoint 这个注解标注后只会暴露给MBean
// @WebEndpoint 这个注解标注后只会开放web接口而不会暴露为MBean
@Component
@Endpoint(id = "notes")
public class NotesEndpoint {

    private final List<String> notes = new ArrayList<>();

    @ReadOperation
    public List<String> notes() {
        return notes;
    }

    @WriteOperation
    public List<String> addNote(String text) {
        notes.add(text);

        return notes;
    }

    @DeleteOperation
    public List<String> deleteNode(String text) {
        notes.remove(text);

        return notes;
    }
}
