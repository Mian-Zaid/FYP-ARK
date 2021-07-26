package com.google.ar.sceneform.fyp.ark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AnimalsEventHandler extends AppCompatActivity {

    CardView btnCat, btnElephant, btnFox, btnGiraffe, btnParrot, btnTiger, btnZebra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_animals);

        initViews();

        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto3Dmodel("cat");
            }
        });

        btnElephant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto3Dmodel("elephant");
            }
        });

        btnFox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto3Dmodel("fox");
            }
        });

        btnGiraffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto3Dmodel("giraffe");
            }
        });

        btnParrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto3Dmodel("parrot");
            }
        });

        btnTiger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto3Dmodel("tiger");
            }
        });

        btnZebra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto3Dmodel("zebra");
            }
        });

    }

    void goto3Dmodel(String model) {
        Intent intent = new Intent(getApplicationContext(), Render3DModel.class);
        intent.putExtra("model", model);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void initViews() {
        btnCat = findViewById(R.id.btnCat);
        btnElephant = findViewById(R.id.btnElephant);
        btnFox = findViewById(R.id.btnFox);
        btnGiraffe = findViewById(R.id.btnGiraffe);
        btnParrot = findViewById(R.id.btnParrot);
        btnTiger = findViewById(R.id.btnTiger);
        btnZebra = findViewById(R.id.btnZebra);
    }

}