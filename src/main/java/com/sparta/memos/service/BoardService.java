package com.sparta.memos.service;

import com.sparta.memos.dto.BoardRequestDto;
import com.sparta.memos.dto.BoardResponseDto;
import com.sparta.memos.entity.Board;
import com.sparta.memos.entity.User;
import com.sparta.memos.repository.BoardRepository;
import com.sparta.memos.repository.UserRepository;
import com.sparta.memos.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class BoardService {//

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public void save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardRequestDto boardRequestDto) {

        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());

        Board board = new Board();
        board.setUser(user);
        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());

        boardRepository.save(board);
    }

}
