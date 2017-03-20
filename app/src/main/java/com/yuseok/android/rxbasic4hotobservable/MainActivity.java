package com.yuseok.android.rxbasic4hotobservable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observables.ConnectableObservable;

public class MainActivity extends AppCompatActivity {

    List<String> data1 = new ArrayList<>();
    List<String> data2 = new ArrayList<>();

    ListView list1, list2;

    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list1 = (ListView) findViewById(R.id.listView1);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
        list1.setAdapter(adapter1);

        list2 = (ListView) findViewById(R.id.listview2);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, data2);
        list2.setAdapter(adapter2);

        // 발행자는 1초당 한번씩 값을 발행하는 Observable
        ConnectableObservable<Long> hotObservable = Observable.interval(1, TimeUnit.SECONDS).publish();
        hotObservable.connect();

        // 첫번째 옵저버를 등록한다.
        hotObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            data1.add("ITEM1 : "+result);
                            adapter1.notifyDataSetChanged();
                        }
                );


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3초후에 두번째 옵저버를 등록한다.
        hotObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            data2.add("ITEM2 : "+result);
                            adapter2.notifyDataSetChanged();
                        }
                );
    }
}