package sample.kingja.bannersir;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Description:TODO
 * Create Time:2020/4/5 13:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SquareImageView extends ImageView {
    private String TAG = getClass().getSimpleName();
    private float ratio = 1.0f;

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth() * ratio));
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }
}
