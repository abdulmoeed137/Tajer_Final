package tawseel.com.tajertawseel;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Junaid-Invision on 7/9/2016.
 */
public class CustomPrimaryTextNormal extends TextView {





    public CustomPrimaryTextNormal(Context context) {
    super(context);
    changeTypeFace(context);
}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomPrimaryTextNormal(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        changeTypeFace(context);

    }

    public CustomPrimaryTextNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
        changeTypeFace(context);
    }

    public CustomPrimaryTextNormal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        changeTypeFace(context);
    }

    public void changeTypeFace (Context c)
    {
        Typeface custom_font = Typeface.createFromAsset(c.getAssets(), "FrutigerLTArabic-75Black.ttf");
        setTypeface(custom_font);
    }
}