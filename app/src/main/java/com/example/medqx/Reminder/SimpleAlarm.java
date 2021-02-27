package com.example.medqx.Reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medqx.MainActivity;
import com.example.medqx.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SimpleAlarm extends AppCompatActivity implements View.OnClickListener {

    Spinner spInterval, spMedtype;

    private int notificationId=1;

    public int selectedMedicalInterval = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_alarm);

        findViewById(R.id.btnSetAlarm).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
        spInterval=findViewById(R.id.sp_interval);
        spMedtype=findViewById(R.id.sp_typeofMed);

        ArrayList<MedicalInterval> medicalIntervalList = new ArrayList<MedicalInterval>();
        //add medical interval
        medicalIntervalList.add(new MedicalInterval("Select Medical Interval", "0"));
        medicalIntervalList.add(new MedicalInterval("Every 2 Hour/s", "2"));
        medicalIntervalList.add(new MedicalInterval("Every 3 Hour/s", "3"));
        medicalIntervalList.add(new MedicalInterval("Every 4 Hour/s", "4"));
        medicalIntervalList.add(new MedicalInterval("Every 6 Hour/s", "6"));
        medicalIntervalList.add(new MedicalInterval("Every 8 Hour/s", "8"));
        medicalIntervalList.add(new MedicalInterval("Every 12 Hour/s", "12"));
        medicalIntervalList.add(new MedicalInterval("Every 24 Hour/s", "24"));

        // fill data in spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SimpleAlarm.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.medicalInterval));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInterval.setAdapter(myAdapter);

        spInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 1:
                        selectedMedicalInterval = 2;
                        // Whatever you want to happen when the second item gets selected
                        break;
                    case 2:
                        selectedMedicalInterval = 3;
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 3:
                        selectedMedicalInterval = 4;
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 4:
                        selectedMedicalInterval = 6;
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 5:
                        selectedMedicalInterval = 8;
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 6:
                        selectedMedicalInterval = 12;
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 7:
                        selectedMedicalInterval = 24;
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                }
                // test run
            }





            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no action
            }
        });

        //String[] MedInterval = {"Every 2 Hour/s","Every 3 Hour/s", "Every 4 Hour/s","Every 6 Hour/s", "Every 8 Hour/s", "Every 12 Hour/s", "Every 24 Hour/s"};
//        String[] MedInterval =  {"2","3", "4","6", "8", "12", "24"};

//        spInterval.setAdapter(new ArrayAdapter<>(SimpleAlarm.this, android.R.layout.simple_spinner_dropdown_item, MedInterval));

//
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);

        String[] MedType= {"Capsule/s", "Tablet/s", "Syrup/s"};
        spMedtype.setAdapter(new ArrayAdapter<>(SimpleAlarm.this, android.R.layout.simple_spinner_dropdown_item, MedType));
    }

    public List<LocalDateTime> getListOfDateIntervals(int increments, int selectedHour, int selectedMinutes, int hourInterval) {
        LocalDateTime nowTime = LocalDate.now().atTime(selectedHour, selectedMinutes);
        System.out.print(nowTime);
        List<LocalDateTime> listOfDatetime = new ArrayList<LocalDateTime>();
        listOfDatetime.add(nowTime);
        for (int i = 1; i < increments; i++) {
            // add hourInterval hrs
            nowTime = nowTime.plusHours(hourInterval);
            listOfDatetime.add(nowTime);
        }
        return listOfDatetime;
    }

    @Override
    public void onClick(View v) {
        /*String freq = spInterval.getSelectedItem().toString();
        int freqint = Integer.parseInt(freq);*/
        EditText etMed = findViewById(R.id.etMed);
        TimePicker timePicker = findViewById(R.id.timePicker);
        EditText med_take = findViewById(R.id.med_take);

        int medTakeIndex = Integer.parseInt(med_take.getText().toString());

        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);



        switch (v.getId()){
            case R.id.btnSetAlarm:
                    if(TextUtils.isEmpty(med_take.getText())){

                        med_take.setError("Enter Number");
                        med_take.requestFocus();

                    }
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                int mHour = 0;

                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                mHour = calendar.getTime().getHours();

                List<LocalDateTime> dateIntervals = getListOfDateIntervals(medTakeIndex, mHour, minute, selectedMedicalInterval);
                int i = 0;
                for (LocalDateTime dateInterval: dateIntervals) {
                    int randomNumber = 2 * (12031996 + i);
                    calendar.set(
                            dateInterval.getYear(),
                            dateInterval.getMonthValue() - 1,
                            dateInterval.getDayOfMonth(),
                            dateInterval.getHour(),
                            dateInterval.getMinute(),
                            0
                    );

                    long alarmStartTime = calendar.getTimeInMillis();
                    Intent intent = new Intent(SimpleAlarm.this, BroadcastReceiver.class);
                    intent.putExtra("notificationId", notificationId + randomNumber);
                    intent.putExtra("todo", etMed.getText().toString());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, randomNumber, intent, 0);
                    // set() schedules an alarm
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                    Log.i("PARSER", "calendar: : "+ calendar);
                    Log.i("PARSER", "Date Milliseconds : "+ alarmStartTime);
                    Log.i("PARSER", "Random Number : "+ randomNumber);
                    Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
                    i ++;
                }
                break;

            case R.id.btnCancel:

                //   alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}