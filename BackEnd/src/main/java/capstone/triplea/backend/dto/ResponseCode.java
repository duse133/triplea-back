package capstone.triplea.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCode {
    private String Code;
    private String Msg;
    private String Url;
}
