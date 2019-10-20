package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
    */
    public void submitOrder(View view) {
        EditText nameInput = (EditText) findViewById(R.id.name_input);
        String clientName = nameInput.getText().toString();

        CheckBox firstCheck = (CheckBox) findViewById(R.id.first_check_box);
        boolean hasWhippedCream = firstCheck.isChecked();

        CheckBox secondCheck = (CheckBox) findViewById(R.id.second_check_box);
        boolean hasChocolate = secondCheck.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = orderSummary(price, hasWhippedCream, hasChocolate, clientName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "caiobahia.nova@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + clientName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }



    public String orderSummary(int orderPrice, boolean wantsWhippedCream, boolean wantsChocolate, String clientName) {

        String message = "Name: " + getString(R.string.order_summary_name, clientName) + "\nAdd whipped cream? " + wantsWhippedCream + "\nAdd choloate? " + wantsChocolate + "\nQuantity: " + quantity + "\nTotal: $" + orderPrice + "\n" + getString(R.string.thank_you);

        return message;
    }


    /*
        This method is called when the plus button is clicked.
     */
    public void increment(View view)
    {
        if(quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffes", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity++;
        displayQuantity(quantity);
    }

    /*
        This method is called when the minus button is clicked.
     */
    public void decrement(View view)
    {
        if(quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffe", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity--;
        displayQuantity(quantity);
    }


    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     * @param hasWhippedCream indicates if the user wants whipped cream or not
     * @param hasChocolate indicates if the user wants chocolate or not
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;

        if(hasWhippedCream) {
            basePrice += 1;
        }

        if(hasChocolate) {
            basePrice += 2;
        }

        return basePrice*quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberIn) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberIn);
    }
}