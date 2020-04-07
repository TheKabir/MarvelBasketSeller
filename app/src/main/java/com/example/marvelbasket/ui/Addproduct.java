package com.example.marvelbasket.ui;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.example.marvelbasket.Afterlogin;
import com.example.marvelbasket.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Addproduct extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner productTypeSpinner, productSpinner, buyerTypeSpinner;
    private EditText productDescription, productBrandName, productName, productPrice, productColor, productWeight, productSize,productStock,
            productLength,productWidth,productHeight,productSpecs;
    private LinearLayout productDimensions, productSpecification, productData;
    private Button btnRequestProduct;
    private AddproductViewModel mViewModel;
    private EditText[] attributes;
    List<String> imagePathList;
    private TextView textView;

    public static Addproduct newInstance() {
        return new Addproduct();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,

                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.addproduct_fragment, container, false);

        //Retrieving And Redefining Attributes From Xml:
        textView = v.findViewById(R.id.textv);
        productDescription = (EditText) v.findViewById(R.id.desc);
        productDimensions = (LinearLayout) v.findViewById(R.id.dimensions);
        productSpecification = v.findViewById(R.id.llspecs);
        productBrandName = v.findViewById(R.id.brandname);
        productName = v.findViewById(R.id.pname);
        productPrice = v.findViewById(R.id.price);
        productData = v.findViewById(R.id.pd);
        productSize = v.findViewById(R.id.size);
        productColor = v.findViewById(R.id.pcolor);
        productWeight = v.findViewById(R.id.weight);
        productStock = v.findViewById(R.id.stock);
        productLength= v.findViewById(R.id.l);
        productWidth = v.findViewById(R.id.b);
        productHeight = v.findViewById(R.id.h);
        productSpecs = v.findViewById(R.id.specs);

        //for resetting making an array
         attributes = new EditText[]{productBrandName, productName,productDescription,  productPrice,productStock, productColor, productWeight,
                 productSize, productLength,productWidth,productHeight,productSpecs};


        //Button Function To Select Image From Gallery
        Button ib = v.findViewById(R.id.selectimage);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 5); //set desired image limit here
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });


        //Defining Spinners
        productTypeSpinner = v.findViewById(R.id.productType);
        productSpinner = v.findViewById(R.id.product);
        buyerTypeSpinner = v.findViewById(R.id.buyerType);

        //Statically Defining Content For 'ProductTypeSpinner' And 'BuyerTypeSpinner'
        final String[] productTypeSpinnerValues = {"Select Category For Products", "Electronics", "Fashion", "General", "Book"};
        String[] buyerTypeSpinnerValues = {"Men", "Women", "Baby/Children"};

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, buyerTypeSpinnerValues);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, productTypeSpinnerValues);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        productTypeSpinner.setAdapter(adapter1);
        buyerTypeSpinner.setAdapter(adapter3);
        productTypeSpinner.setOnItemSelectedListener(this);

        //Dynamic Changes By productSpinner And Visibility:
        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = productSpinner.getSelectedItem().toString();
                if (text.equals("Select Product") | text.equals("First Select Any Category")) {
                    productData.setVisibility(View.GONE);
                } else {
                    productData.setVisibility(View.VISIBLE);
                    if (!productTypeSpinner.getSelectedItem().toString().equals("Education")) {
                        productName.setHint(text + "'s Name");
                        productBrandName.setHint(text + "'s Brand");
                        productColor.setHint(text + "'s Color");
                        productSize.setHint(text + "'s Size (Must Be Consistent)");
                        productWeight.setHint(text + "'s Weight (In Grams)");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            String type = productTypeSpinner.toString();
        });


        // Button Function To Send Product Data :

        btnRequestProduct = v.findViewById(R.id.reqProduct);
        btnRequestProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int check =0,abc =0;
                //Field Validation
                //Empty Check
            for(EditText i : attributes){
                if(i.getVisibility()==View.VISIBLE){
                    if(i.getText().toString().equals("")) {
                        i.setError("Required !");
                        i.requestFocus();
                        check = 1;
                        break;
                    }}}

            //Extra constraints
            if(!productStock.getText().toString().equals("")  & productStock.getVisibility()==View.VISIBLE ){
                if(Integer.parseInt(productStock.getText().toString())<10){
                productStock.setError("Must be atleast 10");
                check=1;}
            }
            if(!productPrice.getText().toString().equals("")  &productPrice.getVisibility()==View.VISIBLE){
                if(Integer.parseInt(productPrice.getText().toString())<50){
                productPrice.setError("Must be atleast 50");}
            }
            if( !productWeight.getText().toString().equals("")  &productWeight.getVisibility()==View.VISIBLE ){
                if(Integer.parseInt(productWeight.getText().toString())==0){
                    productWeight.setError("Invalid data !");
                    check = 1;
            }

            }
            if( !productLength.getText().toString().equals("")  &productLength.getVisibility()==View.VISIBLE){
                if(Integer.parseInt(productLength.getText().toString())==0) {
                    productLength.setError("Invalid data !");
                    check = 1;}
                }
            if(!productWidth.getText().toString().equals("")  &productWidth.getVisibility()==View.VISIBLE ){
                if(Integer.parseInt(productWidth.getText().toString())==0) {
                    productWidth.setError("Invalid data !");
                    check = 1;}
                }
            if(!productHeight.getText().toString().equals("")  &productHeight.getVisibility()==View.VISIBLE ){
                if(Integer.parseInt(productHeight.getText().toString())==0) {
                    productHeight.setError("Invalid data !");
                    check = 1;}
                }
            if(!productSize.getText().toString().equals("") & productSize.getVisibility()==View.VISIBLE) {
                abc = checkSizeFashion(productSize.getText().toString(), productSize.getHint().toString());
            }
                  //final updating
            if(check==0 & abc==0){

                Toast.makeText(getContext(),"Request Succesfully Submitted",Toast.LENGTH_LONG).show();;
            }

        }});

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String sp1 = String.valueOf(productTypeSpinner.getSelectedItem());
        String[] data = new String[0];

        //ELECTRONICS
        if (sp1.contentEquals("Electronics")) {
            data = new String[]{"Select Product", "Mobile", "Tablet", "Laptop", "Desktop", "Camera", "Speaker/Headset", "Power Bank", "Data Storage Device",
                    "Mobile Accessory", "PC Accessory"};
            productDescription.setHint("Description (General Details, Where to use it, Guarantee/Waranty, Its Advantages/Limitations, How to use ,etc)");
            productSpecs.setVisibility(View.VISIBLE);
           productLength.setVisibility(View.VISIBLE);
           productHeight.setVisibility(View.VISIBLE);
           productWidth.setVisibility(View.VISIBLE);
            productColor.setVisibility(View.VISIBLE);
            productWeight.setVisibility(View.VISIBLE);
            productSize.setVisibility(View.GONE);
            buyerTypeSpinner.setVisibility(View.GONE);
            productSpecification.setVisibility(View.VISIBLE);
            productDimensions.setVisibility(View.VISIBLE);
        }

        //NOT SELECTED ANYTHING
        else if (sp1.contentEquals("Select Category For Products")) {
            data = new String[]{"First Select Any Category"};
        }

        //FASHION
        else if (sp1.contentEquals("Fashion")) {
            data = new String[]{"Select Product", "Clothing", "Footwear", "Watch/Band", "Bag/Wallet", "Sunglasses", "Sportswear"};
            productDescription.setHint("Description (Material/Clothtype used, Quality Standards, Guarantee/Waranty, Washing Instructions, etc)");
            productSpecs.setVisibility(View.GONE);
            productLength.setVisibility(View.GONE);
            productHeight.setVisibility(View.GONE);
            productWidth.setVisibility(View.GONE);
            productColor.setVisibility(View.VISIBLE);
            productWeight.setVisibility(View.GONE);
            buyerTypeSpinner.setVisibility(View.VISIBLE);
            productSize.setVisibility(View.VISIBLE);
            productSpecification.setVisibility(View.GONE);
            productDimensions.setVisibility(View.GONE);
        }

        //General
        else if (sp1.contentEquals("General")) {
            data = new String[]{"Select Product", "Beauty/Grooming Product", "Health/Personal-Care Product", "Men's Grooming Device", "Women's Styling Device", "Fitness Tool",
                    "Travel Accessory", "Gift Card", "Kitchen / Home Appliance", "Home Furnishing Item", "Home Storage/Organisation Tool", "Garden/Outdoor Utility",
                    "Pet Supply", "Art/Craft Supply", "Gaming Console", "Gaming Accessory", "Digital Video Game", "Outdoor Sport", "Indoor Sport", "Toy", "Baby's Game/Toy"};
            productDescription.setHint("Description (General Instructions, Material/Quality Standards, Guarantee/Waranty, Charging/Usage Details If Any ,etc)");
            productSize.setVisibility(View.GONE);
            productColor.setVisibility(View.GONE);
            buyerTypeSpinner.setVisibility(View.GONE);
            productWeight.setVisibility(View.VISIBLE);
            productSpecs.setVisibility(View.GONE);
            productLength.setVisibility(View.GONE);
            productHeight.setVisibility(View.GONE);
            productWidth.setVisibility(View.GONE);
            productSpecification.setVisibility(View.GONE);
            productDimensions.setVisibility(View.GONE);
        }

        //Book
        else if (sp1.contentEquals("Book")) {
            data = new String[]{"Select Product", "General Book/Magzine", "Fiction Book", "School Textbook", "Language Book", "Exam Central",
                    "Children's Book", "E-Book", "Audio-Book"};
            productDescription.setHint("Description (Topic/Agenda ,Number Of Pages/Runtime Of Audiobook,etc)");
            productSpecification.setVisibility(View.GONE);
            productDimensions.setVisibility(View.GONE);
            productSpecs.setVisibility(View.GONE);
            productLength.setVisibility(View.GONE);
            productHeight.setVisibility(View.GONE);
            productWidth.setVisibility(View.GONE);
            productSize.setVisibility(View.GONE);
            buyerTypeSpinner.setVisibility(View.GONE);
            productColor.setVisibility(View.GONE);
            productWeight.setVisibility(View.GONE);
            productBrandName.setHint("Author/Publisher's Name");
            productName.setHint("Book/Ebook's Title");

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dataAdapter.notifyDataSetChanged();
        productSpinner.setAdapter(dataAdapter);

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddproductViewModel.class);

        // TODO: Use the ViewModel
    }
public  int  checkSizeFashion(String size,String hint){
        if(hint.contains("Footwear")){
            int shoeNo = Integer.parseInt(size);
            if(shoeNo<1 | shoeNo>10){
                productSize.setError("Must be in range 1-10");
                return 1;
            }
            else {
                return 0;
            }
        }
        else {
            String[] sizes ={"S","M","L","XL","XXL","XXXL","s","m","l","xl","xxl","xxxl"};
            int code =0;
            for(String i:sizes)
            {
                if(i.equals(size))
                {
                 code =1;
                 break;
                }
            }
            if(code==0){
                productSize.setError("Must be any of S/M/L/XL/XXL/XXL");
                return 1;
            }
            else return 0;
        }
}

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data !=null) {
            ArrayList<Image> images =data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            imagePathList.clear();
            StringBuffer stringBuffer = new StringBuffer();

            //loop to retrieve the paths of each image and display to TextView
            for (int i = 0; i < images.size(); i++) {
                stringBuffer.append(images.get(i).path + "\n");
            }
            textView.setText(stringBuffer.toString());
        }
    }
*/
}
