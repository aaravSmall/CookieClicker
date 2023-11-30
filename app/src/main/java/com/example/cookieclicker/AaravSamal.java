/*package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    public static AtomicInteger totalMilesTraveled;
    public static AtomicInteger engineTime = new AtomicInteger(4000);
    public static AtomicInteger aeroTime = new AtomicInteger(4000);
    public static AtomicInteger tiresTime = new AtomicInteger(4000);

    static ConstraintLayout cl;

    Button upgrade1;
    Button upgrade2;
    Button upgrade3;

    ImageView imageView;
    ImageView drs;

    static TextView display;
    static TextView displayDRS;

    TextView displayEngineLevel;
    TextView displayAeroLevel;
    TextView displayCurrentTires;

    int engineLevel = 0;
    int aeroLevel = 0;
    int index = -1;
    int engineGoal = 10;
    int aeroGoal = 50;
    int tiresGoal = 100;
    String [] tireCompounds = {"H", "M", "S", "SS", "US", "HS"};

    final Animation scaleUp = new ScaleAnimation(1f, 1.1f, 1f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    static final ScaleAnimation sa = new ScaleAnimation(1.0f, 3f, 1.0f, 3f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upgrade1 = findViewById(R.id.id_upgrade1);
        upgrade1.setText("" + engineGoal + "mi");
        upgrade1.setEnabled(false);
        upgrade2 = findViewById(R.id.id_upgrade2);
        upgrade2.setText("" + aeroGoal + "mi");
        upgrade2.setEnabled(false);
        upgrade3 = findViewById(R.id.id_upgrade3);
        upgrade3.setEnabled(false);
        upgrade3.setText("" + tiresGoal + "mi");

        display = findViewById(R.id.displayDistanceTraveled);
        displayEngineLevel = findViewById(R.id.id_displayEngineLevel);
        displayAeroLevel = findViewById(R.id.id_displayAeroLevel);
        displayCurrentTires = findViewById(R.id.id_DisplayCurrentTires);
        displayDRS = findViewById(R.id.textView2);

        cl = findViewById(R.id.id_layout);

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.car2);

        drs = findViewById(R.id.imageView2);

        totalMilesTraveled = new AtomicInteger(0);

        new print().start();

        scaleUp.setDuration(500);
        scaleUp.setRepeatCount(Animation.INFINITE);
        scaleUp.setRepeatMode(Animation.REVERSE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalMilesTraveled.getAndAdd(1);
                display.setText("Distance Traveled: " + totalMilesTraveled + " mi");

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;

                TranslateAnimation animation = new TranslateAnimation(0, screenWidth, 0, 0);
                animation.setDuration(1000);
                animation.setFillAfter(true);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        TranslateAnimation animation2 = new TranslateAnimation(-screenWidth, 0, 0, 0);
                        animation2.setDuration(1000);
                        animation2.setFillAfter(true);
                        imageView.startAnimation(animation2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                imageView.startAnimation(animation);

                TextView displayInc;

                displayInc = new TextView(MainActivity.this);
                displayInc.setId(View.generateViewId());
                displayInc.setText("+1");
                displayInc.setTextColor(Color.parseColor("#a7c9b0"));

                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                displayInc.setLayoutParams(lp);

                cl.addView(displayInc);

                ConstraintSet cs = new ConstraintSet();
                cs.clone(cl);

                cs.connect(displayInc.getId(), ConstraintSet.TOP, cl.getId(), ConstraintSet.TOP);
                cs.connect(displayInc.getId(), ConstraintSet.BOTTOM, cl.getId(), ConstraintSet.BOTTOM);
                cs.connect(displayInc.getId(), ConstraintSet.RIGHT, cl .getId(), ConstraintSet.RIGHT) ;
                cs.connect(displayInc.getId(),ConstraintSet.LEFT, cl.getId(), ConstraintSet.LEFT);
                cs.setHorizontalBias(displayInc.getId(),(float)(((Math.random()*10) + 10)/100));
                cs.setVerticalBias(displayInc.getId(),.3f);
                cs.applyTo(cl);

                displayInc.setTextColor(Color.parseColor("#343434"));

                animation = new TranslateAnimation(0, 0, 0, -70);
                animation.setDuration(1000);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation fadeOut = new AlphaAnimation(1, -2);
                        fadeOut.setDuration(2000);
                        displayInc.startAnimation(fadeOut);
                        displayInc.setText(" ");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                displayInc.startAnimation(animation);
                displayInc.setText("+1");
            }
        });

        upgrade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engineLevel++;
                upgrade1.setEnabled(false);
                upgrade1.setText(engineGoal + "mi");
                engineTime.getAndAdd(-(engineTime.get()/2));
                new engineUpgrades().start();
                display.setText("Distance Traveled: " + totalMilesTraveled + " mi");
                displayEngineLevel.setText("Lvl: " + engineLevel);
                scaleUp.cancel();
            }
        });

        upgrade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aeroLevel++;
                upgrade2.setEnabled(false);
                upgrade2.setText(aeroGoal + "mi");
                aeroTime.getAndAdd(-(aeroTime.get()/2));
                new aeroUpgrades().start();
                display.setText("Distance Traveled: " + totalMilesTraveled + " mi");
                displayAeroLevel.setText("Lvl: " + aeroLevel);
                scaleUp.cancel();
            }
        });

        upgrade3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                upgrade3.setEnabled(false);
                upgrade3.setText(tiresGoal + "mi");
                tiresTime.getAndAdd(-(tiresTime.get()/2));
                new tireUpgrades().start();
                display.setText("Distance Traveled: " + totalMilesTraveled + " mi");
                displayCurrentTires.setText("Current: " + tireCompounds[index]);
                scaleUp.cancel();

                if(tireCompounds[index].equals("HS")){
                    upgrade3.setEnabled(false);
                }
            }
        });
    }

    public class print extends Thread{
        @Override
        public void run() {
            while (true) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        display.setText("Distance Traveled: " + totalMilesTraveled + " mi");
                        if(totalMilesTraveled.get()==20 && totalMilesTraveled.get()!=0){
                            animateDRS(drs);
                            drs.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    drs.setImageResource(android.R.color.transparent);
                                    displayDRS.setText("DRS ACTIVATED");
                                    displayDRS.startAnimation(scaleUp);

                                    cl.setBackgroundResource(R.drawable.gradients_and_that);
                                    AnimationDrawable ad = (AnimationDrawable)cl.getBackground();
                                    ad.setEnterFadeDuration(100);
                                    ad.setExitFadeDuration(1500);
                                    ad.start();

                                    new DRSupgrades().start();
                                }
                            });
                        }

                        if(totalMilesTraveled.get()%engineGoal == 0 && totalMilesTraveled.get()>0){
                            upgrade1.setEnabled(true);
                            engineGoal*=10;
                            upgrade1.setText("UPGRADE!");
                            upgrade1.startAnimation(scaleUp);
                        }

                        if(totalMilesTraveled.get()%aeroGoal == 0 && totalMilesTraveled.get()>0){
                            upgrade2.setEnabled(true);
                            aeroGoal*=10;
                            upgrade2.setText("UPGRADE!");
                            upgrade2.startAnimation(scaleUp);
                        }

                        if(totalMilesTraveled.get()%tiresGoal == 0 && totalMilesTraveled.get()>0){
                            upgrade3.setEnabled(true);
                            tiresGoal*=10;
                            upgrade3.setText("UPGRADE!");
                            upgrade3.startAnimation(scaleUp);
                        }
                    }
                });
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class engineUpgrades extends Thread{
        @Override
        public void run() {
            while (true) {
                totalMilesTraveled.getAndIncrement();
                try {
                    Thread.sleep((long)(engineTime.get()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class aeroUpgrades extends Thread{
        @Override
        public void run() {
            while (true) {
                totalMilesTraveled.getAndIncrement();
                try {
                    Thread.sleep((long)(aeroTime.get()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class tireUpgrades extends Thread{
        @Override
        public void run() {
            while (true) {
                totalMilesTraveled.getAndIncrement();
                try {
                    Thread.sleep((long)(tiresTime.get()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class DRSupgrades extends Thread{
        @Override
        public void run() {
            boolean running = true;
            long startTime = System.currentTimeMillis();
            while (running) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= 15000) {
                    running = false;
                    cl.setBackgroundColor(Color.parseColor("#a7c9b0"));
                    scaleUp.cancel();
                    displayDRS.setText(" ");
                }
                totalMilesTraveled.getAndIncrement();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void animateDRS(ImageView imageView){
        imageView.setImageResource(R.drawable.drs);

        sa.setDuration(5000);
        sa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setImageResource(android.R.color.transparent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        imageView.startAnimation(sa);

    }
}*/