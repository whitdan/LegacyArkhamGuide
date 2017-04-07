package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Fragment to display the chaos bag.
 */

public class ChaosBagActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    static ArrayList<Integer> chaosbag;
    int token = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaosbag);
        // Setup back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        globalVariables = (GlobalVariables) getApplication();

        setupBag(this);
        Button draw = (Button) findViewById(R.id.draw_token);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawToken(view);
            }
        });
        LinearLayout chaosBagLayout = (LinearLayout) findViewById(R.id.chaos_bag_layout);
        chaosBagLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBag();
            }
        });

        TextView continueButton = (TextView) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void drawToken(View view) {
        LinearLayout tokens = (LinearLayout) findViewById(R.id.token_layout);
        ImageView currentToken = (ImageView) findViewById(R.id.current_token);
        int numberOfViews = tokens.getChildCount();
        if (numberOfViews < 5) {
            if (token >= 0) {
                ImageView tokenView = new ImageView(view.getContext());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
                        .getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
                        .getDisplayMetrics());
                tokenView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
                int tokenId = view.getContext().getResources().getIdentifier("drawable/token_" + token, null, view
                        .getContext().getPackageName());
                tokenView.setImageResource(tokenId);
                tokens.addView(tokenView);
            }

            int chosen = new Random().nextInt(chaosbag.size());
            token = chaosbag.get(chosen);
            chaosbag.remove(chosen);

            int currentTokenId = view.getContext().getResources().getIdentifier("drawable/token_" + token, null, view
                    .getContext().getPackageName());
            currentToken.setImageResource(currentTokenId);

            Animation tokenAnimation = AnimationUtils.loadAnimation(this, R.anim.chaos_bag_animation);
            currentToken.startAnimation(tokenAnimation);
        }
    }

    private void resetBag() {
        LinearLayout tokens = (LinearLayout) findViewById(R.id.token_layout);
        tokens.removeAllViews();
        ImageView currentToken = (ImageView) findViewById(R.id.current_token);
        currentToken.setImageResource(0);
        token = -1;
        setupBag(this);
    }

    private static void setupBag(Activity activity) {
        // Setup chaos bag
        chaosbag = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < basebag()[i]; j++) {
                chaosbag.add(i);
            }
        }

        if (globalVariables.getCurrentScenario() < 100) {
            globalVariables.setPreviousScenario(globalVariables.getCurrentScenario());
        }

        switch (globalVariables.getCurrentCampaign()) {
            // Night of the Zealot
            case 1:
                if (globalVariables.getPreviousScenario() > 3) {
                    chaosbag.add(14);
                }
                break;
            // The Dunwich Legacy
            case 2:
                if (((globalVariables.getPreviousScenario() > 1 && globalVariables.getFirstScenario() == 1) ||
                        globalVariables.getPreviousScenario() > 2) && globalVariables.getStudents() == 0) {
                    chaosbag.add(13);
                }
                if (((globalVariables.getPreviousScenario() == 1 && globalVariables.getFirstScenario() == 2) ||
                        globalVariables.getPreviousScenario() > 2) && globalVariables.getInvestigatorsCheated() == 1) {
                    chaosbag.add(14);
                }
                if (globalVariables.getPreviousScenario() > 4 && globalVariables.getNecronomicon() == 2) {
                    chaosbag.add(14);
                }
                if (globalVariables.getPreviousScenario() > 4) {
                    switch (globalVariables.getCurrentDifficulty()) {
                        case 0:
                            chaosbag.add(4);
                            break;
                        case 1:
                            chaosbag.add(5);
                            break;
                        case 2:
                            chaosbag.add(6);
                            break;
                        case 3:
                            chaosbag.add(7);
                            break;
                    }
                }
                break;
        }

        Collections.sort(chaosbag);
        LinearLayout currentChaosBagOne = (LinearLayout) activity.findViewById(R.id.current_chaos_bag_one);
        LinearLayout currentChaosBagTwo = (LinearLayout) activity.findViewById(R.id.current_chaos_bag_two);
        LinearLayout currentChaosBagThree = (LinearLayout) activity.findViewById(R.id.current_chaos_bag_three);
        currentChaosBagOne.removeAllViews();
        currentChaosBagTwo.removeAllViews();
        currentChaosBagThree.removeAllViews();

        for (int i = 0; i < chaosbag.size(); i++) {
            int currentToken = chaosbag.get(i);
            ImageView tokenView = new ImageView(activity);
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, activity.getResources()
                    .getDisplayMetrics());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, activity.getResources()
                    .getDisplayMetrics());
            tokenView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            int tokenId = activity.getResources().getIdentifier("drawable/token_" + currentToken, null, activity
                    .getPackageName());
            tokenView.setImageResource(tokenId);
            if (currentChaosBagOne.getChildCount() < 12) {
                currentChaosBagOne.addView(tokenView);
            } else if (currentChaosBagTwo.getChildCount() < 12) {
                currentChaosBagTwo.addView(tokenView);
            } else {
                currentChaosBagThree.addView(tokenView);
            }
        }
    }

    // Difficulties
    private static int[] basebag() {
        int[] bag = new int[17];
        switch (globalVariables.getCurrentCampaign()) {
            // Night of the Zealot
            case 1:
                switch (globalVariables.getCurrentDifficulty()) {
                    case 0:
                        bag = new int[]{0, 2, 3, 3, 2, 0, 0, 0, 0, 0, 0, 2, 1, 1, 0, 1, 1};
                        break;
                    case 1:
                        bag = new int[]{0, 1, 2, 3, 2, 1, 1, 0, 0, 0, 0, 2, 1, 1, 0, 1, 1};
                        break;
                    case 2:
                        bag = new int[]{0, 0, 3, 2, 2, 2, 1, 1, 0, 0, 0, 2, 1, 1, 0, 1, 1};
                        break;
                    case 3:
                        bag = new int[]{0, 0, 1, 2, 2, 2, 2, 1, 1, 0, 1, 2, 1, 1, 0, 1, 1};
                }
                break;
            // Dunwich Legacy
            case 2:
                switch (globalVariables.getCurrentDifficulty()) {
                    case 0:
                        bag = new int[]{0, 2, 3, 3, 2, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 1, 1};
                        break;
                    case 1:
                        bag = new int[]{0, 1, 2, 3, 2, 1, 1, 0, 0, 0, 0, 2, 1, 0, 0, 1, 1};
                        break;
                    case 2:
                        bag = new int[]{0, 0, 3, 2, 2, 2, 1, 1, 0, 0, 0, 2, 1, 0, 0, 1, 1};
                        break;
                    case 3:
                        bag = new int[]{0, 0, 1, 2, 2, 2, 2, 1, 1, 0, 1, 2, 1, 0, 0, 1, 1};
                        break;
                }
                break;
        }
        if (globalVariables.getCurrentCampaign() == 999) {
            switch (globalVariables.getCurrentScenario()) {
                // Curse of the Rougarou
                case 101:
                    switch (globalVariables.getCurrentDifficulty()) {
                        case 0:
                            bag = new int[]{0, 2, 3, 3, 2, 2, 2, 1, 1, 0, 0, 2, 2, 1, 1, 1, 1};
                            break;
                        case 1:
                            bag = new int[]{0, 1, 3, 3, 2, 2, 2, 2, 1, 0, 1, 3, 2, 1, 1, 1, 1};
                            break;
                    }
                    break;
                // Carnevale of Horrors
                case 102:
                    switch (globalVariables.getCurrentDifficulty()) {
                        case 0:
                            bag = new int[]{0, 1, 3, 3, 1, 1, 1, 0, 1, 0, 0, 3, 1, 1, 1, 1, 1};
                            break;
                        case 1:
                            bag = new int[]{0, 1, 3, 2, 0, 1, 1, 1, 1, 1, 0, 3, 1, 1, 1, 1, 1};
                            break;
                    }
                    break;
            }
        }
        return bag;
    }

    /*
     Sets up overflow menu
      */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_chaosbag_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.set_difficulty:
                SetDifficultyDialogFragment setDifficulty = new SetDifficultyDialogFragment();
                setDifficulty.show(this.getFragmentManager(), "difficulty");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SetDifficultyDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.set_difficulty);
            if(globalVariables.getCurrentCampaign() != 999){
            builder.setItems(R.array.difficulty, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    globalVariables.setCurrentDifficulty(which);
                    setupBag(getActivity());
                }
            });}
            else{
                builder.setItems(R.array.difficulty_two, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        globalVariables.setCurrentDifficulty(which);
                        setupBag(getActivity());
                    }
                });
            }
            return builder.create();
        }
    }
}
