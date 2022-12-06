package com.example.kiemtracuoiky.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kiemtracuoiky.R;
import com.example.kiemtracuoiky.models.CN;
import com.example.kiemtracuoiky.models.TP;
import com.example.kiemtracuoiky.models.TPAdapter;
import com.example.kiemtracuoiky.models.TPDataQuery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThanhPhoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThanhPhoFragment extends Fragment implements TPAdapter.UserCallback {
    RecyclerView rvListTP;
    ArrayList<TP> lstUser;
    TPAdapter tpAdapter;
    FloatingActionButton fbAdd;
    Context thisContext;
    Button btnOK,btnCancel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThanhPhoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThanhPhoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThanhPhoFragment newInstance(String param1, String param2) {
        ThanhPhoFragment fragment = new ThanhPhoFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext= context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_thanh_pho, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvListTP = view.findViewById(R.id.rvListTP);
        fbAdd = view.findViewById(R.id.fbAddTP);
        fbAdd.setOnClickListener(view1 -> addUserDialog());

        lstUser = TPDataQuery.getAll(getContext());
        tpAdapter = new TPAdapter(lstUser);
        tpAdapter.setCallback(this);

        rvListTP.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rvListTP.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rvListTP.setHasFixedSize(true);
        rvListTP.setAdapter(tpAdapter);
        tpAdapter.notifyDataSetChanged();

    }

    void addUserDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Thêm mới");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_tp, null);
        alertDialog.setView(dialogView);

        EditText edName = (EditText) dialogView.findViewById(R.id.edName);

        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            String name = edName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
            } else {
                TP tp = new TP(0, name);
                long id = TPDataQuery.insert(getContext(), tp);
                if (id > 0) {
                    Toast.makeText(getContext(), "Thêm thành phố thành công", Toast.LENGTH_LONG).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }

    void updateUserDialog(TP tp) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Cập nhật");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_tp, null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edName);

        edName.setText(tp.getName());

        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            tp.setName(edName.getText().toString());

            if (tp.getName().isEmpty()) {
                Toast.makeText(getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
            } else {
                int id = TPDataQuery.update(thisContext,tp);
                if (id > 0) {
                    Toast.makeText(getContext(), "Cập nhật thành phố thành công", Toast.LENGTH_LONG).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }

    void resetData() {
        lstUser.clear();
        lstUser.addAll(TPDataQuery.getAll(getContext()));
        tpAdapter.notifyDataSetChanged();
    }

    public void onItemDeleteClicked(TP tp, int posidion) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_confirm_delete);
        btnOK = dialog.findViewById(R.id.btnYes);
        btnCancel = dialog.findViewById(R.id.btnNo);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean rs = TPDataQuery.delete(getContext(), tp.getId());
                if (rs) {
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                    resetData();
                } else {
                    Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public void onItemEditClicked(TP tp, int position) {
    updateUserDialog(tp);
    }

    @Override
    public void onClick(TP tp, int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, new ChiNhanhFragment(tp.getId()));
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}