package com.example.dkim5.gpa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Course course;


    private ArrayList<EditText> credit, num;
    private ArrayList<View> itemViews;
    private ArrayList<CheckBox> checkboxes;
    public static File myFile;
    LinearLayout itemsLayout;
    private RelativeLayout resultLayout;
    private ArrayList<Spinner> spinnerg;

    private int ETID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultLayout = (RelativeLayout) findViewById(R.id.result_layout);
        course = new Course();
        credit = new ArrayList<>();
        num = new ArrayList<>();
        spinnerg = new ArrayList<>();

        itemViews = new ArrayList<>();
        checkboxes = new ArrayList<>();

        for (int i = 0; i < course.getNumOfItem(); i++) {
            addItem();
        }
        updateET();


    }


    public void updateET() {
        for (int i = 0; i < course.getNumOfItem(); i++) {
            Item item = course.getItem(i);


            if (item.getcredit() != -1) {
                credit.get(i).setText(convertDouble(item.getcredit()));
            }
            else {
                credit.get(i).getText().clear();
            }
            if (item.getnum() != -1) {
                num.get(i).setText(convertDouble(item.getnum()));

            } else {
                num.get(i).getText().clear();
            }

        }
    }

    private String convertDouble(Double value) {
        if (value.intValue() == value) {
            return "" + value.intValue();
        }
        return "" + value;
    }

    public void checkAll(View view) {
        CheckBox checkbox = (CheckBox) findViewById(R.id.all_checkbox);
        for (int i = 0; i < checkboxes.size(); i++) {
            if (!checkboxes.get(i).isChecked()) {
                for (int j = 0; j < checkboxes.size(); j++) {
                    checkboxes.get(j).setChecked(true);
                }
                checkbox.setChecked(true);
                return;
            }


        }
        for (int j = 0; j < checkboxes.size(); j++) {
            checkboxes.get(j).setChecked(false);
        }
        checkbox.setChecked(false);
    }

    public void addItem() {
        itemsLayout = (LinearLayout) findViewById(R.id.itemsLayout);
        View itemView = getLayoutInflater().inflate(R.layout.item_layout, itemsLayout, false);


        EditText creditET = (EditText) itemView.findViewById(R.id.item_credit);
        EditText numET = (EditText) itemView.findViewById(R.id.item_num);
        CheckBox checkbox = (CheckBox) itemView.findViewById(R.id.item_checkbox);
        Spinner spinnerET = (Spinner) itemView.findViewById(R.id.spinner);
        ArrayAdapter GradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade_arrays, android.R.layout.simple_spinner_item);
        GradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerET.setAdapter(GradeAdapter);

        int index = ETID + 3 * itemViews.size();
        for (int i = 0; i < 3; i++) {
            if (findViewById(index + i) == null) {
                switch (i) {
                    case 0:
                        spinnerET.setId(index + i);
                    case 1:
                        creditET.setId(index + i);
                        break;
                    case 2:
                        numET.setId(index + i);
                        break;

                }
            }
        }
        if ((itemViews.size() != 0) && (findViewById(spinnerET.getId() - 1) != null)) {
            findViewById(spinnerET.getId() - 1).setNextFocusDownId(spinnerET.getId());
        }
        spinnerET.setNextFocusDownId(creditET.getId());
        creditET.setNextFocusDownId(numET.getId());


        itemViews.add(itemView);
        spinnerg.add(spinnerET);
        credit.add(creditET);
        num.add(numET);
        checkboxes.add(checkbox);
        itemsLayout.addView(itemView);

    }

    public void removeItemDialog(View view) {
        alert();
    }

    public void alert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Remove");
        dialog.setMessage("Are you sure you want to delete this entry?");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                removeItem();
            }
        })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.show();

    }


    public void removeItem() {
        buttonBlink(R.id.remove);
        CheckBox checkbox;
        ArrayList<EditText> remove_credit = new ArrayList<>();
        ArrayList<EditText> remove_num = new ArrayList<>();
        ArrayList<CheckBox> remove_checkbox = new ArrayList<>();
        ArrayList<Spinner> remove_spinner = new ArrayList<>();
        ArrayList<View> remove_itemView = new ArrayList<>();
        ArrayList<Item> remove_item = new ArrayList<>();
        for (int i = 0; i < checkboxes.size(); i++) {
            checkbox = checkboxes.get(i);
            if (checkbox.isChecked()) {
                itemsLayout.removeView((itemViews.get(i)));
                remove_item.add(course.getItem(i));
                remove_spinner.add(spinnerg.get(i));
                remove_credit.add(credit.get(i));
                remove_num.add(num.get(i));
                remove_checkbox.add(checkboxes.get(i));
                remove_itemView.add(itemViews.get(i));

            }

        }
        course.deleteAllitem((remove_item));
        spinnerg.removeAll(remove_spinner);
        credit.removeAll(remove_credit);
        num.removeAll(remove_num);
        checkboxes.removeAll(remove_checkbox);
        itemViews.removeAll(remove_itemView);
    }
    public void showExplanation(View view) {
        DialogHelp dialog = new DialogHelp();
        dialog.show(getFragmentManager(), null);
    }

    public void addItemButton(View view) {
        buttonBlink(R.id.add);
        Item item = new Item();
        course.additem(item);
        addItem();
        spinnerg.get(spinnerg.size() - 1).requestFocus();
    }

    public void calculate(View view) {
        buttonBlink(R.id.calculate);
        resultLayout.removeAllViews();
        // show result and add x button
        RelativeLayout.LayoutParams params;
        params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_TOP, R.id.result);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.result_extra);
        Button button = new Button(resultLayout.getContext());
        button.setBackgroundColor(Color.BLACK);
        button.setId(R.id.result_button);
        button.setMinWidth(0);
        button.setMinimumWidth(0);
        button.setText("x");
        button.setTextColor(Color.WHITE);
        button.setLayoutParams(params);
        button.setOnClickListener(this);
        //add vertical line
        TextView line = new TextView(resultLayout.getContext());
        line.setId(R.id.vertical_line);
        params = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()),
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, button.getId());
        params.addRule(RelativeLayout.ALIGN_TOP, R.id.result);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.result_extra);
        params.setMargins(0, 0, 10, 0);
        line.setLayoutParams(params);
        line.setBackgroundColor(Color.WHITE);
        //add resultTV
        TextView resultTV = new TextView(resultLayout.getContext());
        resultTV.setId(R.id.result);
        params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, line.getId());
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        resultTV.setLayoutParams(params);
        resultTV.setBackgroundColor(Color.BLACK);
        resultTV.setTextColor(Color.WHITE);
        //add resultExtraTV
        TextView resultExtraTV = new TextView(resultLayout.getContext());
        resultExtraTV.setId(R.id.result_extra);
        params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, line.getId());
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, R.id.result);
        resultExtraTV.setLayoutParams(params);
        resultExtraTV.setBackgroundColor(Color.BLACK);
        resultExtraTV.setTextColor(Color.WHITE);

        resultTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        resultExtraTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        resultLayout.addView(button);
        resultLayout.addView(line);
        resultLayout.addView(resultTV);
        resultLayout.addView(resultExtraTV);

        doCalculation(resultTV, resultExtraTV);
    }

    private void doCalculation(TextView resultTV, TextView resultExtraTV) {
        double markGained = 0, spin = 0, tt, Total, aa = 0;
        String sgrade, cre, time;
        String str;
        DecimalFormat twoDecimal = new DecimalFormat("#.##");

        CheckBox checkbox;
        for (int i = 0; i < itemViews.size(); i++) {
            checkbox = checkboxes.get(i);
            sgrade = spinnerg.get(i).getSelectedItem().toString();
            cre = credit.get(i).getText().toString();
            time = num.get(i).getText().toString();

            if (!checkbox.isChecked() && (cre.length() != 0) && (time.length() != 0)) {
                if ((cre.equals(".")) || (time.equals("."))) {
                    resultTV.setText("Invalid entry \".\"");
                    return;
                }
                if (sgrade.equals("A")) {
                    spin = 4.0;
                } else if (sgrade.equals("A-")) {
                    spin = 3.7;
                } else if (sgrade.equals("B+")) {
                    spin = 3.3;
                } else if (sgrade.equals("B")) {
                    spin = 3;
                } else if (sgrade.equals("B-")) {
                    spin = 2.7;
                } else if (sgrade.equals("C+")) {
                    spin = 2.3;
                } else if (sgrade.equals("C")) {
                    spin = 2;
                } else if (sgrade.equals("C-")) {
                    spin = 1.7;
                } else if (sgrade.equals("D+")) {
                    spin = 1.3;
                } else if (sgrade.equals("D")) {
                    spin = 1;
                } else if (sgrade.equals("F")) {
                    spin = 0;
                }
                tt = Double.parseDouble(time);
                if (tt < 1) {
                    resultExtraTV.setText("number of class can not be 0");
                    return;
                } else if (tt > 1) {
                    for (int k = 1; k <= tt; k++) {
                        aa += Double.parseDouble(cre);
                    }

                } else
                    aa += Double.parseDouble(cre);

                markGained += spin * (Double.parseDouble(cre) * Double.parseDouble(time));

            }
        }
        Total = markGained / aa;
        String hi = twoDecimal.format(Total);
        str = "Your GPA is:" + hi;
        resultExtraTV.setText(str);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.result_button) {
            resultLayout.removeView(findViewById(R.id.result));
            resultLayout.removeView(findViewById(R.id.vertical_line));
            resultLayout.removeView(findViewById(R.id.result_extra));
            resultLayout.removeView(findViewById(R.id.result_button));
        }
    }

    private void buttonBlink(int id) {
        Animation ani = new AlphaAnimation(1, 0);
        ani.setDuration(70);
        findViewById(id).startAnimation(ani);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Item item;
        for (int i = 0; i < course.getNumOfItem(); i++) {
            item = course.getItem(i);

            if ((credit.get(i).getText().length() != 0) && (!credit.get(i).getText().toString().equals("."))) {
                item.setCredit(Double.parseDouble(credit.get(i).getText().toString()));
            } else { item.setCredit(-1); }
            if ((num.get(i).getText().length() != 0) && (!num.get(i).getText().toString().equals("."))) {
                item.setnum(Double.parseDouble(num.get(i).getText().toString()));
            } else { item.setnum(-1); }

        }
    }
}





