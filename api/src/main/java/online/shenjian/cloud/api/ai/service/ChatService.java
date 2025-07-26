package online.shenjian.cloud.api.ai.service;

import cloud.sfxs.cloud.client.cloud.dto.ai.ChatDto;

/**
 * AI 聊天服务
 */
public interface ChatService {

   Object chat(ChatDto chatDto);

   void sseChat(ChatDto chatDto);
}
