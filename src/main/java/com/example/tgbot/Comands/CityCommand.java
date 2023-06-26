package com.example.tgbot.Comands;

import com.example.tgbot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class CityCommand implements WorKerCommand{
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Погода")) {
            return null;
        }
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
            sendMessage(chatId,"Погоду в каком городе вы бы хотели узнать?");
            return sendMessage(chatId,"Погоду в каком городе вы бы хотели узнать?");

    }
    private SendMessage sendMessage (long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }

    public void start(String text) {
    }
}
