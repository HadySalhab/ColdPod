package com.android.myapplication.coldpod.persistence;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.android.myapplication.coldpod.network.data.Enclosure;
import com.android.myapplication.coldpod.network.data.ItemImage;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.Objects;
import java.util.UUID;

@Root(name = "item", strict = false)
public class Item implements Parcelable {

    private String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

    @Path("title")
    @Text(required = false)
    private String mTitle;

    @Path("description")
    @Text(required = false)
    private String mDescription;

    @Element(name = "summary", required = false)
    private String mITunesSummary;

    @Element(name = "pubDate", required = false)
    private String mPubDate;

    @Element(name = "duration", required = false)
    private String mITunesDuration;

    @Element(name = "enclosure", required = false)
    private Enclosure mEnclosure;

    @Element(name = "image", required = false)
    private ItemImage mItemImage;

    private boolean isFavorite;


    public Item() {
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(mTitle, item.mTitle) &&
                Objects.equals(mDescription, item.mDescription) &&
                Objects.equals(mITunesSummary, item.mITunesSummary) &&
                Objects.equals(mPubDate, item.mPubDate) &&
                Objects.equals(mITunesDuration, item.mITunesDuration) &&
                Objects.equals(mEnclosure, item.mEnclosure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mTitle, mDescription, mITunesSummary, mPubDate, mITunesDuration, mEnclosure);
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getITunesSummary() {
        return mITunesSummary;
    }

    public void setITunesSummary(String iTunesSummary) {
        mITunesSummary = iTunesSummary;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }

    public String getITunesDuration() {
        return mITunesDuration;
    }

    public void setITunesDuration(String iTunesDuration) {
        mITunesDuration = iTunesDuration;
    }

    public Enclosure getEnclosure() {
        return mEnclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        mEnclosure = enclosure;
    }

    public ItemImage getItemImage() {
        return mItemImage;
    }
    public void setItemImage(ItemImage itemImage) {
        mItemImage = itemImage;
    }
    protected Item(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mITunesSummary = in.readString();
        mPubDate = in.readString();
        mITunesDuration = in.readString();
        mEnclosure = (Enclosure) in.readValue(Enclosure.class.getClassLoader());
        mItemImage = (ItemImage) in.readValue(ItemImage.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mITunesSummary);
        dest.writeString(mPubDate);
        dest.writeString(mITunesDuration);
        dest.writeValue(mEnclosure);
        dest.writeValue(mItemImage);
    }
}
