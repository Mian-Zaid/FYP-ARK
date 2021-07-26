package com.google.ar.sceneform.fyp.ark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HomepageActivity extends AppCompatActivity {

    CardView btnAplha, btnAnim, btnNum, btnDetect;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    boolean numClicked = false, alphaClicked = false, animClicked = false;
    boolean detectClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnAplha = findViewById(R.id.btnAlpha);
        btnNum = findViewById(R.id.btnNum);
        btnAnim = findViewById(R.id.btnAnim);
        btnDetect = findViewById(R.id.btnDetect);


        btnNum.setOnClickListener(view -> {
//            numClicked = true;
//            alphaClicked = false;
//            animClicked = false;
//            detectClicked = false;
//            dispatchTakePictureIntent();
            openNumbers();
        });

        btnDetect.setOnClickListener(view -> {
            numClicked = false;
            alphaClicked = false;
            animClicked = false;
            detectClicked = true;
            dispatchTakePictureIntent();
//            openNumbers();
        });

        btnAplha.setOnClickListener(view -> {
//            numClicked = false;
//            alphaClicked = true;
//            animClicked = false;
//            dispatchTakePictureIntent();


            //goto alphabets screen
            openAlphabets();

        });

        btnAnim.setOnClickListener(view -> {
            numClicked = false;
            alphaClicked = false;
            animClicked = true;
            detectClicked = false;
            openAnimals();
        });
    }

    private void openNumbers() {
        Intent intent = new Intent(getApplicationContext(), NumbersEventHandler.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openAlphabets() {
        Intent intent = new Intent(getApplicationContext(), AlphabetsEventHandler.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openAnimals() {
        Intent intent = new Intent(getApplicationContext(), AnimalsEventHandler.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // Displays the error state to the user
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String encodedImage = toBase64(imageBitmap);
            //            System.out.println("Base64: " + encodedImage);

            Thread thread = new Thread(() -> {
                try {
                    try {
                        Socket socket = new Socket(ModelResourceMapper.ARK_SERVER_IP, 7919);
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());

                        writer.println(encodedImage);
                        writer.flush();

                        writer.close();
                        socket.close();

                        Log.d("[ARK IMAGE SEND PROTOCOL]", "Image sent to server successfully!");
                        System.out.println("");
                    } catch (IOException e) {
                        Log.d("[IMAGE PROTOCOL ERROR]", e.toString());

                        System.out.println(e.getMessage());
                    }
                } catch (Exception e) {
                    Log.d("[ERROR IN THREAD CREATION]", e.toString());

                    System.out.println(e.getMessage());
                }
            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread receiverThread = new Thread(() -> {
                try {
                    try {
                        Socket socket = new Socket(ModelResourceMapper.ARK_SERVER_IP, 7921);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String label = bufferedReader.readLine();
                        System.out.println(label);
                        runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), label, Toast.LENGTH_LONG).show();
                            if (!label.isEmpty()) {
                                Intent intent = new Intent(getApplicationContext(), ModelResourceMapper.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("label", label);
                                if (!label.equals("one") && !label.equals("two") && !label.equals("three") && !label.equals("four") &&
                                        !label.equals("five") && !label.equals("six") && !label.equals("seven") && !label.equals("eight") &&
                                        !label.equals("nine")) {
                                    intent.putExtra("source", "alphabet");
                                } else {
                                    intent.putExtra("source", "numbers");
                                }
                                if (numClicked) {
                                    intent.putExtra("clicked", "number");
                                } else if (alphaClicked) {
                                    intent.putExtra("clicked", "alpha");
                                } else if (animClicked) {
                                    intent.putExtra("clicked", "animal");
                                } else {
                                    intent.putExtra("clicked", "detect");
                                }
                                startActivity(intent);
                            }


                        });
                        socket.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            receiverThread.start();
            try {
                receiverThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

}