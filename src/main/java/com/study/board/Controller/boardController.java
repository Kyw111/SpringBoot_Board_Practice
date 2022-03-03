package com.study.board.Controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class boardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm() {

        return "boardwrite";
    }
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board) {

        boardService.Write(board);
        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("list",boardService.boardList());
        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list"; // redirect: 하고서는 redirect할 페이지의 url주소를 명시
    }
}
