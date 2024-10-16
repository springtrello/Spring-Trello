package com.sparta.springtrello.domain.card.controller;

import com.sparta.springtrello.domain.card.dto.CardArrangeRequestDto;
import com.sparta.springtrello.domain.card.dto.CardRequestDto;
import com.sparta.springtrello.domain.card.dto.CardResponseDto;
import com.sparta.springtrello.domain.card.dto.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.service.CardService;
import com.sparta.springtrello.domain.common.Auth;
import com.sparta.springtrello.domain.common.AuthUser;
import com.sparta.springtrello.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trello")
public class CardController {
    private final CardService cardService;

    @PostMapping("/list/{id}/card")
    public ResponseEntity<CardResponseDto> createCard(@PathVariable("id") Long id, @RequestBody CardRequestDto requestDto, @Auth AuthUser authUser){
        CardResponseDto responseDto = cardService.createCard(id, requestDto, authUser);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/list/{listId}/card/{cardId}")
    public ResponseEntity<Long> deleteCard(@Auth AuthUser authUser,@PathVariable("listId") Long listId, @PathVariable("cardId") Long cardId){
        Long id = cardService.deleteCard(authUser,listId, cardId);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/list/{id}/card")
    public ResponseEntity<List<CardResponseDto>> viewAllCardByListId(@Auth AuthUser authUser,@PathVariable Long id){
        List<CardResponseDto> responseDtoList = cardService.viewAllCardByListId(authUser,id);
        return ResponseEntity.ok(responseDtoList);
    }

    @PatchMapping("/list/card")
    public ResponseEntity<Long> arrangeCard(@RequestBody CardArrangeRequestDto requestDto, HttpServletRequest request){
        String email = (String) request.getAttribute("email");
        Long id = cardService.arrangeCard(requestDto, email);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/list/card/{id}")
    public ResponseEntity<CardResponseDto> checkCard(@PathVariable Long id){
        CardResponseDto responseDto = cardService.checkCard(id);
        return ResponseEntity.ok(responseDto);
    }


    // 담당자 멤버 추가 api

    @PatchMapping("list/card/{id}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long id, @RequestBody CardUpdateRequestDto requestDto, @Auth AuthUser authUser){
        CardResponseDto responseDto = cardService.updateCard(id, requestDto, authUser);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/list/archive/{cardId}")
    public ResponseEntity<CardResponseDto> archiveCard(@PathVariable Long cardId, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        CardResponseDto responseDto = cardService.archiveCard(cardId, email);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/list/restore/{cardId}")
    public ResponseEntity<CardResponseDto> RestoreCard(@PathVariable Long cardId, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        CardResponseDto responseDto = cardService.unarchiveCard(cardId, email);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/list/archive/{cardId}")
    public ResponseEntity<List<CardResponseDto>> getArchiveCardList(@Auth AuthUser authUser,@PathVariable Long cardId) {
       return ResponseEntity.ok(cardService.getActiveCardsByList(authUser,cardId));
    }
}
