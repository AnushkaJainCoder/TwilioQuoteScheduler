package com.twilio.sdk;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;

import org.json.JSONObject;



public class Main {
    public static final String ACCOUNT_SID = "ACcca6af313ab8940cfe137334bccb5e0e";
    public static final String AUTH_TOKEN = "64999f83a9c3bc2d3de5ac3a5bc59382";
    public static final String twilioPhoneNo = "+15202241106";
    public static final String Personal = "+917302416699";
    
    private static String sendSMS() {
        String quote;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet("https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {

                HttpEntity entity = response.getEntity();

                if (entity != null) {

                    String result = EntityUtils.toString(entity);

                    JSONObject jsonObject = new JSONObject(result);

                    quote = jsonObject.getString("quoteText");

                } else {

                    throw new IllegalStateException("No content received");

                }

            }

        } catch (Exception e) {

            throw new RuntimeException("Error while getting quote", e);

        }

        return "\n~Good evening! Anushka"+"\n"+quote;









    }
     public static void scheduleSMS(long delay){

                Timer timer = new Timer();

                TimerTask task = new TimerTask() {
                    
               

                @Override

                public void run(){

                    try{
                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


                    
                            Message.creator(new PhoneNumber(Personal), new PhoneNumber(twilioPhoneNo), sendSMS()).create();
                            LocalDateTime now = LocalDateTime.now();
                            LocalDateTime nextSendTime = LocalDateTime.of(now.toLocalDate(), LocalTime.of(7,15));
                            if (now.toLocalTime().isAfter(LocalTime.of(7,15))) {
                                nextSendTime = nextSendTime.plusDays(1);
                            }
                            long delay = ChronoUnit.MILLIS.between(now, nextSendTime);
                            scheduleSMS(delay);

                      



            }catch(Exception e){e.printStackTrace();}



            }
        };

            timer.schedule(task,delay);



    }
    

    public static void main(String[] args) {
    

    LocalDateTime now = LocalDateTime.now();

    LocalDateTime sevenAm = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 7,15);


    if (now.toLocalTime().isAfter(LocalTime.of(7,15)))  {

        sevenAm = sevenAm.plusDays(1);

    }


    long delay = ChronoUnit.MILLIS.between(now, sevenAm);


    scheduleSMS(delay);
    // try {
    //     Thread.sleep(delay);
    // } catch (InterruptedException e) {
    //     e.printStackTrace();
    // }
    // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


    // //    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    //     Message.creator(new PhoneNumber(Personal), new PhoneNumber(twilioPhoneNo), sendSMS()).create();

    // Schedule SMS to be sent after 1 hour (3600 seconds)

    // scheduleSMS(1);
        //   }
        



























































































        
// 

    }
}







































// CloseableHttpClient to make http request

// httpGet for get request i.e requesting for random quote using API

// CloseableHttpResponse , execute the get request using httpClient and store the response in CloseableHttpResponse

// HttpEntity to extracts the data from the http response

// if entity not null

// then convert the entity into string conteing json response

// JSONObject to access the data within

// now to extract the data from json we can use jsonObject.getString("quoteText") and ssve it in string 

// then return the string