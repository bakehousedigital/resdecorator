package digital.bakehouse.resdecorator.sample;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.ResourceDecoratorAppCompatDelegate;

import digital.bakehouse.resdecorator.ResourceContextWrapper;
import digital.bakehouse.resdecorator.ResourceDecorator;

public class MainActivity extends AppCompatActivity {

    private ResourceDecoratorAppCompatDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.dynamic_text)).setText(R.string.dynamic_text);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ResourceContextWrapper.wrap(newBase, getResourceDecorator()));
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        if (delegate == null)
            delegate = new ResourceDecoratorAppCompatDelegate(super.getDelegate(), this);
        return delegate;
    }

    private ResourceDecorator getResourceDecorator() {
        //This can be anything actually. For this sample let's just
        //imagine that we have to make every text retrieved from strings.xml
        // uppercase with underscores instead of spaces
        return new ResourceDecorator() {
            @Override
            public String getString(Resources resources, int id, Object... params) {
                return resources.getString(id).toUpperCase().replaceAll(" ", "_");
            }

            @Override
            public CharSequence getText(Resources resources, int id, Object... params) {
                return getString(resources, id, params);
            }
        };
    }
}
