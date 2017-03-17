package model;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xlagunas on 17/03/17.
 */

@AutoValue
public abstract class Task {
    @SerializedName("id")
    public abstract int id();
    @SerializedName("userId")
    public abstract int userId();
    @SerializedName("title")
    public abstract String title();
    @SerializedName("completed")
    public abstract boolean completed();

    public static Builder builder() {
        return new AutoValue_Task.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(int id);
        public abstract Builder setUserId(int userId);
        public abstract Builder setTitle(String title);
        public abstract Builder setCompleted(boolean completed);
        public abstract Task build();
    }

}
