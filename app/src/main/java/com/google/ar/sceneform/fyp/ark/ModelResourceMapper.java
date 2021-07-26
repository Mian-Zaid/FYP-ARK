
package com.google.ar.sceneform.fyp.ark;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelResourceMapper extends AppCompatActivity {

    private static final String TAG = ModelResourceMapper.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    public static final String ARK_SERVER_IP = "192.168.100.165";

    String sound = "";
    MediaPlayer player;
    ImageView btnSpeak, btnMute;

    RelativeLayout wholeScreen, loadingAnim;

    ImageView btnBack;
    private ArFragment arFragment;
    private ModelRenderable modelRenderable;
    int number = 0, digit = 0;
    boolean modelDisplayed = false;
    HashMap<String, String> phoneticsMap = new HashMap<>();
    ImageView btnPhonetics;
    String source = null;

    TextView lableTxt, phenemTxt;
    List<String> phemenList, lablesList;
    FirebaseStorage storage;
    String label = "", clicked = "";

    @SuppressLint("SetTextI18n")
    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    /* CompletableFuture requires api level 24
     FutureReturnValueIgnored is not valid */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show3_d_model);

        wholeScreen = findViewById(R.id.wholeScreen);
        loadingAnim = findViewById(R.id.loadingAnim);
        btnSpeak = findViewById(R.id.btnSpeak);
        btnMute = findViewById(R.id.btnMute);
        btnBack = findViewById(R.id.btnBack);
        btnPhonetics = findViewById(R.id.btnPhonetics);

        /* Initialize an instance of Firebase Storage */
        FirebaseApp.initializeApp(this);
        storage = FirebaseStorage.getInstance();

        phemenList = new ArrayList<>();
        lablesList = new ArrayList<>();

        /* Initialize the list of phonetics and labels */
        initPhonemeList();
        initLablesList();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            label = bundle.getString("label");
            clicked = bundle.getString("clicked");
            source = bundle.getString("source");
        }

        System.out.println("[ARK DEBUG] Clicked Status: " + clicked);

        lableTxt = findViewById(R.id.lableTxt);
        phenemTxt = findViewById(R.id.phenemTxt);

        String[] lal = label.split(":");
        label = lal[0];

        if ("numbers".equals(source)) {

            switch (label) {
                case "one":
                    number = 1;
                    break;
                case "two":
                    number = 2;
                    break;
                case "three":
                    number = 3;
                    break;
                case "four":
                    number = 4;
                    break;
                case "five":
                    number = 5;
                    break;
                case "six":
                    number = 6;
                    break;
                case "seven":
                    number = 7;
                    break;
                case "eight":
                    number = 8;
                    break;
                case "nine":
                    number = 9;
                    break;
            }

            if (number % 2 == 1) label = "tiger";
            else label = "fox";
        }

        /* Match the label request against its corresponding number - for generation of specific models */

        String appendToLabel = "";

        switch (clicked) {

            case "number":
                appendToLabel = "";

                switch (label) { /* Label:Sound matching for digits/numbers */
                    case "one":
                        lableTxt.setText("One (1) Ladybug");
                        break;
                    case "two":
                        sound = "twotigers";
                        lableTxt.setText("Two (2) Tigers");
                        break;
                    case "three":
                        sound = "threeladybirds";
                        lableTxt.setText("Three (3) Ladybugs");
                        break;
                    case "four":
                        lableTxt.setText("Four (4) Ladybugs");
                        break;
                    case "five":
                        sound = "fivecaps";
                        lableTxt.setText("Five (5) Caps");
                        break;
                    case "six":
                        lableTxt.setText("Six (6) Ladybugs");
                        break;
                    case "seven":
                        lableTxt.setText("Seven (7) Ladybugs");
                        break;
                    case "eight":
                        sound = "eighticecreams";
                        lableTxt.setText("Eight (8) Icecreams");
                        break;
                    case "nine":
                        lableTxt.setText("Nine (9) Ladybugs");
                        break;
                }

                break;

            case "alpha":   /* Alphabet:Sound mapping using labels */

                char ch = label.charAt(0);
                appendToLabel = String.valueOf(ch).toUpperCase() + " for ";

                switch (label) {
                    case "ladybug":
                        sound = "lforladybird";
                        lableTxt.setText(appendToLabel + "L for Ladybug");
                        break;
                    case "icecream":
                        sound = "iforicecream";
                        lableTxt.setText(appendToLabel + "I for Icecream");
                        break;
                    case "jamjar":
                        sound = "jforjamjar";
                        lableTxt.setText(appendToLabel + "J for Jamjar");
                        break;
                    case "yoyo":
                        sound = "yforyoyo";
                        lableTxt.setText(appendToLabel + "Y for Yoyo");
                        break;
                    default:
                        sound = String.valueOf(ch) + "for" + label.toLowerCase();
                        Toast.makeText(this, sound, Toast.LENGTH_SHORT).show();
                        lableTxt.setText(appendToLabel + label.toUpperCase());
                        break;
                }

                break;

            case "animal":   /* Animal:Sound mapping using labels */
                appendToLabel = "This is";

                System.out.println("[ARK DEBUG] User clicked on an animal");

                if (label.equals("elephant")) {
                    sound = "thisis" +
                            "anelephant";
                    lableTxt.setText(appendToLabel + " an Elephant");
                } else if (label.equals("ladybug")) {
                    sound = "thisisaladybird";
                    lableTxt.setText(appendToLabel + " a Ladybug");
                } else {
                    sound = "thisisa" + label.toLowerCase().trim();
                    Toast.makeText(this, sound, Toast.LENGTH_SHORT).show();
                    lableTxt.setText(appendToLabel + " a " + label.toUpperCase());
                }

                break;
        }

        btnPhonetics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (label) {
                    case "apple":
                        startPlayer(R.raw.apple);
                        break;
                    case "bag":
                        startPlayer(R.raw.bag);
                        break;
                    case "cap":
                        startPlayer(R.raw.cap);
                        break;
                    case "doll":
                        startPlayer(R.raw.doll);
                        break;
                    case "elephant":
                        startPlayer(R.raw.elephant);
                        break;
                    case "fox":
                        startPlayer(R.raw.fox);
                        break;
                    case "giraffe":
                        startPlayer(R.raw.giraffe);
                        break;
                    case "house":
                        startPlayer(R.raw.house);
                        break;
                    case "icecream":
                        startPlayer(R.raw.icecream);
                        break;
                    case "jamjar":
                        startPlayer(R.raw.jamjar);
                        break;
                    case "kite":
                        startPlayer(R.raw.kite);
                        break;
                    case "ladybug":
                        startPlayer(R.raw.ladybug);
                        break;
                    case "monkey":
                        startPlayer(R.raw.monkey);
                        break;
                    case "nest":
                        startPlayer(R.raw.nest);
                        break;
                    case "orange":
                        startPlayer(R.raw.orange);
                        break;
                    case "parrot":
                        startPlayer(R.raw.parrot);
                        break;
                    case "queen":
                        startPlayer(R.raw.queen);
                        break;
                    case "rainbow":
                        startPlayer(R.raw.rainbow);
                        break;
                    case "ship":
                        startPlayer(R.raw.ship);
                        break;
                    case "tiger":
                        startPlayer(R.raw.tiger);
                        break;
                    case "umbrella":
                        startPlayer(R.raw.umbrella);
                        break;
                    case "van":
                        startPlayer(R.raw.van);
                        break;
                    case "whale":
                        startPlayer(R.raw.whale);
                        break;
                    case "xylophone":
                        startPlayer(R.raw.xylophone);
                        break;
                    case "yoyo":
                        startPlayer(R.raw.yoyo);
                        break;
                    case "zebra":
                        startPlayer(R.raw.zebra);
                        break;
                }
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

//        /* Set phonetics according to the detected/clicked label */
//        for (int i = 0; i < phemenList.size(); ++i) {
//            if (lablesList.get(i).equals(label)) {
//                phenemTxt.setText(phemenList.get(i));
//            }
//        }

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        getModels();
        setUpPlane();

        btnSpeak.setOnClickListener(view -> {
//            Toast.makeText(ModelResourceMapper.this, sound, Toast.LENGTH_SHORT).show();
            playSounds(label);
//            btnMute.setVisibility(View.VISIBLE);
//            btnSpeak.setVisibility(View.GONE);
        });

        btnMute.setOnClickListener(view -> {
            stopPlayer();
            btnMute.setVisibility(View.GONE);
            btnSpeak.setVisibility(View.VISIBLE);
        });

        btnBack.setOnClickListener(view -> {
            stopPlayer();
            finish();
        });
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "ARK requires Android N or later");
            Toast.makeText(activity, "ARK requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }

        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();

        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "ARK requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "ARK requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }

        return true;
    }

    private void buildModel(File file) {
        /* Create a  renderable source */
        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(this, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();

        Log.d("[ARL PROTOCOL]", "Renderable source has been built.");

        /* Initialize the corresponding 3D model */
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

        Log.d("[ARK PROTOCOL]", "Model source has been set and corresponding 3D file has been generated.");
    }


    private void setUpPlane() {
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            /* Display models corresponding to numbers */
            switch (label) {
                case "three":
                    for (int i = 0; i < 3; ++i) {
                        displayModel(hitResult, arFragment);
                    }

                    break;
                case "five":
                    for (int i = 0; i < 5; ++i) {
                        displayModel(hitResult, arFragment);
                    }

                    break;
                case "eight":

                    for (int i = 0; i < 8; ++i) {
                        displayModel(hitResult, arFragment);
                    }

                    break;
                default:
                    /* For displaying a single model only */
                    displayModel(hitResult, arFragment);
                    break;
            }
        }));
    }

    private void playSounds(String model) { /* Map raw resources to label strings */

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

    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void pausePlayer() {

        if (player != null) {
            player.pause();
        }
    }

    /* Start the sound with the given resource ID */
    private void startPlayer(int sound) {
        if (player == null) {
            player = MediaPlayer.create(this, sound);
            player.setOnCompletionListener(mp -> stopPlayer());
        }

        player.start();
    }

    @SuppressLint("SetTextI18n")
    private void displayModel(HitResult hitResult, ArFragment arFragment) {
        String model = label;
        if (!modelDisplayed || digit < number) {    /* Create an anchor point where the user has tapped */
            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());

            /* Create the transformable node and add it to the anchor.*/
            TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());

            /* Scale the size of 3D Model on the screen */

            node.getScaleController().setMaxScale(0.2f);
            node.getScaleController().setMinScale(0.1f);

            /* Finally set the node with 3D model on the AR fragment */
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

    private void createModel(AnchorNode anchorNode) {
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setParent(anchorNode);
        node.setRenderable(modelRenderable);
        node.select();
    }


    void getModels() {
        try {
            if (label != null) {
                /* FYP-II Mid Evaluation Specific Code (Logic for this section has been revised) */

/*                if (label.equals("three")) {
                    Toast.makeText(this, "" + label.toLowerCase(), Toast.LENGTH_SHORT).show();
                    File file = File.createTempFile("ladybug", "glb");
                    String modl = "ladybug" + ".glb";
                    StorageReference modelRef = storage.getReference().child(modl);
                    modelRef.getFile(file).addOnSuccessListener(taskSnapshot -> buildModel(file))
                            .addOnFailureListener(e -> Log.d("[ERROR IN MODEL BUILDING]", "Status: Failed"));
                } else if (label.equals("two")) {
                    Toast.makeText(this, "" + label.toLowerCase(), Toast.LENGTH_SHORT).show();
                    File file = File.createTempFile("tiger", "glb");
                    String modl = "tiger" + ".glb";
                    StorageReference modelRef = storage.getReference().child(modl);
                    modelRef.getFile(file).addOnSuccessListener(taskSnapshot -> buildModel(file))
                            .addOnFailureListener(e -> Log.d("[ERROR IN MODEL BUILDING]", "Status: Failed"));
                } else if (label.equals("five")) {
                    Toast.makeText(this, "" + label.toLowerCase(), Toast.LENGTH_SHORT).show();
                    File file = File.createTempFile("cap", "glb");
                    String modl = "cap" + ".glb";
                    StorageReference modelRef = storage.getReference().child(modl);
                    modelRef.getFile(file).addOnSuccessListener(taskSnapshot -> buildModel(file))
                            .addOnFailureListener(e -> Log.d("[ERROR IN MODEL BUILDING]", "Status: Failed"));
                } else if (label.equals("eight")) {
                    Toast.makeText(this, "" + label.toLowerCase(), Toast.LENGTH_SHORT).show();
                    File file = File.createTempFile("icecream", "glb");
                    String modl = "icecream" + ".glb";
                    StorageReference modelRef = storage.getReference().child(modl);
                    modelRef.getFile(file).addOnSuccessListener(taskSnapshot -> buildModel(file))
                            .addOnFailureListener(e -> Log.d("[ERROR IN MODEL BUILDING]", "Status: Failed"));
                }
*/

                /* For all models other than numbers */

//                else {

                /* Generic */

                Toast.makeText(this, "" + label.toLowerCase(), Toast.LENGTH_SHORT).show();

                File file = File.createTempFile(label.toLowerCase(), "glb");
                String modl = label.toLowerCase() + ".glb";
                StorageReference modelRef = storage.getReference().child(modl);

                modelRef.getFile(file).addOnSuccessListener(taskSnapshot -> buildModel(file))
                        .addOnFailureListener(e -> Log.d("[ERROR IN MODEL BUILDING]", "Status: Failed"));

//                }

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

    private void initLablesList() {

        lablesList.add("apple");
        lablesList.add("bag");
        lablesList.add("cap");
        lablesList.add("doll");
        lablesList.add("elephant");
        lablesList.add("fox");
        lablesList.add("giraffe");
        lablesList.add("house");
        lablesList.add("icecream");
        lablesList.add("jamjar");
        lablesList.add("kite");
        lablesList.add("ladybug");
        lablesList.add("monkey");
        lablesList.add("nest");
        lablesList.add("orange");
        lablesList.add("parrot");
        lablesList.add("queen");
        lablesList.add("rainbow");
        lablesList.add("ship");
        lablesList.add("tiger");
        lablesList.add("umbrella");
        lablesList.add("van");
        lablesList.add("whale");
        lablesList.add("xylophone");
        lablesList.add("yoyo");
        lablesList.add("zebra");
        lablesList.add("one");
        lablesList.add("two");
        lablesList.add("three");
        lablesList.add("four");
        lablesList.add("five");
        lablesList.add("six");
        lablesList.add("seven");
        lablesList.add("eight");
        lablesList.add("nine");

    }

    private void initPhonemeList() {

        phemenList.add("ˈæpəl");
        phemenList.add("bæg");
        phemenList.add("kæp");
        phemenList.add("dɑl");
        phemenList.add("ˈɛləfənt");
        phemenList.add("fɑks");
        phemenList.add("ʤəˈræf");
        phemenList.add("haʊs");
        phemenList.add("ˈaɪˌskrim");
        phemenList.add("ʤæm ʤɑr");
        phemenList.add("kaɪt");
        phemenList.add("ˈleɪdi bɜrd");
        phemenList.add("ˈmʌŋki");
        phemenList.add("nɛst");
        phemenList.add("ˈɔrənʤ");
        phemenList.add("ˈpɛrət");
        phemenList.add("kwin");
        phemenList.add("ˈreɪnˌboʊ");
        phemenList.add("ʃɪp");
        phemenList.add("ˈtaɪgər");
        phemenList.add("əmˈbrɛlə");
        phemenList.add("væn");
        phemenList.add("weɪl");
        phemenList.add("ˈzaɪləˌfoʊn");
        phemenList.add("joʊ-joʊ");
        phemenList.add("ˈzibrə");
        phemenList.add("wʌn");
        phemenList.add("tu");
        phemenList.add("θri");
        phemenList.add("fɔr");
        phemenList.add("faɪv");
        phemenList.add("sɪks");
        phemenList.add("ˈsɛvən");
        phemenList.add("eɪt");
        phemenList.add("naɪn");

    }
}
