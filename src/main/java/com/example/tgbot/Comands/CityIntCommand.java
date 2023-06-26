package com.example.tgbot.Comands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface CityIntCommand {
    public SendMessage start(Update update) throws IOException;
    public SendMessage sendDefaultMessage(Update update);
}
