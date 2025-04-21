package org.my.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DtoMessage {
    private String text;
    private String dateTime;
    private String userName;
}