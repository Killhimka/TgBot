package com.example.tgbot.Comands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
public class Weather implements CityIntCommand {
    @Override
    public SendMessage start(Update update) throws IOException {
        if (update.getMessage().getText().equals("Погода")&&
        update.getMessage().getText().equals("/start")) {
            return null;
        }
        String city = update.getMessage().getText();
        Openweathermap openweathermap = new Openweathermap(city);
        openweathermap.Weather();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(openweathermap.Weather());
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
