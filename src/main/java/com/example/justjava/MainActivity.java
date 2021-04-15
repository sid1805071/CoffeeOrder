package com.example.justjava;

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
    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckedBox = (CheckBox) findViewById(R.id.Whipped_cream_Checkbox);
        boolean hasWhippedCream = whippedCreamCheckedBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.Chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText assignName = (EditText) findViewById(R.id.Enter_Name);
        String value = assignName.getText().toString();

        int price = calculatePrice( hasWhippedCream ,hasChocolate);
        String priceMessage = createOrderSummary(price , hasWhippedCream , hasChocolate , value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just jave order for :"+ value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(int price , boolean hasWhippedCream ,boolean hasChocolate , String name){
        String priceMessage = "Name : "+ name;
        priceMessage = priceMessage + "\nQuantity : " + quantity;
        priceMessage +="\n Has Whipped Cream : " + hasWhippedCream;
        priceMessage +="\n Has Chocolate :" + hasChocolate;
        priceMessage = priceMessage + "\nTotal price : $" + price;
        priceMessage = priceMessage + "\nThank You!";
        return priceMessage;
    }
    /**
     * Calculates the price of the order.
     */
    private int calculatePrice( boolean hasWippedCream , boolean hasChocolate ) {
        int price =0;
        if(hasWippedCream && hasChocolate){
            price = quantity * 8;
        }else if(hasChocolate){
            price = quantity * 7;
        }else if(hasWippedCream){
            price = quantity * 6;
        }else{
             price = quantity * 5;
        }
        return price;
    }
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this,"You cannot less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */

}