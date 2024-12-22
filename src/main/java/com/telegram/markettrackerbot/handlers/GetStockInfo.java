package com.telegram.markettrackerbot.handlers;

import com.telegram.markettrackerbot.models.MessageResponse;
import com.telegram.markettrackerbot.models.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class GetStockInfo extends UserRequestHandler {
  @Override
  public boolean isApplicable(UserRequest request) {
    return false;
  }

  @Override
  public MessageResponse handle(UserRequest request) {
    return null;
  }
}
