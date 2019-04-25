package com.sadek.se7takdoctor.firebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.model.AboutDoctor;
import com.sadek.se7takdoctor.model.Appointment;
import com.sadek.se7takdoctor.model.ClinicDoctor;
import com.sadek.se7takdoctor.model.DegreeDoctor;
import com.sadek.se7takdoctor.model.Doctor;
import com.sadek.se7takdoctor.model.Order;
import com.sadek.se7takdoctor.model.Patient;
import com.sadek.se7takdoctor.model.SpecialtyModel;
import com.sadek.se7takdoctor.model.WorkDaysDoctor;
import com.sadek.se7takdoctor.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class FireDatabase {

    Context context;
    private static final String TAG = FireDatabase.class.getSimpleName();
    public DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public FireAuth fireAuth;
    ProgressDialog progressDialog;


    public FireDatabase(Context context) {
        this.context = context;
        fireAuth = new FireAuth(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
    }

    public FireDatabase(Context context, FireAuth fireAuth) {
        this.context = context;
        this.fireAuth = fireAuth;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading...");
    }

    public void addUser(final Doctor user) {
        progressDialog.show();
        user.setPublished(false);
        reference.child(Common.FIREBASE_DOCTORS).child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void publishDoctor(final Doctor user) {
        progressDialog.show();
        user.setPublished(true);
        reference.child(Common.FIREBASE_DOCTORS).child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void addSpecialty(final SpecialtyModel model) {
        progressDialog.show();
        if (model.getId() == null) {
            String id = reference.push().getKey();
            model.setId(id);
        }
        reference.child(Common.FIREBASE_SPECIALTY).child(model.getId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    progressDialog.dismiss();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void addPatient(final Patient model) {
        progressDialog.show();
        if (model.getId() == null) {
            String id = reference.push().getKey();
            model.setId(id);
        }
        reference.child(Common.FIREBASE_PATIENTS).child(FirebaseAuth.getInstance().getUid()).child(model.getId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    progressDialog.dismiss();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void deletePatient(final Patient model) {
        progressDialog.show();
        String id = reference.push().getKey();
        model.setId(id);
        reference.child(Common.FIREBASE_PATIENTS).child(FirebaseAuth.getInstance().getUid()).child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    progressDialog.dismiss();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();
                    ((Activity) context).onBackPressed();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    // this method for get all drivers from database
    public void EditAppointment(Order order,String status , final ResultCallback callback) {
        progressDialog.show();
        order.setStuatus(status);
        reference.child(Common.FIREBASE_DOCTOR_ORDER).child(order.getId()).setValue(order)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onCallback(task.isSuccessful());
                        progressDialog.dismiss();
                    }
                });
    }

    public void addAppointment(final Order model) {
        progressDialog.show();
        String id = reference.push().getKey();
        model.setId(id);
        reference.child(Common.FIREBASE_DOCTOR_ORDER).child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    progressDialog.dismiss();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    // this method for get all drivers from database
    public void getSpecialty(final SpecialtyCallback callback) {
        final ArrayList<SpecialtyModel> list = new ArrayList<>();
        reference.child(Common.FIREBASE_SPECIALTY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            SpecialtyModel model = snapshot.getValue(SpecialtyModel.class);
                            list.add(model);
                        }
                    }
                    callback.onCallback(list);
                } else
                    callback.onCallback(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });
    }

    public void getEducationDegree(final DcotorDegreeCallback callback) {
        final ArrayList<DegreeDoctor> list = new ArrayList<>();
        reference.child(Common.FIREBASE_DOCTORS).child(fireAuth.mAuth.getUid()).child(Common.FIREBASE_DEGREE_DOCTOR).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            DegreeDoctor model = snapshot.getValue(DegreeDoctor.class);
                            list.add(model);
                        }
                    }
                    callback.onCallback(list);
                } else
                    callback.onCallback(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });
    }

    // this method for get all drivers from database
    public void getDoctor(final DoctorCallback callback) {

        reference.child(Common.FIREBASE_DOCTORS).child(fireAuth.mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Doctor doctor = new Doctor();
                if (dataSnapshot.exists()) {
                    doctor = dataSnapshot.getValue(Doctor.class);
                    callback.onCallback(doctor);
                } else
                    callback.onCallback(doctor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });
    }

    // this method for get all drivers from database
    public void getPatients(final PatientCallback callback) {
        final ArrayList<Patient> list = new ArrayList<>();
        reference.child(Common.FIREBASE_PATIENTS).child(fireAuth.mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            Patient model = snapshot.getValue(Patient.class);
                            list.add(model);
                        }
                    }
                    callback.onCallback(list);
                } else
                    callback.onCallback(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });
    }

    public void getAppointments(final String status, final AppointmentCallback callback) {
        final ArrayList<Order> list = new ArrayList<>();
        reference.child(Common.FIREBASE_DOCTOR_ORDER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            Order model = snapshot.getValue(Order.class);
                            if (model.getDoctorId().equals(FirebaseAuth.getInstance().getUid()) && model.getStuatus().equals(status))
                                list.add(model);
                        }
                    }
                    callback.onCallback(list);
                } else
                    callback.onCallback(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });
    }

    public void editAboutDoctor(AboutDoctor model) {
        progressDialog.show();

        reference.child(Common.FIREBASE_DOCTORS).child(fireAuth.mAuth.getUid()).child(Common.FIREBASE_ABOUT_DOCTOR).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    progressDialog.dismiss();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();
                    ((Activity) context).onBackPressed();
                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void editDegreeDoctor(DegreeDoctor degreeDoctor) {
        progressDialog.show();

        if (degreeDoctor.getId() == null)
            degreeDoctor.setId(reference.push().getKey());
        reference.child(Common.FIREBASE_DOCTORS).child(fireAuth.mAuth.getUid()).child(Common.FIREBASE_DEGREE_DOCTOR).child(degreeDoctor.getId()).setValue(degreeDoctor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    progressDialog.dismiss();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();
                    ((Activity) context).onBackPressed();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void editClinicDoctor(ClinicDoctor clinicDoctor) {
        progressDialog.show();

        if (clinicDoctor.getId() == null)
            clinicDoctor.setId(reference.push().getKey());
        reference.child(Common.FIREBASE_DOCTORS).child(fireAuth.mAuth.getUid()).child(Common.FIREBASE_CLINIC_DOCTOR).setValue(clinicDoctor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    progressDialog.dismiss();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();
                    ((Activity) context).onBackPressed();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
                ((Activity) context).onBackPressed();

            }
        });
    }

    public void editWorkDaysDoctor(ArrayList<WorkDaysDoctor> workDaysDoctors) {
        progressDialog.show();

        reference.child(Common.FIREBASE_DOCTORS).child(fireAuth.mAuth.getUid()).child(Common.FIREBASE_WORK_DAYS).setValue(workDaysDoctors).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    progressDialog.dismiss();
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();
                    ((Activity) context).onBackPressed();

                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
                ((Activity) context).onBackPressed();

            }
        });
    }

    public void getClinicPhoto(final ClinicPhotoCallback callback) {
        final ArrayList<String> list = new ArrayList<>();
        reference.child(Common.FIREBASE_DOCTORS).child(fireAuth.mAuth.getUid()).child(Common.FIREBASE_CLINIC_DOCTOR).child(Common.FIREBASE_CLINIC_PHOTO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            String model = snapshot.getValue(String.class);
                            list.add(model);
                        }
                    }
                    callback.onCallback(list);
                } else
                    callback.onCallback(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });
    }


    public interface SpecialtyCallback {
        void onCallback(ArrayList<SpecialtyModel> model);
    }

    public interface DcotorDegreeCallback {
        void onCallback(ArrayList<DegreeDoctor> model);
    }

    public interface DoctorCallback {
        void onCallback(Doctor model);
    }

    public interface ClinicPhotoCallback {
        void onCallback(ArrayList<String> model);
    }

    public interface PatientCallback {
        void onCallback(List<Patient> model);
    }

    public interface AppointmentCallback {
        void onCallback(List<Order> model);
    }

    public interface ResultCallback {
        void onCallback(boolean success);
    }
}
