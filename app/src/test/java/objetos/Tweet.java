package objetos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tweet {

    private String autor;
    private String tweet;
    private String fecha;
    private String providerId;
    private String phoneNumber;

    Tweet(String autor, String tweet, String providerId, String phoneNumber) {
        this.autor = autor;
        this.tweet = tweet;
        this.providerId = providerId;
        this.phoneNumber = phoneNumber;
    }

    public void publicarTweet(){
        String fecha_actual = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        this.fecha = fecha_actual;
    }

}
