package com.cham.study3.controller;

import com.cham.study3.entity.Board;
import com.cham.study3.service.BoardService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/board/write")
    public String write() {

        return "boardWrite";
    }

    @PostMapping("/board/writePro")
    public String writePro(Board board, Model model, @RequestParam("file") MultipartFile file) throws IOException {

        boardService.write(board, file);

        model.addAttribute("message", "게시글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String list(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> list = boardService.boardList(pageable);

        int nowPage     = list.getPageable().getPageNumber();
        int startPage   = Math.max(nowPage - 4, 1);
        int endPage     = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardList";
    }

    @GetMapping("/board/view/{id}")
    public String view(@PathVariable("id")Integer id, Model model) {
        model.addAttribute("board", boardService.view(id));

        return "boardView";
    }

    @PostMapping("/board/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        boardService.delete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/update/{id}")
    public String update(@PathVariable("id")Integer id, Model model) {

        model.addAttribute("board", boardService.view(id));

        return "boardUpdate";
    }

    @PostMapping("/board/updatePro/{id}")
    public String updatePro(@PathVariable("id")Integer id, Board board, MultipartFile file) throws IOException {

        Board boardTemp = boardService.view(id);

        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }

}
