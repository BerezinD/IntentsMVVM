package com.tommy.procrastinationtimer.ui.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.tommy.procrastinationtimer.R;

import static android.text.TextUtils.isEmpty;
import static com.tommy.procrastinationtimer.ui.main.MainActivity.EXTRA_TASK_TIME;
import static com.tommy.procrastinationtimer.ui.main.MainActivity.EXTRA_TASK_TITLE;

public class CreateNewTaskActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layoutView = inflater.inflate(R.layout.new_task_layout, null);
        LinearLayout layout = layoutView.findViewById(R.id.new_task_layout);
        setContentView(layout);

        int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
        int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
        final Intent intent = new Intent();

        TextView header = new TextView(this);
        setCommonParamsToTextView(header, wrapContent, wrapContent, Gravity.CENTER_HORIZONTAL, "Add a new Task", null);
        header.setTextSize(45f);

        TextView addTitle = new TextView(this);
        setCommonParamsToTextView(addTitle, wrapContent, wrapContent, Gravity.START, "Add a title:", null);
        addTitle.setTextSize(30f);

        final TextInputEditText addTitleInput = new TextInputEditText(this);
        setCommonParamsToTextView(addTitleInput, matchParent, wrapContent, Gravity.START, null, "type a name of task...");
        addTitleInput.setInputType(InputType.TYPE_CLASS_TEXT);

        TextView addTime = new TextView(this);
        setCommonParamsToTextView(addTime, wrapContent, wrapContent, Gravity.START, "Add time in minutes:", null);
        addTime.setTextSize(30f);

        final TextInputEditText addTimeInput = new TextInputEditText(this);
        setCommonParamsToTextView(addTimeInput, matchParent, wrapContent, Gravity.START, null, "type a name of task...");
        addTimeInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        Button okButton = new Button(this);
        okButton.setText("SAVE");
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(matchParent, wrapContent);
        buttonParams.gravity = Gravity.BOTTOM;
        buttonParams.setMargins(5, 5, 5, 5);
        okButton.setLayoutParams(buttonParams);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(addTimeInput.getText()) && !isEmpty(addTitleInput.getText())) {
                    intent.putExtra(EXTRA_TASK_TITLE, addTitleInput.getText().toString());
                    intent.putExtra(EXTRA_TASK_TIME, Long.parseLong(addTimeInput.getText().toString()));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        layout.addView(header);
        layout.addView(addTitle);
        layout.addView(addTitleInput);
        layout.addView(addTime);
        layout.addView(addTimeInput);
        layout.addView(okButton);
    }

    private void setCommonParamsToTextView(TextView viewToSet, int viewWidth, int viewHeight, int gravity, String text, String hint) {
        viewToSet.setText(text);
        viewToSet.setHint(hint);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(viewWidth, viewHeight);
        params.gravity = gravity;
        params.setMargins(5, 5, 5, 5);
        viewToSet.setLayoutParams(params);
    }
}
