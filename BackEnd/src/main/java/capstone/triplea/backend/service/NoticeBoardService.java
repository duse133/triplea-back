package capstone.triplea.backend.service;

import capstone.triplea.backend.dto.NoticeBoardDTO;
import capstone.triplea.backend.entity.NoticeBoard;
import capstone.triplea.backend.exception.CNoticeNotFound;
import capstone.triplea.backend.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;

    //게시판 글 조회
    public List<NoticeBoardDTO> getNoticeBoard(){
        List<NoticeBoard> noticeBoardList = this.noticeBoardRepository.findAll();
        List<NoticeBoardDTO> noticeBoardDTOList = new ArrayList<>();

        for (NoticeBoard noticeBoard : noticeBoardList) {
            noticeBoardDTOList.add(convertToDTO(noticeBoard));
        }
        return noticeBoardDTOList;

    }

    //게시판 글 작성
    public NoticeBoardDTO makeNotice(String title, String date, String contents, String password){
        String encryptedPassword = hashPassword(password);
        NoticeBoard newNotice = NoticeBoard.builder()
                .title(title)
                .date(date)
                .contents(contents)
                .password(encryptedPassword)
                .build();
        NoticeBoard savedNotice = noticeBoardRepository.save(newNotice);
        return convertToDTO(savedNotice);
    }

    // 게시판 글 수정
    public NoticeBoardDTO updateNotice(int noticeId, String newTitle, String newContents) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeId)
                .orElseThrow(CNoticeNotFound::new);

        noticeBoard.setTitle(newTitle);
        noticeBoard.setContents(newContents);
        noticeBoardRepository.save(noticeBoard);

        return convertToDTO(noticeBoard);
    }
    
    // 게시판 글 삭제
    public void deleteNotice(int id) {
        noticeBoardRepository.deleteById(id);
    }

    //비밀번호 조회
    public boolean verifyPassword(int noticeId, String inputPassword) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeId)
                .orElseThrow(CNoticeNotFound::new);
        String hashedInputPassword = hashPassword(inputPassword);
        return hashedInputPassword.equals(noticeBoard.getPassword());
    }

    //엔티티를 DTO로 변환하는 메소드
    private NoticeBoardDTO convertToDTO(NoticeBoard noticeBoard) {
        return NoticeBoardDTO.builder()
                .id(noticeBoard.getId())
                .title(noticeBoard.getTitle())
                .date(noticeBoard.getDate())
                .contents(noticeBoard.getContents())
                .password(noticeBoard.getPassword())
                .build();
    }

    //password 암호화
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] byteData = md.digest();

            // byte 배열을 16진수 문자열로 변환
            Formatter formatter = new Formatter();
            for (byte b : byteData) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // 암호화 실패 시 기본값으로 반환
            return password;
        }
    }


}
