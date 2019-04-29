package com.sadek.se7takdoctor.firebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.sadek.se7takdoctor.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.model.Doctor;
import com.sadek.se7takdoctor.utils.Common;

public class FireAuth {
    Context context;
    private static final String TAG = FireAuth.class.getSimpleName();
    public FireDatabase fireDatabase;
    public FirebaseAuth mAuth;
    public FirebaseUser firebaseUser;
    ProgressDialog progressDialog;


    public FireAuth(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        fireDatabase = new FireDatabase(context, this);
    }

    public void signIn(String email, String password) {
        progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (mAuth.getCurrentUser().getDisplayName() != null) {
                            if (!mAuth.getCurrentUser().getDisplayName().equals(Common.FIREBASE_PATIENT)) {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }else {
                                mAuth.signOut();
                                Toast.makeText(context, context.getString(R.string.not_valid_user), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        } else {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
//                        Intent intent = new Intent(context, MainActivity.class);
//                        context.startActivity(intent);
//                        ((Activity)context).finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                        Toast.makeText(context, "" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                }
            });
        }
        public void forgetPassword(String email) {
        progressDialog.show();
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, context.getString(com.sadek.se7takdoctor.R.string.sent_to_mail), Toast.LENGTH_SHORT).show();
                                ((Activity)context).finish();
                                Log.d(TAG, "Email sent.");
                            }else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(context, "" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        }
                    });
        }
    public void signUp(final Doctor user) {
        progressDialog.setMessage("loading...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = mAuth.getCurrentUser();
                            user.setId(firebaseUser.getUid());
                            fireDatabase.addUser(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

}
