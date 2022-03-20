package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.entity.User;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    //새글 작성
    public void write(Board board, MultipartFile file, String username) throws Exception {
        // 파일을 업로드할 저장경로부터 지정
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        // 파일 이름에 붙여줄 랜덤id를 생성
        UUID uuid = UUID.randomUUID();
        // 위에 랜덤uuid와 원래 파일명을 합쳐서 파일이름 정의
        String fileName = uuid + "_" + file.getOriginalFilename();
        // File클래스로 new객체생성으로 위에서 정한 파일저장경로, 파일명 선언
        File saveFile = new File(projectPath, fileName); // 파일을 생성하는데 projectPath경로에 fileName이란 이름으로 담을거란 의미
        // transferTo는 에러가 뜨는데 위에 Throws Exception 해주면 됨
        file.transferTo(saveFile);

        board.setFilename(fileName); // DB에다가 fileName을 저장하게 함
        board.setFilepath("/files/" + fileName); // DB에다가 file경로를 저장하게 함

        User user = userRepository.findByUsername(username);
        board.setUser(user);

        boardRepository.save(board);
    }

    //게시글 리스트
//    public List<Board> boardList(Pageable pageable) {
    public Page<Board> boardList(Pageable pageable) { // 페이징처리를 위해 반환타입을 List말고 Page로 바꿔주고 임포트
        return boardRepository.findAll(pageable);
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

//    public Page<Board> boardSearchUserList(String searchUser, Pageable pageable){ // User 검색기능 구현 연습
//
//        return boardRepository.findByUserContaining(searchUser, pageable);
//    }

    //특정 게시글 조회 - 클래스를 Board로 해줘야 함
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
