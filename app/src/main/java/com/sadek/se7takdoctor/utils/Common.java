package com.sadek.se7takdoctor.utils;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sadek.se7takdoctor.model.DataMessage;
import com.sadek.se7takdoctor.model.MyResponse;
import com.sadek.se7takdoctor.model.Token;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Common {
    public static final String FIREBASE_USER	 = "FIREBASE_USER";
    public static final String FIREBASE_DOCTORS	 = "FIREBASE_DOCTORS";
    public static final String FIREBASE_PATIENT = "FIREBASE_PATIENT";
    public static final String FIREBASE_SPECIALTY = "FIREBASE_SPECIALTY";
    public static final String FIREBASE_ABOUT_DOCTOR = "aboutDoctor";
    public static final String FIREBASE_DEGREE_DOCTOR = "degreeDoctor";
    public static final String FIREBASE_CLINIC_DOCTOR = "clinicDoctor";
    public static final String FIREBASE_CLINIC_PHOTO = "imges";
    public static final String FIREBASE_PATIENTS = "patients";
    public static final String FIREBASE_APPOINTMENT = "appointments";
    public static final String FIREBASE_WORK_DAYS = "workDaysDoctor";
    public static final String ADMIN_ACCOUNT = "admin@se7tak.com";
    public static String language="language";
    public static final String FIREBASE_DOCTOR_ORDER = "FIREBASE_DOCTOR_ORDER";
    public static final String ORDER_STATUS_PENDING = "0";
    public static final String ORDER_STATUS_ACCEPTED = "1";
    public static final String ORDER_STATUS_COMPLETED = "2";
    public static final String ORDER_STATUS_REJECTED = "3";
    public static final String ORDER_STATUS_CANCELED = "4";

    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;


    public static void newToken(Activity activity) {
        try {
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( activity,  new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();
                    Log.e("newToken",newToken);
                    if (FirebaseAuth.getInstance().getCurrentUser() != null)
                        updateTokenToFirebase(newToken);

                }
            });
        } catch (Exception e) {

        }
    }

    public static void updateTokenToFirebase(String tokenRefreshed) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref_tokens = db.getReference("Tokens");
        Token token = new Token(tokenRefreshed, false);
        ref_tokens.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    private static final String BASE_URL = "https://fcm.googleapis.com/";
    public static APIService getFCMService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
    public static void sendNotificationOrder(final String doctorId, final String messageTxt, Activity activity) {
        final APIService mService = getFCMService();
        final android.app.AlertDialog waitingDialog = new SpotsDialog(activity);
        waitingDialog.show();

        DatabaseReference ref_tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        final Query data = ref_tokens;
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Token severToken = postSnapshot.getValue(Token.class);
                    if (postSnapshot.getKey().equals(doctorId)) {
                        //Create raw payload to send
//                    Notification notification = new Notification("SADEK RESTAURANT", "You have new Order " + order_number);
//                    Sender content = new Sender(severToken.getToken(), notification);
                        Map<String, String> dataSender = new HashMap<>();
                        dataSender.put("title", "Se7tak App");
                        dataSender.put("message", messageTxt);
                        DataMessage dataMessage = new DataMessage(severToken.getToken(), dataSender);

                        mService.sendNotification(dataMessage)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.code() == 200) {
                                            if (response.body().success == 1) {
                                                waitingDialog.cancel();
                                            } else {
                                                waitingDialog.cancel();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        waitingDialog.cancel();
                                        Log.e("Error", "onFailure: " + t.getMessage());
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
