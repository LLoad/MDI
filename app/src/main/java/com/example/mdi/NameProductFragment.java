package com.example.mdi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NameProductFragment} interface
 * to handle interaction events.
 * Use the {@link NameProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NameProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ProgressDialog asyncDialog;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText searchDrugName;
    Button searchBtn;

    public NameProductFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NameProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NameProductFragment newInstance(String param1, String param2) {
        NameProductFragment fragment = new NameProductFragment();
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
        View v = inflater.inflate(R.layout.fragment_name_product, container, false);

        searchBtn = (Button) v.findViewById(R.id.searchNameBtn);
        searchDrugName = (EditText) v.findViewById(R.id.input_drug_name);

        searchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = searchDrugName.getText().toString();
                if (keyword != null && !keyword.equals("") && !keyword.equals(" ")) {


                    Intent intent = new Intent(getActivity(), ResultNameActivity.class);

                    intent.putExtra("drugName", keyword);

                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "검색어를 입력해주세요,", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}
