package com.google.ar.sceneform.fyp.ark;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.HitResult;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Render3DModel extends AppCompatActivity {

    private static final String TAG = ModelResourceMapper.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable modelRenderable;
    String model = null;

    FirebaseStorage storage;
    RelativeLayout wholeScreen, loadingAnim;
    MediaPlayer player;
    HashMap<String, String> phoneticsMap = new HashMap<>();

    ImageView btnSpeak, btnBack, btnPhonetics;

    boolean modelDisplayed = false;

    TextView lableTxt, phenemTxt;

    String source = null;

    int number = 0;
    int digit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show3_d_model);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        wholeScreen = findViewById(R.id.wholeScreen);
        loadingAnim = findViewById(R.id.loadingAnim);
        storage = FirebaseStorage.getInstance();
        btnSpeak = findViewById(R.id.btnSpeak);
        lableTxt = findViewById(R.id.lableTxt);
        phenemTxt = findViewById(R.id.phenemTxt);
        btnBack = findViewById(R.id.btnBack);
        btnPhonetics = findViewById(R.id.btnPhonetics);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("alphabet".equals(source)) {
                    if (model.equals("apple"))
                        startPlayer(R.raw.aforapple);
                    else if (model.equals("bag"))
                        startPlayer(R.raw.bforbag);
                    else if (model.equals("cap"))
                        startPlayer(R.raw.cforcap);
                    else if (model.equals("doll"))
                        startPlayer(R.raw.dfordoll);
                    else if (model.equals("elephant"))
                        startPlayer(R.raw.eforelephant);
                    else if (model.equals("fox"))
                        startPlayer(R.raw.fforfox);
                    else if (model.equals("giraffe"))
                        startPlayer(R.raw.gforgiraffe);
                    else if (model.equals("house"))
                        startPlayer(R.raw.hforhouse);
                    else if (model.equals("icecream"))
                        startPlayer(R.raw.iforicecream);
                    else if (model.equals("jamjar"))
                        startPlayer(R.raw.jforjamjar);
                    else if (model.equals("kite"))
                        startPlayer(R.raw.kforkite);
                    else if (model.equals("ladybug"))
                        startPlayer(R.raw.lforladybug);
                    else if (model.equals("monkey"))
                        startPlayer(R.raw.mformonkey);
                    else if (model.equals("nest"))
                        startPlayer(R.raw.nfornest);
                    else if (model.equals("orange"))
                        startPlayer(R.raw.ofororange);
                    else if (model.equals("parrot"))
                        startPlayer(R.raw.pforparrot);
                    else if (model.equals("queen"))
                        startPlayer(R.raw.qforqueen);
                    else if (model.equals("rainbow"))
                        startPlayer(R.raw.rforrainbow);
                    else if (model.equals("ship"))
                        startPlayer(R.raw.sforship);
                    else if (model.equals("tiger"))
                        startPlayer(R.raw.tfortiger);
                    else if (model.equals("umbrella"))
                        startPlayer(R.raw.uforumbrella);
                    else if (model.equals("van"))
                        startPlayer(R.raw.vforvan);
                    else if (model.equals("whale"))
                        startPlayer(R.raw.wforwhale);
                    else if (model.equals("xylophone"))
                        startPlayer(R.raw.xforxylophone);
                    else if (model.equals("yoyo"))
                        startPlayer(R.raw.yforyoyo);
                    else if (model.equals("zebra"))
                        startPlayer(R.raw.zforzebra);
                } else {
                    if (model.equals("elephant"))
                        startPlayer(R.raw.thisisanelephant);
                    else if (model.equals("cat"))
                        startPlayer(R.raw.thisisacat);
                    else if (model.equals("giraffe"))
                        startPlayer(R.raw.thisisagiraffe);
                    else if (model.equals("zebra"))
                        startPlayer(R.raw.thisisazebra);
                    else if (model.equals("tiger"))
                        startPlayer(R.raw.thisisatiger);
                    else if (model.equals("parrot"))
                        startPlayer(R.raw.thisisaparrot);
                    else if (model.equals("fox"))
                        startPlayer(R.raw.thisisafox);
                }
            }
        });

        btnPhonetics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.equals("apple"))
                    startPlayer(R.raw.apple);
                else if (model.equals("bag"))
                    startPlayer(R.raw.bag);
                else if (model.equals("cap"))
                    startPlayer(R.raw.cap);
                else if (model.equals("doll"))
                    startPlayer(R.raw.doll);
                else if (model.equals("elephant"))
                    startPlayer(R.raw.elephant);
                else if (model.equals("fox"))
                    startPlayer(R.raw.fox);
                else if (model.equals("giraffe"))
                    startPlayer(R.raw.giraffe);
                else if (model.equals("house"))
                    startPlayer(R.raw.house);
                else if (model.equals("icecream"))
                    startPlayer(R.raw.icecream);
                else if (model.equals("jamjar"))
                    startPlayer(R.raw.jamjar);
                else if (model.equals("kite"))
                    startPlayer(R.raw.kite);
                else if (model.equals("ladybug"))
                    startPlayer(R.raw.ladybug);
                else if (model.equals("monkey"))
                    startPlayer(R.raw.monkey);
                else if (model.equals("nest"))
                    startPlayer(R.raw.nest);
                else if (model.equals("orange"))
                    startPlayer(R.raw.orange);
                else if (model.equals("parrot"))
                    startPlayer(R.raw.parrot);
                else if (model.equals("queen"))
                    startPlayer(R.raw.queen);
                else if (model.equals("rainbow"))
                    startPlayer(R.raw.rainbow);
                else if (model.equals("ship"))
                    startPlayer(R.raw.ship);
                else if (model.equals("tiger"))
                    startPlayer(R.raw.tiger);
                else if (model.equals("umbrella"))
                    startPlayer(R.raw.umbrella);
                else if (model.equals("van"))
                    startPlayer(R.raw.van);
                else if (model.equals("whale"))
                    startPlayer(R.raw.whale);
                else if (model.equals("xylophone"))
                    startPlayer(R.raw.xylophone);
                else if (model.equals("yoyo"))
                    startPlayer(R.raw.yoyo);
                else if (model.equals("zebra"))
                    startPlayer(R.raw.zebra);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            model = bundle.getString("model");
            source = bundle.getString("source");
        }

        if ("numbers".equals(source)) {
            if (model.equals("one")) number = 1;
            else if (model.equals("two")) number = 2;
            else if (model.equals("three")) number = 3;
            else if (model.equals("four")) number = 4;
            else if (model.equals("five")) number = 5;
            else if (model.equals("six")) number = 6;
            else if (model.equals("seven")) number = 7;
            else if (model.equals("eight")) number = 8;
            else if (model.equals("nine")) number = 9;

            if (number % 2 == 1) model = "tiger";
            else model = "fox";
        }

        System.out.println("The model is: " + model);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        getModels();
        setUpPlane();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        phoneticsMap.put("apple", "ˈæpəl");
        phoneticsMap.put("bag", "bæg");
        phoneticsMap.put("cap", "kæp");
        phoneticsMap.put("doll", "dɑl");
        phoneticsMap.put("elephant", "ˈɛləfənt");
        phoneticsMap.put("fox", "fɑks");
        phoneticsMap.put("giraffe", "ʤəˈræf");
        phoneticsMap.put("house", "haʊs");
        phoneticsMap.put("icecream", "ˈaɪˌskrim");
        phoneticsMap.put("jamjar", "ʤæm ʤɑr");
        phoneticsMap.put("kite", "kaɪt");
        phoneticsMap.put("ladybird", "ˈleɪdi bɜrd");
        phoneticsMap.put("monkey", "ˈmʌŋki");
        phoneticsMap.put("nest", "nɛst");
        phoneticsMap.put("orange", "ˈɔrənʤ");
        phoneticsMap.put("parrot", "ˈpɛrət");
        phoneticsMap.put("queen", "kwin");
        phoneticsMap.put("rainbow", "ˈreɪnˌboʊ");
        phoneticsMap.put("ship", "ʃɪp");
        phoneticsMap.put("tiger", "ˈtaɪgər");
        phoneticsMap.put("umbrella", "əmˈbrɛlə");
        phoneticsMap.put("van", "væn");
        phoneticsMap.put("whale", "weɪl");
        phoneticsMap.put("xylophone", "ˈzaɪləˌfoʊn");
        phoneticsMap.put("yoyo", "joʊ-joʊ");
        phoneticsMap.put("zebra", "ˈzibrə");
        phoneticsMap.put("one", "wʌn");
        phoneticsMap.put("two", "tu");
        phoneticsMap.put("three", "θri");
        phoneticsMap.put("four", "fɔr");
        phoneticsMap.put("five", "faɪv");
        phoneticsMap.put("six", "sɪks");
        phoneticsMap.put("seven", "ˈsɛvən");
        phoneticsMap.put("eight", "eɪt");
        phoneticsMap.put("nine", "naɪn");
    }


    private void buildModel(File file) {
        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(this, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();

        Log.d("[ARK PROTOCOL]", "3D model's object source has been parsed.");

        ModelRenderable
                .builder()
                .setSource(this, renderableSource)
                .setRegistryId(Uri.parse(file.getPath()))
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(getApplicationContext(), "Model rendered successfully!", Toast.LENGTH_SHORT).show();
                    this.modelRenderable = modelRenderable;

                    wholeScreen.setVisibility(View.VISIBLE);
                    loadingAnim.setVisibility(View.GONE);
                });

        Log.d("[ARK PROTOCOL]", "Successfully rendered the model's binaries.");
    }


    private void setUpPlane() {
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            displayModel(hitResult, arFragment);

        }));
    }


    @SuppressLint("SetTextI18n")
    private void displayModel(HitResult hitResult, ArFragment arFragment) {

        if (!modelDisplayed || digit < number) {        //create anchor point where we tapped

            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());

            // Create the transformable andy and add it to the anchor.
            TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());

            //sacle size of 3d Model on screen

            node.getScaleController().setMaxScale(0.2f);
            node.getScaleController().setMinScale(0.1f);

            //finally set node with 3d model on AR fragment
            node.setParent(anchorNode);
            node.setRenderable(this.modelRenderable);
            node.select();
            arFragment.getArSceneView().getScene().addChild(anchorNode);

            if ("numbers".equals(source)) {
                digit++;

                if (digit == 1)
                    lableTxt.setText("1 - One - I");
                else if (digit == 2)
                    lableTxt.setText("2 - Two - II");
                else if (digit == 3)
                    lableTxt.setText("3 - Three - III");
                else if (digit == 4)
                    lableTxt.setText("4 - Four - IV");
                else if (digit == 5)
                    lableTxt.setText("5 - Five - V");
                else if (digit == 6)
                    lableTxt.setText("6 - Six - VI");
                else if (digit == 7)
                    lableTxt.setText("7 - Seven - VII");
                else if (digit == 8)
                    lableTxt.setText("8 - Eight - VIII");
                else if (digit == 9)
                    lableTxt.setText("9 - Nine - IX");
            } else if ("alphabet".equals(source)) {
                String word1 = Character.toString(model.charAt(0)).toUpperCase() + " for ";
                String word2 = model.substring(0, 1).toUpperCase() + model.substring(1);
                lableTxt.setText(word1 + word2);
                phenemTxt.setText("(" + phoneticsMap.get(model) + ")");
                btnPhonetics.setVisibility(View.VISIBLE);
            } else {
                String word1 = "This is";
                String word2 = " a";
                char startingCharacter = model.charAt(0);
                if (startingCharacter == 'a' || startingCharacter == 'e' || startingCharacter == 'o' || startingCharacter == 'i' || startingCharacter == 'u') {
                    word2 += "n";
                }
                String word3 = " " + model.substring(0, 1).toUpperCase() + model.substring(1);
                lableTxt.setText(word1 + word2 + word3);
            }

            modelDisplayed = true;
        }
    }

    void getModels() {
        try {

            if (model != null) {
                Toast.makeText(this, "" + model.toLowerCase(), Toast.LENGTH_SHORT).show();
                File file = File.createTempFile(model, "glb");
                String modl = model + ".glb";
                StorageReference modelRef = storage.getReference().child(modl);
                modelRef.getFile(file).addOnSuccessListener(taskSnapshot -> buildModel(file))
                        .addOnFailureListener(e -> Log.d("[ARK PROTOCOL]", "Failed to load model."));
            } else {
                loadingAnim.setVisibility(View.GONE);
                wholeScreen.setVisibility(View.VISIBLE);
            }

        } catch (IOException e) {
            loadingAnim.setVisibility(View.GONE);
            wholeScreen.setVisibility(View.VISIBLE);
            Log.d("exceptionsdghsd", String.valueOf(e));
            Toast.makeText(this, "EXCEPTION", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    //start playing sound
    private void startPlayer(int sound) {
        if (player == null) {
            player = MediaPlayer.create(this, sound);
            player.setOnCompletionListener(mp -> stopPlayer());
        }

        player.start();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

}