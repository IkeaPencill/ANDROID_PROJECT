package iut.s4.project;

import android.os.Parcel;
import android.os.Parcelable;

public class Themes implements Parcelable {
    String label;
    String url;

    public Themes(String label,String url){
        this.label=label;
        this.url=url;
    }

    protected Themes(Parcel in) {
        label = in.readString();
        url = in.readString();
    }

    public static final Creator<Themes> CREATOR = new Creator<Themes>() {
        @Override
        public Themes createFromParcel(Parcel in) {
            return new Themes(in);
        }

        @Override
        public Themes[] newArray(int size) {
            return new Themes[size];
        }
    };

    public String getLabel() {return label;}

    public String getUrl() {return url;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(label);
        parcel.writeString(url);
    }
}
