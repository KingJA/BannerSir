package sample.kingja.bannersir;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.bannersir.index.BaseIndexBar;
import com.kingja.bannersir.pager.BannerSir;
import com.kingja.bannersir.pager.BaseAutoAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] jdImgRes = {R.mipmap.jd1, R.mipmap.jd2, R.mipmap.jd3, R.mipmap.jd4, R.mipmap.jd5};
    private int[] mkImgRes = {R.mipmap.mk1, R.mipmap.mk2, R.mipmap.mk3, R.mipmap.mk4, R.mipmap.mk5};
    private int[] zfbImgRes = {R.mipmap.mk1, R.mipmap.mk2, R.mipmap.mk3, R.mipmap.mk4, R.mipmap.mk5};
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Pager> jdPagers = getJdPagers();
        List<Pager> mkPagers = getMkPagers();
        BannerSir bannerSir = findViewById(R.id.bannerPager);
        BannerSir bannerSir2 = findViewById(R.id.bannerPager2);

        bannerSir.setAdapter(new BaseAutoAdapter<Pager>(this, jdPagers) {
            @Override
            public View getView(Pager data, int position) {
                SquareImageView squareImageView = new SquareImageView(MainActivity.this);
                squareImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                squareImageView.setImageResource(data.getResId());
                return squareImageView;
            }
        });
        bannerSir.setIndexBar(new BaseIndexBar(this) {
            @Override
            public View getView(Context context) {
                return View.inflate(context, R.layout.layout_index_jd, null);
            }

            @Override
            public void drawIndex(View view, int position, int totalCount) {
                TextView tvPosition = view.findViewById(R.id.tv_position);
                TextView tvTotalCount = view.findViewById(R.id.tv_totalCount);
                tvPosition.setText(String.valueOf(position));
                tvTotalCount.setText(String.valueOf(totalCount));
            }
        });


        bannerSir2.setAdapter(new BaseAutoAdapter<Pager>(this, mkPagers) {
            @Override
            public View getView(Pager data, int position) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(data.getResId());
                return imageView;
            }
        });


    }

    private List<Pager> getMkPagers() {
        List<Pager> pagers = new ArrayList<>();
        for (int i = 0; i < mkImgRes.length; i++) {
            pagers.add(new Pager(mkImgRes[i], "星空" + i));
        }
        return pagers;
    }

    private List<Pager> getJdPagers() {
        List<Pager> pagers = new ArrayList<>();
        for (int i = 0; i < jdImgRes.length; i++) {
            pagers.add(new Pager(jdImgRes[i], "星空" + i));
        }

        return pagers;
    }
}
