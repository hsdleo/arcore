package com.google.ar.sceneform.samples.augmentedimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.ar.sceneform.samples.augmentedimage.R;

public class AlertaActivity extends AppCompatActivity {

    private Button btnOpcao1,btnOpcao2,btnOpcao3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
        btnOpcao1 = findViewById(R.id.btn_opcao_1);
        btnOpcao2 = findViewById(R.id.btn_opcao_2);
        btnOpcao3 = findViewById(R.id.btn_opcao_3);


        btnOpcao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlertaActivity.this,PrecificacaoActivity.class);
                startActivity(i);
            }
        });

        btnOpcao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlertaActivity.this,InfoActivity.class);
                startActivity(i);
            }
        });

        btnOpcao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlertaActivity.this,PrecificacaoActivity.class);
                startActivity(i);
            }
        });



    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
