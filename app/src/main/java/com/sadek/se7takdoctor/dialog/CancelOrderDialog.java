package com.sadek.se7takdoctor.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;

public class CancelOrderDialog extends Dialog {


    EditText reason_txt_input;
    Button apply_btn;

    public CancelOrderDialog(@NonNull final Context context, final DialogInterAction dialogInterAction) {
        super(context);
        setContentView(R.layout.cancel_order_dialog);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        reason_txt_input = findViewById(R.id.reason_txt_input);

        apply_btn = findViewById(R.id.apply_btn);

        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = reason_txt_input.getText().toString();
                if (!validate(context,reason)) {
                    return;
                }

                dialogInterAction.onSubmit(reason);
            }
        });

        this.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialogInterAction.onDismissed();
            }
        });
    }

    private boolean validate(Context context, String phone) {
        reason_txt_input.setError(null);
        if (phone.isEmpty()) {
            reason_txt_input.setError(context.getString(R.string.enter_reason));
            return false;
        }
        return true;
    }

    public interface DialogInterAction {
        void onDismissed();
        void onSubmit(String reason);
    }
}