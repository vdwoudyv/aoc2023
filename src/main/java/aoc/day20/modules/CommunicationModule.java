package aoc.day20.modules;

import aoc.day20.ModuleWorld;
import aoc.day20.Pulse;

import java.util.ArrayList;
import java.util.List;

public abstract class CommunicationModule implements Comparable<CommunicationModule> {

    protected final List<CommunicationModule> outputModules = new ArrayList<>();
    protected final List<CommunicationModule> inputModules = new ArrayList<>();

    protected final List<Boolean> state = new ArrayList<>();
    private final String name;
    protected final ModuleWorld world;

    public CommunicationModule(String name, ModuleWorld world) {
        this.name = name;
        this.world = world;
    }

    public void addInputModule(CommunicationModule input) {
        inputModules.add(input);
        initState();
    }

    public void addOutputModule(CommunicationModule output) {
        outputModules.add(output);
        initState();
    }

    public List<Boolean> getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void processPulse(Pulse p) {
        world.addPulses(calculatePulseResponse(p));
    }

    public String exportState() {
        StringBuilder sb = new StringBuilder(name);
        for (boolean b: state) {
            sb.append(b ? "1" : "0");
        }
        return sb.toString();
    }

    protected abstract void initState();

    protected abstract List<Pulse> calculatePulseResponse(Pulse pulse);

    public int compareTo(CommunicationModule other) {
        return getName().compareTo(other.getName());
    }

    public List<CommunicationModule> getOutputModules() {
        return outputModules;
    }

    public List<CommunicationModule> getInputModules() {
        return inputModules;
    }

    public boolean canReach(CommunicationModule output) {
        if (output == this) {
            return true;
        } else {
            return outputModules.stream().anyMatch(m -> m.canReach(output));
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract CommunicationModule cloneToDifferentWorld(ModuleWorld mw);
}
