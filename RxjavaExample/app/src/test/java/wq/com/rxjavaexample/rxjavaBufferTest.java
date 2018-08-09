package wq.com.rxjavaexample;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by dou_d on 2018/8/8.
 */

public class rxjavaBufferTest {


    // 一次订阅两个，知道数组被消耗完毕
    @Test
    public void test1() {

        Observable.range(1, 5).buffer(2).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                Log.i(integers.toString());
            }
        });
    }

    // 一次全部订阅
    @Test
    public void test2() {
        Observable.range(1, 5).buffer(5).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                Log.i(integers.toString());
            }
        });
    }

    // 最多拿出count个buffer数据，并每次跳过数组前一个数据
    @Test
    public void test3() {
        Observable.range(1, 5).buffer(5, 1).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                Log.i(integers.toString());
            }
        });
        Log.i("--------------------");
        Observable.range(1, 10).buffer(4, 1).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                Log.i(integers.toString());
            }
        });
    }
















    @Test
    public void test_flatMap(){

        mapObserver().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(s);
            }
        });

        flatMapObserver().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(s);
            }
        });

        flatMapIterableObserver().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(s);
            }
        });
    }



    private Observable<String> mapObserver() {

        return Observable.just(1, 2, 3)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "map:"+integer;
                    }
                });
    }




    private Observable<String> flatMapObserver() {

        return Observable.just(1, 2, 3)
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return Observable.just("flat map " + integer);
                    }
                });
    }

    private Observable<String> flatMapIterableObserver() {
        return Observable.just(1, 2, 3)
                .flatMapIterable(new Func1<Integer, Iterable<String>>() {

                    @Override
                    public Iterable<String> call(Integer integer) {
                        List<String> strs = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            strs.add("flatMapIterable: " + i);
                        }
                        return strs;
                    }
                });
    }

}
