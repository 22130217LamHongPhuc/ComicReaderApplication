package admin.example.apptruyentranh.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class Regular extends AppCompatTextView {
    private  static Typeface typeface;

    public Regular(@NonNull Context context) {
        super(context);
        setTypefaceRegular();
    }

    public Regular(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTypefaceRegular();

    }

    public Regular(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypefaceRegular();

    }


    public static  Typeface getTypefaceRegular(Context context){
        if(typeface==null){
            typeface=Typeface.createFromAsset(context.getAssets(),"fonts/happy-times-at-the-ikob.otf");
        }
        return typeface;

    }

    public void setTypefaceRegular(){
        setTypeface(getTypefaceRegular(getContext()));
    }
}
