package com.ntncorp.amitos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ntncorp.amitos.services.AmitosBusinessUtility;
import com.ntncorp.amitos.utility.DateUtility;

import java.time.format.TextStyle;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MonthSummaryActivity extends AppCompatActivity {

    TableLayout summaryGridLayout;
    TableRow rowHeader;
    TextView seqRow, dateRow, amountRow, txtMonthId;
    AmitosBusinessUtility amitosBusinessUtility;

    private void initialisedUIItems() {

        summaryGridLayout = findViewById(R.id.summaryGridId);
        txtMonthId = findViewById(R.id.txtMonthId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_summary);

        amitosBusinessUtility = new AmitosBusinessUtility(getApplicationContext(), MonthSummaryActivity.this);
        initialisedUIItems();

        displayCurrentMonthSummary();
    }

    private void displayCurrentMonthSummary() {
        Date currDate = new Date();
        Map<String, Integer> dateStringMap = amitosBusinessUtility.currentMonthSummary(currDate);
        int totalAmount = 0;
        String month = DateUtility.convertDateToLocalDate(currDate).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        txtMonthId.setText("Current Month Summary : " + month);
        if (!dateStringMap.isEmpty()) {
            Set<String> dateKeySet = dateStringMap.keySet();
            Iterator<String> itr = dateKeySet.iterator();
            int seqNo = 0;

            setTableRow("S.No", "Date", "Amount");
            int amount = 0;
            while (itr.hasNext()) {
                String date = itr.next();
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                amount = dateStringMap.get(date);
                if (amount > 0) {
                    seqNo++;
                    totalAmount = totalAmount + amount;
                    setTableRow(seqNo + "", date, amount + "");

                    row.addView(seqRow);
                    row.addView(dateRow);
                    row.addView(amountRow);
                    summaryGridLayout.addView(row);
                }
            }
//            Log.i("Total Amount",totalAmount+"");
            displayTotalAmountRow(totalAmount, month);
        }
    }

    private void setTableRow(String seqId, String date, String amount) {
        seqRow = new TextView(this);
        dateRow = new TextView(this);
        amountRow = new TextView(this);

        seqRow.setText(seqId);
        dateRow.setText(date);
        amountRow.setText(amount);
    }

    private void displayTotalAmountRow(int totalAmount, String month) {
        TableRow totalRow = new TableRow(this);
        TextView totalAmtTextView = new TextView(this);
        totalAmtTextView.setText("Total Amount :" +month + " - Rs. "+ totalAmount);
        totalRow.addView(totalAmtTextView);
        summaryGridLayout.addView(totalRow);
    }
}
