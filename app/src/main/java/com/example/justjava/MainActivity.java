package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.widget.Toast.LENGTH_SHORT;


public class MainActivity<hasWhippedCream> extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    int quantity = 1;
//    public void submitOrders(View view) {
//        display(quantity);
//        displayPrice(quantity*5);
//    }

    public void increment(View view) {
        if (quantity == 10) {
            Toast.makeText(this, "You cannot more than 10 coffees!!", LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee!!", LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view){
        EditText text = (EditText) findViewById(R.id.PersonName);
        String name = text.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedcream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java" + name);
//        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
//        if (null != intent.resolveActivity(getPackageManager())) {
//            startActivity(intent);
//        }
        displayMessage(priceMessage);
        Toast.makeText(MainActivity.this, "Your order of coffees has been taken! Enjoy! ", Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("QueryPermissionsNeeded")
    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 50;
        if (addWhippedCream){
            basePrice = basePrice + 10;
        }
        if (addChocolate){
         basePrice = basePrice +20;
        }
        return quantity*basePrice;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolateBox, String name) {
        String priceMessage = "Name: " + name;
        if (addWhippedCream){
            priceMessage += "\nWhipped Cream Added";
        }
        if (addChocolateBox){
            priceMessage += "\nChocolate Added";
        }
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: Rs." + price;
        priceMessage += "\nThank You!";
        return priceMessage;
    }

    public void payHere(View view){
        String payHereButton = "You have Paid Rs. " + quantity*50;
        displayMessage(payHereButton);
        Toast.makeText(MainActivity.this,
                "Your payment has been done! ",
                Toast.LENGTH_SHORT).show();
    }
//    public void onClick(View view){
//        Toast.makeText(MainActivity.this, "You have succesfully converted!! ", LENGTH_SHORT).show();
//
//    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView;
        quantityTextView = (TextView) findViewById(R.id.show_text_view);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    private void displayMessage(String message){
        TextView priceTextView = (TextView) findViewById(R.id.order_message);
        priceTextView.setText(message);
    }


}