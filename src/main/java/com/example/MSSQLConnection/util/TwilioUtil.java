package com.example.MSSQLConnection.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioUtil {
    public static final String ACCOUNT_SID = "AC692db15dd58b1771eb6d2e58e16c0323";
    public static final String AUTH_TOKEN = "0206cac26e8cf8498d931613f10fec5d";

    public static void sendSMS(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(
                        new PhoneNumber("+905435974152"), new PhoneNumber("+14436028837") ,
                        "Happy day"
                )
                .create();

        System.out.println(message.getSid());
    }
}