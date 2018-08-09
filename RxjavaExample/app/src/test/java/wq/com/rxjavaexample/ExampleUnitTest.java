package wq.com.rxjavaexample;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_range(){

        // 第二次开始累加(n+m) ，最后一次n+m-1
        Observable.range(10,5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(integer.toString());
            }
        });
    }


    //  只发送一个数据或者一个错误事件。 类似http请求 GET
    @Test
    public void test_single() {

        Single.create(new Single.OnSubscribe<Integer>() {
            @Override
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                if (!singleSubscriber.isUnsubscribed()) {
                    // 不管循环多少次，最后订阅者只会调用一次
                    for (int i = 0; i < 5; i++) {
                        singleSubscriber.onSuccess(i);
                    }
                }
            }
        }).subscribe(new SingleSubscriber<Integer>() {
            @Override
            public void onSuccess(Integer value) {
                Log.i("onsuccess: " + value);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }








    @Test
    public void test_createObserver() {
        createObserver().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("onCompleted: ");
            }
            @Override
            public void onError(Throwable e) {
                Log.i("onError: " + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("onNext: " + integer);
            }
        });
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {


            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 10; i++) {
                        int temp = new Random().nextInt(20);
                        if (temp > 19) {
                            subscriber.onError(new Throwable("value > 15"));
                            break;
                        } else {
                            subscriber.onNext(temp);
                        }

                        if (temp == 4) {
                            subscriber.onCompleted();
                        }
                    }
                }
            }
        });
    }









    /*

      deffer: 每次订阅都会得到一个全新的 Observable 对象
      just: 不管订阅多少次，都只有唯一一个observable对象，如果是数组，会将结果一次性发送出去
     */

    @Test
    public void test_justOrDeffer(){

        Observable<Long> justObservable =    getJust();
        Observable<Long> defferObservable = getDeffer();
        for (int i = 0; i < 4; i++) {
            justObservable.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    Log.i("just:"+aLong);
                }
            });
        }

        for (int i = 0; i < 4; i++) {

            defferObservable.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    Log.i("deffer:"+aLong);
                }
            });
        }

    }

    private Observable<Long> getJust(){

        return Observable.just(System.currentTimeMillis() + new Random().nextInt(10));
    }

    // deffer (英译: 不同)
    private Observable<Long> getDeffer () {
        return Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return getJust();
            }
        });
    }





    @Test
    public  void test_from(){

        Integer[] arrays =  {1,2,3,4,5};
        List<Integer> lists =  new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            lists.add(i);
        }

        fromArray(arrays).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i("from-array: " +integer);
            }
        });

        fromIterable(lists).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i("from-list: "+integer);
            }
        });

    }

    private Observable<Integer> fromArray(Integer[] arrs){
        return Observable.from(arrs);
    }

    private Observable<Integer> fromIterable(Iterable iterable){
        return Observable.from(iterable);
    }




    @Test
    public void test_interval() throws InterruptedException {


        interval().subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.i("interval: "+aLong);
            }
        });
        Thread.sleep(5000);
    }
    private  Observable<Long> interval(){
        TestScheduler scheduler = new TestScheduler();
        return Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread() );
    }




    /*
    timer 类似js 的setTimeout 表示在指定时间后运行1一次
     */


    @Test
    public void test_repeatOrTimer() throws InterruptedException {
        repeatObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i("repeat: "+integer);
            }
        });

        timerObservable().subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.i("timer: "+aLong);
            }
        });

        Thread.sleep(5000);
    }


    private Observable<Integer> repeatObservable(){
        return Observable.just(1,2,3).repeat(3);
    }

    private Observable<Long> timerObservable(){
        return Observable.timer(1,TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread());
    }




}