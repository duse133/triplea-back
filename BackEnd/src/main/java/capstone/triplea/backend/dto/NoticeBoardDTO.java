package capstone.triplea.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NoticeBoardDTO {
    private int id;
    private String title;
    private String date;
    private String contents;
    private String password;
}
