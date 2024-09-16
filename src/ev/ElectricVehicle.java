package ev;

import java.text.DecimalFormat;

/**
 * Represents an electric vehicle with various attributes and methods to calculate its range and
 * efficiency.
 */
public class ElectricVehicle {
  private String name;
  private double batterySize;
  private double stateOfCharge;
  private double currentEfficiency;
  private double defaultEfficiency;

  private static final double MIN_BATTERY_SIZE = 10.0;
  private static final double MAX_BATTERY_SIZE = 150.0;
  private static final double MIN_EFFICIENCY = 0.5;
  private static final double MAX_EFFICIENCY = 4.5;
  private static final double MIN_SOC = 0.15;
  private static final double MAX_SOC = 1.0;

  /**
   * Constructs an ElectricVehicle with the given parameters.
   *
   * @param name             the name of the electric vehicle
   * @param batterySize      the battery size in kilowatt-hours (kWh)
   * @param stateOfCharge    the initial state of charge (between 0.15 and 1.0)
   * @param defaultEfficiency the default efficiency of the vehicle
   */
  public ElectricVehicle(
          String name, double batterySize, double stateOfCharge, double defaultEfficiency) {
    this.name = (name != null && !name.isEmpty()) ? name : "unknown EV";
    this.batterySize = clamp(batterySize, MIN_BATTERY_SIZE, MAX_BATTERY_SIZE);
    this.stateOfCharge = clamp(stateOfCharge, MIN_SOC, MAX_SOC);
    this.defaultEfficiency = clamp(defaultEfficiency, MIN_EFFICIENCY, MAX_EFFICIENCY);
    this.currentEfficiency = this.defaultEfficiency;
  }

  /**
   * Calculates the current range of the electric vehicle.
   *
   * @return the current range in miles
   */
  public double range() {
    return currentEfficiency * stateOfCharge * batterySize;
  }

  /**
   * Updates the current efficiency based on the given temperature.
   *
   * @param currentTemp the current temperature in Fahrenheit
   */
  public void updateEfficiency(double currentTemp) {
    if (currentTemp >= 65.0 && currentTemp <= 77.0) {
      currentEfficiency = defaultEfficiency;
    } else if (currentTemp > 77.0) {
      currentEfficiency = defaultEfficiency * 0.85;
    } else {
      double tempDiff = 65.0 - currentTemp;
      double efficiencyLoss = Math.min(tempDiff * 0.01, 0.5);
      currentEfficiency = defaultEfficiency * (1 - efficiencyLoss);
    }
  }

  /**
   * Gets the current efficiency of the electric vehicle.
   *
   * @return the current efficiency
   */
  public double getEfficiency() {
    return currentEfficiency;
  }

  /**
   * Gets the battery size of the electric vehicle.
   *
   * @return the battery size in kWh
   */
  public double getBatterySize() {
    return batterySize;
  }

  /**
   * Gets the current state of charge of the electric vehicle.
   *
   * @return the state of charge as a decimal value
   */
  public double getStateOfCharge() {
    return stateOfCharge;
  }

  /**
   * Gets the name of the electric vehicle.
   *
   * @return the name of the electric vehicle
   */
  public String getName() {
    return name;
  }

  /**
   * Sets a new state of charge for the electric vehicle.
   *
   * @param stateOfCharge the new state of charge (between 0.15 and 1.0)
   */
  public void setStateOfCharge(double stateOfCharge) {
    this.stateOfCharge = clamp(stateOfCharge, MIN_SOC, MAX_SOC);
  }

  /**
   * Clamps a value between a minimum and maximum.
   *
   * @param value the value to clamp
   * @param min   the minimum allowed value
   * @param max   the maximum allowed value
   * @return the clamped value
   */
  private double clamp(double value, double min, double max) {
    return Math.max(min, Math.min(max, value));
  }

  /**
   * Returns a string representation of the electric vehicle.
   *
   * @return a string describing the electric vehicle's name, state of charge, and range
   */
  @Override
  public String toString() {
    return String.format("%s SOC: %.1f%% Range (miles): %.1f", name, stateOfCharge * 100, range());
  }
}
