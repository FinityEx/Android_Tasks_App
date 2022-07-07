package com.example.todo;

import static android.widget.LinearLayout.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todo.databinding.FragmentFirstBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {



        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();



    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        layTasks();

        binding.addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.buttonDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                removeTask();
                binding.buttonDone.hide();
                binding.addTask.show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void layTasks(){
        LinearLayout ll = getView().findViewById(R.id.linearLayout2);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        Collection<String> values = (Collection<String>) sharedPref.getAll().values();

        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.YELLOW);
        border.setStroke(1, 0xFF000000); //black border with full opacity

        int i = 0;
        for(String str : values){
            CheckBox box = new CheckBox(getContext());
            box.setText(str);
            box.setElevation((float)5.5);
            box.setBackground(border);
            box.setTag("task");
            box.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View w) {
                   int viewCount = ll.getChildCount();
                   for (int i = 0; i < viewCount; i++) {
                       Log.d("i: ", String.valueOf(i));
                       View view = ll.getChildAt(i);
                       if (view instanceof CheckBox && ((CheckBox) view).isChecked()) {
                           binding.addTask.hide();
                           binding.buttonDone.show();
                           break;
                       }
                       else {
                           binding.addTask.show();
                           binding.buttonDone.hide();
                       }
                   }
               }
            });
            ll.addView(box);

        }

    }

    private void removeTask(){

        LinearLayout l = getView().findViewById(R.id.linearLayout2);

        int viewCount = l.getChildCount();
        for(int i = 0; i < viewCount; i++){
            View view = l.getChildAt(i);
            //View view = getView().findViewWithTag("task");
                if (view instanceof CheckBox && ((CheckBox) view).isChecked()) {
                    //view.setVisibility(View.GONE);
                    ((LinearLayout) view.getParent()).removeView(view);

                    Log.d("View usuniety", "");
                SharedPreferences sharedPref = getActivity().getSharedPreferences("tasks", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit().remove(((CheckBox) view).getText().toString());
                editor.commit();

            }
        }
        Toast.makeText(getActivity(), "textView usuniety.",
                Toast.LENGTH_LONG).show();



    }

}