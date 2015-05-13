package nucchallenge.utils;

public class BloodPressureCuff {
    public static native int [] getValues();

    static {
        System.loadLibrary("novaomron");
    }

    public BloodPressureCuff() {
    }

    public int[] read() {
        BloodPressureCuff bloodPressureCuff = new BloodPressureCuff();

         return getValues();



    }
}
