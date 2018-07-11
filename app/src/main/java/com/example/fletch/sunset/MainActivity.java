package com.example.fletch.sunset;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    /* Propriétés */
    private ImageView sunView;
    private FrameLayout skyView;
    private int blueSkyColor;
    private int sunsetSkyColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * FindViewById retourne la view définit dans /res
         * Ici, nous retournons une ImageVIe et un FrameLayout qui sont
         * des enfants de View.
         * Le "Casting" ici n'est pas obligatoire puisque le
         * type de retour de findViewById est T
         */
        sunView = findViewById(R.id.sun);
        skyView = (FrameLayout) findViewById(R.id.sky);

        ViewGroup currentViewGroup = findViewById(android.R.id.content);

        /**
         * Prenez notre que setOnClickListener attend un interface View.OnClickListener
         * Un interface ne possède pas de code. Seulement un contrat d'utilisation.
         * Vous devez donc définif le code des méthodes de celui-ci.
         * Dans le cas qui nous concerne : onClick
         *
         * D'ailleurs, lorsque l'on crée une classe à la volé comme ceci, on
         * parle d'une "Anonymous class"
         */
        currentViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Appel de la méthode startAnimation de l'objet en cours .. */
                startAnimation();
            }
        });


    }

    private void startAnimation() {
        /**
         * La variable position est utilisée afin de
         * connaître le positionnement du soleil
         * position[0] = axe x
         * position[1] = axe y
         */
        int[] position = new int[2];
        /**
         * Retourne la localisation sur l'écran. Contrairement à des méthodes tel getTop(),
         * ça retourne la position de la view même si elle est différente de ce qui a été
         * défini dans le ressource (sun.xml).
         *
         * N.B. : Le paramètre position est passé comme une référence à position.
         * L'array est un objet en java et lors objets sont toujours traité comme cela.
         *
         * C'est pour cette raison que geLocationOnScreen ne retourne pas de valeur.
         * La valeur de position est directement changée.
         *
         */
        sunView.getLocationOnScreen(position);
        if(position[1] >= skyView.getHeight()){
            /* Lever de soleil */
            sunriseAnimation();
        } else {
            /* Coucher de soleil */
            sunsetAnimation();
        }
    }


    private void sunsetAnimation(){
        float sunYStart = sunView.getTop();
        float sunYEnd = skyView.getHeight();
        /**
         * La méthode ofFloat est static
         * Conséquemment, elle est appelée directement sans le mot clé new.
         * Elle retourne un objet ObjectAnimator
         */
        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());



        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", getResources().getColor(R.color.blue_sky), getResources().getColor(R.color.sunset_sky))
                .setDuration(3000);

        /**
         * L'interpolation permet de modifier le comportement lors de l'animation.
         * Ici, on accèlere de plus en plus le couche de soleil
         */
        sunsetSkyAnimator.setInterpolator(new AccelerateInterpolator());
        /**
         * Permet d'annuler l'effet stroboscopique et de modifier les couleurs
         * de manière harmonieuse
         *
         * cela indique à ObjectAnimator quelle valeur est
         * entre une valeur de départ et une valeur de fin.
         */
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        heightAnimator.start();
        sunsetSkyAnimator.start();
    }

    private void sunriseAnimation(){
        float sunYStart = sunView.getTop();
        float sunYEnd = skyView.getHeight();
        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunYEnd, sunYStart)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());


        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", getResources().getColor(R.color.sunset_sky), getResources().getColor(R.color.blue_sky))
                .setDuration(3000);
        sunsetSkyAnimator.setInterpolator(new AccelerateInterpolator());
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        heightAnimator.start();
        sunsetSkyAnimator.start();
    }




}
