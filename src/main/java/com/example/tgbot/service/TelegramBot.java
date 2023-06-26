package com.example.tgbot.service;

import com.example.tgbot.Comands.CityCommand;
import com.example.tgbot.Comands.LatLon;
import com.example.tgbot.Comands.Openweathermap;
import com.example.tgbot.Comands.WorKerCommand;
import com.example.tgbot.config.BotConfig;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig botConfig;

    public TelegramBot(BotConfig botConfig){
        this.botConfig=botConfig;
    }
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
    String city;
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            /*if (!update.getMessage().getText().equals("Погода")&&
                    !update.getMessage().getText().equals("/start")){
                Openweathermap oW = new Openweathermap(messageText);
                oW.Weather();
                sendMessage(chatId,oW.getAnswer());
            }*/
            if (update.getMessage().getText().equals("Погода")){
                sendMessage(chatId,"Я тут");
                new CityCommand().start(update.getMessage().getText());
                SendMessage sendMessage = new SendMessage();
                List < WorKerCommand> list = new ArrayList<>();
                list.add(new CityCommand());
                for (WorKerCommand w: list){
                    /*sendMessage.setText("Еще ищу");*/
                    if (w.start(update) != null) {
                        sendMessage = w.start(update);
                       /* sendMessage.setText("Ищу");*/
                        break;
                    }
                }
            }   else sendMessage(chatId,"Не найдено!");
            switch (messageText){
                case "/start":
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getMessage().getChatId().toString());
                    sendMessage.setText("Привет! Чем могу помочь?");
                    sendMessage(chatId,"Могу помочь?");
                    /*sendMessage(chatId,"Погоду в каком городе ты бы хотел узнать?");*/

                    Scanner scanner = new Scanner(update.getMessage().getText());
                    String city = scanner.nextLine();
                    sendMessage(chatId,city);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                       /* try {
                            startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                *//*case "Погода":
                    Openweathermap oW = new Openweathermap("Мальдивы");
                    oW.Weather(chatId,update.getMessage().getChat().getFirstName());*//*
                    break;
                default: sendMessage(chatId,"Прости брат, такой функции еще нет");*/
                default: sendMessage(chatId, "Прости брат, такой функции еще нет");
            }
        }
    }

    private void startCommandReceived(long chatId,String name) throws IOException {
        Openweathermap oW = new Openweathermap(city);
        oW.Weather();
        sendMessage(chatId,oW.getAnswer());
        /*Document doc = Jsoup.connect("https://world-weather.ru/pogoda/belarus/grodno/24hours/").get();
        Elements table = doc.select("table.weather-today");
        String element = table.select("tr.day.hourly-1").first().text();
        String element1 = doc.select("div[class=\"wi d440 wi-v tooltip\"]").attr("title");

        String answer = name + " Сейчас в Гродно " + element1 +" "+ element;

        sendMessage(chatId, answer);*/
    }
    private void sendMessage (long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e){

        }
    }
}
