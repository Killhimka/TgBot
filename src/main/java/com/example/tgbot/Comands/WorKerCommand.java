package com.example.tgbot.Comands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface WorKerCommand {
    public SendMessage start(Update update);
    public SendMessage sendDefaultMessage(Update update);
}
