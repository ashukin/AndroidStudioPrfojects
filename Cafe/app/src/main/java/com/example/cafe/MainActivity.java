/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.cafe;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.*;
import com.example.cafe.R;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity =0;


    // To tell that this method defined here overrides any such method defined in AppCompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

       EditText text = (EditText) findViewById(R.id.name_view);
       String name = text.getText().toString();

// To check if the whipped cream and chocolate checkbox have been selected or not
       CheckBox WhippedCream = (CheckBox) findViewById(R.id.cream_checkbox);
       boolean hasWhippedCream =  WhippedCream.isChecked();

       CheckBox chocolate = (CheckBox) findViewById(R.id.choc_checkbox);
       boolean hasChocolate = chocolate.isChecked();

       int price = calculatePrice(hasWhippedCream,hasChocolate);

       String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Cafe order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
       displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    public void increment(View view) {
        quantity = quantity+1;
        display(quantity);
        displayPrice(quantity*20);
    }

    public void decrement(View view) {
        if (quantity ==0) {
            quantity = quantity+0;
        } else {
            quantity = quantity - 1;
            display(quantity);
            displayPrice(quantity * 20);
        }
    }

// Calculate price if the customer chooses some toppings
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int base_price = 20;
        if (hasWhippedCream==true) {
            base_price += 5;
        }
        if(hasChocolate==true) {
            base_price+= 10;
        }
        return base_price;
    }


    private String createOrderSummary(int price_of_Coffee,boolean hasWhippedCream, boolean hasChocolate, String name) {
        return "Name : " + name + "\nAdd whipped cream? "+hasWhippedCream +"\nAdd chocolate? "+hasChocolate+ "\nQuantity : "+ quantity+ "\nTotal: Rs "+ (quantity*price_of_Coffee)+"\nThank you!";
    }
}