package com.example.grupo2_ta3_iniciosesionfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PerfilUsuario extends AppCompatActivity {

    TextView txt_id, txt_name, txt_email, txt_provider_id, txt_phone_number;
    ImageView imv_photo;
    Button btn_logout;
    DatabaseReference db_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        Intent intent = getIntent();
        HashMap<String, String> info_user = (HashMap<String, String>)intent.getSerializableExtra("info_user");

        txt_id = findViewById(R.id.txt_userId);
        txt_name = findViewById(R.id.txt_nombre);
        txt_email = findViewById(R.id.txt_correo);
        imv_photo = findViewById(R.id.imv_foto);
        txt_provider_id = findViewById(R.id.txt_provider_id);
        txt_phone_number = findViewById(R.id.txt_phone_number);

        txt_id.setText(info_user.get("user_id"));
        txt_name.setText(info_user.get("user_name"));
        txt_email.setText(info_user.get("user_email"));
        txt_provider_id.setText(info_user.get("user_provider_id"));
        txt_phone_number.setText(info_user.get("user_phone_number"));
        String photo = info_user.get("user_photo");
        Picasso.with(getApplicationContext()).load(photo).into(imv_photo);

        iniciarBaseDeDatos();
        leerTweets();
        consultarSilla("1");
        escribirTweets(info_user.get("user_name"), info_user.get("user_provider_id"), info_user.get("user_phone_number"));
    }

    public void cerrarSesion(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("msg", "cerrarSesion");
        startActivity(intent);
    }

    public void iniciarBaseDeDatos(){
        db_reference = FirebaseDatabase.getInstance().getReference().child("Grupo");
    }

    public void leerTweets(){
        db_reference.child("Grupo 2").child("tweets")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            System.out.println(snapshot);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.out.println(error.toException());
                    }
                });
    }

    public void consultarSilla(String id){
        DatabaseReference sillas = db_reference.child("Mesa").child("Sillas");
        sillas.child(id).child("Disponible")
                .addValueEventListener (new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot.getValue());
                        if ((boolean)dataSnapshot.getValue()) {
                            System.out.println("Silla disponible");
                        } else {
                            System.out.println("Silla ocupada");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.out.println(error.toException());
                    }
                });;
    }


    public void escribirTweets(String autor, String providerId, String phoneNumber){
        String tweet = "hola mundo firebase 2";
        String fecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Map<String, String> hola_tweet = new HashMap<String, String>();
        hola_tweet.put("autor", autor);
        hola_tweet.put("fecha", fecha);
        hola_tweet.put("provider ID", providerId);
        hola_tweet.put("telefono", phoneNumber);
        DatabaseReference tweets = db_reference.child("Grupo 2").child("tweets");
        tweets.setValue(tweet);
        tweets.child(tweet).child("autor").setValue(autor);
        tweets.child(tweet).child("fecha").setValue(fecha);
        tweets.child(tweet).child("provider ID").setValue(providerId);
        tweets.child(tweet).child("telefono").setValue(phoneNumber);
    }

}
