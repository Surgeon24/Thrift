package m.ermolaev.thrift.rabbitmq;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("message")
    private String message;
    // ...
}