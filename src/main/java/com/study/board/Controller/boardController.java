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
import org.springframework.web.multipart.MultipartFile;

@Controller
public class boardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm() {
        return "boardwrite";
    }
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception {
        boardService.write(board, file);
        model.addAttribute("message", "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
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

//    @GetMapping("/board/delete")
//    public String boardDelete(Integer id){
//        boardService.boardDelete(id);
//        return "redirect:/board/list"; // redirect: 하고서는 redirect할 페이지의 url주소를 명시
//    }
    @GetMapping("/board/delete")
    public String boardDelete(Integer id, Model model){
        boardService.boardDelete(id);

        model.addAttribute("message", "글 삭제 완료");
        model.addAttribute("searchUrl", "/board/list");
//        return "redirect:/board/list"; // redirect: 하고서는 redirect할 페이지의 url주소를 명시
        return "message";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable Integer id, Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}") //url의 id를 @PathVariable이 Integer id로 넣어줌
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception{//boardWrite와 똑같이 board로 받아옴

        Board boardTemp = boardService.boardView(id); // 기존에 작성했던 글이 담겨져서 옴
        boardTemp.setTitle(board.getTitle()); //덮어씌우기 - 기존의 내용에다가 새로 작성한 글의 내용을
        boardTemp.setContent(board.getContent()); //덮어씌우기 - 기존의 내용에다가 새로 작성한 글의 내용을

        model.addAttribute("message", "글 수정 완료"); // 수정완료 메세지 띄우기
        model.addAttribute("searchUrl", "/board/list"); //메세지 띄워주고 list페이지로 이동

        boardService.write(boardTemp, file); // 수정한 내용 레포지토리에 반영
        return "message";
    }
}
