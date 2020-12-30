package com.ntncorp.amitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ntncorp.amitos.services.AmitosBusinessUtility;
import com.ntncorp.amitos.utility.DateUtility;
import com.ntncorp.amitos.utility.NewDateUtility;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UtilityActivity extends AppCompatActivity {

    TextView headerTextView;
    TextView amountTextView;

    static ArrayList<String> utilityItemList = new ArrayList<>();
    AmitosBusinessUtility amitosBusinessUtility;
    Date currentDate = new Date();

    private void initialisedUIItems() {
        headerTextView = findViewById(R.id.headerTextView);
        amountTextView = findViewById(R.id.amountTextView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility);

        amitosBusinessUtility = new AmitosBusinessUtility(getApplicationContext(), UtilityActivity.this);
        // Initlised UI items here
        initialisedUIItems();

        // Received Common from Main activity
        receivedCommandFromMainActivity();
    }

    private void receivedCommandFromMainActivity() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("index", -1);
        utilityItemList = intent.getStringArrayListExtra("utilityItemsList");
        int amount = 0;
        headerTextView.setVisibility(View.VISIBLE);
        amountTextView.setVisibility(View.VISIBLE);
        if (utilityItemList != null && !utilityItemList.isEmpty()) {
            String utilityName = utilityItemList.get(position);
            if (utilityName.equals("Today")) {
//                headerTextView.setText(DateUtility.convertDateToLocalDate(currentDate) + "");
                headerTextView.setText(NewDateUtility.convertDateToString_YYYYmmDD(currentDate) + "");
                amount = this.todayTotalCount(currentDate);
                amountTextView.setText(amount + " Rs.");
            } else if (utilityName.equals("Yesterday")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
//                headerTextView.setText(DateUtility.convertDateToLocalDate(cal.getTime()) + "");
                headerTextView.setText(NewDateUtility.convertDateToString_YYYYmmDD(cal.getTime()) + "");
                amount = this.todayTotalCount(cal.getTime());
                amountTextView.setText(amount + " Rs.");
            } else if (utilityName.equals("Current Month")) {
//                headerTextView.setText(DateUtility.getMonthInFull(DateUtility.convertDateToLocalDate(currentDate)));
                headerTextView.setText(NewDateUtility.getMonthInText(NewDateUtility.convertDateToString_YYYYmmDD(currentDate)));
                amount = currentMonthTotal(currentDate);
                amountTextView.setText(amount + " Rs.");
            } else if (utilityName.equals("Month Summary")) {
                headerTextView.setText(NewDateUtility.getMonthInText(NewDateUtility.convertDateToString_YYYYmmDD(currentDate)));
                currentMonthSummary();
            }

        }
    }

    private int todayTotalCount(Date date) {
        Log.i("Date", date.toString());
        int amount = amitosBusinessUtility.totalAmountByDate(date);
        return amount;
    }

    private int currentMonthTotal(Date date) {
        Log.i("Date", date.toString());
        int amount = amitosBusinessUtility.currentMonthTotal(date);
        return amount;
    }

    private void currentMonthSummary() {
        amountTextView.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getApplicationContext(), MonthSummaryActivity.class);
        startActivity(intent);
    }
}


