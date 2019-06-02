package com.linger.taobaoad;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener {
    GestureDetector detector;
    int distance=50;
    private ViewFlipper flipper;
    private ImageView imageView;
    Message message;
    final int FLAG_MSG = 0x001;
    private int[] images = {R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8};
    Animation[] animations = new Animation[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        flipper = findViewById(R.id.viewFlipper);
        detector=new GestureDetector(this,this);
        animations[0] = AnimationUtils.loadAnimation(this, R.anim.silde_in_left);
        animations[1] = AnimationUtils.loadAnimation(this, R.anim.silde_in_right);
        animations[2] = AnimationUtils.loadAnimation(this, R.anim.silde_out_left);
        animations[3] = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        flipper.setInAnimation(animations[1]);
        flipper.setOutAnimation(animations[2]);
        message = Message.obtain();
        message.what = FLAG_MSG;
        handler.sendMessage(message);

        for (int i = 0; i < images.length; i++) {
            imageView = new ImageView(this);
            imageView.setImageResource(images[i]);
            flipper.addView(imageView);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_MSG) {
                flipper.showPrevious();
                handler.obtainMessage(FLAG_MSG);
                handler.sendEmptyMessageDelayed(FLAG_MSG, 3000);
            }
        }
    };

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX()-e2.getX()>distance){
            flipper.setOutAnimation(animations[2]);
            flipper.setInAnimation(animations[1]);
            flipper.showPrevious();
            return true;
        }else if(e2.getX()-e1.getX()>distance){
            flipper.setInAnimation(animations[0]);
            flipper.setOutAnimation(animations[3]);
            flipper.showNext();
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }
}
