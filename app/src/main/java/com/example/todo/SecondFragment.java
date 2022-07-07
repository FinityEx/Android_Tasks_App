package com.example.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todo.databinding.FragmentSecondBinding;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.Collection;

public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;
    private String taskDesc;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // layTasks();



       binding.finalAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDesc = binding.taskNameInput.getText().toString();
                saveTask(taskDesc);
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void saveTask(String description){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit().putString(description, description);
        editor.apply();
    }


    private void layTasks(){
        LinearLayout ll = getView().findViewById(R.id.linearLayout);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        Collection<String> values = (Collection<String>) sharedPref.getAll().values();

        int index = 0;
        for(String str : values){
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(str);
            textView.setTextColor(0xFFFF0000);
            textView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View w){
                    removeTask(ll, w, str);
                }
            });
            ll.addView(textView, index++);

        }
    }



    private void removeTask(LinearLayout l, View w, String s){
        l.removeView(w);
        Toast.makeText(getActivity(), "textView usuniety.",
                Toast.LENGTH_LONG).show();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit().remove(s);
        editor.commit();

    }

}