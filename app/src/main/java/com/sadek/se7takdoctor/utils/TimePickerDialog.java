package com.sadek.se7takdoctor.utils;

import android.content.Context;
import android.widget.TimePicker;
import com.sadek.se7takdoctor.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimePickerDialog  {

    public static void fire(Context context, final TimeSelectionView timeView){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        android.app.TimePickerDialog mTimePicker;
        mTimePicker = new android.app.TimePickerDialog(context, new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                final Calendar myCalendar = Calendar.getInstance(Locale.ENGLISH);
                String am_pm = "";
                myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);

                DateFormat formatterhour = new SimpleDateFormat("HH:mm");
                myCalendar.set(Calendar.MINUTE, selectedMinute);



//                etDate.setText(sdf.format(mcurrentDate.getTime()));
                timeView.onTimeSelected(formatterhour.format(myCalendar.getTime()),myCalendar.getTime());
               /* if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";
                String strHrsToShow = (myCalendar.get(Calendar.HOUR) == 0) ? "12" : myCalendar.get(Calendar.HOUR) + "";
                timeView.onTimeSelected(String.format(Locale.ENGLISH,"%02d", Integer.parseInt(strHrsToShow)) + ":" + String.format(Locale.ENGLISH,"%02d",myCalendar.get(Calendar.MINUTE)) + " " + am_pm, mcurrentTime.getTime());
*/            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle(context.getString(R.string.chosetime));
        mTimePicker.show();
    }

    public interface TimeSelectionView {
        void onTimeSelected(String time, Date dateTime);
    }
}