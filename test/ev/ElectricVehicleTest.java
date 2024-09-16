package ev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the ElectricVehicle class.
 * This class contains unit tests to verify the functionality of the ElectricVehicle class methods,
 * including edge cases and boundary conditions.
 */
public class ElectricVehicleTest {
  private ElectricVehicle ev;

  @BeforeEach
  void setUp() {
    ev = new ElectricVehicle("Ford MachE", 75.0, 0.5, 4.0);
  }

  /**
   * Tests that the constructor correctly initializes an ElectricVehicle object
   * with valid input parameters.
   */
  @Test
  void testConstructor() {
    assertNotNull(ev);
    assertEquals("Ford MachE", ev.getName());
    assertEquals(75.0, ev.getBatterySize());
    assertEquals(0.5, ev.getStateOfCharge());
    assertEquals(4.0, ev.getEfficiency());
  }

  /**
   * Tests that the constructor correctly handles invalid input parameters
   * by clamping values to their respective valid ranges and setting the default name.
   */
  @Test
  void testConstructorWithInvalidValues() {
    ElectricVehicle invalidEv = new ElectricVehicle("", 200.0, 1.5, 5.0);
    assertEquals("unknown EV", invalidEv.getName());
    assertEquals(150.0, invalidEv.getBatterySize());
    assertEquals(1.0, invalidEv.getStateOfCharge());
    assertEquals(4.5, invalidEv.getEfficiency());
  }

  /**
   * Tests that the range() method correctly calculates the vehicle's range
   * based on its current efficiency, state of charge, and battery size.
   */
  @Test
  void testRange() {
    assertEquals(150.0, ev.range(), 0.01);
  }

  /**
   * Tests that the updateEfficiency() method correctly maintains the efficiency
   * when the temperature is within the optimal range (65.0F to 77.0F).
   */
  @Test
  void testUpdateEfficiencyNormalTemperature() {
    ev.updateEfficiency(70.0);
    assertEquals(4.0, ev.getEfficiency());
  }

  /**
   * Tests that the updateEfficiency() method correctly reduces the efficiency
   * to 85% when the temperature is above 77.0F.
   */
  @Test
  void testUpdateEfficiencyHighTemperature() {
    ev.updateEfficiency(80.0);
    assertEquals(3.4, ev.getEfficiency(), 0.01);
  }

  /**
   * Tests that the updateEfficiency() method correctly reduces the efficiency
   * by 1% for each degree below 65.0F, up to a maximum of 50% reduction.
   */
  @Test
  void testUpdateEfficiencyLowTemperature() {
    ev.updateEfficiency(55.0);
    assertEquals(3.6, ev.getEfficiency(), 0.01);
  }

  /**
   * Tests that the setStateOfCharge() method correctly updates the state of charge
   * when given a valid value using the setter.
   */
  @Test
  void testSetStateOfCharge() {
    ev.setStateOfCharge(0.75);
    assertEquals(0.75, ev.getStateOfCharge());
  }

  /**
   * Tests that the setStateOfCharge() method correctly clamps the state of charge
   * to the maximum and minimum allowed values when given out-of-bounds values.
   */
  @Test
  void testSetStateOfChargeOutOfBounds() {
    ev.setStateOfCharge(1.5);
    assertEquals(1.0, ev.getStateOfCharge());

    ev.setStateOfCharge(0.1);
    assertEquals(0.15, ev.getStateOfCharge());
  }

  /**
   * Tests that the toString() method correctly formats the ElectricVehicle object's
   * information as a string, matching the assignments format.
   */
  @Test
  void testToString() {
    assertEquals("Ford MachE SOC: 50.0% Range (miles): 150.0", ev.toString());
  }

  /**
   * Tests the constructor with minimum valid values for all parameters.
   */
  @Test
  void testConstructorMinimumValues() {
    ElectricVehicle minEv = new ElectricVehicle("Min EV", 10.0, 0.15, 0.5);
    assertEquals("Min EV", minEv.getName());
    assertEquals(10.0, minEv.getBatterySize());
    assertEquals(0.15, minEv.getStateOfCharge());
    assertEquals(0.5, minEv.getEfficiency());
  }

  /**
   * Tests the constructor with maximum valid values for all params.
   */
  @Test
  void testConstructorMaximumValues() {
    ElectricVehicle maxEv = new ElectricVehicle("Max EV", 150.0, 1.0, 4.5);
    assertEquals("Max EV", maxEv.getName());
    assertEquals(150.0, maxEv.getBatterySize());
    assertEquals(1.0, maxEv.getStateOfCharge());
    assertEquals(4.5, maxEv.getEfficiency());
  }

  /**
   * Tests the constructor with a null name, expecting it to use the default name.
   */
  @Test
  void testConstructorNullName() {
    ElectricVehicle nullNameEv = new ElectricVehicle(null, 75.0, 0.5, 4.0);
    assertEquals("unknown EV", nullNameEv.getName());
  }

  /**
   * Tests the range calculation when the state of charge is at its minimum (15%).
   */
  @Test
  void testRangeMinimumStateOfCharge() {
    ev.setStateOfCharge(0.15);
    assertEquals(45.0, ev.range(), 0.01);
  }

  /**
   * Tests the range calculation when the state of charge is at its maximum (100%).
   */
  @Test
  void testRangeMaximumStateOfCharge() {
    ev.setStateOfCharge(1.0);
    assertEquals(300.0, ev.range(), 0.01);
  }

  /**
   * Tests the updateEfficiency method with the minimum possible temperature.
   */
  @Test
  void testUpdateEfficiencyMinimumTemperature() {
    ev.updateEfficiency(Double.MIN_VALUE);
    assertEquals(2.0, ev.getEfficiency(), 0.01); // 50% of default efficiency
  }

  /**
   * Tests the updateEfficiency method with the max possible temperature.
   */
  @Test
  void testUpdateEfficiencyMaximumTemperature() {
    ev.updateEfficiency(Double.MAX_VALUE);
    assertEquals(3.4, ev.getEfficiency(), 0.01); // 85% of default efficiency
  }

  /**
   * Tests the updateEfficiency method at the lower boundary of temperature range.
   */
  @Test
  void testUpdateEfficiencyLowerOptimalBoundary() {
    ev.updateEfficiency(65.0);
    assertEquals(4.0, ev.getEfficiency());
  }

  /**
   * Tests the updateEfficiency method at the upper boundary of the optimal temperature range given
   * in the instructions.
   */
  @Test
  void testUpdateEfficiencyUpperOptimalBoundary() {
    ev.updateEfficiency(77.0);
    assertEquals(4.0, ev.getEfficiency());
  }

  /**
   * Tests the toString method when the ends up being a whole number.
   */
  @Test
  void testToStringWholeNumberRange() {
    ElectricVehicle wholeRangeEv = new ElectricVehicle("Whole Range EV", 100.0, 1.0, 1.0);
    assertEquals("Whole Range EV SOC: 100.0% Range (miles): 100.0", wholeRangeEv.toString());
  }

  /**
   * Tests the toString method when range is a fractional #.
   */
  @Test
  void testToStringFractionalRange() {
    ElectricVehicle fractionalRangeEv = new ElectricVehicle("Fractional Range EV", 100.0, 0.333, 1.0);
    assertEquals("Fractional Range EV SOC: 33.3% Range (miles): 33.3", fractionalRangeEv.toString());
  }
}