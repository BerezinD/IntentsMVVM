package com.tommy.procrastinationtimer.ui.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tommy.procrastinationtimer.R;
import com.tommy.procrastinationtimer.models.Task;

import static android.text.TextUtils.isEmpty;
import static com.tommy.procrastinationtimer.ui.main.MainActivity.*;

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

        final TextInputEditText addTitleInput = new TextInputEditText(this);
        setCommonParamsToTextView(addTitleInput, matchParent, wrapContent, Gravity.START, null, "type a name of task...");
        addTitleInput.setInputType(InputType.TYPE_CLASS_TEXT);

        final TextInputEditText addTimeInput = new TextInputEditText(this);
        setCommonParamsToTextView(addTimeInput, matchParent, wrapContent, Gravity.START, null, "type a time of task in minutes...");
        addTimeInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        final View stubView = new View(this);
        LinearLayout.LayoutParams stubParams = new LinearLayout.LayoutParams(matchParent, 0, 1);
        stubView.setLayoutParams(stubParams);

        final FloatingActionButton saveButton = new FloatingActionButton(this);
        final int margin16 = getPixelsFromDP(this, 16);
        LinearLayout.LayoutParams saveButtonParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);
        saveButtonParams.gravity = Gravity.BOTTOM | Gravity.END;
        saveButtonParams.setMargins(margin16, margin16, margin16, margin16);
        saveButton.setLayoutParams(saveButtonParams);
        saveButton.setImageResource(android.R.drawable.ic_menu_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(addTimeInput.getText()) && !isEmpty(addTitleInput.getText())) {
                    intent.putExtra(EXTRA_TASK, new Task(addTitleInput.getText().toString(),
                            Long.parseLong(addTimeInput.getText().toString())));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        layout.addView(addTitleInput);
        layout.addView(addTimeInput);
        layout.addView(stubView);
        layout.addView(saveButton);
    }

    private void setCommonParamsToTextView(TextView viewToSet, int viewWidth, int viewHeight, int gravity, String text, String hint) {
        final int margin5 = getPixelsFromDP(this, 5);
        viewToSet.setText(text);
        viewToSet.setHint(hint);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(viewWidth, viewHeight);
        params.gravity = gravity;
        params.setMargins(margin5, margin5, margin5, margin5);
        viewToSet.setLayoutParams(params);
    }

    private int getPixelsFromDP(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }
}
