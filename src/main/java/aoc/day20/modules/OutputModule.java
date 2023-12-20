package aoc.day20.modules;

import aoc.day20.ModuleWorld;
import aoc.day20.Pulse;
import aoc.day20.PulseType;

import java.util.List;

public class OutputModule extends CommunicationModule {

    public OutputModule(String name, ModuleWorld world) {
        super(name, world);
    }

    @Override
    protected void initState() {
        state.add(false);
    }

    @Override
    protected List<Pulse> calculatePulseResponse(Pulse pulse) {
        if (pulse.pulseType() == PulseType.LOW) {
            state.set(0, true);
        }
        return List.of();
    }

    @Override
    public CommunicationModule cloneToDifferentWorld(ModuleWorld mw) {
        return new OutputModule(getName(), mw);
    }

    public void clear() {
        state.set(0, false);
    }
}
