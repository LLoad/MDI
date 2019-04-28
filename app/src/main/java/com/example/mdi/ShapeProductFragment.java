package com.example.mdi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShapeProductFragment} interface
 * to handle interaction events.
 * Use the {@link ShapeProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShapeProductFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String color = "";
    String type = "";
    String shape = "";
    EditText identificationFrontText, identificationBackText;
    RadioGroup optionGroup;
    RadioButton agreementRadioBtn;
    CheckBox refinementType, hardCapsuleType, softCapsuleType;
    CheckBox circleShape, ellipseShape, rectangleShape, semiCircularShape, rhombusShape, triangleShape,
            quadrangleShape, pentagonShape, hexagonShape, octagonShape,etcShape;
    CheckBox whiteColor, yellowColor, orangeColor, pinkColor, redColor, brownColor, yellowGreenColor, greenColor,
            blueGreenColor, blueColor, sodomyColor, reddishPurpleColor, purpleColor, grayColor, blackColor, transparencyColor;
    Button searchBtn, initBtn;

    public ShapeProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShapeProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShapeProductFragment newInstance(String param1, String param2) {
        ShapeProductFragment fragment = new ShapeProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shape_product, container, false);


        identificationFrontText = (EditText) v.findViewById(R.id.identificationFrontText);
        identificationBackText = (EditText) v.findViewById(R.id.identificationBackText);

        optionGroup = (RadioGroup) v.findViewById(R.id.optionGroup);

        agreementRadioBtn = (RadioButton) v.findViewById(R.id.agreementRadioBtn);

        refinementType = (CheckBox) v.findViewById(R.id.refinementType);
        hardCapsuleType = (CheckBox) v.findViewById(R.id.hardCapsuleType);
        softCapsuleType = (CheckBox) v.findViewById(R.id.softCapsuleType);

        circleShape = (CheckBox) v.findViewById(R.id.circleShape);
        ellipseShape = (CheckBox) v.findViewById(R.id.ellipseShape);
        rectangleShape = (CheckBox) v.findViewById(R.id.rectangleShape);
        semiCircularShape = (CheckBox) v.findViewById(R.id.semiCircularShape);
        rhombusShape = (CheckBox) v.findViewById(R.id.rhombusShape);
        triangleShape = (CheckBox) v.findViewById(R.id.triangleShape);
        quadrangleShape = (CheckBox) v.findViewById(R.id.quadrangleShape);
        pentagonShape = (CheckBox) v.findViewById(R.id.pentagonShape);
        hexagonShape = (CheckBox) v.findViewById(R.id.hexagonShape);
        octagonShape = (CheckBox) v.findViewById(R.id.octagonShape);
        etcShape = (CheckBox) v.findViewById(R.id.etcShape);

        whiteColor = (CheckBox) v.findViewById(R.id.whiteColor);
        yellowColor = (CheckBox) v.findViewById(R.id.yellowColor);
        orangeColor = (CheckBox) v.findViewById(R.id.orangeColor);
        pinkColor = (CheckBox) v.findViewById(R.id.pinkColor);
        redColor = (CheckBox) v.findViewById(R.id.redColor);
        brownColor = (CheckBox) v.findViewById(R.id.brownColor);
        yellowGreenColor = (CheckBox) v.findViewById(R.id.yellowGreenColor);
        greenColor = (CheckBox) v.findViewById(R.id.greenColor);
        blueGreenColor = (CheckBox) v.findViewById(R.id.blueGreenColor);
        blueColor = (CheckBox) v.findViewById(R.id.blueColor);
        sodomyColor = (CheckBox) v.findViewById(R.id.sodomyColor);
        reddishPurpleColor = (CheckBox) v.findViewById(R.id.reddishPurpleColor);
        purpleColor = (CheckBox) v.findViewById(R.id.purpleColor);
        grayColor = (CheckBox) v.findViewById(R.id.grayColor);
        blackColor = (CheckBox) v.findViewById(R.id.blackColor);
        transparencyColor = (CheckBox) v.findViewById(R.id.transparencyColor);

        searchBtn = (Button) v.findViewById(R.id.shapeSearchBtn);
        initBtn = (Button) v.findViewById(R.id.initializationBtn);

        optionGroup.setOnCheckedChangeListener(this);

        refinementType.setOnCheckedChangeListener(this);
        hardCapsuleType.setOnCheckedChangeListener(this);
        softCapsuleType.setOnCheckedChangeListener(this);

        circleShape.setOnCheckedChangeListener(this);
        ellipseShape.setOnCheckedChangeListener(this);
        rectangleShape.setOnCheckedChangeListener(this);
        semiCircularShape.setOnCheckedChangeListener(this);
        rhombusShape.setOnCheckedChangeListener(this);
        triangleShape.setOnCheckedChangeListener(this);
        quadrangleShape.setOnCheckedChangeListener(this);
        pentagonShape.setOnCheckedChangeListener(this);
        hexagonShape.setOnCheckedChangeListener(this);
        octagonShape.setOnCheckedChangeListener(this);
        etcShape.setOnCheckedChangeListener(this);

        whiteColor.setOnCheckedChangeListener(this);
        yellowColor.setOnCheckedChangeListener(this);
        orangeColor.setOnCheckedChangeListener(this);
        pinkColor.setOnCheckedChangeListener(this);
        redColor.setOnCheckedChangeListener(this);
        brownColor.setOnCheckedChangeListener(this);
        yellowGreenColor.setOnCheckedChangeListener(this);
        greenColor.setOnCheckedChangeListener(this);
        blueGreenColor.setOnCheckedChangeListener(this);
        blueColor.setOnCheckedChangeListener(this);
        sodomyColor.setOnCheckedChangeListener(this);
        reddishPurpleColor.setOnCheckedChangeListener(this);
        purpleColor.setOnCheckedChangeListener(this);
        grayColor.setOnCheckedChangeListener(this);
        blackColor.setOnCheckedChangeListener(this);
        transparencyColor.setOnCheckedChangeListener(this);

        searchBtn.setOnClickListener(this);
        initBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(agreementRadioBtn.isChecked()) {
            switch (buttonView.getId()) {
                case R.id.refinementType:
                    if(!refinementType.isChecked()) { type = ""; refinementType.setChecked(false); }
                    else { type = "정제"; refinementType.setChecked(true); }

                    hardCapsuleType.setChecked(false);  softCapsuleType.setChecked(false);
                    break;
                case R.id.hardCapsuleType:
                    if(!hardCapsuleType.isChecked()) { type = ""; hardCapsuleType.setChecked(false); }
                    else { type = "경질캡슐"; hardCapsuleType.setChecked(true); }

                    refinementType.setChecked(false);   softCapsuleType.setChecked(false);
                    break;
                case R.id.softCapsuleType:
                    if(!softCapsuleType.isChecked()) { type = ""; softCapsuleType.setChecked(false); }
                    else { type = "연질캡슐"; softCapsuleType.setChecked(true); }

                    refinementType.setChecked(false);   hardCapsuleType.setChecked(false);
                    break;


                case R.id.circleShape:
                    if(!circleShape.isChecked()) { shape = ""; circleShape.setChecked(false); }
                    else { shape = "원형"; circleShape.setChecked(true); }

                    ellipseShape.setChecked(false);     rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);    rhombusShape.setChecked(false);
                    triangleShape.setChecked(false);    quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);        hexagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.ellipseShape:
                    if(!ellipseShape.isChecked()) { shape = ""; ellipseShape.setChecked(false); }
                    else { shape = "타원"; ellipseShape.setChecked(true); }

                    circleShape.setChecked(false);      rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);    rhombusShape.setChecked(false);
                    triangleShape.setChecked(false);    quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);        hexagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.rectangleShape:
                    if(!rectangleShape.isChecked()) { shape = ""; rectangleShape.setChecked(false); }
                    else { shape = "장방형"; rectangleShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         semiCircularShape.setChecked(false);    rhombusShape.setChecked(false);
                    triangleShape.setChecked(false);    quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);        hexagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.semiCircularShape:
                    if(!semiCircularShape.isChecked()) { shape = ""; semiCircularShape.setChecked(false); }
                    else { shape = "반원형"; semiCircularShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       rhombusShape.setChecked(false);
                    triangleShape.setChecked(false);    quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);        hexagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.rhombusShape:
                    if(!rhombusShape.isChecked()) { shape = ""; rhombusShape.setChecked(false); }
                    else { shape = "마름모형"; rhombusShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                    triangleShape.setChecked(false);    quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);        hexagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.triangleShape:
                    if(!triangleShape.isChecked()) { shape = ""; triangleShape.setChecked(false); }
                    else { shape = "삼각형"; triangleShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                    rhombusShape.setChecked(false);     quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);        hexagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.quadrangleShape:
                    if(!quadrangleShape.isChecked()) { shape = ""; quadrangleShape.setChecked(false); }
                    else { shape = "사각형"; quadrangleShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                    rhombusShape.setChecked(false);     triangleShape.setChecked(false);        pentagonShape.setChecked(false);        hexagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.pentagonShape:
                    if(!pentagonShape.isChecked()) { shape = ""; pentagonShape.setChecked(false); }
                    else { shape = "오각형"; pentagonShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                    rhombusShape.setChecked(false);     triangleShape.setChecked(false);        quadrangleShape.setChecked(false);      hexagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.hexagonShape:
                    if(!hexagonShape.isChecked()) { shape = ""; hexagonShape.setChecked(false); }
                    else { shape = "육각형"; hexagonShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                    rhombusShape.setChecked(false);     triangleShape.setChecked(false);        quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);
                    octagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.octagonShape:
                    if(!octagonShape.isChecked()) { shape = ""; octagonShape.setChecked(false); }
                    else { shape = "팔각형"; octagonShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                    rhombusShape.setChecked(false);     triangleShape.setChecked(false);        quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);
                    hexagonShape.setChecked(false);     etcShape.setChecked(false);
                    break;
                case R.id.etcShape:
                    if(!etcShape.isChecked()) { shape = ""; etcShape.setChecked(false); }
                    else { shape = "기타"; etcShape.setChecked(true); }

                    circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                    rhombusShape.setChecked(false);     triangleShape.setChecked(false);        quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);
                    hexagonShape.setChecked(false);     octagonShape.setChecked(false);
                    break;


                case R.id.whiteColor:
                    if(!whiteColor.isChecked()) { color = ""; whiteColor.setChecked(false); }
                    else { color = "하양"; whiteColor.setChecked(true); }

                    yellowColor.setChecked(false);      orangeColor.setChecked(false);          pinkColor.setChecked(false);            redColor.setChecked(false);
                    brownColor.setChecked(false);       yellowGreenColor.setChecked(false);     greenColor.setChecked(false);           blueGreenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.yellowColor:
                    if(!yellowColor.isChecked()) { color = ""; yellowColor.setChecked(false); }
                    else { color = "노랑"; yellowColor.setChecked(true); }

                    whiteColor.setChecked(false);       orangeColor.setChecked(false);          pinkColor.setChecked(false);            redColor.setChecked(false);
                    brownColor.setChecked(false);       yellowGreenColor.setChecked(false);     greenColor.setChecked(false);           blueGreenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.orangeColor:
                    if(!orangeColor.isChecked()) { color = ""; orangeColor.setChecked(false); }
                    else { color = "주황"; orangeColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          pinkColor.setChecked(false);            redColor.setChecked(false);
                    brownColor.setChecked(false);       yellowGreenColor.setChecked(false);     greenColor.setChecked(false);           blueGreenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.pinkColor:
                    if(!pinkColor.isChecked()) { color = ""; pinkColor.setChecked(false); }
                    else { color = "분홍"; pinkColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          redColor.setChecked(false);
                    brownColor.setChecked(false);       yellowGreenColor.setChecked(false);     greenColor.setChecked(false);           blueGreenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.redColor:
                    if(!redColor.isChecked()) { color = ""; redColor.setChecked(false); }
                    else { color = "빨강"; redColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    brownColor.setChecked(false);       yellowGreenColor.setChecked(false);     greenColor.setChecked(false);           blueGreenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.brownColor:
                    if(!brownColor.isChecked()) { color = ""; brownColor.setChecked(false); }
                    else { color = "남색"; brownColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         yellowGreenColor.setChecked(false);     greenColor.setChecked(false);           blueGreenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.yellowGreenColor:
                    if(!yellowGreenColor.isChecked()) { color = ""; yellowGreenColor.setChecked(false); }
                    else { color = "연두"; yellowGreenColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           greenColor.setChecked(false);           blueGreenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.greenColor:
                    if(!greenColor.isChecked()) { color = ""; greenColor.setChecked(false); }
                    else { color = "초록"; greenColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     blueGreenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.blueGreenColor:
                    if(!blueGreenColor.isChecked()) { color = ""; blueGreenColor.setChecked(false); }
                    else { color = "청록"; blueGreenColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                    blueColor.setChecked(false);        sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.blueColor:
                    if(!blueColor.isChecked()) { color = ""; blueColor.setChecked(false); }
                    else { color = "파랑"; blueColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                    blueGreenColor.setChecked(false);   sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.sodomyColor:
                    if(!sodomyColor.isChecked()) { color = ""; sodomyColor.setChecked(false); }
                    else { color = "남색"; sodomyColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                    blueGreenColor.setChecked(false);   blueColor.setChecked(false);            reddishPurpleColor.setChecked(false);   purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.reddishPurpleColor:
                    if(!reddishPurpleColor.isChecked()) { color = ""; reddishPurpleColor.setChecked(false); }
                    else { color = "자주"; reddishPurpleColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                    blueGreenColor.setChecked(false);   blueColor.setChecked(false);            sodomyColor.setChecked(false);          purpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.purpleColor:
                    if(!purpleColor.isChecked()) { color = ""; purpleColor.setChecked(false); }
                    else { color = "보라"; purpleColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                    blueGreenColor.setChecked(false);   blueColor.setChecked(false);            sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);
                    grayColor.setChecked(false);        blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.grayColor:
                    if(!grayColor.isChecked()) { color = ""; grayColor.setChecked(false); }
                    else { color = "회색"; grayColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                    blueGreenColor.setChecked(false);   blueColor.setChecked(false);            sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);
                    purpleColor.setChecked(false);      blackColor.setChecked(false);           transparencyColor.setChecked(false);
                    break;
                case R.id.blackColor:
                    if(!blackColor.isChecked()) { color = ""; blackColor.setChecked(false); }
                    else { color = "검정"; blackColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                    blueGreenColor.setChecked(false);   blueColor.setChecked(false);            sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);
                    purpleColor.setChecked(false);      grayColor.setChecked(false);            transparencyColor.setChecked(false);
                    break;
                case R.id.transparencyColor:
                    if(!transparencyColor.isChecked()) { color = ""; transparencyColor.setChecked(false); }
                    else { color = "투명"; transparencyColor.setChecked(true); }

                    whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                    redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                    blueGreenColor.setChecked(false);   blueColor.setChecked(false);            sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);
                    purpleColor.setChecked(false);      grayColor.setChecked(false);            blackColor.setChecked(false);
                    break;
            }
        } else {

        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.agreementRadioBtn:
                identificationFrontText.setText("");
                identificationBackText.setText("");

                refinementType.setChecked(false);   hardCapsuleType.setChecked(false);      softCapsuleType.setChecked(false);

                circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                rhombusShape.setChecked(false);     triangleShape.setChecked(false);        quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);
                hexagonShape.setChecked(false);     octagonShape.setChecked(false);         etcShape.setChecked(false);

                whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                blueGreenColor.setChecked(false);   blueColor.setChecked(false);            sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);
                purpleColor.setChecked(false);      grayColor.setChecked(false);            blackColor.setChecked(false);           transparencyColor.setChecked(false);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shapeSearchBtn:
                Intent intent = new Intent(getActivity(), ResultShapeActivity.class);
                intent.putExtra("frontText", identificationBackText.getText());
                intent.putExtra("backText", identificationBackText.getText());
                intent.putExtra("type", type);
                intent.putExtra("shape", shape);
                intent.putExtra("color", color);
                startActivity(intent);
                break;
            case R.id.initializationBtn:
                identificationFrontText.setText("");
                identificationBackText.setText("");

                refinementType.setChecked(false);   hardCapsuleType.setChecked(false);      softCapsuleType.setChecked(false);

                circleShape.setChecked(false);      ellipseShape.setChecked(false);         rectangleShape.setChecked(false);       semiCircularShape.setChecked(false);
                rhombusShape.setChecked(false);     triangleShape.setChecked(false);        quadrangleShape.setChecked(false);      pentagonShape.setChecked(false);
                hexagonShape.setChecked(false);     octagonShape.setChecked(false);         etcShape.setChecked(false);

                whiteColor.setChecked(false);       yellowColor.setChecked(false);          orangeColor.setChecked(false);          pinkColor.setChecked(false);
                redColor.setChecked(false);         brownColor.setChecked(false);           yellowGreenColor.setChecked(false);     greenColor.setChecked(false);
                blueGreenColor.setChecked(false);   blueColor.setChecked(false);            sodomyColor.setChecked(false);          reddishPurpleColor.setChecked(false);
                purpleColor.setChecked(false);      grayColor.setChecked(false);            blackColor.setChecked(false);           transparencyColor.setChecked(false);

                optionGroup.check(R.id.agreementRadioBtn);
                break;
        }
    }
}