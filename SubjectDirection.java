import org.apache.commons.math3.geometry.euclidean.threed.Rotation;

public class SubjectDirection {

    @Nullable
    private Rotation calibrationDirection;
    @Nullable
    private Rotation latestDirection;

    /**
     * Updates the direction using the latest sensor values obtained
     * from Sensor.TYPE_ROTATION_VECTOR
     *
     * @param sensorValues values obtained from the sensor
     */
    public void onSensorValues(@NotNull float[] sensorValues) {
        Rotation rotation = new Rotation(
                (double) sensorValues[3], // quaternion scalar
                (double) sensorValues[0], // quaternion x
                (double) sensorValues[1], // quaternion y
                (double) sensorValues[2], // quaternion z
                false); // no need to normalise

        if (calibrationDirection == null) {
            // Save the first sensor value obtained as the calibration value
            calibrationDirection = rotation;
        } else {
            // Apply the reverse of the calibration direction to the newly
            //  obtained direction to obtain the direction the user is facing
            //  relative to his/her original direction
            latestDirection = calibrationDirection.applyInverseTo(rotation);
        }
    }

    /**
     * @return The latest known direction the user is facing relative to his/her
     * original direction (if one is available)
     */
    @Nullable
    public Rotation getLatestDirection() {
        return latestDirection;
    }

}
