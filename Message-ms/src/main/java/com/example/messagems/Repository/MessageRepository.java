package com.example.messagems.Repository;

import com.example.messagems.Dto.MessageResponseDto;
import com.example.messagems.Model.Message;
import com.netflix.appinfo.ApplicationInfoManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface MessageRepository extends JpaRepository<Message , Long> {
    List<MessageResponseDto> findBySenderIdOrReceiverId(Long userId, Long userId1);
}
