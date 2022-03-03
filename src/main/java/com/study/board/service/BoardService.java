package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //새글 작성
    public void write(Board board) {
        boardRepository.save(board);
    }

    //게시글 리스트
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    //특정 게시글 조회 - 클래스를 Board로 해줘야 함
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }
    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
