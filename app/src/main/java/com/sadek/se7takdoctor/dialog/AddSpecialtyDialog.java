package com.sadek.se7takdoctor.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.sadek.se7takdoctor.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSpecialtyDialog extends Dialog {
    AddSpecialtyAction mAddSpecialtyAction;
    EditText specialty_en_name_input, specialty_ar_name_input ;
    ImageView reg_specialty_img;
    Button save_btn, back_btn;

    public AddSpecialtyDialog(@NonNull final Context context, final AddSpecialtyAction mAddSpecialtyAction) {
        super(context);
        setContentView(R.layout.add_specialty_dialog);
        this.mAddSpecialtyAction = mAddSpecialtyAction;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        specialty_en_name_input = findViewById(R.id.specialty_en_name_input);
        specialty_ar_name_input = findViewById(R.id.specialty_ar_name_input);
        reg_specialty_img = findViewById(R.id.reg_specialty_img);


        save_btn = findViewById(R.id.save_btn);
        back_btn = findViewById(R.id.back_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (specialty_en_name_input.getText().toString().isEmpty()) {
                    specialty_en_name_input.setError("error");
                }  else if (specialty_ar_name_input.getText().toString().isEmpty()) {
                    specialty_ar_name_input.setError("error");
                }  else {
                    mAddSpecialtyAction.onGetData(specialty_en_name_input.getText().toString(), specialty_ar_name_input.getText().toString());
                }
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        reg_specialty_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddSpecialtyAction.onUploadImage(reg_specialty_img);
            }
        });

    }

    public interface AddSpecialtyAction {
        void onGetData(String specialtyEnName,String specialtyArName);
        void onUploadImage(ImageView image);
    }

}