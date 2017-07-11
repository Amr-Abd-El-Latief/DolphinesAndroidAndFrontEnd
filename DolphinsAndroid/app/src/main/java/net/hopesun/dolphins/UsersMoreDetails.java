package net.hopesun.dolphins;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import net.hopesun.dolphins.models.MoreDetails;

/**
 * Created by ahmedabd-elbaky on 5/14/17.
 */

public class UsersMoreDetails {
    private Button good;
    private Button normal;
    private Button sever;
    private Button notSure;

    // --------------

    private Button child;
    private Button man;
    private Button women;

    // --------------

    private SeekBar age;

    // --------------

    private EditText comment;

    private MoreDetails moreDetails = new MoreDetails();

    public UsersMoreDetails(View view){
        View.OnClickListener healthOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                good.setBackgroundResource(android.R.color.holo_blue_light);
                normal.setBackgroundResource(android.R.color.holo_blue_light);
                sever.setBackgroundResource(android.R.color.holo_blue_light);
                notSure.setBackgroundResource(android.R.color.holo_blue_light);

                good.setTag(0);
                normal.setTag(0);
                sever.setTag(0);
                notSure.setTag(0);

                view.setBackgroundResource(android.R.color.holo_red_dark);
                view.setTag(1);
            }
        };

        good = (Button) view.findViewById(R.id.good);
        normal = (Button) view.findViewById(R.id.normal);
        sever = (Button) view.findViewById(R.id.sever);
        notSure = (Button) view.findViewById(R.id.notSure);

        good.setOnClickListener(healthOnClickListener);
        normal.setOnClickListener(healthOnClickListener);
        sever.setOnClickListener(healthOnClickListener);
        notSure.setOnClickListener(healthOnClickListener);

        View.OnClickListener genderOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                child.setBackgroundResource(android.R.color.holo_blue_light);
                man.setBackgroundResource(android.R.color.holo_blue_light);
                women.setBackgroundResource(android.R.color.holo_blue_light);

                child.setTag(0);
                man.setTag(0);
                women.setTag(0);

                view.setBackgroundResource(android.R.color.holo_red_dark);
                view.setTag(1);
            }
        };

        child = (Button) view.findViewById(R.id.child);
        man = (Button) view.findViewById(R.id.man);
        women = (Button) view.findViewById(R.id.women);

        child.setOnClickListener(genderOnClickListener);
        man.setOnClickListener(genderOnClickListener);
        women.setOnClickListener(genderOnClickListener);

        age = (SeekBar) view.findViewById(R.id.age);
        comment = (EditText) view.findViewById(R.id.comment);

        initTags();
    }

    private void initTags(){
        good.setTag(0);
        normal.setTag(0);
        sever.setTag(0);
        notSure.setTag(0);

        child.setTag(0);
        man.setTag(0);
        women.setTag(0);
    }

    public MoreDetails getMoreDetails(){
        if((int)good.getTag() == 1){
            moreDetails.health = "good";
        }else if((int)normal.getTag() == 1){
            moreDetails.health = "normal";
        }else if((int)sever.getTag() == 1){
            moreDetails.health = "sever";
        }else if((int)notSure.getTag() == 1){
            moreDetails.health = "notSure";
        }

        if((int)child.getTag() == 1){
            moreDetails.gender = "child";
        }else if((int)man.getTag() == 1){
            moreDetails.gender = "man";
        }else if((int)child.getTag() == 1){
            moreDetails.gender = "child";
        }

        moreDetails.age = ""+age.getProgress();

        moreDetails.comment = comment.getText().toString();

        return moreDetails;
    }
}
