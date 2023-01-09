package com.example.desters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Animation shake;
    ListView list;
    String[] initialList;
    int[] iaInitialImages;
    String scorDeTrimis;
    TextView scorView;
    TextView rez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.main);
//        mediaPlayer.start();
//        mediaPlayer.release();


        scorView = findViewById(R.id.scor);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        list = findViewById(R.id.lista);
        initialList = new String[]{"Animals", "Colors", "Numbers", "Clothes"};
        iaInitialImages = new int[]{R.drawable.animals, R.drawable.colors, R.drawable.numbers, R.drawable.clothes};
        itemAdapter myCustomAdapter = new itemAdapter(this, initialList, iaInitialImages);
        list.setAdapter(myCustomAdapter);
        list.startAnimation(shake);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    onClickAnimals();
                } else if (position == 1) {
                    onClickColors();
                } else if (position == 2) {
                    onClickNumbers();
                } else if (position == 3) {
                    onClickClothes();
                }
            }
        });
    }

    public void onClickAnimals() {

        scorDeTrimis = scorView.getText().toString();
        Toast toast1 = Toast.makeText(getApplicationContext(), "Animals were selected", Toast.LENGTH_SHORT);
        toast1.show();
        Intent intentAnimals = new Intent(MainActivity.this, Animals.class);
        intentAnimals.putExtra("scor", scorDeTrimis);
        startActivityForResult(intentAnimals, 1);
    }

    public void onClickColors() {
        Toast toast2 = Toast.makeText(getApplicationContext(), "Colors were selected", Toast.LENGTH_SHORT);
        toast2.show();
        Intent intentColors = new Intent(MainActivity.this, Colors.class);
        startActivity(intentColors);
    }

    public void onClickNumbers() {
        Toast toast3 = Toast.makeText(getApplicationContext(), "Numbers were selected", Toast.LENGTH_SHORT);
        toast3.show();
        Intent intentNumbers = new Intent(MainActivity.this, Numbers.class);
        startActivity(intentNumbers);
    }

    public void onClickClothes() {
        Toast toast4 = Toast.makeText(getApplicationContext(), "Clothes were selected", Toast.LENGTH_SHORT);
        toast4.show();
        Intent intentClothes = new Intent(MainActivity.this, Clothes.class);
        startActivity(intentClothes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        rez = findViewById(R.id.scor);
        int scor = data.getIntExtra("result", 0);
        rez.setText("" + scor);

//        if (rez.getText().toString().equals("12")) {
//            Toast toast5 = Toast.makeText(getApplicationContext(), "You won", Toast.LENGTH_SHORT);
//            toast5.show();
//        }
    }
}