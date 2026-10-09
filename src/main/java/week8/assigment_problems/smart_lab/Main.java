package week8.assigment_problems.smart_lab;

import java.util.*;

interface Capability {
    String getName();
    String apply(String value);
}

class PowerCapability implements Capability {
    private boolean on = false;

    public String getName() {
        return "Power";
    }

    public String apply(String value) {
        if (!value.equalsIgnoreCase("ON") && !value.equalsIgnoreCase("OFF")) {
            return "Rejected: Power must be ON or OFF.";
        }

        on = value.equalsIgnoreCase("ON");
        return on ? "ON" : "OFF";
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 0;

    public String getName() {
        return "Brightness";
    }

    public String apply(String value) {
        try {
            int level = Integer.parseInt(value);

            if (level < 0 || level > 100) {
                return "Rejected: Brightness must be between 0 and 100%.";
            }

            brightness = level;
            return brightness + "%";
        } catch (NumberFormatException e) {
            return "Rejected: Invalid brightness value.";
        }
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 20;

    public String getName() {
        return "Temperature";
    }

    public String apply(String value) {
        try {
            int level = Integer.parseInt(value);

            if (level < 16 || level > 30) {
                return "Rejected: Temperature must be between 16°C and 30°C.";
            }

            temperature = level;
            return temperature + "°C";
        } catch (NumberFormatException e) {
            return "Rejected: Invalid temperature value.";
        }
    }
}

class Device {
    private String name;
    private Map<String, Capability> capabilities = new LinkedHashMap<>();

    Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCapability(Capability capability) {
        capabilities.put(capability.getName(), capability);
        System.out.println(name + ": " + capability.getName() + " capability added.");
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName);
    }

    public String apply(String capabilityName, String value) {
        Capability capability = capabilities.get(capabilityName);

        if (capability == null) {
            return null;
        }

        String result = capability.apply(value);

        if (result.startsWith("Rejected:")) {
            System.out.println(result);
            return result;
        }

        if (capabilityName.equals("Power")) {
            System.out.println(name + ": " + result + ".");
        } else {
            System.out.println(name + ": " + capabilityName.toLowerCase()
                    + " set to " + result + ".");
        }

        return result;
    }
}

class SceneStep {
    private String capabilityName;
    private String value;

    SceneStep(String capabilityName, String value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }

    public int execute(List<Device> devices) {
        int count = 0;

        for (Device device : devices) {
            if (device.hasCapability(capabilityName)) {
                String result = device.apply(capabilityName, value);

                if (result != null && !result.startsWith("Rejected:")) {
                    count++;
                }
            }
        }

        return count;
    }
}

class Scene {
    private String name;
    private List<SceneStep> steps = new ArrayList<>();

    Scene(String name) {
        this.name = name;
    }

    public void addStep(SceneStep step) {
        steps.add(step);
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");

        int count = 0;

        for (SceneStep step : steps) {
            count += step.execute(devices);
        }

        System.out.println("Scene '" + name
                + "' completed: " + count + " actions applied.");
    }
}

public class Main {
    public static void main(String[] args) {
        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = Arrays.asList(ac, lights, projector);

        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep(new SceneStep("Power", "ON"));
        lectureMode.addStep(new SceneStep("Brightness", "40"));
        lectureMode.addStep(new SceneStep("Temperature", "24"));

        lectureMode.execute(devices);

        String result = ac.apply("Temperature", "12");

        if (result.startsWith("Rejected:")) {
            System.out.println("Rejected: Lab AC temperature must be between 16°C and 30°C.");
        }

        projector.addCapability(new BrightnessCapability());
        projector.apply("Brightness", "70");
    }
}