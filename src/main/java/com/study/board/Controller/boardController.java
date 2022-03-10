package com.study.board.Controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/board/write")
    public String boardWriteForm() {
        return "boardwrite";
    }
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception {
        boardService.write(board, file);
        model.addAttribute("message", "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");
        model.addAttribute("image", "/board/view"); //상세페이지에서 이미지보이게
        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model, // 0페이지가 1페이지, 한번에 10개의 게시글 보이게, 정렬은 id로, 정렬순서는 역순(DESC)
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1; // pageable에서 넘어온 현재페이지를 담아줌 - 1을 더해줘야 0페이지가 1페이지가 됨
        int startPage = Math.max(nowPage - 2, 1); // start페이지는 1일 수 밖에 없으므로 Math.max를 활용하여 매개값 2개중 1이 반환될 수 있도록 처리
        int endPage = Math.min(nowPage + 2, list.getTotalPages()); // 위와 같은 원리로 마지막페이지 처리 - getTotalPages()는 전체 페이지의 수를 말함

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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
